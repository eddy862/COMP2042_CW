package com.example.demo.level;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actor.ActiveActor;
import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.Boss;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.actor.projectile.UserProjectile;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent extends Observable {

    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    private static final int MILLISECOND_DELAY = 50;
    private final double screenHeight;
    private final double screenWidth;
    private final double enemyMaximumYPosition;

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
    private LevelView levelView;
    private LevelAudio levelAudio;

    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
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
        this.levelAudio = new LevelAudio();
        this.currentNumberOfEnemies = 0;
        initializeTimeline();
        friendlyUnits.add(user);
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
        initialiseLevelScene();
        return scene;
    }

    public void startGame() {
        levelAudio.playBackgroundMusic();
        background.requestFocus();
        timeline.play();
    }

    public void goToNextLevel(String levelName) {
        levelAudio.stopBackgroundMusic();
        levelAudio.playNextLevel();
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
        updateKillCount();
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
                if (kc == KeyCode.UP) user.moveUp();
                if (kc == KeyCode.DOWN) user.moveDown();
                if (kc == KeyCode.SPACE) fireProjectile();
            }
        });
        background.setOnKeyReleased(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                KeyCode kc = e.getCode();
                if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
            }
        });
        root.getChildren().add(background);
    }

    private void fireProjectile() {
        ActiveActorDestructible projectile = user.fireProjectile();
        root.getChildren().add(projectile);
        userProjectiles.add(projectile);
        levelAudio.playUserFire();
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
        removeDestroyedActors(enemyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
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
                    levelAudio.playShieldHit();
                } else {
                    levelAudio.playEnemyHit();
                }
            } else {
                levelAudio.playEnemyHit();
            }
        }
    }

    private void handleEnemyProjectileCollisions() {
        if (handleCollisions(enemyProjectiles, friendlyUnits) != null) {
            levelAudio.playUserHit();
        }
    }

    private void handleUserAndEnemyProjectileCollisions() {
        if (handleCollisions(userProjectiles, enemyProjectiles) != null) {
            levelAudio.playEnemyProjectileDestroyed();
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
    };

    /**
     * Check if any enemy has penetrated the defenses and if so, destroy the user
     */
    private void handleEnemyPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
            }
        }
    }

    protected abstract void updateSpecificLevelView();

    private void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        updateSpecificLevelView();
    }

    private void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount();
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
        return Math.abs(enemy.getTranslateX()) > screenWidth;
    }

    protected void winGame() {
        levelAudio.stopBackgroundMusic();
        timeline.stop();
        levelView.showWinImage();
        levelAudio.playWin();
    }

    protected void loseGame() {
        levelAudio.stopBackgroundMusic();
        timeline.stop();
        levelView.showGameOverImage();
        levelAudio.playGameOver();
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
}
