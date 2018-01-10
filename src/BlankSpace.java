public class BlankSpace extends Space {

    public BlankSpace (String n){
        super(n);
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs){
        return true;
    }
}
