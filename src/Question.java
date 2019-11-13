import java.io.Serializable;

public class Question implements Serializable {
    private int type;
    private String fileName;
	public int getType() {
		return type;
	}
	public String getFileName() {
		return fileName;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
}
