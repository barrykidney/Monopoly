import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {

    public static Scanner scan = new Scanner(System.in);
    public static Scanner menuInput = new Scanner(System.in);
    public static Scanner housePurchaseInput = new Scanner(System.in);
    public static Scanner propMortInput = new Scanner(System.in);
    public static Random rand = new Random();
    public static Bank bank = new Bank();
    public static List<Space> board = new ArrayList<>();
    public static List<Player> playerList = new ArrayList<>();

    boolean doubleRolled, gameOver;
    static int playersRemaining, dice1, dice2, doubleCount;
//    int diceThrows[] = new int[11];


    public Game(){

        board.add(new BlankSpace("Go"));
        board.add(new Street("Crumlin",60,2,"br",50));
        board.add(new CardSpace("Community Chest"));
        board.add(new Street("Kimmage",60, 4, "br",50));
        board.add(new TaxSpace("Income Tax",200));
        board.add(new Station("Busarus Dublin",200,25));

        board.add(new Street("Rathgar Rd.",100,6,"lb",50));
        board.add(new CardSpace("Chance"));
        board.add(new Street("South Circular Rd.",100,6,"lb",50));
        board.add(new Street("Rathmines Rd.",120,8,"lb",50));
        board.add(new BlankSpace("Just visiting"));
        board.add(new Street("Dawson St.",140,10,"pk",100));
        board.add(new Utility("Electricity Co.",150,0));
        board.add(new Street("Kildare St.",140,10,"pk",100));
        board.add(new Street("Nassau St.",160,12,"pk",100));
        board.add(new Station("Dublin Airport",200,25));

        board.add(new Street("Pearse St.",180,14,"or",100));
        board.add(new CardSpace("Community Chest"));
        board.add(new Street("Dame St.",180,14,"or",100));
        board.add(new Street("Westmoreland St.",200,16,"or",100));
        board.add(new BlankSpace("Free Parking"));
        board.add(new Street("Abbey St.",220,18,"rd",150));
        board.add(new CardSpace("Chance"));
        board.add(new Street("Capel St.",220,18,"rd",150));
        board.add(new Street("Henery St.",240,20,"rd",150));
        board.add(new Station("Heuston Station",200,25));

        board.add(new Street("Talbot St.",260,22,"yl",150));
        board.add(new Street("North Earl St.",260,22,"yl",150));
        board.add(new Utility("Water Works",150,0));
        board.add(new Street("O'Connell St.",280,24,"yl",150));
        board.add(new GoToJail("Go to jail"));
        board.add(new Street("George's St.",300,26,"gr",200));
        board.add(new Street("Wicklow St.",300,26,"gr",200));
        board.add(new CardSpace("Community Chest"));
        board.add(new Street("Grafton St.",320,28,"gr",200));
        board.add(new Station("Shannon Airport",200,25));

        board.add(new CardSpace("Chance"));
        board.add(new Street("Alesbury Rd.",350,35,"nv",200));
        board.add(new TaxSpace("Super Tax", 100));
        board.add(new Street("Shewsbury Rd.",400,50,"nv",200));

//        playerList.add(new Player("Dog", bank));
//        playerList.add(new Player("Boat", bank));
//        playerList.add(new Player("Hat", bank));

        playersRemaining = playerList.size();
    }

    private static int diceThro(Random r) {
        int dice = r.nextInt(6);
        return dice + 1;
    }

    private void freePlayer(Player ply) {
        System.out.print("You're out of jail");
        ply.setInJail(false);
    }

    protected static void bankruptPlayer(Player ply) {
        System.out.printf("\nThe %s is bankrupt", ply.getPlayerCharacter());
        ply.setActive(false);
        playersRemaining--;
        // if they have property left mortgage it and return money to the bank
    }

    private void doTime(Player ply, boolean dt, Bank bk){
        if (dt) {
            freePlayer(ply);
        } else if (ply.getCchestGOOJC() || ply.getChanceGOOJC()) {
            System.out.printf("\nYou have a get out of jail free card, press y to use it");
            if (scan.nextLine().toLowerCase().equals("y")) {
                if (!ply.getCchestGOOJC() && ply.getChanceGOOJC()) {
                    ply.setChanceGOOJC(false);
                    CardSpace.setChanceGOOJCpresent(true);
                    freePlayer(ply);
                } else {
                    ply.setCchestGOOJC(false);
                    CardSpace.setCchestGOOJCpresent(true);
                    freePlayer(ply);
                }
            } else {
                ply.setJailTurns(ply.getJailTurns() - 1);
            }
        } else {
            System.out.printf("\nYou have %d more turns in jail.", ply.getJailTurns());
            if (ply.getJailTurns() == 0) {
                if (ply.makeTransferTo(bk, 50)) {
                    freePlayer(ply);
                } else {
                    bankruptPlayer(ply);
                }
            } else {
                System.out.print("\nPress y to pay 50 to get out of jail,\nor miss a turn.");
                if (scan.nextLine().toLowerCase().equals("y")) {
                    if (ply.makeTransferTo(bk, 50)) {
                        freePlayer(ply);
                    } else {
                        ply.setJailTurns(ply.getJailTurns() - 1);
                    }
                } else {
                    ply.setJailTurns(ply.getJailTurns() - 1);
                }
            }
        }
    }

    protected static void goToJail (Player p) {
        System.out.printf("\nGO TO JAIL,\nMOVE DIRECTLY TO JAIL," +
                "\nDO NOT PASS GO,\nDO NOT COLLECT $200");
        p.setBoardSpot(999);
    }

    protected static void passGo(Player p, Bank b) {
        System.out.print("\nYou passed go collect $200");
        b.makeTransferTo(p, 200);
        p.setLap(p.getLap()+1);
    }

    private void shuffle(int[] arr) {
        int l = arr.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < l; i++) {
            int change = i + random.nextInt(l - i);
            swap(arr, i, change);
        }
    }

    private static void swap(int[] a, int i, int change) {
        int helper = a[i];
        a[i] = a[change];
        a[change] = helper;
    }

    public static int[] rollDice(){
        String userInput="_";
        // do-while loop to throw dice
        while(!userInput.equals("")) {
            System.out.printf("\nPlease press Enter to roll the dice:");
            userInput = scan.nextLine();
        }
        int[] dice = new int[2];
        dice[0] = diceThro(rand);
        dice[1] = diceThro(rand);
        return dice;
    }


    public void play(){

        Menu menu = new Menu();
        menu.display();

        shuffle(CardSpace.chance);
        shuffle(CardSpace.cChest);

        gameOver = false;
        // do-while loop until 1 player left
        while(!gameOver) {
            for (Player ply : playerList) {

                if (playersRemaining == 1){ gameOver = true; break; }
                if(!ply.getActive()) { break; }

                // do-while loop to deal with doubles
                doubleCount = 0;
                do {

                    System.out.printf("\n\nIt's the " + ply.getPlayerCharacter() + "'s turn.");

                    int[] dice = rollDice();
                    int dice1 = dice[0];
                    int dice2 = dice[1];
                    doubleRolled = dice1 == dice2;
//                    doubleRolled = dice1 == dice2 ? true : false
//                    diceThrows[(dice1 + dice2) - 2]++;
                    System.out.printf("You rolled %d & %d, total: %d", dice1, dice2, dice1 + dice2);

                    // deal with jail condition
                    if(ply.getInJail()){doTime(ply,doubleRolled,bank);break;}

                    // deal with a double thrown
                    if (doubleRolled && doubleCount == 2) {
                        goToJail(ply);
                        doubleRolled = false;
                        break;
                    } else if(doubleRolled) { doubleCount++; System.out.print("\nYou rolled a double, " +
                            "get another turn.");
                    }

                    // move the player
                    ply.setBoardSpot(dice1 + dice2);

                    // if you pass go collect $200
                    if (ply.getBoardSpot() - (dice1 + dice2) < 0) {
                        passGo(ply,bank);
                    }

//                    // inform the player of their position and balance before effect applied
                    System.out.printf("\nYou have landed on %s.", board.get(ply.getBoardSpot()).getName());
//                    System.out.printf("\nYou have landed on %s (position %s),\nyour starting balance is: %d," +
//                                    "\nYour properties are: ", board.get(ply.getBoardSpot()).getName(),
//                            ply.getBoardSpot(), ply.getBalance());
//                    board.get(ply.getBoardSpot()).setNumbLand();
//                    for(Space p : ply.getOwned()){
//                        System.out.print(p.getName() + " ");
//                    }

                    boolean cs = board.get(ply.getBoardSpot()) instanceof CardSpace;
                    // apply the space effect on the player
                    if (!board.get(ply.getBoardSpot()).effect(ply, bank, (dice1 + dice2), cs)){
                        bankruptPlayer(ply);
                        break;
                    }
                    if(ply.getInJail()){doubleRolled=false;}


                    boolean exitcode = false;
                    while (!exitcode) {

                        if(ply.getBroke()) { System.out.print("\nYou must clear your debt before ending your turn. " +
                                        "If you end your turn while still in debt you will be declared bankrupt."); }

                        System.out.print("\n\n" +
                                "1. view current spot info\n" +
                                "2. view balance & GOOJF cards\n" +
                                "3. view properties\n" +
                                "4. view board position of other players\n" +
                                "5. make offer to other player\n" +
                                "6. buy house/hotel\n" +
                                "7. sell house/hotel\n" +
                                "8. mortgage/unmortgage property\n" +
                                "9. end turn\n" +
                                "0. game menu");

                        if(menuInput.hasNextInt()) {
                            int userInt = menuInput.nextInt();

                            switch (userInt) {
                                case 0: // game menu
                                    break;

                                case 1: // view current spot info
                                    Space spot = board.get(ply.getBoardSpot());
                                    spot.displayInfo();
                                    break;

                                case 2: // view balance & GOOJF cards
                                    System.out.print("\nYour balance is: " + ply.getBalance());
                                    if(ply.getChanceGOOJC() && ply.getCchestGOOJC()) {
                                        System.out.print("\n\nYou have two Get out of jail free cards.");
                                    } else if(ply.getChanceGOOJC() || ply.getCchestGOOJC()) {
                                        System.out.print("\n\nYou have one Get out of jail free card.");
                                    } else {
                                        System.out.print("\n\nYou have no Get out of jail free cards.");
                                    }
                                    break;

                                case 3: // sell house/hotel
                                    if(ply.getOwned().size() == 0) {
                                        System.out.print("\nYou don't own any properties.");
                                    } else {
                                        for(Property p : ply.getOwned()){
                                            System.out.print("\n");
                                            p.displayInfo();
                                        }
                                    }
                                    break;

                                case 4: // view board position of other players
                                    for(Player p : playerList) {
                                        if(!p.getPlayerCharacter().equals(ply.getPlayerCharacter())) {
                                            System.out.print("\nThe " + p.getPlayerCharacter() + " is on spot "
                                                    + p.getBoardSpot() + ".");
                                        }
                                    }
                                    break;

                                case 5: // make offer to other player



                                    break;
                                case 6: // buy house/hotel
                                    System.out.print("\nList of valid streets.\n");
                                    List<Street> strLst1 = new ArrayList<>();
                                    int refNumb = 1;
                                    for(Property p : ply.getOwned()){
                                        if(p instanceof Street){
                                                Street str = (Street) p;
                                            if(str.getFullSet()) {
                                                System.out.print("\n" + refNumb + ". " + str.getName() +
                                                        " Price: " + str.getHousePrice());
                                                strLst1.add(str);
                                                refNumb++;
                                            }
                                        }
                                    }
                                    System.out.print("\n\nSelect the number of the street for which you would like to "
                                            + "purchase a house or hotel.");
                                    if(housePurchaseInput.hasNextInt()) {
                                        int userPH = housePurchaseInput.nextInt();
                                        if(userPH > 0 && userPH <= strLst1.size()) {
                                            ply.buyHouse(strLst1.get(userPH-1), bank);
                                        } else { System.out.print("\nPlease enter a valid option"); }
                                        housePurchaseInput.nextLine();
                                    } else { System.out.print("\nPlease enter a valid option"); }
                                    break;

                                case 7: // sell house/hotel
                                    System.out.print("\nList of valid streets.\n");
                                    List<Street> strLst2 = new ArrayList<>();
                                    int refNumb2 = 1;
                                    for(Property p : ply.getOwned()){
                                        if(p instanceof Street){
                                            Street str = (Street) p;
                                            if(str.getFullSet() && str.getHouses()>0) {
                                                System.out.print("\n" + refNumb2 + ". " + str.getName() +
                                                        " No. of houses: " + str.getHouses() + ", Price: "
                                                        + str.getHousePrice()/2);
                                                strLst2.add(str);
                                                refNumb2++;
                                            }
                                        }
                                    }
                                    System.out.print("\n\nSelect the number of the street for which you would like to "
                                            + "sell a house.");
                                    if(housePurchaseInput.hasNextInt()) {
                                        int userPH = housePurchaseInput.nextInt();
                                        if(userPH > 0 && userPH <= strLst2.size()) {
                                            ply.sellHouse(strLst2.get(userPH-1), bank);
                                        } else { System.out.print("\nPlease enter a valid option"); }
                                        housePurchaseInput.nextLine();
                                    } else { System.out.print("\nPlease enter a valid option"); }
                                    break;

                                case 8: // mortgage/unmortgage property
                                    System.out.print("\nList of valid streets.\n");
                                    List<Property> mortLst = new ArrayList<>();
                                    int refNumb3 = 1;
                                    for(Property p : ply.getOwned()){
                                        if(!p.getMortgaged()){
                                            if(p instanceof Street){
                                                Street str = (Street) p;
                                                if(str.getHouses()==0 && str.getHotels()==0){
                                                    System.out.print("\n" + refNumb3 + ". " + str.getName() + ", Mortgage " +
                                                            "price: " + str.getPurchasePrice()/2);
                                                    mortLst.add(str);
                                                    refNumb3++;
                                                }
                                            } else {
                                                System.out.print("\n" + refNumb3 + ". " + p.getName() + ", Mortgage price: "
                                                        + p.getPurchasePrice()/2);
                                                mortLst.add(p);
                                                refNumb3++;
                                            }
                                        }
                                    }
                                    System.out.print("\n\nSelect the number of the street that you would \nlike to " +
                                            "mortgage or press 0 to cancel.");
                                    if(propMortInput.hasNextInt()) {
                                        int userMor = propMortInput.nextInt();
                                        if(userMor == 0){ break; }
                                        if(userMor > 0 && userMor <= mortLst.size()){
                                            ply.mortgageProperty(bank, mortLst.get(userMor-1));
                                        } else { System.out.print("\nPlease enter a valid option"); }
                                        propMortInput.nextLine();
                                    } else { System.out.print("\nPlease enter a valid option"); }
                                    break;

                                case 9: // end turn
                                    String confirm = "_";
                                    while(!confirm.toLowerCase().equals("y") || !confirm.toLowerCase().equals("n")) {
                                        System.out.print("\nAre you sure you want to end your turn? y/n");
                                        if(ply.getBroke()) {System.out.print("\nThis will result in you losing the game.");}
                                        confirm = scan.nextLine();
                                        if(confirm.toLowerCase().equals("y")) {
                                            System.out.print("\nYou chose to end your turn.");
                                            if(ply.getBroke()) { bankruptPlayer(ply); }
                                            exitcode = true;
                                            break;
                                        } else if(confirm.toLowerCase().equals("n")) {
                                            System.out.print("\nYou chose not to end your turn.");
                                            break;
                                        } else {
                                            System.out.print("\nPlease enter a valid option.");
                                        }
                                    }
//                                default: System.out.print("\nPlease enter a valid option");
                            }
                        } else { System.out.print("\nPlease enter a valid option"); }
                        menuInput.nextLine();
                    }
                } while (doubleRolled);
            }
        }

        String winner="";
        for (Player ply : playerList) {
            if (ply.getActive()){
                winner=ply.getPlayerCharacter();
            }
        }

        System.out.printf("\n\n\nCongratulations, the %s has won the game", winner);
//        System.out.print("\nThe spot frequency is:\n");
//        for(Space sp: board){
//            System.out.print(sp.getNumbLand() + ", ");
//        }
//        System.out.print("\nThe dice frequency is:\n");
//        System.out.print(Arrays.toString(diceThrows));

    }
}