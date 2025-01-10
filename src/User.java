public class User {

    final private String socialSecurityNum;
    private int pin;
    private SalaryAccount salaryAccount;
    private SavingsAccount savingsAccount;

    public User(String socialSecurityNum, int pin){

        this.pin = pin;
        this.socialSecurityNum = socialSecurityNum;
        this.salaryAccount = new SalaryAccount(Account.genSalAccNo());
        this.savingsAccount = new SavingsAccount(Account.genSavAccNo());
    }

    public boolean checkPin(int inputPin) {
        return this.pin == inputPin;
    }

    public String getSocialSecurityNum() {
        return socialSecurityNum;
    }
}


