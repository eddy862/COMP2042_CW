package com.example.demo.level;

import java.util.*;

import com.example.demo.actor.ActiveActorDestructible;
import com.example.demo.actor.plane.EnemyPlane;
import com.example.demo.actor.plane.FighterPlane;
import com.example.demo.actor.plane.UserPlane;
import com.example.demo.audio.Music;
import com.example.demo.audio.SoundEffect;
import com.example.demo.controller.Main;
import com.example.demo.level.view.LevelView;
import com.example.demo.ui.inGameElement.LevelCompletionMenu;
import com.example.demo.ui.inGameElement.PauseButton;
import com.example.demo.ui.inGameElement.PauseMenu;
import com.example.demo.ui.inGameElement.PostLevelButtons;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Abstract class representing a parent level in the game.
 * This class provides common functionality for all levels, including handling user input, updating the scene, and managing game state.
 */
public abstract class LevelParent extends Observable {

    /**
     * The adjustment to the screen height to account for the height of the hearts display to prevent overlap.
     */
    private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
    /**
     * The delay between each frame in milliseconds.
     */
    private static final int MILLISECOND_DELAY = 50;
    /**
     * The height of the screen.
     */
    private final double screenHeight;
    /**
     * The width of the screen.
     */
    private final double screenWidth;
    /**
     * The maximum Y position for enemies.
     */
    private final double enemyMaximumYPosition;
    /**
     * The time between each fire in milliseconds.
     */
    private static final int FIRE_COOLDOWN_MILLIS = 200; // time between each fire in milliseconds
    /**
     * The last time a fire occurred in milliseconds.
     */
    private long lastFireTime; // time of last fire in milliseconds
    /**
     * The threshold for low health of the player.
     */
    private static final int USER_LOW_HEALTH_THRESHOLD = 3;

    /**
     * The lower group that contains all the game elements except the pause menu.
     */
    private final Group lowerRoot;
    /**
     * The upper group that contains the pause menu.
     */
    private final Group upperRoot;
    /**
     * The timeline for the game loop.
     */
    private final Timeline timeline;
    /**
     * The user plane in the game.
     */
    private final UserPlane user;
    /**
     * The scene for the level.
     */
    private final Scene scene;
    /**
     * The background image for the level.
     */
    private final ImageView background;

    /**
     * The list of friendly units in the game.
     */
    private final List<ActiveActorDestructible> friendlyUnits;
    /**
     * The list of enemy units in the game.
     */
    private final List<ActiveActorDestructible> enemyUnits;
    /**
     * The list of user projectiles in the game.
     */
    private final List<ActiveActorDestructible> userProjectiles;
    /**
     * The list of enemy projectiles in the game.
     */
    private final List<ActiveActorDestructible> enemyProjectiles;

    /**
     * The level view for the level.
     */
    private final LevelView levelView;
    /**
     * The sound effect manager passed from the main class.
     */
    private final SoundEffect soundEffect;
    /**
     * The music manager passed from the main class.
     */
    private final Music music;
    /**
     * The main menu for the game, serving as entry point to the game.
     */
    private final Main mainMenu = new Main();
    /**
     * The pause button for the game.
     */
    private final PauseButton pauseButton;
    /**
     * The pause menu for the game.
     */
    private PauseMenu pauseMenu;
    /**
     * The flag to check if the game is paused.
     */
    private boolean isPause = false;
    /**
     * The flag to check if the level is complete (user win), for level that is not the final level.
     */
    private boolean levelComplete = false;
    /**
     * The flag to check if the player has low health.
     */
    private boolean playerLowHealth = false;
    /**
     * The post level buttons for the game displayed with win or game over image.
     */
    private final PostLevelButtons postLevelButtons;
    /**
     * The flag to check if the game is over.
     */
    private boolean isGameOver = false;
    /**
     * The flag to check if the game is won.
     */
    private boolean isGameWin = false;

    /**
     * Constructs a LevelParent object with the specified parameters.
     *
     * @param backgroundImageName the name of the background image file
     * @param screenHeight        the height of the screen
     * @param screenWidth         the width of the screen
     * @param playerInitialHealth the initial health of the player
     * @param music               the music manager
     * @param soundEffect         the sound effect manager
     */
    public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth, Music music, SoundEffect soundEffect) {
        this.lowerRoot = new Group();
        this.upperRoot = new Group();
        Group root = new Group();
        root.getChildren().addAll(lowerRoot, upperRoot);
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
        initializeTimeline();
        friendlyUnits.add(user);
        this.pauseButton = new PauseButton(this::pauseGame);
        this.postLevelButtons = new PostLevelButtons(getScreenWidth(), this::returnToMenu, () -> replayLevel("com.example.demo.level.LevelOne"));
    }

    /**
     * Initializes the friendly units in the game.
     */
    private void initializeFriendlyUnits() {
        getLowerRoot().getChildren().add(getUser());
    }


    /**
     * Abstract method to check if the game is over.
     * This method should be implemented by subclasses to define specific game over conditions.
     */
    protected abstract void checkIfGameOver();

    /**
     * Abstract method to spawn enemy units.
     * This method should be implemented by subclasses to define specific enemy spawning behavior.
     */
    protected abstract void spawnEnemyUnits();

    /**
     * Abstract method to instantiate the level view.
     * This method should be implemented by subclasses to define specific level view instantiation.
     *
     * @return the instantiated level view
     */
    protected abstract LevelView instantiateLevelView();

    /**
     * Initializes the scene for the level.
     *
     * @return the initialized scene
     */
    public Scene initializeScene() {
        initializeBackground();
        initializeFriendlyUnits();
        levelView.showHeartDisplay();
        levelView.initialiseWarningPool();
        levelView.initialiseExplosionPool();
        lowerRoot.getChildren().add(pauseButton);
        lowerRoot.getChildren().add(postLevelButtons.getLayout());
        return scene;
    }

    /**
     * Starts the game by playing background music and starting the timeline.
     */
    public void startGame() {
        music.playGameBackgroundMusic();
        background.requestFocus();
        timeline.play();
    }

    /**
     * Transitions to the next level.
     *
     * @param levelName the name of the next level
     */
    public void goToNextLevel(String levelName) {
        soundEffect.playNextLevel();
        replayLevel(levelName);
    }

    /**
     * Replays the current level.
     *
     * @param levelName the name of the current level
     */
    protected void replayLevel(String levelName) {
        cleanUp();
        notifyObservers(levelName);
    }

    /**
     * Marks the level as completed and shows the level completed menu.
     */
    protected void levelCompleted() {
        setLevelComplete();
        soundEffect.pauseWarning();
        soundEffect.playWin();
        music.stopGameBackgroundMusic();
        timeline.stop();
        setChanged();
        LevelCompletionMenu levelCompletionMenu = showLevelCompletedMenu();
        lowerRoot.getChildren().add(levelCompletionMenu.getLayout());
    }

    /**
     * Updates the scene by performing various game actions such as spawning enemies, updating actors, and handling collisions.
     */
    protected void updateScene() {
        spawnEnemyUnits();
        updateActors();
        generateEnemyFire();
        handleActorPenetration();
        handleUserProjectileCollisions();
        handleEnemyProjectileCollisions();
        handleUserAndEnemyProjectileCollisions();
        handlePlaneCollisions();
        removeAllDestroyedActors();
        updateLevelView();
        checkUserHealth();
        checkIfGameOver();
    }

    /**
     * Initializes the timeline for the game loop.
     */
    private void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene()); // define how much delay between each frame, and what to do in each frame
        timeline.getKeyFrames().add(gameLoop); // add the frame to the timeline
    }

    /**
     * Initializes the background image and sets up key event handlers for user input.
     */
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
        lowerRoot.getChildren().add(background);
    }

    /**
     * Fires a projectile from the user's plane if the fire cooldown has elapsed.
     */
    protected void fireProjectile() {
        long currentTime = System.currentTimeMillis();
        // check if enough time has passed since the last fire
        if (currentTime - lastFireTime > FIRE_COOLDOWN_MILLIS || lastFireTime == 0) {
            ActiveActorDestructible projectile = user.fireProjectile();
            lowerRoot.getChildren().add(projectile);
            userProjectiles.add(projectile);
            soundEffect.playUserFire();
            lastFireTime = currentTime;
        }
    }

    /**
     * Generates enemy fire by spawning projectiles from enemy units.
     */
    private void generateEnemyFire() {
        enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Spawns an enemy projectile and adds it to the scene.
     *
     * @param projectile the enemy projectile to spawn
     */
    private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
        if (projectile != null) {
            lowerRoot.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Updates all actors in the game.
     */
    private void updateActors() {
        friendlyUnits.forEach(ActiveActorDestructible::updateActor);
        enemyUnits.forEach(ActiveActorDestructible::updateActor);
        userProjectiles.forEach(ActiveActorDestructible::updateActor);
        enemyProjectiles.forEach(ActiveActorDestructible::updateActor);
    }

    /**
     * Removes all destroyed actors from the scene and their respective lists.
     */
    private void removeAllDestroyedActors() {
        removeDestroyedActors(friendlyUnits);
        removeDestroyedActors(userProjectiles);
        removeDestroyedActors(enemyProjectiles);
        removeDestroyedEnemyUnits();
    }

    /**
     * Removes destroyed enemy units and updates the level view.
     */
    private void removeDestroyedEnemyUnits() {
        for (ActiveActorDestructible enemy : removeDestroyedActors(enemyUnits)) {
            levelView.showExplosion(enemy);
            levelView.hideWarning(enemy);
            if (enemy instanceof EnemyPlane) {
                user.incrementKillCount();
            }
        }
    }

    /**
     * Removes destroyed actors from the specified list and the scene.
     *
     * @param actors the list of actors to check for destruction
     * @return the list of destroyed actors
     */
    private List<ActiveActorDestructible> removeDestroyedActors(List<ActiveActorDestructible> actors) {
        List<ActiveActorDestructible> destroyedActors = actors.stream().filter(ActiveActorDestructible::isDestroyed).toList();
        lowerRoot.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);

        return destroyedActors;
    }

    /**
     * Handles collisions between friendly and enemy planes.
     */
    private void handlePlaneCollisions() {
        handleCollisions(friendlyUnits, enemyUnits);
    }

    /**
     * Handles collisions between user projectiles and enemy units.
     */
    private void handleUserProjectileCollisions() {
        ActiveActorDestructible enemyHit = handleCollisions(userProjectiles, enemyUnits);
        if (enemyHit != null) soundEffect.playEnemyHit(enemyHit);
    }

    /**
     * Handles collisions between enemy projectiles and friendly units.
     */
    private void handleEnemyProjectileCollisions() {
        if (handleCollisions(enemyProjectiles, friendlyUnits) != null) {
            soundEffect.playUserHit();
        }
    }

    /**
     * Handles collisions between user and enemy projectiles.
     */
    private void handleUserAndEnemyProjectileCollisions() {
        if (handleCollisions(userProjectiles, enemyProjectiles) != null) {
            soundEffect.playEnemyProjectileDestroyed();
        }
    }

    /**
     * Handles collisions between two lists of actors.
     *
     * @param actors1 the first list of actors
     * @param actors2 the second list of actors
     * @return the actor that was hit, or null if no collision occurred
     */
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
     * Handles actors that have penetrated the defenses by checking if they have passed the screen width.
     */
    private void handleActorPenetration() {
        for (ActiveActorDestructible enemy : enemyUnits) {
            if (actorHasPenetratedDefenses(enemy)) {
                user.takeDamage();
                enemy.destroy();
                soundEffect.playUserHit();
            }
        }

        for (ActiveActorDestructible projectile : enemyProjectiles) {
            if (actorHasPenetratedDefenses(projectile)) {
                projectile.destroy();
            }
        }

        for (ActiveActorDestructible projectile : userProjectiles) {
            if (actorHasPenetratedDefenses(projectile)) {
                projectile.destroy();
            }
        }
    }

    /**
     * Updates the level view based on the current game state.
     */
    protected void updateLevelView() {
        levelView.removeHearts(user.getHealth());
        updateWarningImage();
    }

    /**
     * Updates the warning image based on the position of enemy planes.
     */
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

    /**
        * Checks if an actor has penetrated the defenses by checking if it has passed the screen width.
        *
        * @param actor the actor to check
        * @return true if the actor has penetrated the defenses, false otherwise
        */
    private boolean actorHasPenetratedDefenses(ActiveActorDestructible actor) {
        return Math.abs(actor.getTranslateX()) > screenWidth;
    }

    /**
     * Handles the win game scenario by stopping the game and showing the win image.
     */
    protected void winGame() {
        isGameWin = true;
        soundEffect.stopWarning();
        music.stopGameBackgroundMusic();
        timeline.stop();
        levelView.showWinImage();
        soundEffect.playWin();
        setLevelComplete();
        postLevelButtons.show();
        setChanged();
    }

    /**
     * Handles the game over scenario by stopping the game and showing the game over image.
     */
    protected void loseGame() {
        isGameOver = true;
        soundEffect.stopWarning();
        music.stopGameBackgroundMusic();
        timeline.stop();
        levelView.showGameOverImage();
        soundEffect.playGameOver();
        setLevelComplete();
        postLevelButtons.show();
        setChanged();
    }

    /**
     * Returns the user plane.
     *
     * @return the user plane
     */
    protected UserPlane getUser() {
        return user;
    }

    /**
     * Returns the root group of the scene.
     *
     * @return the root group
     */
    protected Group getLowerRoot() {
        return lowerRoot;
    }

    /**
     * Returns the current number of enemies.
     *
     * @return the current number of enemies
     */
    protected int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    /**
     * Adds an enemy unit to the scene.
     *
     * @param enemy the enemy unit to add
     */
    protected void addEnemyUnit(ActiveActorDestructible enemy) {
        enemyUnits.add(enemy);
        lowerRoot.getChildren().add(enemy);
    }

    /**
     * Returns the maximum Y position for enemies.
     *
     * @return the maximum Y position for enemies
     */
    protected double getEnemyMaximumYPosition() {
        return enemyMaximumYPosition;
    }

    /**
     * Returns the screen width.
     *
     * @return the screen width
     */
    protected double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the screen height.
     *
     * @return the screen height
     */
    protected double getScreenHeight() {
        return screenHeight;
    }

    /**
     * Checks if the user plane is destroyed.
     *
     * @return true if the user plane is destroyed, false otherwise
     */
    protected boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Pauses the game and shows the pause menu.
     */
    private void pauseGame() {
        if (!isPause && !levelComplete) {
            lowerRoot.setEffect(new GaussianBlur(20));
            pauseMenu = new PauseMenu(this::resumeGame, this::returnToMenu, music, soundEffect, screenWidth, screenHeight);
            upperRoot.getChildren().add(pauseMenu.getLayout());
            isPause = true;
            timeline.pause();
            music.pauseGameBackgroundMusic();
            soundEffect.pauseWarning();
            if (playerLowHealth) {
                levelView.heartsStopZooming();
            }
        }
    }

    /**
     * Resumes the game from the paused state.
     */
    private void resumeGame() {
        if (isPause && !levelComplete) {
            lowerRoot.setEffect(null);
            pauseMenu.hide();
            upperRoot.getChildren().remove(pauseMenu.getLayout());
            isPause = false;
            timeline.play();
            music.playGameBackgroundMusic();
            if (playerLowHealth) {
                levelView.heartsStartZooming();
            }
        }
    }

    /**
     * Returns to the main menu.
     */
    protected void returnToMenu() {
        cleanUp();
        music.stopGameBackgroundMusic();
        soundEffect.stopWarning();
        timeline.stop();
        setChanged();
        mainMenu.start((Stage) lowerRoot.getScene().getWindow());
    }

    /**
     * Checks the user's health and updates the level view if the health is low.
     */
    private void checkUserHealth() {
        if (user.getHealth() <= USER_LOW_HEALTH_THRESHOLD && !playerLowHealth) {
            levelView.heartsStartZooming();
            playerLowHealth = true;
        } else if (user.getHealth() > USER_LOW_HEALTH_THRESHOLD && playerLowHealth) {
            levelView.heartsStopZooming();
        }
    }

    /**
     * Abstract method to show the level completed menu.
     * This method should be implemented by subclasses to define specific level completed menu behavior.
     *
     * @return the level completed menu
     */
    protected abstract LevelCompletionMenu showLevelCompletedMenu();

    /**
     * Returns the timeline for the game loop.
     *
     * @return the timeline
     */
    protected Timeline getTimeline() {
        return timeline;
    }

    /**
     * Sets the level as complete.
     */
    protected void setLevelComplete() {
        this.levelComplete = true;
    }

    /**
     * Cleans up the scene by removing all children from the root group to reduce memory usage.
     */
    private void cleanUp() {
        lowerRoot.getChildren().clear();
    }

    /**
     * Checks if the level is complete.
     *
     * @return true if the level is complete, false otherwise
     */
    protected boolean isLevelCompleted() {
        return levelComplete;
    }

    /**
     * Checks if the game is won.
     *
     * @return true if the game is won, false otherwise
     */
    protected boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    protected ArrayList<ActiveActorDestructible> getEnemyUnits() {
        return (ArrayList<ActiveActorDestructible>) enemyUnits;
    }

    /**
     * Checks if the game is won.
     *
     * @return true if the game is won, false otherwise
     */
    protected boolean isGameWin() {
        return isGameWin;
    }
}