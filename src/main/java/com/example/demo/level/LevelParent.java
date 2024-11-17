package com.example.demo.level;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.Boss;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.controller.Main;
import com.example.demo.ui.PauseButton;
import com.example.demo.ui.PauseMenu;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 50;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;
    private static final int FIRE_COOLDOWN_MILLIS = 200; // time between each fire in milliseconds
    private long lastFireTime; // time of last fire in milliseconds

    private final Group root;
    private final Timeline timeline;
    private final UserPlane user;
    private final Scene scene;
    private final ImageView background;

    private final List<ActiveActorDestructible> friendlyUnits;
    private final List<ActiveActorDestructible> enemyUnits;
    private final List<ActiveActorDestructible> userProjectiles;
    private final List<ActiveActorDestructible> enemyProjectiles;

    private int currentNumberOfEnemies;
    private final LevelView levelView;
    private final SoundEffect soundEffect;
    private final Music music;
    private final Main mainMenu = new Main();
    private final PauseButton pauseButton;
    private final PauseMenu pauseMenu;
    private boolean isPause = false;

    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Music music, SoundEffect soundEffect) {
        this.root = new Group();
        this.scene = new Scene(root, screenWidth, screenHeight);
        this.timeline = new Timeline();
        this.user = new UserPlane(playerInitialHealth);
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();

        this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
        this.levelView = instantiateLevelView();
        this.soundEffect = soundEffect;
        this.music = music;
        this.currentNumberOfEnemies = 0;
        initializeTimeline();
        friendlyUnits.add(user);
        this.pauseButton = new PauseButton(this::pauseGame);
        this.pauseMenu = new PauseMenu(this::resumeGame, this::returnToMenu, music, soundEffect);
    }

    protected abstract void initializeFriendlyUnits();

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    protected abstract void initialiseLevelScene();

    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        levelView.displayWarningImage();
        root.getChildren().add(pauseButton);
        root.getChildren().add(pauseMenu.getLayout());
        initialiseLevelScene();
        return scene;
    }

    public void startGame() {
        music.playGameBackgroundMusic();
        background.requestFocus();
        timeline.play();
    }

    public void goToNextLevel(String levelName) {
        soundEffect.pauseWarning();
        music.stopGameBackgroundMusic();
        soundEffect.playNextLevel();
        timeline.stop();
        setChanged();
        notifyObservers(levelName);
    }

    /**
     * Update the scene by spawning enemy units,
     * updating actors,
     * generating enemy fire,
     * updating the number of enemies,
     * handling enemy penetration of defenses,
     * handling user projectile collisions with enemies,
     * handling enemy projectile collisions with friendly units,
     * handling plane collisions with each other,
     * removing all destroyed actors,
     * updating the kill count,
     * updating the level view,
     * and checking if the game is over.
     */
    private void updateScene() {
        spawnEnemyUnits();
        updateActors();
        generateEnemyFire();
        updateNumberOfEnemies();
        handleEnemyPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleUserAndEnemyProjectileCollisions();
        handlePlaneCollisions();
        removeAllDestroyedActors();
        updateLevelView();
        checkIfGameOver();
    }

    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene()); // define how much delay between each frame, and what to do in each frame
        timeline.getKeyFrames().add(gameLoop); // add the frame to the timeline
    }

    private void initializeBackground() {
        background.setFocusTraversable(true);
        background.setFitHeight(screenHeight);
        background.setFitWidth(screenWidth);
        background.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP || kc == KeyCode.W) user.moveUp();
                if (kc == KeyCode.DOWN || kc == KeyCode.S) user.moveDown();
                if (kc == KeyCode.LEFT || kc == KeyCode.A) user.moveLeft();
                if (kc == KeyCode.RIGHT || kc == KeyCode.D) user.moveRight();
                if (kc == KeyCode.SPACE || kc == KeyCode.L) fireProjectile();
                if (kc == KeyCode.ESCAPE || kc == KeyCode.P) {
                    if (isPause) {
                        resumeGame();
                    } else {
                        pauseGame();
                    }
                }
            }
        });
        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP || kc == KeyCode.DOWN || kc == KeyCode.W || kc == KeyCode.S) user.stopVertically();
                if (kc == KeyCode.LEFT || kc == KeyCode.RIGHT || kc == KeyCode.A || kc == KeyCode.D)
                    user.stopHorizontally();
            }
        });
        root.getChildren().add(background);
    }

    private void fireProjectile() {
        long currentTime = System.currentTimeMillis();
        // check if enough time has passed since the last fire
        if (currentTime - lastFireTime > FIRE_COOLDOWN_MILLIS || lastFireTime == 0) {
            ActiveActorDestructible projectile = user.fireProjectile();
            root.getChildren().add(projectile);
            userProjectiles.add(projectile);
            soundEffect.playUserFire();
            lastFireTime = currentTime;
        }
    }

    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    private void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedActors(enemyUnits);
    }

    private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }

    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    private void handleUserProjectileCollisions() {
        ActiveActorDestructible enemyHit = handleCollisions(userProjectiles, enemyUnits);

        // check if boss is shielded and play the appropriate sound
        if (enemyHit != null) {
            if (enemyHit instanceof Boss) {
                if (((Boss) enemyHit).isShielded()) {
                    soundEffect.playShieldHit();
                } else {
                    soundEffect.playEnemyHit();
                }
            } else {
                soundEffect.playEnemyHit();
            }

            if (enemyHit instanceof EnemyPlane) {
                user.incrementKillCount();
            }
        }
    }

    private void handleEnemyProjectileCollisions() {
        if (handleCollisions(enemyProjectiles, friendlyUnits) != null) {
            soundEffect.playUserHit();
        }
    }

    private void handleUserAndEnemyProjectileCollisions() {
        if (handleCollisions(userProjectiles, enemyProjectiles) != null) {
            soundEffect.playEnemyProjectileDestroyed();
        }
    }

    private ActiveActorDestructible handleCollisions(List<ActiveActorDestructible> actors1,
                                                     List<ActiveActorDestructible> actors2) {
        for (ActiveActorDestructible actor : actors2) {
            for (ActiveActorDestructible otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                    return actor;
                }
            }
        }
        return null;
    }

    /**
     * Check if any enemy has penetrated the defenses and if so, destroy the user
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
                soundEffect.playUserHit();
            }
        }
    }

    protected abstract void updateSpecificLevelView();

    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        updateWarningImage();
        updateSpecificLevelView();
    }

    private void updateWarningImage() {
        List<EnemyPlane> warningEnemies = enemyUnits.stream()
                .filter(enemy -> enemy instanceof EnemyPlane && ((EnemyPlane) enemy).getInWarningArea())
                .map(enemy -> (EnemyPlane) enemy)
                .collect(Collectors.toList());

        if (!warningEnemies.isEmpty()) {
            EnemyPlane lastEnemy = warningEnemies.get(warningEnemies.size() - 1);
            levelView.showWarning(lastEnemy.getLayoutY());
            soundEffect.playWarning();
        } else {
            levelView.hideWarning();
            soundEffect.pauseWarning();
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    protected void winGame() {
        soundEffect.pauseWarning();
        music.stopGameBackgroundMusic();
        timeline.stop();
        levelView.showWinImage();
        soundEffect.playWin();
    }

    protected void loseGame() {
        soundEffect.pauseWarning();
        music.stopGameBackgroundMusic();
        timeline.stop();
        levelView.showGameOverImage();
        soundEffect.playGameOver();
    }

    protected UserPlane getUser() {
        return user;
    }

    protected Group getRoot() {
        return root;
    }

    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    protected double getScreenWidth() {
        return screenWidth;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }

    protected void pauseGame() {
        if (!isPause) {
            isPause = true;
            timeline.pause();
            pauseMenu.show();
            music.pauseGameBackgroundMusic();
            soundEffect.pauseWarning();
        }
    }

    protected void resumeGame() {
        if (isPause) {
            isPause = false;
            timeline.play();
            pauseMenu.hide();
            music.playGameBackgroundMusic();
        }
    }

    protected void returnToMenu() {
        music.stopGameBackgroundMusic();
        soundEffect.pauseWarning();
        timeline.stop();
        setChanged();
        mainMenu.start((Stage) root.getScene().getWindow());
    }
}
