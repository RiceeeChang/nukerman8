import java.io.BufferedInputStream;
import java.io.FileInputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
 
public class Sound extends Thread {
	private Player player; 
	
	public Sound(String filename) {
		try {
			player = new Player(new BufferedInputStream(new FileInputStream(filename)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		if (player != null) player.close();
	}
    
    public void run() {
    	try {
			player.play();
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
    }
}
