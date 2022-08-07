
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

    public String getPassword() {
        return password;
    }

    public Account createAccount (String type) {
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
        
        if(num < accounts.length) {
            found = true;
        }
        
        for(int i=0; i<num; i++) {
            acctNums[i] = accounts[i];
        }
        
        for(int i=num+1; i<accounts.length; i++) {
            acctNums[i-1] = accounts[i];
        }
        
        accounts = acctNums;
        
        if(found) {
            return true;
        }
        
        for(int i=0; (i<accounts.length && found) || i<acctNums.length; i++) {
            
            if(found) {
                acctNums[i-1] = accounts[i];
            } 
            else {
                
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
        
        if(accounts.length == 0) {
            return false;
        }
        
        if(amount <= 0) {
            return false;
        }
        
        worked = accounts[from].withdraw(amount);
        accounts[to].deposit(amount);
        
        return worked;

    }
}
