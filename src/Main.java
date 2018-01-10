public class Main {

    public static void main(String[] args) {
        Game game = new Game();

//        for (Player p : game.playerList) {
//            p.setLap(2);
//        }
//

        System.out.print(game.board.get(6).getName());
        System.out.print(game.board.get(8).getName());
        System.out.print(game.board.get(9).getName());



        game.playerList.get(0).buyProperty(game.bank,(Property) game.board.get(6));
        game.playerList.get(0).buyProperty(game.bank,(Property) game.board.get(8));
        game.playerList.get(0).buyProperty(game.bank,(Property) game.board.get(9));

        for(Property p : game.playerList.get(0).getOwned()) {
            System.out.print(p.getName());
        }

        System.out.print(game.playerList.get(0).getBalance());
        game.bank.makeTransferTo(game.playerList.get(0), 320);
        System.out.print(game.playerList.get(0).getBalance());


        game.play();
    }
}

// make offer - 1. accept (sure y/n) 2. decline (sure y/n) 3. make counter offer
// hotels
// to do count up totals to finish early
// if transaction returns false handle selling & raising cash
// add timer to stop trolling
