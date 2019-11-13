import java.io.Serializable;
import java.net.Socket;

public class SocketAndSize {
   public Answer answer;
   public Socket socket;
public SocketAndSize(Answer answer, Socket socket) {
	super();
	this.answer = answer;
	this.socket = socket;
}
public Answer getAnswer() {
	return answer;
}
public Socket getSocket() {
	return socket;
}
   
}
