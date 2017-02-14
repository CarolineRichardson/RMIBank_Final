package engine;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;


/* 
 * 
 * CT414 - Distributed Systems & Co Operative Computing
 * 4BCT 
 * Nicole Ferry - 13344381
 * Caroline Richardson - 13358846 
 * 
*/


public interface BankInterface extends Remote 
{

	public Account returnAccount(String username, String password) throws RemoteException;
	
	public int returnAccountNumber(String username, String password) throws RemoteException;

	public long login(String username, String password) throws RemoteException, InvalidLogin;
	
	public void deposit(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;
	
	public void withdraw(int accountnum, int amount, long sessionID) throws RemoteException, InvalidSession;
	
	public int inquiry(int accountnum, long sessionID) throws RemoteException, InvalidSession;
	
	public Statement getStatement(int accountnum, Date from, Date to, long sessionID) throws RemoteException, InvalidSession;

}