import java.util.Random;

public class Account {
    int num = 0;
    String type = "";
    float balance = 0.0f;

    public Account (String type) {
        this.type = type;
        balance = 0f;

        // Lines below should: grab the account number of the most recently created account,
        // set the new account's number to one more than the number found,
        // and update the .txt file to the newest account number.
        Random rand = new Random();
        num = rand.nextInt(90000) + 10000;
        
    }

    public Account(String type, int num) { // Second constructor to recreate an existing account with the same number
        this.type = type;
        this.num = num;
    }

    public int getNumber () {
        return num;
    }

    public String getType () {
        return type;
    }

    public float getBalance () {
        return balance;
    }

    public void deposit (float amount) {
        if (amount > 0){
            balance = balance + amount;
        }
    }

    public boolean withdraw (float amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance = balance - amount;
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

}
