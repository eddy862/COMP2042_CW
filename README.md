# COMP2042 Coursework

## Table of Contents
1. [Github Respository](#github-repository)
2. [Compilation Instructions](#compilation-instructions)
3. [Implemented and Working Properly](#implemented-and-working-properly)
4. [Implemented but Not Working Properly](#implemented-but-not-working-properly)
5. [Features Not Implemented](#features-not-implemented)
6. [New Java Classes](#new-java-classes)
7. [Modified Java Classes](#modified-java-classes)
8. [Unexpected Problems](#unexpected-problems)

## Github Repository
- Access here: [Repository Link](https://github.com/eddy862/COMP2042_CW)

## Compilation Instructions

### Prerequisites
1. Clone the repository:
    ```bash
    git clone https://github.com/eddy862/COMP2042_CW.git
    ```
2. Open the cloned repository in IntelliJ IDEA.
3. Install the [JavaFX Library](https://gluonhq.com/products/javafx/) and unzip it to a desired location.

### Setup
1. Go to **File > Project Structure** or press **Ctrl + Alt + Shift + S**.
2. In the **Project Settings > Project** section:
    - Set the project SDK to 21 or greater.
    - Set the language level as sdk default.
3. In the **Project Settings > Libraries** section:
    - Add a new project library that points to the `lib` folder of the JavaFX SDK you installed.
4. Ensure the JavaFX classes are recognized by IntelliJ IDEA.

### Running the Project
1. In the `src` folder, locate the `Main` class file in the `com.example.demo.controller` package.
2. Run the `main` method within the `Main` class.
3. Maven will build the project, and the application will start running.

## Implemented and Working Properly

### Fixed Bugs
1. **Fixed progression issues to level 2**: Changed the shield image format from .jpg to .png and added `timeline.stop()` when the level is completed.
2. **Ensured the shield follows the boss in level 2**: Tracked the boss's x and y positions using `getLayoutX() + getTranslateX()` and `getLayoutY() + getTranslateY()` and set the shield's x and y positions to the boss's coordinates using `setLayoutX()` and `setLayoutY()`.

### General Features
1. **User plane movement**: Enabled movement of the user plane both horizontally and vertically.

2. **Sound effects and music**:
   - **Sound effects**: User fires projectile, enemy destroyed, user's projectile hits enemy, enemy projectile destroyed, boss activates & deactivates shield, user's projectile hits boss's shield, next level, warning for approaching boundary, game over, and win.
   - **Music**: Main menu, gameplay.
   
3. **Menus and pages**: 
   - **Main menu**: Displayed the main menu with options to start the game, view the tutorial, control the volume, and exit the game.
   - **Tutorial**: Displayed the tutorial page with control instructions and descriptions of each level.
   - **Audio settings**: Displayed the audio settings page with options to mute/unmute sound effects and music.
   
4. **Pause functionality**: Enabled the user to pause the game, mute/unmute sound effects and music, resume the game, restart the game, and return to the main menu.

5. **Warning sign**: Displayed a warning sign and played a sound effect when an enemy plane approaches the leftmost boundary.

6. **User health indicator**: Made displayed hearts zoom in and out when the user's health is low.

7. **Fire rate limitation**: Limited the user's fire rate to save memory and prevent frame rate drops.

8. **Loading page**: Inserted a loading page between levels to hide latency during level transitions.

9. **Level completion menu**: Inserted a level completion menu with options to play the level again, return to the main menu, and proceed to the next level.

10. **Projectile cleanup**: Cleaned up user and enemy projectiles when they are off-screen to save memory.

11. **UI cleanup**: Cleaned up all children of the root of the scene when entering a new level to save memory.

12. **Final level options**: When the user loses or wins the final level, display buttons to restart from level 1 or return to the main menu.

13. **Pause Menu and Screen Blur**: When the game is paused, the game screen is blurred, and a pause menu is displayed on top of it.

14. **Explosion images**: Displayed explosion images when an enemy unit is destroyed.

### Level 1 Features
1. **Kill count display**: Displayed the number of kills in level 1.

### Level 2 Features
1. **Boss health bar**: Displayed the boss's health with a progress bar and text.

### Level 3 Features
1. **New level**: User has limited projectiles and must survive for a certain time. Projectiles increase every few seconds.
2. **Timer and remaining projectiles**: Added a timer to count down the time left for the user to survive and use projectile images to represent the remaining projectiles.

### Level 4 Features
1. **New level**: User must defeat a multi-stage boss with changing tactics and abilities:
    - **Stage 1**: Boss shoots projectiles at the user.
    - **Stage 2**: Boss shoots projectiles and summons enemy planes.
2. **Boss stage change**: Changed the boss's image and reset the health bar when the boss advanced to stage 2.

## Implemented but Not Working Properly

### Bugs

1. **Reinitializing Music and Sound Effects**: When the user returns to the main menu from the levels, the background music and sound effects are reinitialized, causing them to play again even if they were muted.
   - **Debugging Attempts**:
       - The goal is to maintain the music and sound effects globally, so they are not reinitialized when returning to the main menu.
       - Initially, I created a `Main` object `mainMenu` in the `LevelParent` and called `mainMenu.start()` when returning to the main menu, which caused the reinitialization.
       - The issue is that I do not have access to the `stage` in the `LevelParent` class, preventing me from setting the scene to the main menu.
       - I tried passing the `stage` as a parameter to the `LevelParent` class, but it caused numerous errors that I could not resolve

This is considered a minor bug in the game, so it has been left as is since it does not affect the game's functionality.

## Features Not Implemented
1. **Power-ups**: Randomly generated power-ups that increases the user's health, speed, or projectile damage when hit by the user's projectile.
2. **Enemy plane movement patterns**: Implemented different movement patterns for enemy planes, such as zigzag, circular, and random movements.
3. **Level selection**: Added a level selection page to choose a specific level to play.
4. **Settings**: Added a settings page to adjust the game's difficulty, such as enemy speed, projectile speed, and user health.

These features were not implemented due to time constraints as we also had to allocate time for documentation and testing, as well as other academic commitments.

## New Java Classes

| Class Name | File Path | Description |
|------------|------------|-------------|
| **MultiStageBoss** | `src/main/java/com/example/demo/actor/plane/MultiStageBoss.java` | Inherits from `Boss` and implements a multi-stage boss in Level Four with changing tactics and abilities. |
| **Music** | `src/main/java/com/example/demo/audio/Music.java` | Manages background music across different levels and menus. |
| **SoundEffect** | `src/main/java/com/example/demo/audio/SoundEffect.java` | Handles various sound effects triggered by game events, such as firing projectiles and explosions. |
| **LevelViewLevelOne** | `src/main/java/com/example/demo/level/view/LevelViewLevelOne.java` | Manages the visual representation and UI elements specific to Level One. |
| **LevelThree** | `src/main/java/com/example/demo/level/LevelThree.java` | Implements the third level of the game, where the user has limited projectiles and must survive for a certain time. |
| **LevelViewLevelThree** | `src/main/java/com/example/demo/level/view/LevelViewLevelThree.java` | Manages the visual representation and UI elements specific to Level Three. |
| **LevelFour** | `src/main/java/com/example/demo/level/LevelFour.java` | Implements the fourth level of the game, featuring a multi-stage boss with changing tactics. |
| **LevelViewLevelFour** | `src/main/java/com/example/demo/level/view/LevelViewLevelFour.java` | Manages the visual representation and UI elements specific to Level Four. |
| **BossHealthDisplay** | `src/main/java/com/example/demo/ui/inGameElement/BossHealthDisplay.java` | Displays the health bar of the boss during gameplay. |
| **ExplosionImage** | `src/main/java/com/example/demo/ui/inGameElement/ExplosionImage.java` | Displays explosion animations when an enemy unit is destroyed. |
| **LevelCompletionMenu** | `src/main/java/com/example/demo/ui/inGameElement/LevelCompletionMenu.java` | Provides options to replay the level, return to the main menu, or proceed to the next level upon level completion for all levels except the final level. |
| **NumberOfKillsLabel** | `src/main/java/com/example/demo/ui/inGameElement/NumberOfKillsLabel.java` | Displays the number of kills achieved by the user in Level One. |
| **PauseButton** | `src/main/java/com/example/demo/ui/inGameElement/PauseButton.java` | Represents the button used to pause the game across all levels. |
| **PauseMenu** | `src/main/java/com/example/demo/ui/inGameElement/PauseMenu.java` | Displays the pause menu with options to resume the game, restart, or return to the main menu. |
| **PostLevelButtons** | `src/main/java/com/example/demo/ui/inGameElement/PostLevelButtons.java` | Displays buttons to restart from Level One or return to the main menu after the final level. |
| **WarningImage** | `src/main/java/com/example/demo/ui/inGameElement/WarningImage.java` | Displays a warning sign when an enemy plane approaches the leftmost boundary. |
| **UserProjectileDisplay** | `src/main/java/com/example/demo/ui/inGameElement/UserProjectileDisplay.java` | Displays the remaining projectiles available to the user in Level Three. |
| **LoadingPage** | `src/main/java/com/example/demo/ui/page/LoadingPage.java` | Displays a loading screen between levels to hide latency during transitions. |
| **SettingPage** | `src/main/java/com/example/demo/ui/page/SettingPage.java` | Displays the audio settings page with options to mute/unmute sound effects and music. |
| **TutorialPage** | `src/main/java/com/example/demo/ui/page/TutorialPage.java` | Displays the tutorial page with control instructions and descriptions of each level. |

## Modified Java Classes

1. **Boss.java**:
   - Added `isShieldActive()` method to check if the boss's shield is active.

2. **EnemyPlane.java**:
   - Added `inWarningArea` and `WARNING_AREA_X_BOUNDARY` fields.
   - Added `updateWarningState()` method to check if the enemy plane is in the warning area.
   - Added `getInWarningArea()` method to get the value of `inWarningArea`.

3. **UserPlane.java**:
   - Added `X_UPPER_BOUND` and `X_LOWER_BOUND` constants to define the user plane's horizontal movement boundaries.
   - Added `horizontalVelocityMultiplier` field to adjust the user plane's horizontal movement speed.
   - Updated `updatePosition()` method to limit the user plane's horizontal movement within the defined boundaries.
   - Added `moveLeft()`, `moveRight()`, and `stopHorizontally()` methods to handle the user plane's horizontal movement.
   - Renamed the initial `stop()` method to `stopVertically()` to handle the user plane's vertical movement.

4. **ActiveActor.java**:
   - Added `changeImage()` method to change the actor's image based on the current state, mainly used by the multi-stage boss when advancing to the next stage.

5. **ActiveActorDestructible.java**:
   - Removed the `setDestroyed()` method as it is not needed.

6. **Controller.java**:
   - Initialized `music` and `soundEffect` objects to manage background music and sound effects across different levels and menus.
   - In the `gotoLevel()` method, inserted a `loadingPage` object to display a loading screen before the next level is initialized to hide latency during transitions.

7. **Main.java**:
   - Added `initializeScene()` method to initialize the main menu scene and set the stage's scene to the main menu.
   - Added `startLevelOne()` method for a button to start Level One and set the stage's scene to Level One.
   - Added `startTutorial()` method for a button to view the tutorial and set the stage's scene to the tutorial page.
   - Added `startSettings()` method for a button to view the audio settings and set the stage's scene to the settings page.
   - Added `backgroundPlayer` field and initialized it in the `start()` method to manage the background video in the main menu.

8. **LevelParent.java**:
   - Added `upperRoot` and `lowerRoot` `Group` objects to manage the game screen and pause menu separately to blur the game screen when the game is paused.
   - Moved `music` and `soundEffect` objects from the `Controller` class to manage background music and sound effects across different levels.
   - Initialized `mainMenu` object to manage the main menu scene and set the stage's scene to the main menu.
   - Initialized `pauseButton` and `pauseMenu` objects to manage the pause button and pause menu across all levels.
   - Initialized `postLevelButtons` object to manage the buttons displayed after game over or winning the final level.
   - Changed `initializeFriendlyUnits()` from abstract to concrete method as only the user plane is a friendly unit.
   - In `initializeScene()` method, added warning and explosion image pools as well as the pause button and pause menu to the scene.
   - Added `levelCompleted()` method to handle level completion before the user proceeds to the next level, stop the playing music, play the level completed sound effect, and display the level completion menu.
   - Added methods to play and stop background music in `startGame()` and `levelCompleted()`.
   - Created `replayLevel()` method to replay the current level and used this method in `goToNextLevel()` with sound effect.
   - In `initializeBackground()` method, added new keys to control the user's movement, fire projectiles, and pause the game.
   - Added `FIRE_COOLDOWN_MILLIS` and `lastFireTime` fields, and used them in `fireProjectile()` method to limit the user's fire rate.
   - Added `removeDestroyedEnemyUnits()` to handle explosion and warning image visibility as well as keep track of the number of kills.
   - Changed the return type of `removeDestroyedActors()` method from `void` to `List<ActiveActorDestructible>` to return the list of destroyed actors.
   - In `handleUserProjectileCollisions()` method, added sound effect when the user's projectile hits the enemy plane.
   - Added `handleUserAndEnemyProjectileCollisions()` to handle collisions between the user's and enemy's projectiles and play sound effects.
   - Changed the return type of `handleCollisions()` from `void` to `ActiveActorDestructible` to return the collided actor.
   - Renamed `handleEnemyPenetration()` method to `handleActorPenetration()` to handle both enemy (play sound effect) and user projectiles (remove from scene) penetration.
   - Added `updateWarningImage()` method to update the warning image's visibility based on the enemy plane's position and play or pause the warning sound effect. Added this method to `updateLevelView()`.
   - In `winGame()` and `loseGame()` methods, stopped the playing music, played the game over or win sound effect, displayed the game over or win screen, and displayed the post-level buttons.
   - Changed `getRoot()` to `getLowerRoot()` method to return the `lowerRoot` object which contains all game elements except the pause menu.
   - Removed `updateNumberOfEnemies()` as it is not needed.
   - Added `pauseGame()`, `resumeGame()`, and `returnToMenu()` methods to handle game pause, resume, and return to the main menu.
   - Added `checkUserHealth()` method to check the user's health and make the hearts zoom in and out when the user's health is low.
   - Added an abstract `showLevelCompletedMenu()` to initialize the level completion menu for each level as the next level is different.
   - Added `getTimeline()` method to return the timeline object.
   - Added `setLevelCompleted()` method to set the level completion status.
   - Added `cleanUp()` method to clean up all children of the root of the scene when entering a new level.
   - Added `isGameOver()` method to check if the game is over.
   - Added `getEnemyUnits()` method to return the list of enemy units.
   - Added `isGameWin()` method to check if the game is won.

9. **LevelOne.java**:
   - Overridden `initializeScene()` method to add the number of kills label to the scene.
   - Overridden `updateLevelView()` method to update the number of kills label.
   - Implemented `showLevelCompletedMenu()` method to initialize the level completion menu for Level One.

10. **LevelTwo.java**:
    - Added `BOSS_HEALTH` constant to define the boss's health and used it when initializing the boss.
    - Added `soundEffect` object to manage the sound effects of activating and deactivating the boss's shield.
    - Added `isBossShielded` field to check if the boss's shield is active.
    - In `checkIfGameOver()`, hid the boss's health bar when the boss is destroyed.
    - Overridden `initializeScene()` method to add the boss's health bar and shield to the scene.
    - Overridden `updateLevelView()` method to update the boss's health bar and shield.
    - Implemented `showLevelCompletedMenu()` method to initialize the level completion menu for Level Two.
    - Added `getBoss()` method to return the boss object.

11. **LevelView.java**:
    - Added `explosionPool` field, `initializeExplosionPool()` method to initialize the explosion images pool, `showExplosion()` method to display the explosion images when an enemy unit is destroyed, and `getAvailableExplosion()` method to return an available explosion image.
    - Added `warningImageMap` and `warningImagePool` fields, `initializeWarningImagePool()` method to initialize the warning images pool, `showWarning()` method to display the warning image when an enemy plane approaches the leftmost boundary, `hideWarning()` to hide the warning image, and `getAvailableWarningImage()` method to return an available warning image.
    - Added `totalEnemiesPerTime` field to define the number of explosions and warning images to be initialized.
    - Added `heartsStartZooming()` method to make the hearts zoom in and out when the user's health is low, and `heartsStopZooming()` to stop the zooming effect.
    - Added `getRoot()`, `getHeartDisplay()`, `getWinImage()`, `getGameOverImage()`, `getExplosionPool()`, and `getWarningImageMap()` methods to return the corresponding objects for testing purposes.

12. **LevelViewLevelTwo.java**:
    - Renamed `addImagesToRoot()` to `displayShield()` method to display the boss's shield.
    - Added `bossHealthDisplay` field, `showBossHealth()` method to add the boss's health bar to the scene, `updateBossHealth()` method to update the percentage of the boss's health, `updateBossHealthPosition()` method to update the boss's health bar position, and `hideBossHealth()` method to hide the boss's health bar.
    - Added `getBossHealthDisplay()` and `getShieldImage()` methods to return the corresponding objects for testing purposes.
    - Removed `SHIELD_X_POSITION` and `SHIELD_Y_POSITION` constants as they can be calculated based on the boss's position.

13. **GameOverImage.java**:
    - Added `HEIGHT`, `X_POSITION`, and `Y_POSITION` constants and initialized the image with these values in the constructor.

14. **WinImage.java**:
    - Added `X_POSITION` and `Y_POSITION` constants and removed the `xPosition` and `yPosition` parameters from the constructor.
    - Removed the `showWinImage()` method and the visibility property in the constructor as it is not needed.

15. **HeartDisplay.java**:
    - Added `CONTAINER_X_POSITION` and `CONTAINER_Y_POSITION` constants and removed the `xPosition` and `yPosition` parameters from the constructor.
    - Added `isZooming` field to check if the hearts are zooming in and out, `createZoomTransition()` method to create the zooming effect, and `startZooming()` and `stopZooming()` methods to start and stop the zooming effect. Added `isZooming()` method to return the value of `isZooming`.

16. **ShieldImage.java**:
    - Removed the `xPosition` and `yPosition` parameters from the constructor as the image position can be calculated based on the boss's position.

## Unexpected Problems

### Bugs
1. **Scene Alignment**: When switching between pages in the main menu, the new page's scene does not fit perfectly within the stage, leaving the top-left corner blank. For example, when switching from the main menu to the tutorial page, the tutorial page is not centered in the stage. Similarly, when switching back from the tutorial page to the main menu, the main menu is not centered in the stage.
    - **Debugging Attempts**:
        - Observed that the initial main menu scene is centered in the stage, but the problem occurs when switching to new pages.
        - Printed the x and y coordinates of the scene in the stage using `getX()` and `getY()` to check if the scene is centered.
        - Found that the x and y coordinates remain the same when switching pages, but visually the scene is not centered like the initial scene.
        - Tried setting the width and height of the new scene to match the previous scene's dimensions instead of the stage's dimensions, but the issue persisted.

2. **User plane, projectile, enemy plane and boss dimensions**: The collision detection was triggered even when the projectile did not visually hit the user plane, enemy plane, or boss.
    - **Debugging Attempts**:
        - Used an image editor to inspect the dimensions of the images and discovered that the white space around the images was significantly larger than the actual image.
        - Removed the unnecessary white space from the images and tested the game again. The issue was resolved.

3. **Main menu background video**: The background video in the main menu sometimes was not displayed after user returns from the levels.
    - **Debugging Attempts**:
        - Checked the code to ensure that the background video is set correctly when initializing the main menu scene.
        - set add a listener to the MediaPlayer to print the status of the media player and found that the media player was not playing the video after returning from the levels.
        - set the media player as a class variable and initialized it in the `start()` to ensure that the media player is not null when returning from the levels. However, the issue persisted.

4. **Warning Sign**: Displaying multiple warning signs for approaching enemy planes caused frame rate drops significantly.
    - **Debugging Attempts**:
        - Initially, created a new warning image for each enemy plane which I suspected caused the frame rate drops.
        - Switched to a pool of reusable warning images, but the issue persisted.
        - Reduced the number of initialized warning images using third-party software, then tested the game again. The issue was resolved.

5. **Pause Menu and Screen Blur**: The pause menu is blurred along with the game screen when the game is paused.
   - **Debugging Attempts**:
       - Initially, the `Group` object, `root` contained all game elements. Adding the pause menu to `root` and blurring the game screen caused the issue.
       - Created `upperRoot` for the pause menu and replaced `root` with `lowerRoot` for the game screen which contains all the in-game elements. Blurring `lowerRoot` while adding the pause menu to `upperRoot` resolved the issue.
       - Resuming the game removes the pause menu from `upperRoot` and restores the game screen.
    
