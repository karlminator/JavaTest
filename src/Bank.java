import java.util.Scanner;
import java.util.ArrayList;

public class Bank {
    private Scanner sc = new Scanner(System.in);
    private ArrayList<User> users = new ArrayList<>();

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
                // showLoggedInMenu(user);
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
}