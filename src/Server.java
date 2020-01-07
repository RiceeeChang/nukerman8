import java.net.*;
import java.io.*;

public class Server extends Thread{
	  public static final int SERVICE_PORT = 2013;
	  int[][] map = new int[11][15];
	  ObjectOutputStream out;
	  ObjectInputStream in;
	  Control main;
	  
	  public Server(Control main) {
		  this.main = main;
	  }
	  
	  public void refresh(int[][] map){
		  for(int y=0; y<11; y++){
	    	  for(int x=0; x<15; x++){
	    		  this.map[y][x] = map[y][x];
	    	  }
	     }
	  }
	   
	  public void run() {
		    try {
		      int i,j;
		      String MSG = null, clientMSG = null;
		      ServerSocket server = new ServerSocket (SERVICE_PORT);	
		      Socket connection = server.accept();
		      System.out.println("Connection received from " + connection.getInetAddress().getHostName());
				//3. get Input and Output streams
		      out = new ObjectOutputStream(connection.getOutputStream());
		      out.flush();
		      in = new ObjectInputStream(connection.getInputStream());
		      //sendMessage("Connection successful");
		      
				do{
					
			        MSG = " ";
			        if (main.end) MSG += " 1";
			        else MSG += " 0";
			        MSG += " " + main.level.remain;
			        for (i = 0; i != 2; ++i) {
			        	if (main.level.player[i].dead)
			        		MSG += " 1";
			        	else MSG += " 0";
						  MSG += " " + main.player_x[i];
						  MSG += " " + main.player_y[i];
						  MSG += " " + main.level.player[i].state;
						  MSG += " " + main.level.player[i].range;
						  MSG += " " + main.level.player[i].bomb;
						  MSG += " " + main.level.player[i].speed;
					  }
			        for(i=0; i<11; i++){
				    	for(j=0; j<15; j++){
				    		MSG += " " + map[i][j]; 
				    	}
				    }
					sendMessage(MSG);
					
					try {
						clientMSG = (String)in.readObject();
					} 
					catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println("client>" + clientMSG);
					if(!clientMSG.equals(null)){
						String[] cmd = clientMSG.split(" ");
						if (Integer.parseInt(cmd[1]) == 1) main.key_up[1] = true; else main.key_up[1] = false;
						if (Integer.parseInt(cmd[2]) == 1) main.key_down[1] = true; else main.key_down[1] = false;
						if (Integer.parseInt(cmd[3]) == 1) main.key_left[1] = true; else main.key_left[1] = false;
						if (Integer.parseInt(cmd[4]) == 1) main.key_right[1] = true; else main.key_right[1] = false;
						if (Integer.parseInt(cmd[5]) == 1) main.key_space[1] = true; else main.key_space[1] = false;
					}
					if (clientMSG.equals("bye"))
						sendMessage("bye");
					//message = makeMapMSG(map);
					
					if (MSG.equals("bye"))
						sendMessage("bye");

				}while(!MSG.equals("bye"));
		      
		    } catch (BindException be) {
		      System.err.println ("Service already running on port " + SERVICE_PORT );
		    } catch (IOException ioe) {
		      System.err.println ("I/O error - " + ioe);
		    }
		    
		    
		    
		    
		    
		    
	  }
	String setMap(int [][]map){
	        int i, j;
	        String MSG = " ";
		    for(i=0; i<11; i++){
		    	for(j=0; j<15; j++){
		    		MSG += " " + map[i][j]; 
		    	}
		    }
		    return MSG;
	}
	  void sendMessage(String msg)
	  {
	  	try{
	  		out.writeObject(msg);
	  		out.flush();
	  		System.out.println("server>" + msg);
	  	}
	  	catch(IOException ioException){
	  		ioException.printStackTrace();
	  	}
	  }
}

