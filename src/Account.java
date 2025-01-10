public abstract class Account {
    private double balance;
    private long accountNo;


    public Account(long accountNo) {
        this.balance = 500.0;  // Alla nya konton börjar med 500 i saldo
        this.accountNo = accountNo;
    }

    // Getters
    public double getBalance() {
        return balance;
    }

    public long getAccountNo() {
        return accountNo;
    }

    // Setter för balance - med validering
    protected void setBalance(double newBalance) {
        if (newBalance >= 0) {
            this.balance = newBalance;
        } else {
            throw new IllegalArgumentException("Saldo kan inte vara negativt");
        }
    }
}