package client;

import java.io.Console;
import java.rmi.Naming;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import javax.swing.text.DateFormatter;

import engine.Account;
import engine.BankInterface;
import engine.InvalidLogin;
import engine.Session;
import engine.Statement;
import engine.Transaction;


/* 
 * 
 * CT414 - Distributed Systems & Co Operative Computing
 * 4BCT 
 * Nicole Ferry - 13344381
 * Caroline Richardson - 13358846 
 * 
*/


public class ATM 
{

	public static void main (String args[]) throws Exception 
	{
	
		// get user’s input, and perform the operations
		boolean loggedIn = false;
		
		long sessionID = 0;
		int aNum = 0;
		Account acc;
		
		System.out.println("\nClient - ATM");
		
		
		try 
		{
			
			//reference this for methods
			BankInterface bank = (BankInterface) Naming.lookup("//localhost/BankServer");
			
			Console console = System.console();

			  
            if (console == null) 
            {
                System.err.println("No console"); 
                System.exit(1); 
            }
 
             console.printf("Login is required to access bank features %n");
             console.printf("========================================== %n%n");
             
             String accName = console.readLine("Enter Bank Account Name: ");
             String password = console.readLine("Enter Password: ");
             
             //char[] passwordArray = console.readPassword("Enter Password: ");
             //String password = new String(passwordArray);
          
             
             sessionID = bank.login(accName, password);
             console.printf("Current Session ID: " + sessionID + "\n");
             aNum = bank.returnAccountNumber(accName, password);
             
            
             if (sessionID > 0)
             { 
            	 System.out.println("Successful login");         	 
             }
             
             else
             {
                 throw new InvalidLogin();
             }
            
             
            while (sessionID > 0 )
            {
            	console.printf("%n");
            	console.printf("Please use commands below to preform an operation %n");
            	console.printf("================================================= %n");
            	console.printf("deposit %n");
            	console.printf("withdraw %n");
            	console.printf("inquiry %n");
            	console.printf("statement %n"); 
            	console.printf("exit %n"); 
            	console.printf("================================================== %n");
            	String answer = console.readLine("Enter Choice: ");

            if (answer.equalsIgnoreCase("exit"))
            {
                System.exit(1); 
            }
            
            
            if(answer.equalsIgnoreCase("deposit"))
            {

            	Scanner scan = new Scanner(System.in);
            	
                String amount = console.readLine("Please enter deposit amount: "); 
                int amountToAdd = Integer.parseInt(amount); //watch out this could be null
                
                while (amountToAdd <= 0)
                {
                	String amendedAmount = console.readLine("Please enter a valid deposit amount: ");
                	amountToAdd = Integer.parseInt(amendedAmount);
                }
                
                System.out.println("Current session ID: " + sessionID);
                bank.deposit(aNum, amountToAdd, sessionID);
                acc = bank.returnAccount(accName, password);
                console.printf("Bank deposit successful, new balance: " + acc.getBalance() + "%n" );
                
            }
            
            
            if(answer.equalsIgnoreCase("withdraw")) 
            {
            	
            	
            	//NEED CHECKER - VALID AMOUNT - atm I just put in a check to make sure its not less than 0 
            	
            	String amount = console.readLine("Please enter withdraw amount: "); 
                int amountToTake = Integer.parseInt(amount); //watch out this could be null
                
                while (amountToTake <= 0) //checking its above 0 or 0 itself 
                {
                	String amendedAmount = console.readLine("Please enter a valid withdrawal amount: ");
                	amountToTake = Integer.parseInt(amendedAmount);
                }
                
                int oldAmount = bank.returnAccount(accName, password).getBalance();      
                int newAmount = oldAmount - amountToTake; 
            
                
                while(newAmount < 0)
                {
                	String amendedAmount = console.readLine("Please enter a valid withdrawl amount, not enough dough yo: ");
                	amountToTake = Integer.parseInt(amendedAmount);
                }
                
                
            	bank.withdraw(aNum, amountToTake, sessionID);
                int nBal = bank.returnAccount(accName, password).getBalance();
            	console.printf("Bank withdrawal successful, new balance: " + nBal + "%n" );
            	
            }
            
            
            //print account balance
            if(answer.equalsIgnoreCase("inquiry")) 
            {
                int amountInAccount = bank.inquiry(aNum, sessionID);
                
                console.printf("Amount in account: "+ amountInAccount + "%n");
                console.printf("Bank inquiry successful %n");
            }
            
            
            
            if(answer.equalsIgnoreCase("statement"))
            {
            	
            	//formatter for date input
            	DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            	
            	//date start and to for statement of transactions
            	String from = console.readLine("Please enter from date: ", formatter); 
            	Date fromD = (Date)formatter.parse(from); 
            	
            	String to = console.readLine("Please enter to date: ", formatter); 
            	Date toD = (Date)formatter.parse(to); 
       
            	
            	//create statement object 
            	Statement statement = bank.getStatement(aNum, fromD, toD, sessionID);
                
            	console.printf("%nAccount Name: " + statement.getAccoutName());
            	console.printf("%nFrom: " + statement.getStartDate());
            	console.printf("%nTo: " + statement.getEndDate());

            	
            	Transaction t; 
            	
                for (int i=0; i<statement.getTransactions().size(); i++)
                {
                    t = statement.getTransactions().get(i);
                    console.printf(t.toString());
                }
            	
            }
            
            }
        }
      
	
	   catch (Exception e) 
	   {
	       System.err.println("ATM exception:");
	       e.printStackTrace();
	   }
	   
	}
	

}
