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


public interface StatementInterface extends Serializable 
{

	public int getAccountNumber();  // returns account number associated with this statement
	
	public Date getStartDate(); // returns start Date of Statement
	
	public Date getEndDate(); // returns end Date of Statement
	
	public String getAccoutName(); // returns name of account holder
	
	public List getTransactions(); // returns list of Transaction objects that encapsulate details about each transaction

}