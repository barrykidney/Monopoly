public class GoToJail extends Space {

    public GoToJail(String n){
        super(n);
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {
        ply.setBoardSpot(999);
        Game.goToJail(ply);
        return true;
    }
}
