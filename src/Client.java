import java.net.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Client extends JFrame implements Runnable{
	private int players = 2;
	private float remain = 300;
	private boolean[] dead = new boolean[4];
	private boolean end = false;
	private Nukerman main;
	private Sound bg = new Sound("sounds/bg2.mp3");
	private int[][] map = new int[11][15];
	private int[][] player = new int[4][6];
	private int[] key = new int[6];
	public boolean key_right, key_left, key_down, key_up, key_space;
	ObjectOutputStream ou;
	public Client(Nukerman main) {
		config();
		bg.start();
		this.main = main;
		this.setTitle("Nukerman : Fallout");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(new GamePanel());
		this.setVisible(true);
	}
	
	public void config() {
		String temp;
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.ini"));
			for (int j = 0; j != 6; ++j) {
				temp = br.readLine();
				key[j] = Integer.parseInt(temp.split(": ")[1]);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void close() {
		try {
			Thread.sleep(4500);
			main.setVisible(true);
			this.setVisible(false);
		} catch (Exception e) {
			
		}
	}
	
	public class GamePanel extends JPanel {
		public GamePanel() {
			this.setFocusable(true);
			addKeyListener(new GameInput());
		}
		
		public void paintComponent(Graphics g) {
			
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(new ImageIcon("images/background.pic").getImage(), 0, 0, this);
			for (int i = -1; i != 12; ++i) {
				for (int j = -1; j != 16; ++j) {
					if (i == -1  || j == -1) {
						g.drawImage(new ImageIcon("images/out_block.pic").getImage(), 100+40*j, 60+40*i, this);
					}
				}
			}
			for (int i = 0; i != 11; ++i) {
				for (int j = 0; j != 15; ++j) {
					g.drawImage(new ImageIcon("images/ground.pic").getImage(), 100+40*j, 70+40*i, this);
				}
			}
			for (int j = 0; j != 15; ++j) {
				for (int i = 0; i != 11; ++i) {
					switch (map[i][j]) {
					case -1:
						g.drawImage(new ImageIcon("images/ground.pic").getImage(), 100+40*j, 70+40*i, this);
						g.drawImage(new ImageIcon("images/fire1.pic").getImage(), 90+40*j, 60+40*i, this);
						g.drawImage(new ImageIcon("images/fire1.pic").getImage(), 90+40*j, 80+40*i, this);
						g.drawImage(new ImageIcon("images/fire1.pic").getImage(), 110+40*j, 60+40*i, this);
						g.drawImage(new ImageIcon("images/fire1.pic").getImage(), 110+40*j, 80+40*i, this);
						g.drawImage(new ImageIcon("images/fire1.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 1:
						g.drawImage(new ImageIcon("images/block_A.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 2:
						g.drawImage(new ImageIcon("images/block_B.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 3:
						g.drawImage(new ImageIcon("images/bomb.pic").getImage(), 100+40*j, 60+40*i, this);
						break;
					case 4:
						g.drawImage(new ImageIcon("images/block_C.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 5:
						g.drawImage(new ImageIcon("images/item_power.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 6:
						g.drawImage(new ImageIcon("images/item_bomb3.GIF").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 7:
						g.drawImage(new ImageIcon("images/item_leg_2.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 8:
						g.drawImage(new ImageIcon("images/item_overwall.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					case 9:
						g.drawImage(new ImageIcon("images/item_nuke.pic").getImage(), 100+40*j, 70+40*i, this);
						break;
					}
				}
			}
			for (int i = 0; i != 2; ++i) {
				if (!dead[i])
				switch (player[i][2]) {
				case 0:
					g.drawImage(new ImageIcon("images/ch_old_up.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 1:
					g.drawImage(new ImageIcon("images/ch_old_down.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 2:
					g.drawImage(new ImageIcon("images/ch_old_left.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 3:
					g.drawImage(new ImageIcon("images/ch_old_right.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 4:
					g.drawImage(new ImageIcon("images/ch_old_up.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 5:
					g.drawImage(new ImageIcon("images/ch_old_down.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 6:
					g.drawImage(new ImageIcon("images/ch_old_left.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				case 7:
					g.drawImage(new ImageIcon("images/ch_old_right.pic").getImage(), 100+player[i][1], 65+player[i][0], this);
					break;
				}
			}
			for (int i = -1; i != 12; ++i) {
				for (int j = -1; j != 16; ++j) {
					if (i == 11  || j == 15) {
						g.drawImage(new ImageIcon("images/out_block.pic").getImage(), 100+40*j, 60+40*i, this);
					}
				}
			}
			g.drawImage(new ImageIcon("images/"+(int)remain/60+".pic").getImage(), 343, 10, this);
			g.drawImage(new ImageIcon("images/"+(int)remain%60/10+".pic").getImage(), 360, 10, this);
			g.drawImage(new ImageIcon("images/"+(int)remain%60%10+".pic").getImage(), 375, 10, this);
			g.drawImage(new ImageIcon("images/"+(int)(remain%60*10%10)+".pic").getImage(), 392, 10, this);
			g.drawImage(new ImageIcon("images/"+(int)(remain%60*100%10)+".pic").getImage(), 407, 10, this);
			g.drawImage(new ImageIcon("images/ch_old.pic").getImage(), 0, 0, this);
			if (dead[0]) g.drawImage(new ImageIcon("images/del.pic").getImage(), 0, 0, this);
			g.drawImage(new ImageIcon("images/ch_old.pic").getImage(), 0, 500, this);
			if (dead[1]) g.drawImage(new ImageIcon("images/del.pic").getImage(), 0, 500, this);
			g.drawImage(new ImageIcon("images/ch_bomb3.pic").getImage(), 0, 50, this);
			g.drawImage(new ImageIcon("images/"+player[0][4]+".pic").getImage(), 0, 50, this);
			g.drawImage(new ImageIcon("images/ch_bomb3.pic").getImage(), 0, 460, this);
			g.drawImage(new ImageIcon("images/"+player[1][4]+".pic").getImage(), 0, 460, this);
			g.drawImage(new ImageIcon("images/ch_leg_2.pic").getImage(), 0, 95, this);
			g.drawImage(new ImageIcon("images/"+player[0][5]+".pic").getImage(), 0, 95, this);
			g.drawImage(new ImageIcon("images/ch_leg_2.pic").getImage(), 0, 420, this);
			g.drawImage(new ImageIcon("images/"+player[1][5]+".pic").getImage(), 0, 420, this);
			g.drawImage(new ImageIcon("images/ch_power.pic").getImage(), 0, 130, this);
			g.drawImage(new ImageIcon("images/"+(player[0][3]-1)+".pic").getImage(), 0, 130, this);
			g.drawImage(new ImageIcon("images/ch_power.pic").getImage(), 0, 375, this);
			g.drawImage(new ImageIcon("images/"+(player[1][3]-1)+".pic").getImage(), 0, 375, this);
			
			try {Thread.sleep(30);} catch (Exception e) {}
			repaint();
			if (end) close();
			else if (players <= 1 || (int)(remain*100) == 0) {
				if (!dead[0]) g.drawImage(new ImageIcon("images/win_0.pic").getImage(), 0, 0, this);
				else if (!dead[1]) g.drawImage(new ImageIcon("images/win_1.pic").getImage(), 0, 0, this);
				else g.drawImage(new ImageIcon("images/draw.pic").getImage(), 0, 0, this);
				bg.close();
				new Sound("sounds/end.mp3").start();
				end = true;
			}
		}
		
		private class GameInput implements KeyListener {
			
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				if (e.getKeyCode() == key[0] && !dead[1]) {
					key_up = true;
				}
				if (e.getKeyCode() == key[1] && !dead[1]) {
					key_down = true;
				}
				if (e.getKeyCode() == key[2] && !dead[1]) {
					key_left = true;
				}
				if (e.getKeyCode() == key[3] && !dead[1]) {
					key_right = true;
				}
				if (e.getKeyCode() == key[4] && !dead[1]) key_space = true;
				if (e.getKeyCode() == key[5] && !dead[1]) key_space = true;
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == key[0] && !dead[1]) {
					key_up = false;
				}
				if (e.getKeyCode() == key[1] && !dead[1]) {
					key_down = false;
				}
				if (e.getKeyCode() == key[2] && !dead[1]) {
					key_left = false;
				}
				if (e.getKeyCode() == key[3] && !dead[1]) {
					key_right = false;
				}
				if (e.getKeyCode() == key[4] && !dead[1]) key_space = false;
				if (e.getKeyCode() == key[5] && !dead[1]) key_space = false;
			}
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				//TODO Auto-generated method stub
			}
		}
	}
	  public static final int SERVICE_PORT = 2013;
	  
	  public void refresh(Scanner scanner) {
		  players = 2;
		  if (scanner.nextInt() == 1) end = true;
		  else end = false;
		  remain = scanner.nextFloat();
		  for (int i = 0; i != 2; ++i) {
			  if (dead[i]) --players;
		  }
		  for (int i = 0; i != 2; ++i) {
			  if (scanner.nextInt() == 1)
				  dead[i] = true;
			  else dead[i] = false;
			  for (int j = 0; j != 6; ++j) {
				  player[i][j] = scanner.nextInt();
			  }
		  }
		  for(int i=0; i<11; i++){
		    	for(int j=0; j<15; j++){
		    		map[i][j] = scanner.nextInt(); 
		    	}
		    }	
	  }
	
	  public void run() {
		  String IP = JOptionPane.showInputDialog(null,
				  "IP battle",
				  "Enter connection IP address",
				  JOptionPane.QUESTION_MESSAGE);
	      	ObjectInputStream in;
	     	String message = null;
		    while(true){
		      	try {
		      	  
			      // Get a socket to the daytime service
			      Socket daytime = new Socket (IP, SERVICE_PORT);
				  System.out.println("Connected to localhost in NukerPort :" + SERVICE_PORT);
			      // Set the socket option just in case server stalls
				  ou = new ObjectOutputStream(daytime.getOutputStream());
				  ou.flush();
				  in = new ObjectInputStream(daytime.getInputStream());
			      // Read from the server
				  
				  do{
						try{
							message = (String)in.readObject();
							System.out.println("server>" + message);
							refresh(new Scanner(message));
							//map = recvMapMSG(message);
							
							message = "control:player";
							if (key_up) message += " 1"; else message += " 0";
							if (key_down) message += " 1"; else message += " 0";
							if (key_left) message += " 1"; else message += " 0";
							if (key_right) message += " 1"; else message += " 0";
							if (key_space) message += " 1"; else message += " 0";
							sendMessage(message);
							try {
								Thread.sleep(30);
							} catch (InterruptedException e) {
								e.printStackTrace();
							} 
							
						}
						catch(ClassNotFoundException classNot){
							System.err.println("data received in unknown format");
						}
					}while(!message.equals("bye"));
		      	}
		      	catch(IOException e){
		      		System.out.println(e);
		      	}
				  
		    }
		    
	  }
		void sendMessage(String msg)
		{
			try{
				ou.writeObject(msg);
				ou.flush();
				System.out.println("client>" + msg);
			}
			catch(IOException ioException){
				ioException.printStackTrace();
			}
		}
	  
}
  