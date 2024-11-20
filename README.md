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
   - Sound effects: user fires projectile, enemy destroyed, enemy hit, enemy projectile destroyed, boss activates & deactivates shield, shield hit, next level, game over, and win.
8. Implemented main menu, tutorial page, audio setting page and pause menu.
9. Enabled user to pause the game, mute/unmute sound effects and music, resume the game, restart the game, and return to the main menu.
10. Displayed a warning sign and played a sound effect when an enemy plane enters a warning state (approaching the leftmost boundary).
11. Made displayed hearts zoom in and out when the user's health is low.
12. Limited the user's fire rate to save memory usage and prevent frame rate drops.
13. Inserted loading screen between levels to prevent user see the latency of the level transition.

## Implemented but Not Working Properly
1. When user pause the game, blur the background and display the buttons. However, the background can recover to normal when user resume the game.

## Feature Not Implemented

## New Java Class

## Modified Java Class

## Unexpected Problems
1. In main menu, when user switch between pages, the scene of the new page is not fit perfectly withing the stage. (e.g.When click switch from main menu to tutorial page, the tutorial page is not centered in the stage; when click switch from tutorial page to main menu, the main menu is not centered in the stage.)
   - steps to debug:
      - sdf