public class Bank {

    private int balance;
    private int housesAvail;
    private int hotelsAvail;

    Bank(){
        this.setBalance(20580);
        this.housesAvail = 32;
        this.hotelsAvail = 12;
    }

    public int getBalance(){
        return this.balance;
    }

    public void setHousesAvail(int amt){
        this.housesAvail = amt;
    }

    public int getHousesAvail(){
        return this.housesAvail;
    }

    public void setHotelsAvail(int amt){
        this.hotelsAvail = amt;
    }

    public int getHotelsAvail(){
        return this.hotelsAvail;
    }

    public void setBalance(int amt){
        this.balance+=amt;
    }

    public boolean makeTransferTo(Player p, int amt) {
        if(this.getBalance()<amt) {
            return false;
        } else {
            this.setBalance(-amt);
            p.setBalance(amt);
            return true;
        }
    }

}
