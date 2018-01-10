public class Utility extends Property{


    Utility(String name, int p, int r){
        super(name, p, r);
    }

    private int getRentUtil(int dice, boolean cs) {
        double rnt = 1.6;
        for(Space Prop : getOwner().getOwned()){
            if(Prop instanceof Utility){rnt *=2.5;}
        }
        return cs ? dice*10 : (int)(dice*rnt);
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {
        return super.effect(ply, bk, dice, cs) || ply.makeTransferTo(this.getOwner(), this.getRentUtil(dice, cs));
    }
}
