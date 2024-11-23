# COMP2042 Coursework

## Table of Contents
1. [Github Respository](#github-repository)
2. [Compilation Instructions](#compilation-instructions)
3. [Implemented and Working Properly](#implemented-and-working-properly)
4. [Implemented but Not Working Properly](#implemented-but-not-working-properly)
5. [Feature Not Implemented](#feature-not-implemented)
6. [New Java Class](#new-java-class)
7. [Modified Java Class](#modified-java-class)
8. [Unexpected Problems](#unexpected-problems)

## Github Repository
- Access here: [Repository Link](https://github.com/eddy862/COMP2042_CW)

## Compilation Instructions
1. Clone the repository:
  ```bash
  git clone https://github.com/eddy862/COMP2042_CW.git
  ```
2. Open the cloned repository in Intellij
3. Go to **File > Project Structure** or press **Ctrl + Alt + Shift + S**
4. In the **Project Setting > Project** section, set the project SDK to 21 or greater and the language level to 11 or greater
5. Make sure to install [JavaFX Library](https://gluonhq.com/products/javafx/) and unzip to a desired location
6. In the **Project Setting > Libraries** section, set a new project library that point to the **lib** folder of the JavaFX SDK you installed
7. Now the JavaFX classes should be recognized by Intellij
7. In the **src** folder, locate **Main** class file in the **com.example.demo.controller** package and run the main method within the class
8. Maven will build the project and the application will be running

## Implemented and Working Properly

### Fixed Bugs
1. **Fixed progression issues to level 2**: Changed the shield image format from .jpg to .png and added `timeline.stop()` when the level is completed.
2. **Ensured the shield follows the boss in level 2**: Tracked the boss's x and y positions using `getLayoutX() + getTranslateX()` and `getLayoutY() + getTranslateY()` and set the shield's x and y positions to the boss's coordinates using `setLayoutX()` and `setLayoutY()`.

### Features Implemented
1. **User plane movement**: Enabled movement of the user plane both horizontally and vertically.
2. **Kill count display**: Displayed the number of kills in level 1.
3. **Boss health bar**: Displayed the boss's health with a progress bar and text.
4. **Explosion images**: Displayed explosion images when an enemy unit is destroyed.
5. **Sound effects and music**:
   - **Sound effects**: User fires projectile, enemy destroyed, user's projectile hits enemy, enemy projectile destroyed, boss activates & deactivates shield, user's projectile hits boss's shield, next level, warning for approaching boundary, game over, and win.
   - **Music**: Main menu, gameplay.
6. **Menus and pages**: 
   - **Main menu**: Displayed the main menu with options to start the game, view the tutorial, control the volume, and exit the game.
   - **Tutorial**: Displayed the tutorial page with control instructions and descriptions of each level.
   - **Audio settings**: Displayed the audio settings page with options to mute/unmute sound effects and music.
7. **Pause functionality**: Enabled the user to pause the game, mute/unmute sound effects and music, resume the game, restart the game, and return to the main menu.
8. **Warning sign**: Displayed a warning sign and played a sound effect when an enemy plane approaches the leftmost boundary.
9. **User health indicator**: Made displayed hearts zoom in and out when the user's health is low.
10. **Fire rate limitation**: Limited the user's fire rate to save memory and prevent frame rate drops.
11. **Loading page**: Inserted a loading page between levels to hide latency during level transitions.
12. **Level completion menu**: Inserted a level completion menu with options to play the level again, return to the main menu, and proceed to the next level.
13. **New levels**:
    - **Level 3**: User has limited projectiles and must survive for a certain time. Projectiles increase every few seconds.
    - **Level 4**: User must defeat a multi-stage boss with changing tactics and abilities:
        - **Stage 1**: Boss shoots projectiles at the user.
        - **Stage 2**: Boss shoots projectiles and summons enemy planes.
14. **Timer and remaining projectiles**: In Level 3, added a timer to count down the time left for the user to survive and use projectile images to represent the remaining projectiles.
15. **Boss stage change**: In Level 4, changed the boss's image and reset the health bar when the boss advanced to stage 2.
16. **Projectile cleanup**: Cleaned up user and enemy projectiles when they are off-screen to save memory, resulting in a significant decrease in memory usage.
17. **UI cleanup**: Cleaned up all children of the root of the scene when entering a new level to save memory.
18. **Final level options**: When the user loses or wins the final level, display buttons to restart from level 1 or return to the main menu.

## Implemented but Not Working Properly

### Bugs
1. **Scene Alignment Issue**: When switching between pages in the main menu, the new page's scene does not fit perfectly within the stage, leaving the top-left corner blank. For example, when switching from the main menu to the tutorial page, the tutorial page is not centered in the stage. Similarly, when switching back from the tutorial page to the main menu, the main menu is not centered in the stage.
    - **Debugging Attempts**:
        - Observed that the initial main menu scene is centered in the stage, but the problem occurs when switching to new pages.
        - Printed the x and y coordinates of the scene in the stage using `getX()` and `getY()` to check if the scene is centered.
        - Found that the x and y coordinates remain the same when switching pages, but visually the scene is not centered like the initial scene.
        - Tried setting the width and height of the new scene to match the previous scene's dimensions instead of the stage's dimensions, but the issue persisted.

Features attempted to implement:
1. When user pause the game, blur the current scene and display the buttons. However, the scene didn't recover to normal state when user resume the game.
   - attempts: 
     - I captured the current scene and blurred it


## Feature Not Implemented

## New Java Class

## Modified Java Class

## Unexpected Problems
