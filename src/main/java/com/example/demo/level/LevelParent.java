package com.example.demo.level;

import java.util.*;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.controller.Main;
import com.example.demo.ui.LevelCompletedMenu;
import com.example.demo.ui.PauseButton;
import com.example.demo.ui.PauseMenu;
import com.example.demo.ui.PostLevelButtons;
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
    private static final int USER_LOW_HEALTH_THRESHOLD = 3;

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
    private PauseMenu pauseMenu;
    private boolean isPause = false;
    private boolean levelComplete = false;
    private boolean playerLowHealth = false;
    private final PostLevelButtons postLevelButtons;
    private int userFireCount = 0;

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
        this.postLevelButtons = new PostLevelButtons(getScreenWidth(), this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelOne"));
    }

    private void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    };

    protected abstract void checkIfGameOver();

    protected abstract void spawnEnemyUnits();

    protected abstract LevelView instantiateLevelView();

    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        levelView.initialiseWarningPool();
        levelView.initialiseExplosionPool();
        root.getChildren().add(pauseButton);
        root.getChildren().add(postLevelButtons.getLayout());
        return scene;
    }

    public void startGame() {
        music.playGameBackgroundMusic();
        background.requestFocus();
        timeline.play();
    }

    public void goToNextLevel(String levelName) {
        soundEffect.playNextLevel();
        replayLevel(levelName);
    }

    protected void replayLevel(String levelName) {
        notifyObservers(levelName);
    }

    protected void levelCompleted() {
        setLevelComplete();
        soundEffect.pauseWarning();
        soundEffect.playWin();
        music.stopGameBackgroundMusic();
        timeline.stop();
        setChanged();
        LevelCompletedMenu levelCompletedMenu = showLevelCompletedMenu();
        root.getChildren().add(levelCompletedMenu.getLayout());
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
    protected void updateScene() {
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
        checkUserHealth();
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
                if (!isPause && !levelComplete) {
                    if (kc == KeyCode.UP || kc == KeyCode.W) user.moveUp();
                    if (kc == KeyCode.DOWN || kc == KeyCode.S) user.moveDown();
                    if (kc == KeyCode.LEFT || kc == KeyCode.A) user.moveLeft();
                    if (kc == KeyCode.RIGHT || kc == KeyCode.D) user.moveRight();
                    if (kc == KeyCode.SPACE || kc == KeyCode.L) fireProjectile();
                }
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

    protected void fireProjectile() {
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
        friendlyUnits.forEach(ActiveActorDestructible::updateActor);
        enemyUnits.forEach(ActiveActorDestructible::updateActor);
        userProjectiles.forEach(ActiveActorDestructible::updateActor);
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
    }

    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedEnemyUnits();
    }

    private void removeDestroyedEnemyUnits() {
        for (ActiveActorDestructible enemy : removeDestroyedActors(enemyUnits)) {
            levelView.showExplosion(enemy);
            levelView.hideWarning(enemy);
            if (enemy instanceof EnemyPlane) {
                user.incrementKillCount();
            }
        }
    }

    private List<ActiveActorDestructible> removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed).toList();
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);

        return destroyedActors;
    }

    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    private void handleUserProjectileCollisions() {
        ActiveActorDestructible enemyHit = handleCollisions(userProjectiles, enemyUnits);
        if (enemyHit != null) soundEffect.playEnemyHit(enemyHit);
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

    private ActiveActorDestructible handleCollisions(List<ActiveActorDestructible> actors1, List<ActiveActorDestructible> actors2) {
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

    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        updateWarningImage();
    }

    private void updateWarningImage() {
        List<EnemyPlane> warningEnemies = enemyUnits.stream().filter(enemy -> enemy instanceof EnemyPlane && ((EnemyPlane) enemy).getInWarningArea()).map(enemy -> (EnemyPlane) enemy).toList();

        for (EnemyPlane enemy : warningEnemies) {
            levelView.showWarning(enemy);
        }

        if (!warningEnemies.isEmpty()) {
            soundEffect.playWarning();
        } else {
            soundEffect.pauseWarning();
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    protected void winGame() {
        soundEffect.stopWarning();
        music.stopGameBackgroundMusic();
        timeline.stop();
        levelView.showWinImage();
        soundEffect.playWin();
        setLevelComplete();
        postLevelButtons.show();
        setChanged();
    }

    protected void loseGame() {
        soundEffect.stopWarning();
        music.stopGameBackgroundMusic();
        timeline.stop();
        levelView.showGameOverImage();
        soundEffect.playGameOver();
        setLevelComplete();
        postLevelButtons.show();
        setChanged();
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

    protected double getScreenHeight() {
        return screenHeight;
    }

    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    private void updateNumberOfEnemies() {
        currentNumberOfEnemies = enemyUnits.size();
    }

    private void pauseGame() {
        if (!isPause && !levelComplete) {
            pauseMenu = new PauseMenu(this::resumeGame, this::returnToMenu, music, soundEffect, screenWidth, screenHeight);
            root.getChildren().add(pauseMenu.getLayout());
            isPause = true;
            timeline.pause();
            music.pauseGameBackgroundMusic();
            soundEffect.pauseWarning();
            if (playerLowHealth) {
                levelView.heartsStopZooming();
            }
        }
    }

    private void resumeGame() {
        if (isPause && !levelComplete) {
            pauseMenu.hide();
            root.getChildren().remove(pauseMenu.getLayout());
            isPause = false;
            timeline.play();
            music.playGameBackgroundMusic();
            if (playerLowHealth) {
                levelView.heartsStartZooming();
            }
        }
    }

    protected void returnToMenu() {
        music.stopGameBackgroundMusic();
        soundEffect.stopWarning();
        timeline.stop();
        setChanged();
        mainMenu.start((Stage) root.getScene().getWindow());
    }

    private void checkUserHealth() {
        if (user.getHealth() <= USER_LOW_HEALTH_THRESHOLD && !playerLowHealth) {
            levelView.heartsStartZooming();
            playerLowHealth = true;
        } else if (user.getHealth() > USER_LOW_HEALTH_THRESHOLD && playerLowHealth) {
            levelView.heartsStopZooming();
        }
    }

    protected abstract LevelCompletedMenu showLevelCompletedMenu();

    protected Timeline getTimeline() {
        return timeline;
    }

    protected void setLevelComplete() {
        this.levelComplete = true;
    }

    private void increaseUserFireCount() {
        userFireCount++;
    }

    protected int getUserFireCount() {
        return userFireCount;
    }
}
