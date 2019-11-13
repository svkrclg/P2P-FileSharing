import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientHandler implements Runnable{
    Socket clientSocket;
	public ClientHandler(Socket client) {
		super();
		this.clientSocket = client;
	}
	@Override
	public void run() {
          
		try {
			ObjectInputStream ois =new ObjectInputStream(clientSocket.getInputStream());
			Question question =(Question) ois.readObject();
			Util.print("Question fileName: "+question.getFileName());
			Answer answer = new Answer();
			answer.setFileSize(156156654);
			ObjectOutputStream oos= new ObjectOutputStream(clientSocket.getOutputStream());
			oos.writeObject(answer);
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
