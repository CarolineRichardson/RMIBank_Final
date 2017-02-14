package engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import engine.StatementInterface;


/* 
 * 
 * CT414 - Distributed Systems & Co Operative Computing
 * 4BCT 
 * Nicole Ferry - 13344381
 * Caroline Richardson - 13358846 
 * 
*/


public class Statement implements StatementInterface, Serializable 
{

    private List<Transaction> transactions;
    private String accountName;
    private int accNum;
	private Date startDate;
	private Date endDate;
	
	
    public Statement(String accountName, int accountNum,  Date start, Date end, List<Transaction> transactions)
    {
        this.transactions = transactions; 
        this.accountName = accountName; 
        this.accNum = accountNum; 
        this.startDate = start;
        this.endDate = end;
    }

	

	public int getAccountNumber() 
	{
		return this.accNum;
	}

	public Date getStartDate() 
	{
		return startDate;
	}

	public Date getEndDate() 
	{
		return endDate;
	}

	public String getAccoutName() 
	{
		return this.accountName;
	}


	
	//transaction handling
    public void setTransaction(Transaction t) 
    {
        transactions.add(t);
    }


    public List<Transaction> getTransactions()
    {
        return transactions;
    }
    
    
    
    //print transactions of statement
    public void printTransactions(Date startDate, Date endDate) 
    {
        for (Transaction t: transactions)
        {
        	
            if (t.getDate().after(startDate) && t.getDate().before(endDate) )
            {
                System.out.println(t.toString() + "\n");
            }
            
        }
    }

	
	
}
