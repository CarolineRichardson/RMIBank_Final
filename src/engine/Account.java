package engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import engine.Statement;
import engine.Session;;


/* 
 * 
 * CT414 - Distributed Systems & Co Operative Computing
 * 4BCT 
 * Nicole Ferry - 13344381
 * Caroline Richardson - 13358846 
 * 
*/


public class Account implements Serializable
{
	
	private int balance;
	private int accountNumber;
	private String accountName;
	private String password;
	
	
	//user has list of transactions 
	private List<Transaction> transactions;
	
	
	
	public Account(String name, String pass, int accNum) 
	{
		
        this.accountNumber = accNum;
        this.accountName = name;
        this.password = pass;
        this.transactions = new ArrayList<Transaction>();
        
    }
	
    
	public void setBalance(int b)
	{
		this.balance = b;
	}
	
	
	
	public String getPassword()
	{
		return password;
	}
	
	public String getAccountName()
	{
		return accountName;
	}
	
	public int getAccountNumber()
	{
		return accountNumber;
	}
	
	public int getBalance()
	{
		return balance;
	}
	
	
	
	//TRANSACTION STUFF
    public void addTransaction(Transaction t) 
    {
        this.transactions.add(t);
    }
    
    public List<Transaction> getTransactions()
    {
        return this.transactions;
    }
    
    
    
    //PRINT INF0 HANDLING
    public String getAccountInfo() 
    {
        return accountNumber + "\n" + accountName + "\n" + balance + "\n";
    }
    
    
    public void printAccountInfo()
    {
    	System.out.print(accountNumber + "\n" + accountName + "\n" + balance + "\n");
    }
	

}
