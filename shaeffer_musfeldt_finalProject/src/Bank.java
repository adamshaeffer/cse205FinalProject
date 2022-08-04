import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Bank extends Customer {

    static ArrayList<String> customers;
    static ArrayList<String> accounts;

    public static void main(String[] args) throws IOException {
        openApplication();

        // for(int i=0; i<customers.size(); i++) {
        //     print(customers.get(i));
        // }

        boolean login = false;
        boolean action1 = false;
        boolean done = false;
        String username;
        String password;
        String action;
        String custInfo = "";
        int[] accts = new int[0];
        ArrayList<Integer> acctNums = new ArrayList<Integer>();
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
            acctNums.add(Integer.parseInt(acctInfo.substring(0,5)));
            acctInfo = acctInfo.substring(6);
        }
        acctNums.add(Integer.parseInt(acctInfo.substring(0,5)));
        accts = new int[acctNums.size()];
        for(int i=0; i<acctNums.size(); i++) {
            accts[i] = acctNums.get(i);
        }
        //Create the Customer object for the current user.

        //Create the Account objects for the Customer.
        while(!done) { 
            line();
            print("My Accounts:");
            line();
        }

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

    public static void print(String toPrint) {
        System.out.println(toPrint);
    }

    public static String ask(String toAsk) {
        Scanner scnr = new Scanner(System.in);
        System.out.print(toAsk);
        String answer = scnr.nextLine();
        return answer;
    }

    public static void line() {
        System.out.println("-----------------------");
    }
}
