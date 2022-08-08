import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

// Main file for the Final Project of CSE 205, summer 2022.
// Group participants are: Adam Shaeffer and Caleb Musfeldt.

// This program requires two text files, one called "customers.txt" and the other "accounts.txt" to save
//  the data from previous tests and runs of the program.
// The program also runs on the two classes Customer and Account for functionality of the Bank simulation.

class Bank {

    public static ArrayList<String> customers;
    public static ArrayList<String> accounts;
    public static Customer cust;
    public static int custNum;
    public static int[] accountttNum;

    public static void main(String[] args) throws IOException {
        openApplication();

        // for(int i=0; i<customers.size(); i++) {
        //     print(customers.get(i));
        // }

        boolean login = false;
        boolean create = false;
        boolean done = false;
        String username = "";
        String password = "";
        String action;
        String custInfo = "";
        int[] acctNums = new int[0];
        ArrayList<Integer> accountNums = new ArrayList<Integer>();
        
        print("");
        print("Welcome to the VELLS PARGO BANK");
        
        while(!login) {
           
            print("");
            print("Actions:");
            print("        (r)egister, (l)ogin\n");
            action = ask("Choose an action: ").toLowerCase();
            if(action.equals("r") || action.equals("register")) {
                int i=0;
                while(i == 0) {
                    print("");
                    username = ask("Create a username: ");
                    password = ask("Create a password: ");
                    print("");
                    if(checkUsername(username)) {
                        i++;
                        print("    Thank you for joining us " + username);
                        login = true;
                        custNum = customers.size();
                        customers.add("username=\"" + username + "\",password=\"" + password + "\",accounts={}");
                        closeApplication();
                        openApplication();
                    } else {
                        print("    Sorry, that username is already taken");
                    }
                }
            } else if(action.equals("l") || action.equals("login")) {
                print("");
                username = ask("Username: ");
                password = ask("Password: ");
                print("");
                for(int i=0; i<customers.size(); i++) {
                    if(customers.get(i).contains("username=\"" + username + "\"") && customers.get(i).contains("password=\"" + password + "\"")) {
                        custNum = i;
                        custInfo = customers.get(i);
                        login = true;
                    }
                }
                if(!login) {
                    print("    Incorrect username/password, please try again");
                } else {
                    print("    Welcome back, " + username);
                }
            } else {
                print("    Sorry, the command you entered does not exist");
            }
        }
        String acctInfo = custInfo.substring(custInfo.indexOf("{")+1,custInfo.length());
        
        while(acctInfo.indexOf(",") != -1) {
            accountNums.add(Integer.parseInt(acctInfo.substring(0,5)));
            acctInfo = acctInfo.substring(6);
        }

        if(acctInfo.length() > 4) {
            accountNums.add(Integer.parseInt(acctInfo.substring(0,5)));
            acctNums = new int[accountNums.size()];
        }
        
        for(int i=0; i<accountNums.size(); i++) {
            acctNums[i] = accountNums.get(i);
        }
        //Create the Customer object for the current user.
        cust = new Customer(username,password);

        //Create the Account objects for the Customer.
        accountttNum = new int[acctNums.length];
        
        for(int i=0; i<acctNums.length; i++) {
            int r = -1;
            
            for(int j=0; j<accounts.size(); j++) {
                
                if(accounts.get(j).contains("num="+acctNums[i])) {
                    r = j;
                }
            }
            accountttNum[i] = r;
            String row = accounts.get(r);
            String postType = row.substring(row.indexOf("\"")+1);
            String type = postType.substring(0,postType.indexOf("\""));
            cust.createAccount(type,acctNums[i]);
            postType = postType.substring(postType.indexOf("=")+1);
            float balance = Float.parseFloat(postType);
            cust.getAccounts()[i].deposit(balance);
        }

        while(!done) { 
            line();
            print("My Accounts:");
            displayAccounts();
            line();
            print("");
            // Actions for customer view:
            // Financing accounts: Deposit, withdraw, transfer
            // Edit accounts: New account, delete account
            // Edit customer: change password, change username
            print("Actions:");
            print("        (f)inances, (a)ccounts, (p)rofile\n");
            action = ask("Choose an action: ").toLowerCase();
            print("");
            
            if(action.equals("f") || action.equals("finances")) {
                print("Finance Actions:");
                print("        (d)eposit, (w)ithdraw, (t)ransfer\n");
                action = ask("Choose an action: ").toLowerCase();
                print("");
                
                if(action.equals("d") || action.equals("deposit")) {
                    int accountNum = Integer.parseInt(ask("Select the account to deposit into (1,2,etc.): "));
                    float amount = Float.parseFloat(ask("Amount to be deposited: "));
                    
                    if(accountNum > cust.getAccounts().length || accountNum < 1) {
                        print("\n    Sorry, that account does not exist");
                    } 
                    else {
                        cust.getAccounts()[accountNum-1].deposit(amount);
                        print("\n    Amount successfully deposited");
                    }
                } 
                else if(action.equals("w") || action.equals("withdraw")) {
                    int accountNum = Integer.parseInt(ask("Select the account to withdraw from (1,2,etc.): "));
                    float amount = Float.parseFloat(ask("Amount to withdraw: "));
                    
                    if(accountNum > cust.getAccounts().length || accountNum < 1) {
                        print("\n    Sorry, that account does not exist");
                    } 
                    else if(cust.getAccounts()[accountNum-1].withdraw(amount)) {
                        print("\n    Amount successfully withdrawn");
                    } 
                    else {
                        print("\n    Insufficient funds, amount not withdrawn");
                    }
                } 
                else if(action.equals("t") || action.equals("transfer")) {
                    int fromNum = Integer.parseInt(ask("Select the account to transfer from (1,2,etc.): "));
                    int toNum = Integer.parseInt(ask("Select the account to transfer to (1,2,etc.): "));
                    float amount = Float.parseFloat(ask("Amount to transfer: "));
                    
                    if(fromNum > cust.getAccounts().length || fromNum < 1 || toNum > cust.getAccounts().length || toNum < 1) {
                        print("\n    Sorry, one or both account(s) do(es) not exist");
                    } 
                    else if(cust.transfer(amount,fromNum-1,toNum-1)) {
                        print("\n    Amount successfully transferred");
                    } 
                    else {
                        print("\n    Insufficient funds, failed to transfer");
                    }
                }
                else {
                    print("Sorry, the command you entered does not exist");
                }
            } 
            else if (action.equals("a") || action.equals("accounts")){
                print("Accounts actions:");
                print("        (n)ew account, (r)emove account\n");
                action = ask("Choose an action: ").toLowerCase();
                print("");
                
                if (action.equals("n") || action.equals("new account") || action.equals("new")){
                    print("What type of account are you creating? (c)hecking, (s)avings\n");
                    String type = ask("Choose a type: ").toLowerCase();
                    
                    if(type.equals("c") || type.equals("checking")) {
                        create = true;
                        cust.createAccount("Checking");
                        print("\n    Account successfully created");
                    } 
                    else if(type.equals("s") || type.equals("savings")) {
                        create = true;
                        cust.createAccount("Savings");
                        print("\n    Account successfully created");
                    } 
                    else {
                        print("\n    Sorry, that account type does not exist");
                    }
                    
                    if(create) {
                        Account created = cust.getAccounts()[cust.getAccounts().length-1];
                        String row = String.format("num=%d,type=\"%s\",balance=%.2f",created.getNumber(),created.getType(),created.getBalance());
                        int[] temp = new int[accountttNum.length+1];
                        for(int i=0; i<accountttNum.length; i++) {
                            temp[i] = accountttNum[i];
                        }
                        temp[temp.length-1] = accounts.size();
                        accountttNum = temp;
                        accounts.add(row);
                    }
                }
                else if (action.equals("r") || action.equals("remove account") || action.equals("remove")){
                    int accountNum = Integer.parseInt(ask("Select the account to remove (1,2,etc.): "));
                    
                    if(accountNum < 1 || accountNum > cust.getAccounts().length) {
                        print("\n    Sorry, that account does not exist");
                    } 
                    else {
                        
                        if(cust.removeAccount(accountNum-1)) {
                            print("\n    Account successfully removed");
                            accounts.remove(accountttNum[accountNum-1]);
                            int[] temp = new int[accountttNum.length-1];
                            for(int i=0; i<accountNum-1; i++) {
                                temp[i] = accountttNum[i];
                            }
                            for(int i=accountNum+1; i<accountttNum.length; i++) {
                                temp[i-1] = accountttNum[i];
                            }
                            accountttNum = temp;
                        } 
                        else {
                            print("\n    Sorry, for some reason that account was not removed");
                        }
                    }
                }
                else {
                    print("\n    Sorry, the command you entered does not exist");
                }
                    
            } else if (action.equals("p") || action.equals("profile")){
                print("Customer actions:");
                print("        change (p)assword, change (u)sername\n");
                action = ask("Choose an action: ").toLowerCase();
                print("");
                
                if (action.equals("p") || action.equals("change password")){
                    String newPass = ask("Type your new password: ");
                    cust.setPassword(newPass);
                    print("\n    Password saved");
                }
                else if (action.equals("u") || action.equals("change username")){
                    int i = 0;
                    while(i == 0) {
                        String newUser = ask("Type your new username: ");
                        if(checkUsername(newUser)) {
                            i++;
                            cust.setName(newUser);
                            print("\n    Username saved");
                        } else {
                            print("\n    Sorry, that username is taken.\n");
                        }
                    }
                    // check if username already exists
                }
                else {
                    print("    Sorry, the command you entered does not exist");
                }
            } else {
                print("    Sorry, the command you entered does not exist");
            }

            action = ask("\nKeep working? (y/n): ");
            print("");

            if(action.equals("n")) {
                done = true;
            }
        }

        // Edit all arrays to match new information to update the .txt files
        updateArrays();

        closeApplication();
    }

    public static void openApplication() throws IOException { // Method to read all previously saved data from the .txt files
        FileInputStream cstmr = new FileInputStream("lib/customers.txt");
        FileInputStream acct = new FileInputStream("lib/accounts.txt");
        Scanner scnr = new Scanner(cstmr);
        customers = new ArrayList<String>();
        accounts = new ArrayList<String>();
        
        while(scnr.hasNextLine()) {
            customers.add(scnr.nextLine());
        }
        scnr.close();
        scnr = new Scanner(acct);
        
        while(scnr.hasNextLine()) {
            accounts.add(scnr.nextLine());
        }
        scnr.close();
    }

    public static void closeApplication() throws IOException { // Method to save all changes to the .txt files to preserve data from the program.
        FileOutputStream cstmr = new FileOutputStream("lib/customers.txt");
        FileOutputStream acct = new FileOutputStream("lib/accounts.txt");
        PrintWriter writer = new PrintWriter(cstmr);
        
        for(int i=0; i<customers.size(); i++) {
            writer.println(customers.get(i));
        }
        writer.close();
        writer = new PrintWriter(acct);
        
        for(int i=0; i<accounts.size(); i++) {
            writer.println(accounts.get(i));
        }
        writer.close();
    }

    public static void displayAccounts() { // Method to print out the accounts and all relating information.
        for(int i=0; i<cust.getAccounts().length; i++) {
            String toPrint = "   " + (i+1) + ". ";
            Account acct = cust.getAccounts()[i];
            String type = acct.getType();
            toPrint += type;
            
            if(type.equals("Checking")) {
                toPrint += " #";
            } 
            else {
                toPrint += "  #";
            }
            
            toPrint += acct.getNumber() + ": $";
            String balance = String.format("%.2f",acct.getBalance());
            int count = 0;
            
            for(int j=balance.indexOf(".")-1; j>0; j--) {
                count++;
                
                if(count == 3) {
                    balance = balance.substring(0,j) + "," + balance.substring(j);
                    count = 0;
                }
            }
            
            toPrint += balance;
            print(toPrint);
        }
    }

    public static boolean updateArrays() { // Method that changes the ArrayLists customers and accounts so that 
        // account and customer information is updated before being written to the .txt files
        
        String acctNums = "{";
        Account[] accts = cust.getAccounts();
        
        for(int i=0; i<accts.length-1; i++) {
            acctNums += accts[i].getNumber() + ",";
        }
        acctNums += accts[accts.length-1].getNumber() + "}";
        customers.set(custNum,String.format("username=\"%s\",password=\"%s\",accounts=%s",cust.getUsername(),cust.getPassword(),acctNums));
        
        for(int i=0; i<accountttNum.length; i++) {
            Account acct = accts[i];
            accounts.set(accountttNum[i],String.format("num=%d,type=\"%s\",balance=%.2f",acct.getNumber(),acct.getType(),acct.getBalance()));
        }
        return false;
    }

    public static void print(String toPrint) { // Method to make printing to the terminal easier.
        System.out.println(toPrint);
    }

    public static String ask(String toAsk) { // Method to make user input easier and more efficient.
        Scanner scnr = new Scanner(System.in); // Ignore the warning on scnr
        System.out.print(toAsk);
        String answer = scnr.nextLine();
        return answer;
    }

    public static void line() { // Prints a dividing line
        System.out.println("-----------------------");
    }

    public static boolean checkUsername (String user) { // Method to check all other registered usernames and ensure that the new username doesn't already exist.
        // Returns true if the username is not already taken.
        for(int i=0; i<customers.size(); i++) {
            if(customers.get(i).contains(user)) {
                return false;
            }
        }
        return true;
    }
}
