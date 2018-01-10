import java.util.Scanner;

public class Property extends Space {

    private int purchasePrice;
    private int rent;
    private Player owner;
    private boolean mortgaged;


    Property(String name, int p, int r){
        super(name);
        this.rent = r;
        this.owner = null;
        this.purchasePrice = p;
        this.mortgaged = false;
    }

    int getPurchasePrice() {
        return this.purchasePrice;
    }

    Player getOwner(){
        return this.owner;
    }

    void setOwner(Player ply){
        this.owner = ply;
    }

    boolean getMortgaged(){
        return this.mortgaged;
    }

    void setMortgaged(boolean m){ this.mortgaged = m; }

    public int getRent() {
        return this.rent;
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {

        if (mortgaged || ply.getLap() < 2){ return true; }
        else if (this.getOwner() == null) {
            Scanner s = new Scanner(System.in);
            System.out.print("\nThe property is available for " + this.getPurchasePrice() + ",\nPress y to purchase.");
            if (s.nextLine().toLowerCase().equals("y")) {
                if (ply.buyProperty(bk, this)) {
                    System.out.print("You are now the owner of " + this.getName());
                } else {
                    System.out.print("\nYou have insufficient funds to buy " + this.getName());
                }
            }
            return true;
        } else if (this.getOwner().getPlayerCharacter().equals(ply.getPlayerCharacter())) {
            System.out.print("\nYou own this property");
            return true;
        } else {
            System.out.print("\nThis property is owned by the " + this.getOwner().getPlayerCharacter());
            return false;
        }
    }

    @Override
    void displayInfo() {
        super.displayInfo();
        if(this.getMortgaged()) { System.out.print("\nMortgaged"); }
        if(this.getOwner() == null) {
            System.out.print("\nFor sale");
        } else { System.out.print("\nOwner: " + this.getOwner().getPlayerCharacter()); }
        System.out.print("\nPrice: " + this.getPurchasePrice());
        System.out.print("\nRent: " + this.getRent());
    }
}
