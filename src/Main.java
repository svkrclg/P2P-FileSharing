import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    
	public ServerSocket serverSocket;
	public int port;
	//Constructor for server
	public Main(int port) {
		super();
		try {
			this.serverSocket = new ServerSocket(port);
			this.serverSocket.setReuseAddress(true);   //immediate close of socket on program termination
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.port = port;
	}
   
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	 
      if(args.length==0)
      {
    	  //create Server and keep on listening
    	  new Main(7777).listen();
      }
      else
      {
    	  //connect to all server in network 
          GetFile getFile =new GetFile(args[0]);
          getFile.start();
      }
	}
	public void listen()
	{
	  while(true)
	  {
		  try {
			Socket socket=serverSocket.accept();
			Runnable client=new ClientHandler(socket);
			client.run();
	  } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	  }
	}

}
