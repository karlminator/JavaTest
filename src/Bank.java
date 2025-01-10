import java.util.Scanner;
import java.util.ArrayList;

public class Bank {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<User> users = new ArrayList<>();

    User user1 = new User("111111-1111", 1234);
    User user2 = new User("112233-1234", 1234);

    public Bank() {
    users.add(user1);
    users.add(user2);
    }

    public void run() {
        while (true) {
            System.out.println("\n1. Skapa användare");
            System.out.println("2. Logga in");
            System.out.println("3. Avsluta");

            int choice = sc.nextInt();
            sc.nextLine(); // Rensa buffer

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    return;
            }
        }
    }

    private void showLoggedInMenu(User user) {
        while (true) {
            System.out.println("\n1. Visa mina konton");
            System.out.println("2. Gör överföring");
            System.out.println("3. Logga ut");

            int choice = sc.nextInt();
            sc.nextLine(); // Rensa buffer

            switch (choice) {
                case 1:
                    showAccounts(user);
                    break;
                case 2:
                    makeTransfer(user);
                    break;
                case 3:
                    return;
            }
        }
    }

    private void showAccounts(User user) {
        System.out.println("Lönekonto: " + user.getSalaryAccount().getAccountNo() +
                " Saldo: " + user.getSalaryAccount().getBalance());
        System.out.println("Sparkonto: " + user.getSavingsAccount().getAccountNo() +
                " Saldo: " + user.getSavingsAccount().getBalance());
    }

    private void makeTransfer(User user) {
        System.out.println("\nVälj konto att föra över FRÅN:");
        System.out.println("1. Lönekonto: " + user.getSalaryAccount().getAccountNo());
        System.out.println("2. Sparkonto: " + user.getSavingsAccount().getAccountNo());

        int fromChoice = sc.nextInt();
        Account fromAccount = (fromChoice == 1) ? user.getSalaryAccount() : user.getSavingsAccount();

        System.out.println("Ange mottagarkonto (kontonummer): ");
        long toAccountNo = sc.nextLong();

        System.out.println("Ange belopp: ");
        double amount = sc.nextDouble();

        Account toAccount = findAccount(toAccountNo);
        if (toAccount == null) {
            System.out.println("Kontot finns inte!");
            return;
        }

        if (fromAccount.transfer(toAccount, amount)) {
            System.out.println("Överföring genomförd!");
        } else {
            System.out.println("Överföring misslyckades. Kontrollera saldo.");
        }
    }

    private void createUser() {
        System.out.println("Ange personnummer (ååmmdd-nnnn): ");
        String ssn = sc.nextLine();

        if (!isValSSN(ssn)) {
            System.out.println("Ogiltigt personnummer! Använd format: ååmmdd-nnnn");
            return;
        }

        System.out.println("Ange PIN (4 siffror): ");
        String pin = sc.nextLine();

        if (!isValidPin(pin)) {
            System.out.println("PIN måste vara 4 siffror!");
            return;
        }

        int pinNum = Integer.parseInt(pin);
        User user = new User(ssn, pinNum);
        users.add(user);
        System.out.println("Användare skapad!");
    }

    private void login() {
        System.out.println("Ange personnummer: ");
        String ssn = sc.nextLine();

        System.out.println("Ange PIN: ");
        String pin = sc.nextLine();
        int attempts = 3;

        while (attempts > 0) {
            User user = findUser(ssn);
            if (user != null && user.checkPin(Integer.parseInt(pin))) {
                System.out.println("Logged in..");
                showLoggedInMenu(user);
                return;
            }
            attempts--;
            if (attempts > 0) {
                System.out.println("Fel inloggning. " + attempts + " försök kvar.");
                System.out.println("Ange PIN: ");
                pin = sc.nextLine();
            }
        }
        System.out.println("För många felaktiga försök. Programmet avslutas.");
        System.exit(0);
    }

    private User findUser(String ssn) {
        for (User user : users) {
            if (user.getSocialSecurityNum().equals(ssn)) {
                return user;
            }
        }
        return null;
    }

    private Account findAccount(long accountNo) {
        for (User u : users) {
            if (u.getSalaryAccount().getAccountNo() == accountNo) return u.getSalaryAccount();
            if (u.getSavingsAccount().getAccountNo() == accountNo) return u.getSavingsAccount();
        }
        return null;
    }

    // Validator methods
    private boolean isValSSN(String ssn) {
        ssn = ssn.replace("-", "");
        if (ssn.length() != 10) return false;
        try {
            Long.parseLong(ssn);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private boolean isValidPin(String pin) {
        if (pin.length() != 4) {
            return false;
        }

        try {
            Integer.parseInt(pin);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}