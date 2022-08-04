public class Account {
    int num = 0;
    String type = "";
    Float balance = 0.0f;
    Float amount;

    public static Account (String type) {

    }

    public static getNumber () {

    }

    public static getType () {

    }

    public static getBalance () {
        return balance;
    }

    public static void deposit (float amount) {
        balance = balance + amount;
    }

    public static withdrawal (float amount) {
        if (amount <= balance) {
            balance = balance - amount;
        }
        else {
            System.out.print("Insufficient funds");
        }
    }

}
