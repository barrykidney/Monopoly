import java.util.List;

public class CardSpace extends Space{

    public static int[] chance = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    public static int[] cChest = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
    public static int currCardChance, currCardCchest;
    public static boolean chanceGOOJCpresent, cChestGOOJCpresent;


    public CardSpace (String n){
        super(n);
        this.currCardChance = 0;
        this.currCardCchest = 0;
        this.chanceGOOJCpresent = true;
        this.cChestGOOJCpresent = true;
    }

    public void advCard(Player p, Bank b, int spot, boolean cs) {
        if(p.getBoardSpot()>spot) { Game.passGo(p, b); }
        p.setBoardSpot((spot - p.getBoardSpot() + Game.board.size() ) % Game.board.size());
        if(!Game.board.get(spot).effect(p, b, 99, cs)) {
            Game.bankruptPlayer(p);
        }
    }

    public int getCurrCardChance(){
        return this.currCardChance;
    }

    public int getCurrCardCchest(){
        return this.currCardCchest;
    }

    public static void setChanceGOOJCpresent(boolean b) { chanceGOOJCpresent = b; }

    public static void setCchestGOOJCpresent(boolean b) { cChestGOOJCpresent = b;}


    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs){

        if(this.getName().equals("Chance")){

            int c = chance[this.getCurrCardChance()%chance.length];

            if(c == 6 && !chanceGOOJCpresent){
                this.currCardChance++;
            }

            switch (c){

                case 0: System.out.print("\nAdvance to Go");
                    advCard(ply,bk,0, cs);
                    break;

                case 1: System.out.print("\nTake a trip to Busarus, If you pass Go, collect 200");
                    advCard(ply,bk,5, cs);
                    break;

                case 2: System.out.print("\nAdvance to Dawson St. if you pass Go collect 200");
                    advCard(ply,bk,11, cs);
                    break;

                case 3: System.out.print("\nAdvance to Henery's St. if you pass Go collect 200");
                    advCard(ply,bk,24, cs);
                    break;

                case 4: System.out.print("\nTake a walk on the Shewsbury Rd. – Shewsbury Rd.");
                    advCard(ply,bk,39, cs);
                    break;

                case 5: System.out.print("\nGo back 3 spaces");
                    ply.setBoardSpot((ply.getBoardSpot()-3));
                    if(!Game.board.get(ply.getBoardSpot()).effect(ply, bk, 99, cs)) {
                        Game.bankruptPlayer(ply);
                    }
                    break;

                case 6: System.out.print("\nPay speeding fine of 15");
                    ply.makeTransferTo(bk, 15);
                    break;

                case 7: System.out.print("\nBank pays you dividend of 50");
                    bk.makeTransferTo(ply,50);
                    break;

                case 8: System.out.print("\nYour building loan matures – Collect 150");
                    bk.makeTransferTo(ply,150);
                    break;

                case 9: System.out.print("\nYou have won a crossword competition - Collect 100");
                    bk.makeTransferTo(ply,100);
                    break;

                case 10: System.out.print("\nYou have been elected Chairman of the Board – Pay each player 50");
                    if(ply.getBalance()<(Game.playerList.size() * 50)){
                        System.out.print("\nYou don't have enough to cover the fine.");
                        Game.bankruptPlayer(ply);
                    } else {
                        for(Player debtor : Game.playerList) {
                            if(!debtor.getPlayerCharacter().equals(ply.getPlayerCharacter()))
                                ply.makeTransferTo(debtor, 50);
                        }
                    }
                    break;

                case 11: System.out.print("\nMake general repairs on all your property – For each house pay 25 – For each hotel 100");
                    int cost = 0;
                    for(Property p : ply.getOwned()) {
                        if(p instanceof Street){
                            Street st = (Street) p;
                            cost = (st.getHouses() * 25) + (st.getHotels() * 100);
                        }
                    }
                    if(ply.getBalance()<cost){
                        System.out.print("\nYou don't have enough to cover the fine.");
                        Game.bankruptPlayer(ply);
                    } else {
                        ply.makeTransferTo(bk, cost);
                    }
                    break;

                case 12: System.out.print("\nAdvance to the nearest Utility. If unowned, you may buy it from the Bank. " +
                        "If owned, throw dice and pay owner a total ten times the amount thrown.");
                    if(ply.getBoardSpot()>=12 && ply.getBoardSpot()<=28) {
                        ply.setBoardSpot(28);

                        int[] d = Game.rollDice();
                        int d1 = d[0];
                        int d2 = d[1];

                        if(!Game.board.get(ply.getBoardSpot()).effect(ply, bk,(d1+d2), cs)) {
                            Game.bankruptPlayer(ply);
                        }

                    } else if(ply.getBoardSpot()<12) {
                        ply.setBoardSpot(12);

                        int[] d = Game.rollDice();
                        int d1 = d[0];
                        int d2 = d[1];

                        if(!Game.board.get(ply.getBoardSpot()).effect(ply, bk,(d1+d2), cs)) {
                            Game.bankruptPlayer(ply);
                        }

                    } else {
                        Game.passGo(ply,bk);
                        ply.setBoardSpot(12);

                        int[] d = Game.rollDice();
                        int d1 = d[0];
                        int d2 = d[1];

                        if(!Game.board.get(ply.getBoardSpot()).effect(ply, bk,(d1+d2), cs)) {
                            Game.bankruptPlayer(ply);
                        }
                    }
                    break;

                case 13: System.out.print("\nAdvance to the nearest Station and pay owner twice the rental to which he is otherwise entitled. If Station is unowned, you may buy it from the Bank.");
                    int move13 = (ply.getBoardSpot()-(ply.getBoardSpot()%5))+10;
                    ply.setBoardSpot(move13);
                    if(!Game.board.get(ply.getBoardSpot()).effect(ply, bk,2, cs)) {
                        Game.bankruptPlayer(ply);
                    }
                    break;

                case 14: System.out.print("\nAdvance to the nearest Station and pay owner twice the rental to which he is otherwise entitled. If Station is unowned, you may buy it from the Bank.");
                    int move14 = (ply.getBoardSpot()-(ply.getBoardSpot()%5))+10;
                    ply.setBoardSpot(move14);
                    if(!Game.board.get(ply.getBoardSpot()).effect(ply, bk,2, cs)) {
                        Game.bankruptPlayer(ply);
                    }
                    break;

                case 15: Game.goToJail(ply);
                    break;

                case 16: System.out.print("\nGet out of Jail Free – This card may be kept until needed, or traded/sold (This card may be kept until needed or sold)");
                    ply.setChanceGOOJC(true);
                    this.chanceGOOJCpresent = false;
                    break;
            }

            this.currCardChance++;

        } else {
            int c = cChest[this.getCurrCardCchest()%cChest.length];
            if(c == 6 && !cChestGOOJCpresent){
                this.currCardCchest++;
            }
            switch (c){
                case 0: System.out.print("\nAdvance to Go (Collect $200)");
                    advCard(ply,bk,0, cs);
                    break;

                case 1: System.out.print("\nBank error in your favor – Collect 200");
                    bk.makeTransferTo(ply,200);
                    break;

                case 2: System.out.print("\nFrom sale of stock you get 50");
                    bk.makeTransferTo(ply,50);
                    break;

                case 3: System.out.print("\nHoliday Fund matures - Receive 100");
                    bk.makeTransferTo(ply,100);
                    break;

                case 4: System.out.print("\nIncome tax refund – Collect 20");
                    bk.makeTransferTo(ply,20);
                    break;

                case 5: System.out.print("\nYou inherit 100");
                    bk.makeTransferTo(ply,100);
                    break;

                case 6: System.out.print("\nLife insurance matures – Collect 100");
                    bk.makeTransferTo(ply,100);
                    break;

                case 7: System.out.print("\nReceive 25 consultancy fee");
                    bk.makeTransferTo(ply,25);
                    break;

                case 8: System.out.print("\nYou have won second prize in a beauty contest – Collect 10");
                    bk.makeTransferTo(ply,10);
                    break;

                case 9: System.out.print("\nGrand Opera Night – Collect 50 from every player for opening night seats");
                    for(Player creditor : Game.playerList) {
                        if(!creditor.getPlayerCharacter().equals(ply.getPlayerCharacter())) {
                            if (!creditor.makeTransferTo(ply, 50)) {
                                Game.bankruptPlayer(creditor);
                            }
                        }
                    }
                    break;

                case 10: System.out.print("\nIt is your birthday - Collect 10 from each player");
                    for(Player creditor : Game.playerList) {
                        if(!creditor.getPlayerCharacter().equals(ply.getPlayerCharacter())) {
                            if (!creditor.makeTransferTo(ply, 10)) {
                                Game.bankruptPlayer(creditor);
                            }
                        }
                    }
                    break;

                case 11: System.out.print("\nDoctor's fees – Pay 50");
                    if(!ply.makeTransferTo(bk,50)) {
                        Game.bankruptPlayer(ply);
                    }
                    break;

                case 12: System.out.print("\nPay hospital fees of $100");
                    if(!ply.makeTransferTo(bk,100)) {
                        Game.bankruptPlayer(ply);
                    }
                    break;

                case 13: System.out.print("\nPay school fees of 150");
                    if(!ply.makeTransferTo(bk,150)) {
                        Game.bankruptPlayer(ply);
                    }
                    break;

                case 14: System.out.print("\nYou are assessed for street repairs – 40 per house – 115 per hotel");
                    int cost = 0;
                    for(Property p : ply.getOwned()) {
                        if(p instanceof Street){
                            Street st = (Street) p;
                            cost = (st.getHouses() * 40) + (st.getHotels() * 115);
                        }
                    }
                    if(ply.getBalance()<cost){
                        System.out.print("\nYou don't have enough to cover the fine.");
                        Game.bankruptPlayer(ply);
                    } else {
                        ply.makeTransferTo(bk, cost);
                    }
                    break;

                case 15: Game.goToJail(ply);
                    break;

                case 16: System.out.print("\nGet out of Jail Free – This card may be kept until needed, or traded/sold (This card may be kept until needed or sold)");
                    ply.setCchestGOOJC(true);
                    this.cChestGOOJCpresent = false;
                    break;
            }
            this.currCardCchest++;
        }

        return true;
    }
}
