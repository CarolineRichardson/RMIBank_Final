package engine;

import java.util.Date;
import java.util.Random;

/* 
 * 
 * CT414 - Distributed Systems & Co Operative Computing
 * 4BCT 
 * Nicole Ferry - 13344381
 * Caroline Richardson - 13358846 
 * 
*/


public class Session 
{

	public long sessionID;
	public Date start;
	public String accN;
	
	
	public Session(String aName)
	{
		this.sessionID = this.generateSessionID(); 
		this.start = new Date();
		this.accN = aName;
	}
	
	
	public Date setTime()
	{
		return start;
	}
	
	
	public long getSessionID()
	{
		return sessionID;
	}
	
	public String getAccN()
	{
		return accN;
	}
	
	public Date getStart()
	{
		return start;
	}
	
	
	
	//create random session id which is a long
	public long generateSessionID() 
	{
		long range = 1234567L;
		Random r = new Random();
		long sID = (long)(r.nextDouble()*range);
		
		return sID; 
	}
	
	
	//return boolean if session is still valid or not
	public boolean checkSession(long sid) throws InvalidSession
	{

        Date time = new Date();
        
        long t = time.getTime() - start.getTime();
        
        if ((t<300000)) //check if less than 5 minuten, i think will return millisecond
        { 
            return true;
        }
        
        else
        {
            throw new InvalidSession();
        }
    }
	
}
