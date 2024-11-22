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
1. Fixed bugs to allow progression to level 2.
2. Fixed bugs to ensure the shield follows the boss in level 2.
3. Enabled user plane movement both horizontally and vertically.
4. Displayed the number of kills in level 1.
5. Displayed boss health in level 2.
6. Displayed explosion images when an enemy plane or boss is destroyed.
7. Added sound effects and music:
   - Sound effects: user fires projectile, enemy destroyed, user's projectile hit enemy, enemy projectile destroyed, boss activates & deactivates shield, user's projectile hit boss's shield, next level, game over, and win.
   - Music: main menu, game play
8. Implemented main menu, tutorial page, audio setting page and pause menu.
9. Enabled user to pause the game, mute/unmute sound effects and music, resume the game, restart the game, and return to the main menu.
10. Displayed a warning sign and played a sound effect when an enemy plane enters a warning state (approaching the leftmost boundary).
11. Made displayed hearts zoom in and out when the user's health is low.
12. Limited the user's fire rate to save memory usage and prevent frame rate drops.
13. Inserted loading screen between levels to prevent user see the latency of the level transition.
14. Inserted level completion menu with play again, return to main menu, and next level buttons.
15. Randomly generated a power-up which user can hit to increase health.
16. Created new levels:
    - Level 3: User has limit projectiles and has to survive for a certain amount of time. Every a few seconds, increase the number of user's projectiles.
    - Level 4: User has to defeat a boss with multiple stages. The boss changes tactics and abilities with each stage.
        - Stage 1: Boss shoots projectiles at user.
        - Stage 2: Boss shoots projectiles at user and summons enemy planes.
17. In Level 4, display prompt and play sound effect when boss changes stage. Change the boss's image and health bar when boss changes stage.

## Implemented but Not Working Properly
1. When user pause the game, blur the current scene and display the buttons. However, the scene didn't recover to normal state when user resume the game.

## Feature Not Implemented

## New Java Class

## Modified Java Class

## Unexpected Problems
1. In main menu, when user switch between pages, the scene of the new page is not fit perfectly withing the stage where the top-left corner is blank. (e.g.When click switch from main menu to tutorial page, the tutorial page is not centered in the stage; when click switch from tutorial page to main menu, the main menu is not centered in the stage.)
   - Attempts to debug:
      - What I observed is the initial main menu scene is centered in the stage. The problem occurs when I switch to new pages.
      - I printed the x and y coordinates of the scene in the stage using ```getX()``` and ```getY()``` in the terminal to see if the scene is centered in the stage. 
      - It turns out when I switch to new pages, the x and y coordinates is same as the initial scene, but visually the scene is not centered in the stage like the initial scene.
      - When create a new scene, I set the width and height of the scene to the width and height of the previous scene of the stage instead of the stage itself. However, the result is the same.