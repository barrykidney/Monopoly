import java.util.ArrayList;
import java.util.List;

public class Player {

    private static int numbOfPlayers;
    private boolean active, inJail, chanceGOOJC, cChestGOOJC, broke;
    private String playerCharacter;
    private int balance, boardSpot, currentLap, JailTurns, playerNumb;
    private List<Property> owned = new ArrayList<>();
//    private List<Property> owned = new ArrayList<>();


    public Player(String Character, Bank bk){
        numbOfPlayers ++;
        this.playerNumb = numbOfPlayers;
        this.active = true;
        this.playerCharacter = Character;
        this.boardSpot = 0;
        this.currentLap = 1;
        this.chanceGOOJC = false;
        this.cChestGOOJC = false;
        this.JailTurns = 0;
        bk.makeTransferTo(this, 1550);
    }

    public String getPlayerCharacter(){
        return this.playerCharacter;
    }

    public int getPlayerNumb(){
        return this.playerNumb;
    }

    public void setBoardSpot(int move) {
        if(move == 999) {
            this.boardSpot = 10;
            this.inJail = true;
            this.JailTurns = 3;
        } else {
            this.boardSpot = (getBoardSpot() + move)%40;
        }
    }

    public int getBoardSpot(){
        return this.boardSpot;
    }

    public void setLap(int l){
        this.currentLap = l;
    }

    public int getLap(){
        return this.currentLap;
    }

    public void setBalance(int money){
        this.balance += money;
    }

    public int getBalance(){
        return this.balance;
    }

    public void setBroke(boolean b){
        this.broke = b;
    }

    public boolean getBroke(){
        return this.broke;
    }

    public boolean getActive(){
        return this.active;
    }

    public void setActive(boolean bol){
        this.active = bol;
    }

    public boolean getInJail(){
        return this.inJail;
    }

    public void setInJail(boolean j){
        this.inJail = j;
    }

    public int getJailTurns(){
        return this.JailTurns;
    }

    public void setJailTurns(int j){
        this.JailTurns = j;
    }

    public boolean getChanceGOOJC(){
        return this.chanceGOOJC;
    }

    public void setChanceGOOJC(boolean jc){
        this.chanceGOOJC = jc;
    }

    public boolean getCchestGOOJC(){
        return this.cChestGOOJC;
    }

    public void setCchestGOOJC(boolean jc){
        this.cChestGOOJC = jc;
    }

    public List<Property> getOwned(){
        return this.owned;
    }

    public boolean buyProperty(Bank seller, Property p) {
        if (this.makeTransferTo(seller, p.getPurchasePrice())) {
            this.owned.add(p);
            p.setOwner(this);
            if(p instanceof Street) {
                if(this.checkFullSet((Street) p)) {
                    System.out.print("You have completed the set.");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void mortgageProperty(Bank bk, Property p) {
        if(p instanceof Street && (((Street) p).getHouses()!=0 || ((Street) p).getHotels()!=0)) {
            System.out.print("You mush sell all houses and hotels before you can mortgage this property.");
        } else {
            if (p.getMortgaged()) {
                System.out.print("This property is already mortgaged");
            } else {
                bk.makeTransferTo(this, p.getPurchasePrice() / 2);
                p.setMortgaged(true);
                System.out.print(p.getName() + " is now mortgaged.");
            }
        }
    }

    public void unMortgageProperty(Bank bk, Property prop) {
        if (!prop.getMortgaged()) {
            System.out.print("This property is not mortgaged");
        } else {
            if(this.makeTransferTo(bk, ((prop.getPurchasePrice()/2) + (prop.getPurchasePrice()/2)/10))){
                prop.setMortgaged(false);
            } else {
                System.out.print("You do no have the required fund to un-mortgage this property");
            }
        }
    }

    private boolean checkFullSet(Street s) {
        int str = 0;
        for(Space o : this.getOwned()){
            if(o instanceof Street && s.getColor().equals(((Street) o).getColor())) {
                str++;
            }
        }
        if(((s.getColor().equals("br") || s.getColor().equals("nv")) && str == 2) || (str == 3)) {
            for (Space p : this.getOwned()) {
                if(p instanceof Street && s.getColor().equals(((Street) p).getColor())) {
                    ((Street) p).setFullSet(true);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean buyHouse(Street s, Bank b) {

        if (b.getHousesAvail() > 0) {
            if (s.getHouses() < 4) {

                boolean validSale = true;
                for (Property prop : this.owned) {
                    if (prop instanceof Street) {
                        if ((((Street) prop).getColor().equals(s.getColor())) &&
                                (((Street) prop).getHouses() < s.getHouses())) {
                            validSale = false;
                        }
                    }
                }
                if(!validSale) {
                    System.out.print("\nYou must first purchase a house on another street in this set.");
                    return false;
                }
                if (this.makeTransferTo(b, s.getHousePrice())) {
                    b.setHousesAvail(b.getHousesAvail()-1);
                    s.setHouses(s.getHouses()+1);
                    System.out.print("\nYou have purchased a house on " + s.getName() + ".");
                    return true;
                } else {
                    System.out.print("\nYou have insufficient funds to buy a house on this property.");
                    return false;
                }
            } else {
                System.out.print("\nThere is already the maximum number of houses on this property.");
                return false;
            }
        } else {
            System.out.print("\nThere are no more houses available in the bank.");
            return false;
        }
    }

    public boolean sellHouse(Street s, Bank b) {
        if(s.getHouses()>0) {
            boolean validSale = true;
            for (Property prop : this.owned) {
                if (prop instanceof Street) {
                    if ((((Street) prop).getColor().equals(s.getColor())) &&
                            (((Street) prop).getHouses() > s.getHouses())) {
                        validSale = false;
                    }
                }
            }
            if (validSale) {
                b.makeTransferTo(this, s.getHousePrice() / 2);
                s.setHouses(s.getHouses()-1);
                b.setHousesAvail(b.getHousesAvail()+1);
                System.out.print("Sale successful");
                return true;
            } else {
                System.out.print("You must sell a house on another property of this set first.");
                return false;
            }
        } else {
            System.out.print("You do not own any houses on this street.");
            return false;
        }
    }

    boolean makeTransferTo(Player ply, int amt) {
        if(this.getBalance()<amt) {
            return false;
        } else {
            this.setBalance(-amt);
            ply.setBalance(amt);
            return true;
        }
    }

    boolean makeTransferTo(Bank bk, int amt) {
        if(this.getBalance()<amt) {
            return false;
        } else {
            this.setBalance(-amt);
            bk.setBalance(amt);
            return true;
        }
    }

    public boolean makeOffer(Player p, int noReq, int amtRequest, int amtOffer, Property... args) {
        return true;
    }

}
