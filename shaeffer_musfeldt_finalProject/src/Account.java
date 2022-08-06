import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Account {
    int num = 0;
    String type = "";
    float balance = 0.0f;

    public Account (String type) throws IOException {
        this.type = type;
        balance = 0f;

        // Lines below should: grab the account number of the most recently created account,
        // set the new account's number to one more than the number found,
        // and update the .txt file to the newest account number.
        Scanner scnr = new Scanner(new FileInputStream("lib/number.txt"));
        num = scnr.nextInt()+1;
        scnr.close();
        PrintWriter writer = new PrintWriter(new FileOutputStream("lib/number.txt"));
        writer.println(num);
        writer.close();
        
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
        balance = balance + amount;
    }

    public boolean withdraw (float amount) {
        if (amount <= balance) {
            balance = balance - amount;
            return true;
        }
        else {
            System.out.println("Warning: Insufficient funds");
            return false;
        }
    }

}
