import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class GetFile {
  public String fileName;

public GetFile(String fileName) {
	super();
	this.fileName = fileName;
}
public void start()
{
	System.out.println(fileName);
	//Get ip
	String ip=HostTest.getIp();
	if(ip.equals("-1"))
		Util.print("Not able to get IP address of LAN");
	else 
		Util.print("Your ip: "+ip);
	//get listening peer
	Vector<SocketAndSize> peersSocketsAndSize=HostTest.getFileStatusFromPeer(fileName); //question type1, getting socket of peer 
	//size of file, -1 for no file available.
	Enumeration<SocketAndSize> each= Collections.enumeration(peersSocketsAndSize);
	Map<Long, Integer> sizes=new HashMap<Long, Integer>();
	while(each.hasMoreElements())
	{
		SocketAndSize obj= each.nextElement();
		if(obj.getAnswer().getFileSize()==-1)
			 continue;
		if(sizes.containsKey(obj.getAnswer().getFileSize()))
		{
			int x=sizes.get(obj.getAnswer().getFileSize());
			sizes.put(new Long(obj.getAnswer().getFileSize()), new Integer(x+1));
		}
		else
		{
			sizes.put(new Long(obj.getAnswer().getFileSize()), 1);
		}
	}
	int max=-1;
	Long requiredSize = new Long(-1);
    for(Map.Entry<Long, Integer> entry : sizes.entrySet())
    {
    	if(entry.getValue()>max)
    	{
    		max=entry.getValue();
    		requiredSize=entry.getKey();
    	}
    }
    Util.print(requiredSize+" is the requiredSize: "+ max);
    //create list of eligible peers 
    Vector<String> eligiblePeersIp = new Vector<String>();
    each= Collections.enumeration(peersSocketsAndSize);
    while(each.hasMoreElements())
    {
    	SocketAndSize obj = each.nextElement();
    	if(obj.getAnswer().getFileSize() == requiredSize)
    	{
    		SocketAddress s = obj.getSocket().getRemoteSocketAddress();

    		eligiblePeersIp.add(getIp(s+""));
    	}
    }
    //Create threads to connect to eligible ip's with required details regarding part of data needed
    //Dividing requiredSize equally among eligibleIp
    
}
public String getIp(String s)
{
	String res=""; 
	int i=1;
	while(s.charAt(i)!=':')
	{
		res+=s.charAt(i);
		i++;
	}
	Util.print(res);
	return res;
}
}
