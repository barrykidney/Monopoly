import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Menu {

    private Scanner menuScanner = new Scanner(System.in);

    public void display() {

        boolean notDone = true;
        String[] options = {"1", "2", "3", "4"};
        String[] characters = {"Dog", "Boat", "Hat", "Car", "Iron", "Boot"};
        List<String> taken = new ArrayList<>();

        while(notDone) {
            System.out.print("\n1. Start new game\n2. Load saved game\n3. Settings\n4. exit");
            String menuStr = menuScanner.nextLine();

            if(Arrays.asList(options).contains(menuStr)) {
                switch (menuStr) {

                    case "1":

                        while(true) {
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
                                            Game.playerList.add(new Player(chrLst.get(charChoice-1), Game.bank));
                                            taken.add(chrLst.get(charChoice-1));
                                            validChar = true;
                                            System.out.print("\nThe " + chrLst.get(charChoice-1) + " has been added to the game.");
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
                            } else if(p.toLowerCase().equals("s") && taken.size() < 2) {
                                System.out.print("\nYou must choose at least two players before starting the game.");
                            } else {
                                System.out.print("\nBeginning game, good luck.");
                                notDone = false;
                                break;
                            }
                            menuScanner.nextLine();
                        }

                    case "2": System.out.print("\nLoad saved game");
                        break;
                    case "3": System.out.print("\nSettings");
                        break;

                    case "4":
                        System.out.print("\nAre you sure you want to quit? y/n");
                        String confirm = menuScanner.nextLine();
                        while(!confirm.toLowerCase().equals("y") && !confirm.toLowerCase().equals("n")) {
                            System.out.print("\n444Please enter a valid option.");
                            confirm = menuScanner.nextLine();
                        }
                        if(confirm.toLowerCase().equals("y")) {
                            System.out.print("\nquitter!");
                            System.exit(0);
                        }
                }
            } else { System.out.print("\n333Please enter a valid option"); }
        }
    }
}
