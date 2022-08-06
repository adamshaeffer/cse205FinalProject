import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class Bank {

    public static ArrayList<String> customers;
    public static ArrayList<String> accounts;
    public static Customer cust;

    public static void main(String[] args) throws IOException {
        openApplication();

        // for(int i=0; i<customers.size(); i++) {
        //     print(customers.get(i));
        // }

        boolean login = false;
        boolean action1 = false;
        boolean done = false;
        String username = "";
        String password = "";
        String action;
        String custInfo = "";
        int[] acctNums = new int[0];
        ArrayList<Integer> accountNums = new ArrayList<Integer>();
        print("");
        print("Welcome to the BANK OF COMPUTER SCIENCE");
        while(!login) {
            print("");
            line();
            username = ask("Username: ");
            password = ask("Password: ");
            line();
            action1 = false;
            while(!action1) {
                print("Actions:");
                print("        (r)egister, (l)ogin\n");
                action = ask("Choose an action: ").toLowerCase();
                print("");
                if(action.equals("r") || action.equals("register")) {
                    action1 = true;
                    print("Thank you for joining us " + username);
                    login = true;
                    customers.add("username=\"" + username + "\",password=\"" + password + "\",accounts={}");
                }else if(action.equals("l") || action.equals("login")) {
                    action1 = true;
                    for(int i=0; i<customers.size(); i++) {
                        if(customers.get(i).contains("username=\"" + username + "\"") && customers.get(i).contains("password=\"" + password + "\"")) {
                            print("Welcome back, " + username);
                            custInfo = customers.get(i);
                            login = true;
                        }
                    }
                    if(!login) {
                        print("Incorrect username/password, please try again");
                    }
                }else {
                    action1 = false;
                    print("Sorry, the command you entered does not exist");
                }
            }
        }
        String acctInfo = custInfo.substring(custInfo.indexOf("{")+1,custInfo.length());
        while(acctInfo.indexOf(",") != -1) {
            accountNums.add(Integer.parseInt(acctInfo.substring(0,5)));
            acctInfo = acctInfo.substring(6);
        }
        accountNums.add(Integer.parseInt(acctInfo.substring(0,5)));
        acctNums = new int[accountNums.size()];
        for(int i=0; i<accountNums.size(); i++) {
            acctNums[i] = accountNums.get(i);
        }
        //Create the Customer object for the current user.
        cust = new Customer(username,password);

        //Create the Account objects for the Customer.
        for(int i=0; i<acctNums.length; i++) {
            int r = -1;
            for(int j=0; j<accounts.size(); j++) {
                if(accounts.get(j).contains("num="+acctNums[i])) {
                    r = j;
                }
            }
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
            // Actions for customer view:
            // Financing accounts: Deposit, withdraw, transfer
            // Edit accounts: New account, delete account
            // Edit customer: change password, change username
            print("Actions:");
            print("        (f)inances, (a)ccounts, (p)rofile\n");
            action = ask("Choose an action: ").toLowerCase();
            if(action.equals("f") || action.equals("finances")) {
                print("Finance Actions:");
                print("        (d)eposit, (w)ithdraw, (t)ransfer\n");
                action = ask("Choose an action: ").toLowerCase();
                if(action.equals("d") || action.equals("deposit")) {
                    int accountNum = Integer.parseInt(ask("Select the account to deposit into (1,2,etc.): "));
                    float amount = Float.parseFloat(ask("Amount to be deposited: "));
                    if(accountNum > cust.getAccounts().length || accountNum < 1) {
                        print("Sorry, that account does not exist");
                    } else {
                        cust.getAccounts()[accountNum-1].deposit(amount);
                        print("Amount successfully deposited");
                    }
                } else if(action.equals("w") || action.equals("withdraw")) {
                    int accountNum = Integer.parseInt(ask("Select the account to withdraw from (1,2,etc.): "));
                    float amount = Float.parseFloat(ask("Amount to withdraw: "));
                    if(accountNum > cust.getAccounts().length || accountNum < 1) {
                        print("Sorry, that account does not exist");
                    } else if(cust.getAccounts()[accountNum-1].withdraw(amount)) {
                        print("Amount successfully withdrawn");
                    } else {
                        print("Insufficient funds, amount not withdrawn");
                    }
                } else if(action.equals("t") || action.equals("transfer")) {
                    int fromNum = Integer.parseInt(ask("Select the account to transfer from (1,2,etc.): "));
                    int toNum = Integer.parseInt(ask("Select the account to transfer to (1,2,etc.): "));
                    float amount = Float.parseFloat(ask("Amount to transfer: "));
                    if(fromNum > cust.getAccounts().length || fromNum < 1 || toNum > cust.getAccounts().length || toNum < 1) {
                        print("Sorry, one or both account(s) do(es) not exist");
                    } else if(cust.transfer(amount,fromNum-1,toNum-1)) {
                        print("Amount successfully transferred");
                    } else {
                        print("Insufficient funds, failed to transfer");
                    }
                }
                else {
                    print("Sorry, the command you entered does not exist");
                }
            } 
            else if (action.equals("a") || action.equals("accounts")){
                print("Accounts actions:");
                print("        (n)ew account, (d)elete account\n");
                action = ask("Choose an action: ").toLowerCase();
                if (action.equals("n") || action.equals("new account")){
                }
                else if (action.equals("d") || action.equals("delete account")){
                }
                else {
                    print("Sorry, the command you entered does not exist");
                }
                    
            } else if (action.equals("p") || action.equals("profile")){
                print("Customer actions:");
                print("        change (p)assword, change (u)sername\n");
                action = ask("Choose an action: ").toLowerCase();
                if (action.equals("p") || action.equals("change password")){
                    String newPass = ask("Type your new password: ");
                    cust.setPassword(newPass);
                }
                else if (action.equals("u") || action.equals("change username")){
                    String newUser = ask("Type your new username: ");
                    cust.setName(newUser);
                }
                else {
                    print("Sorry, the command you entered does not exist");
                }
            }
        }

        // Continue to edit after each action
        // ask("Keep working? (y/n)")
        // if(n) { done = true }

        // Edit all arrays to match new information

        closeApplication();
    }

    public static void openApplication() throws IOException {
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

    public static void closeApplication() throws IOException {
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

    public static void displayAccounts() {
        for(int i=0; i<cust.getAccounts().length; i++) {
            String toPrint = "   " + (i+1) + ". ";
            Account acct = cust.getAccounts()[i];
            String type = acct.getType();
            toPrint += type;
            if(type.equals("Checking")) {
                toPrint += " #";
            } else {
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

    public static void print(String toPrint) {
        System.out.println(toPrint);
    }

    public static String ask(String toAsk) {
        Scanner scnr = new Scanner(System.in); // Ignore the warning on scnr
        System.out.print(toAsk);
        String answer = scnr.nextLine();
        return answer;
    }

    public static void line() {
        System.out.println("-----------------------");
    }
}