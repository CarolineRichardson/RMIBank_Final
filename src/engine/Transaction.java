package engine;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/* 
 * 
 * CT414 - Distributed Systems & Co Operative Computing
 * 4BCT 
 * Nicole Ferry - 13344381
 * Caroline Richardson - 13358846 
 * 
*/


public class Transaction implements Serializable
{

	  public Date date;
	  private String type;
	  private int amount;
	  private int balance;
	  private int accountNum;

	  
	  //TRANSACTION CONSTRUCTOR
	  public Transaction(int accNum, String typ, int amt, int bal) 
	  {
		  
		this.type = typ;
		this.amount = amt;
		this.balance = bal;
		this.accountNum = accNum;
		this.date = new Date();
	
	  }

	  //GETTERS
	  public Date getDate()
	  {
		  return date;
	  }
		
	  public String getTransactionType()
	  {
		  return type;
	  }
		
	  public int getAmount()
	  {
		  return amount;
	  }
		
	  public int getBalance()
	  {
		  return balance;
	  }
	  
	  public int getAccountNum()
	  {
		  return accountNum;
	  }
	  
	  
	  
	  //SETTERS
	  public void setTransactionType(String t)
	  {
		  type = t;
	  }
		
	  public void setAmount(int a)
	  {
		  amount = a;
	  }
		
	  public void setBalance(int b)
	  {
		  balance = b;
	  }
	  
	  
	  public void setAccountNumber(int aNumber) 
	  {
		  accountNum = aNumber;
	  }
	  
	
	  
	  //TO STRING
	  public String toString() 
	  {
		  return "\nDate" + date.toString() + "\nTransaction Type: " + type + "\nTransaction Amount: " + amount + "\nBalance: " + balance + "\n";
	  }

	
	
}
