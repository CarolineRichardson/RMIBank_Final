package server;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import engine.Account;
import engine.BankInterface;
import engine.InvalidLogin;
import engine.InvalidSession;
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



public class Bank extends UnicastRemoteObject implements BankInterface
{
	
	//arrays for holding all accounts and sessions, 
	//account has transactions, and these makeup statement which is based on account info
    private ArrayList<Account> accounts = new ArrayList<Account>();
    private ArrayList<Session> sessions = new ArrayList<Session>();
	
    
	private static long sessionID;
	private static boolean valid = false;
	private int userAccIndex; 
	
	
	
	public Bank() throws RemoteException
	{
	
		super();
	
		
		//account name, password, account number
		Account a1 = new Account("Rick", "1234", 10);
		Account a2 = new Account("Negan", "password", 11);
		
		accounts.add(a1);
		accounts.add(a2);

		
	}
	
	
	public static void main(String args[]) throws Exception 
	{
	
		
		// Initialize Bank server - see sample code in the notes for details
		if (System.getSecurityManager() == null) 
        {
            System.setSecurityManager(new SecurityManager());
        }
		
		
		try 
        {
			
			//name client will look for, new bank instance
            String name = "BankServer";
            BankInterface bank = new Bank();
            
            System.out.println("New instance of bank created");
            
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, bank);
     
            //all good
            System.out.println("BankServer bound");
            
        } 
		
		catch (Exception e) 
        {
            System.err.println("BankServer exception:");
            e.printStackTrace();
        }
	
	}
	
	
	
	//return account number relating to login directly
	public int returnAccountNumber(String username, String password)
	{
		
		int accNum = 0;
		
		for(Account account : accounts) 
		{
			if(username.equals(account.getAccountName()) && password.equals(account.getPassword())) 
            {
				accNum = account.getAccountNumber();
            }
		}
		
		return accNum;
	}
	
	
	
	//returns account for login details match
	public Account returnAccount(String username, String password)
	{
		System.out.println("Return account method called in bank " ); 
		Account acc = null;
		
		for(Account account : accounts) 
		{
			if(username.equals(account.getAccountName()) && password.equals(account.getPassword())) 
            {
				acc = account;
            }
			
		}
		
		return acc;
	}
	
	
	//login all good
	@Override 
	public long login(String username, String password) throws RemoteException, InvalidLogin 
	{

	    for(Account a : accounts)
	    {
	    	
	    	if(a.getAccountName().equals(username))
	    	{
	    		
	    		if(a.getPassword().equals(password))
	    		{
	    			Session s = new Session(username);
	    			sessions.add( s );
	    			return s.getSessionID();	
	    		}
	    	}
	    }
		
        return -1;
 
	}

	
	
	@Override
	public void deposit(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession 
	{
		
		boolean valid = false;
		long sessID =-1;
		
        System.out.println("Deposit method called in bank, Session ID: " + sessionID ); 
        
        
        out:
        for (Session s :sessions) //getting the right session object to call check session on 
        {
        	sessID = s.getSessionID();
        	
        	if ( sessID == sessionID)
        	{
        		System.out.println("Correct Session object found"); 
        		valid = s.checkSession(sessionID);
        		break out;
        	}
        }
        
		if(valid == true)
		{
			System.out.println("Session valid..." );
	        
	        Account acc = null;
	        for(Account a : accounts)
	        {
	        	if(a.getAccountNumber() == accountnum)
	        	{
	        		acc = a;
	            } 
	        }
	        
	        System.out.println("Account name : " + acc.getAccountName() + ", Amount : " + acc.getBalance() );
	        
	        
	        Account userAcc = acc;
	        int oldAmount = userAcc.getBalance();      
	        int newAmount = oldAmount + amount; 
	            
	            
	        Transaction depositT = new Transaction(accountnum, "deposit", amount, newAmount); 
	           
	        userAcc.setBalance(newAmount);
	        userAcc.addTransaction(depositT);
	        
	        System.out.println("Account name : " + userAcc.getAccountName() + ", Amount : " + userAcc.getBalance() ); 
	        
		}
          
    }
	

	
	@Override
	public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession 
	{
		long sessID =-1;
		System.out.println("Withdraw method called in bank, Session ID: " + sessionID );
        
        out:
        for (Session s :sessions) //getting the right session object to call check session on 
        {
        	sessID = s.getSessionID();
        	
        	if ( sessID == sessionID)
        	{
        		System.out.println("Correct Session object found"); 
        		valid = s.checkSession(sessionID);
        		break out;
        	}
        }
        
		if(valid == true)
		{
		
	        int userAccIndex = 0; 
	        
	        for(int i = 0; i <= accounts.size(); i++)
	        {
	            Account acc = accounts.get(i); 
	            
	            if(accountnum == acc.getAccountNumber())
	            {
	                 userAccIndex = i;   
	                 break; 
	            }    
	        }
	        
	        
	        Account userAcc = accounts.get(userAccIndex); 
	        int newAmount = userAcc.getBalance() - amount; 
	        
	        System.out.println("Account name : " + userAcc.getAccountName() + ", Amount : " + userAcc.getBalance() );
	        
	        Transaction withdrawT = new Transaction(accountnum, "withdraw", amount, newAmount);
	        
	        userAcc.setBalance(newAmount);
	        userAcc.addTransaction(withdrawT);
	        
	        System.out.println("Account name : " + userAcc.getAccountName() + ", Amount : " + userAcc.getBalance() );
	        
		}
        
    }

	
	@Override
	public int inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession 
	{
		long sessID =-1;
		System.out.println("Inquiry method called in bank, Session ID: " + sessionID ); 
        
        out:
        for (Session s :sessions) //getting the right session object to call check session on 
        {
        	sessID = s.getSessionID();
        	
        	if ( sessID == sessionID)
        	{
        		System.out.println("Correct Session object found"); 
        		valid = s.checkSession(sessionID);
        		break out;
        	}
        }
        
		if(valid == true)
		{
		
		
	        int userAccIndex = 0; 
	        for(int i = 0; i <= accounts.size(); i++)
	        {
	           Account acc = accounts.get(i); 
	           
	           if(accountnum == acc.getAccountNumber())
	           {
	                userAccIndex = i;   
	                break; 
	           }    
	       }
	        
	       Account userAcc = accounts.get(userAccIndex); 
	       int amount = userAcc.getBalance();
	       
	       return amount; 
		}
		
		return 0;

   }

	
	@Override
	public Statement getStatement(int accountnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSession
	{
		
		long sessID =-1;
		System.out.println("Statement method called in bank, Session ID: " + sessionID ); 
        
        out:
        for (Session s : sessions) //getting the right session object to call check session on 
        {
        	sessID = s.getSessionID();
        	
        	if ( sessID == sessionID)
        	{
        		System.out.println("Correct Session object found"); 
        		valid = s.checkSession(sessionID);
        		break out;
        	}
        }
        
		//if session valid
		if(valid == true)
		{
	        int userAccIndex = 0; 
	        
	        for(int i = 0; i <= accounts.size(); i++)
	        {
		        Account acc = accounts.get(i); 
		        
		        if(accountnum == acc.getAccountNumber())
		        {
		             userAccIndex = i;   
		             break; 
		        }    
	        }
	    
	        
	        //account relating to index
		    Account userAcc = accounts.get(userAccIndex);
		    
		    //transactions for that account
		    List<Transaction> userTransactions = userAcc.getTransactions();
		    
		    Statement statement = new Statement(userAcc.getAccountName(),userAcc.getAccountNumber(), from, to, userTransactions);
		    statement.printTransactions(from, to);
		    
		    return statement; 
 
		}
	    
		return null; 

    }
	

}