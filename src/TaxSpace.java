public class TaxSpace extends Space {

    private int tax;

    public TaxSpace(String n, int amt) {
        super(n);
        this.tax = amt;
    }

    public int getTax(){
        return this.tax;
    }

    @Override
    public boolean effect(Player ply, Bank bk, int dice, boolean cs) {
        return ply.makeTransferTo(bk,this.getTax());
    }
}
