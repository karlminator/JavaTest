public abstract class Account {
    private double balance;
    private final long accountNo;
    private static long salaryCounter = 1000;
    private static long savingsCounter = 2000;


    public Account(long accountNo) {
        this.balance = 500.0;  // Alla nya konton börjar med 500 i saldo
        this.accountNo = accountNo;
    }

    protected static long genSalAccNo() {
        return salaryCounter++;
    }

    protected static long genSavAccNo() {
        return savingsCounter++;
    }

    public boolean transfer(Account toAccount, double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        toAccount.balance += amount;
        return true;
    }

    public double getBalance() {
        return balance;
    }

    public long getAccountNo() {
        return accountNo;
    }

    protected void setBalance(double newBalance) {
        if (newBalance >= 0) {
            this.balance = newBalance;
        } else {
            throw new IllegalArgumentException("Saldo kan inte vara negativt");
        }
    }
}