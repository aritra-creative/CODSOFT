import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

class Transaction {
    private String transactionType;
    private double amount;
    private String timestamp;

    public Transaction(String transactionType, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Transaction: " + transactionType + ", Amount: $" + amount + ", Timestamp: " + timestamp;
    }
}

class User {
    private String userId;
    private String userPin;
    private double accountBalance;
    private List<Transaction> transactionHistory;

    public User(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.accountBalance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public List<Transaction> getTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No Transaction history");
        }
        return transactionHistory;
    }

    public void deposit(double amount) {
        accountBalance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        }
        return false;
    }

    public boolean transfer(User recipient, double amount) {
        if (amount <= accountBalance && recipient != null) {
            accountBalance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            return true;
        }
        return false;
    }

    public boolean authenticate(String userPin) {
        return this.userPin.equals(userPin);
    }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Account Balance: $" + accountBalance;
    }
}

class ATM {
    private BufferedReader reader;
    private Map<String, User> userDatabase;
    private User currentUser;

    public ATM() {
        reader = new BufferedReader(new InputStreamReader(System.in));
        userDatabase = new HashMap<>();
        userDatabase.put("user1", new User("user1", "1234"));
        userDatabase.put("user2", new User("user2", "5678"));
    }

    public void run() {
        System.out.println("Welcome to the ATM!");

        while (true) {
            if (currentUser == null) {
                System.out.print("Enter User ID: ");
                String userId;
                try {
                    userId = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                System.out.print("Enter User PIN: ");
                String userPin;
                try {
                    userPin = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }

                // Authenticate user
                currentUser = authenticateUser(userId, userPin);

                if (currentUser == null) {
                    System.out.println("Authentication failed. Please check your credentials.");
                    continue;
                }

                System.out.println("Authentication successful. Welcome, " + currentUser.getUserId() + "!");
            }

            // Display menu
            System.out.println("Select an option:");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Logout");

            int choice;
            try {
                choice = Integer.parseInt(reader.readLine());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                continue;
            }

            switch (choice) {
                case 1:
                    // Transaction History
                    displayTransactionHistory(currentUser);
                    break;
                case 2:
                    // Withdraw
                    performWithdrawal(currentUser);
                    break;
                case 3:
                    // Deposit
                    performDeposit(currentUser);
                    break;
                case 4:
                    // Transfer
                    performTransfer(currentUser);
                    break;
                case 5:
                    // Logout
                    System.out.println("Logging out. Thank you for using the ATM.");
                    currentUser = null;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private User authenticateUser(String userId, String userPin) {
        User user = userDatabase.get(userId);
        if (user != null && user.authenticate(userPin)) {
            return user;
        }
        return null;
    }

    private void displayTransactionHistory(User user) {
        System.out.println("Transaction History for " + user.getUserId() + ":");
        List<Transaction> transactions = user.getTransactionHistory();
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void performWithdrawal(User user) {
        System.out.print("Enter withdrawal amount: $");
        double amount;
        try {
            amount = Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        if (user.withdraw(amount)) {
            System.out.println("Withdrawal successful. New balance: $" + user.getAccountBalance());
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private void performDeposit(User user) {
        System.out.print("Enter deposit amount: $");
        double amount;
        try {
            amount = Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        user.deposit(amount);
        System.out.println("Deposit successful. New balance: $" + user.getAccountBalance());
    }

    private void performTransfer(User user) {
        System.out.print("Enter recipient's User ID: ");
        String recipientId;
        try {
            recipientId = reader.readLine();
        } catch (IOException e) {
            System.out.println("Invalid input. Please enter a valid User ID.");
            return;
        }

        User recipient = userDatabase.get(recipientId);
        if (recipient == null) {
            System.out.println("Recipient not found. Please check the User ID.");
            return;
        }

        System.out.print("Enter transfer amount: $");
        double amount;
        try {
            amount = Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        if (user.transfer(recipient, amount)) {
            System.out.println("Transfer successful. New balance: $" + user.getAccountBalance());
        } else {
            System.out.println("Transfer failed. Insufficient funds or invalid recipient.");
        }
    }
}

public class ATM_Interface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}
