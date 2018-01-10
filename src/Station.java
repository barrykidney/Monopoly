public class Station extends Property {


    Station(String name, int p, int r){
        super(name, p, r);
    }

    private int getRentStation(boolean cs) {
        int rnt = this.getRent();
        for(Space Prop : getOwner().getOwned()){
            if(Prop instanceof Station){rnt *=2;}
        }
        return cs ? rnt : rnt/2;
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {
        return super.effect(ply, bk, dice, cs) || ply.makeTransferTo(this.getOwner(), this.getRentStation(cs));
    }
}
