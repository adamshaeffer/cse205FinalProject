import java.io.IOException;

public class Customer {
    private String username = "";
    private String password = "";
    private Account[] accounts = new Account[0];

    public Customer(String name, String pin) {
        username = name;
        password = pin;
    }
    
    public String getUsername() {
        return username;
    }

    public Account[] getAccounts() {
        return accounts;
    }

    public void setName (String name) {
        username = name;
    }

    public void setPassword (String pin) {
        password = pin;
    }

    public Account createAccount (String type) throws IOException {
        Account newAcct = new Account(type);
        Account[] acctNums = new Account[accounts.length+1];
        for(int i=0; i<accounts.length; i++) {
            acctNums[i] = accounts[i];
        }
        acctNums[accounts.length] = newAcct;
        accounts = acctNums;
        return newAcct;
    }

    public Account createAccount(String type, int num) { // Second createAccount for the second constructor
        Account newAcct = new Account(type,num);
        Account[] acctNums = new Account[accounts.length+1];
        for(int i=0; i<accounts.length; i++) {
            acctNums[i] = accounts[i];
        }
        acctNums[accounts.length] = newAcct;
        accounts = acctNums;
        return newAcct;
    }

    public boolean removeAccount (int num) {
        Account[] acctNums = new Account[accounts.length-1];
        boolean found = false;
        for(int i=0; (i<accounts.length && found) || i<acctNums.length; i++) {
            if(found) {
                acctNums[i-1] = accounts[i];
            } else {
                if(accounts[i].getNumber() == num) {
                    found = true;
                    continue;
                }
                acctNums[i] = accounts[i];
            }
        }
        if(found) {
            accounts = acctNums;
        }
        return found;
    }

    public boolean transfer(Float amount, int from, int to) {
        // need to find the index of account numbers from and to
        boolean worked = false;
        boolean workeda = false;
        if(accounts.length == 0) {
            return false;
        }
        Account aFrom = accounts[0];
        Account aTo = accounts[0];
        for(int i=0; i<accounts.length; i++) {
            if(accounts[i].getNumber()==from) {
                aFrom = accounts[i];
                worked = true;
            }
            if(accounts[i].getNumber()==to) {
                aTo = accounts[i];
                workeda = true;
            }
        }
        if(!(worked && workeda)) {
            return false;
        }
        worked = aFrom.withdraw(amount);
        aTo.deposit(amount);
        return worked;
    }
}
