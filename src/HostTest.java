import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class HostTest {
	public static Logger logger;
	public static String ip;
	static Vector<SocketAndSize> peerSocketAndSize;
	static 
	{
		peerSocketAndSize=new Vector<SocketAndSize>();
		logger = Logger.getLogger("HostTest");
	    logger.setUseParentHandlers(false);
		FileHandler fh;
		try {
			fh=new FileHandler("HostTest.log", true);
		    logger.addHandler(fh);
		    SimpleFormatter sf=new  SimpleFormatter();
		    fh.setFormatter(sf);
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public static Vector<SocketAndSize> getFileStatusFromPeer (String fileName) 
	{
		// TODO Auto-generated method stub
		peerSocketAndSize.clear();
		if(ip==null)
		  getIp();		
		
		String netaddr="";
		int c=0;
	 	for(int i=0;i<ip.length();i++)
	 	{
	 		if(ip.charAt(i)=='.')
	 			{
	 			c++;
	 			netaddr+='.';
	 			if(c==3)
	 				 break;
	 			}
	 		else
	 			netaddr+=ip.charAt(i);
	 	}
	 	
	 	System.out.println(netaddr);
	 	logger.info(netaddr);
	 	ExecutorService exec=Executors.newCachedThreadPool();
	 	
	 	for(int i=1;i<=254;i++)
		{
	 		String remoteIp=netaddr+i; 
			/*
			 * if(remoteIp.equals(ip)) continue;
			 */
	 		Runnable obj= new HostTest().new CheckCurrentIPStatus(fileName, remoteIp); 
	 		exec.execute(obj);

	 	}
	 	exec.shutdown();
	 	 try {
			exec.awaitTermination(10, TimeUnit.SECONDS);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 	return peerSocketAndSize;
	}
  public static String getIp()
  {
	   try
       {
       	 Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
       	 while(interfaces.hasMoreElements())
       	 {
       		
       		 NetworkInterface intf=interfaces.nextElement();
       		 if(intf.isLoopback()== true || intf.isUp()==false)
       			 continue;
       		 Enumeration <InetAddress> addr= intf.getInetAddresses();
       		 
       		 while(addr.hasMoreElements())
       		 {
       			 InetAddress address= addr.nextElement();
       			 String ip=address.getHostAddress();
       			//System.out.println(ip);
                 if(ip.contains("192.168."))
                 {
                	 logger.info("Subnet: "+ intf.getInterfaceAddresses().get(1).getNetworkPrefixLength());
                	 System.out.println("Subnet: "+ intf.getInterfaceAddresses().get(1).getNetworkPrefixLength());
                	 HostTest.ip=ip;
                	 return ip;
                 }
       		 }
       	 }
       }
      catch(Exception e)
       {
   	   e.printStackTrace();
       }
	return "-1";
  }
  public class CheckCurrentIPStatus implements Runnable
  {
	  String ip;
	  String fileName;
	public CheckCurrentIPStatus(String fileName, String ip) {
		super();
		this.ip = ip;
		this.fileName=fileName;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
		   Socket s=new Socket();
		   s.connect(new InetSocketAddress(ip, 7777), 2000);
		   Question question =new Question();
		   question.setType(1);
		   question.setFileName(fileName);
		   ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
		   oos.writeObject(question);
		   ObjectInputStream ois= new ObjectInputStream(s.getInputStream());
		   Answer answer = (Answer) ois.readObject();
		   SocketAndSize obj=new SocketAndSize(answer, s);
		   peerSocketAndSize.add(obj);
		   s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  }
}













