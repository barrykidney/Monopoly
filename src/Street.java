//import java.util.Sy;
public class Street extends Property {

    private String color;
    private int houses, hotels, housePrice;
    private boolean fullSet;


    Street(String name, int p, int r, String col, int hp){
        super(name, p, r);
        this.color = col;
        this.houses = 0;
        this.hotels = 0;
        this.housePrice = hp;
        this.fullSet = false;
    }


    String getColor(){
        return this.color;
    }

    int getHouses(){
        return this.houses;
    }

    void setHouses(int amt){
        this.houses = amt;
    }

    int getHotels(){
        return this.hotels;
    }

    public void setHotels(int amt){ this.hotels = amt; }

    int getHousePrice(){
        return this.housePrice;
    }

    boolean getFullSet(){
        return this.fullSet;
    }

    void setFullSet( boolean b ){
        this.fullSet = b;
    }

    @Override
    public int getRent() {
        int rnt = super.getRent();
        if (this.getFullSet()) {
//            System.out.print("\nThe player owns the full set, rent is doubled.");
            return rnt*2;
        } else { return rnt; }
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {
        return super.effect(ply, bk, dice, cs) || ply.makeTransferTo(this.getOwner(), this.getRent());
    }

    @Override
    void displayInfo() {
        System.out.print("\nColour: " + this.getColor());
        super.displayInfo();
        if(this.getFullSet()) { System.out.print("\nPlayer owns the full set"); }
        if(this.getHouses()>0) {
            System.out.print("\nno. of houses: " + this.getHouses());
            System.out.print("\nno. of hotels: " + this.getHotels());
        }
    }
}
