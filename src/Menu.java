import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.File;


class Menu {

    private Scanner menuScanner = new Scanner(System.in);
    private File folder = new File("C:/monopoly/savegames/");


    void inGameMenu() {

        boolean done = false;
        String[] options = {"1", "2", "3", "4", "5"};

        while (!done) {
            System.out.print("\n1. Save game\n2. Load saved game\n3. Settings\n4. Return to game\n5. Quit");
            String menuStr = menuScanner.nextLine();

            if (Arrays.asList(options).contains(menuStr)) {
                switch (menuStr) {
                    case "1": // Save game
                        System.out.print("\nThis feature has not been implemented yet.");
                        break;

                    case "2": // Load saved game
                        loadGame();
                        break;

                    case "3": // Settings
                        System.out.print("\nThis feature has not been implemented yet.");
                        break;

                    case "4": // Return to game
                        done = true;
                        break;

                    case "5": // Quit
                        quitTheGame();
                        break;
                }
            } else {
                System.out.print("\nPlease enter a valid option");
            }
        }
    }

    void startMenu() {

        boolean done = false;
        String[] options = {"1", "2", "3", "4"};

        while (!done) {
            System.out.print("\n1. Start new game\n2. Load saved game\n3. Settings\n4. Quit");
            String menuStr = menuScanner.nextLine();

            if (Arrays.asList(options).contains(menuStr)) {
                switch (menuStr) {
                    case "1": // Start new game
                        done = startNewGame();
                        break;

                    case "2": // Load saved game
                        loadGame();
                        break;

                    case "3": // Settings
                        saveGame();
                        break;

                    case "4": // Quit
                        quitTheGame();
                        break;
                }
            } else {
                System.out.print("\nPlease enter a valid option");
            }
        }
    }

    private void loadGame() {
        if (folder.exists()) {
            if (folder.isDirectory()) {
                File[] saveGames = folder.listFiles();
                System.out.print("\nSaved games.\n");
                if (saveGames.length > 0) {

                    for (int i = 1; i <= saveGames.length; i++) {
                        if (saveGames[i].isFile()) {
                            System.out.print("\n" + i + ". " + saveGames[i].getName());
                        }
                    }
                    System.out.print("\nSelect the number of the game you would like to load.");
                    // ******** code to load a game. ***********

                } else { System.out.print("\nYou do not have any saved games to load."); }
            } else { System.out.print("\nYou do not have any saved games to load."); }
        } else { System.out.print("\nYou do not have any saved games to load."); }
    }

    private void saveGame() {
        System.out.print("\nThis feature has not been implemented yet.");
    }

    private boolean startNewGame() {

        String[] characters = {"Dog", "Boat", "Hat", "Car", "Iron", "Boot"};
        List<String> taken = new ArrayList<>();

        while (true) {
            System.out.print("\nPress \'a\' to add a maximum of six players and \'s\' to start the game.");
            String p = menuScanner.nextLine();
            while (!p.toLowerCase().equals("a") && !p.toLowerCase().equals("s")) {
                System.out.print("\nPlease enter a valid option.");
                p = menuScanner.nextLine();
            }
            if (p.toLowerCase().equals("a")) {

                System.out.print("\nPlease choose a character.");
                List<String> chrLst = new ArrayList<>();
                int refNumb = 1;
                for (String c : characters) {
                    if (!taken.contains(c)) {
                        System.out.print("\n" + refNumb + ". " + c);
                        chrLst.add(c);
                        refNumb++;
                    }
                }

                boolean validChar = false;
                while (!validChar) {
                    try {
                        int charChoice = menuScanner.nextInt();
                        if (charChoice > 0 && charChoice <= refNumb) {
                            Game.playerList.add(new Player(chrLst.get(charChoice - 1), Game.bank));
                            taken.add(chrLst.get(charChoice - 1));
                            validChar = true;
                            System.out.print("\nThe " + chrLst.get(charChoice - 1) + " has been added to the game.");
                        } else {
                            System.out.print("\nPlease enter a valid option.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.print("\nPlease enter a valid option.");
                        menuScanner.next();
                    } catch (IndexOutOfBoundsException e) {
                        System.out.print("\nPlease enter a valid option.");
                    }
                }
            } else if (p.toLowerCase().equals("s") && taken.size() < 2) {
                System.out.print("\nYou must choose at least two players before starting the game.");
            } else {
                System.out.print("\nBeginning game, good luck.");
                return true;
            }
            menuScanner.nextLine();
        }
    }

    private void quitTheGame() {

        System.out.print("\nAre you sure you want to quit? y/n");
        String confirm = menuScanner.nextLine();
        while (!confirm.toLowerCase().equals("y") && !confirm.toLowerCase().equals("n")) {
            System.out.print("\nPlease enter a valid option.");
            confirm = menuScanner.nextLine();
        }
        if (confirm.toLowerCase().equals("y")) {
            System.out.print("\nquitter!");
            System.exit(0);
        }
    }
}
