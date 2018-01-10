public class Space {

    private String name;
    private int numbLand;

    public Space(String n){
        this.name = n;
        this.numbLand = 0;
    }

    String getName() {
        return this.name;
    }

    public void setName(String typ){
        this.name = typ;
    }

    public int getNumbLand(){
        return this.numbLand;
    }

    void setNumbLand(){
        this.numbLand++;
    }

    void displayInfo() {
        System.out.print("\nName: " + this.getName());
    }

    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {
        return true;
    }
}
