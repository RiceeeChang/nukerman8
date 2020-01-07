import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Option extends JFrame {
	private BufferedWriter bw;
	private int state = 1;
	private OptionInput option = new OptionInput();
	private Nukerman main;
	
	public Option(Nukerman main) {
		this.main = main;
		this.setTitle("Nukerman : Fallout");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(new OptionPanel());
		this.setVisible(true);
		try {
			bw = new BufferedWriter(new FileWriter("config.ini"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public class OptionPanel extends JPanel {
		
		public OptionPanel() {
			this.setFocusable(true);
			addKeyListener(option);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(new ImageIcon("images/background.pic").getImage(), 0, 0, this);
			if (state != 0) {
				g.drawImage(new ImageIcon("images/opt0.pic").getImage(), 100, 20, this);
				g.drawImage(new ImageIcon("images/opt4.pic").getImage(), 150, 52, this);
				g.drawImage(new ImageIcon("images/opt5.pic").getImage(), 150, 84, this);
				g.drawImage(new ImageIcon("images/opt6.pic").getImage(), 150, 116, this);
				g.drawImage(new ImageIcon("images/opt7.pic").getImage(), 150, 148, this);
				g.drawImage(new ImageIcon("images/opt8.pic").getImage(), 150, 180, this);
				g.drawImage(new ImageIcon("images/opt9.pic").getImage(), 150, 212, this);
				g.drawImage(new ImageIcon("images/opt1.pic").getImage(), 100, 270, this);
				g.drawImage(new ImageIcon("images/opt4.pic").getImage(), 150, 302, this);
				g.drawImage(new ImageIcon("images/opt5.pic").getImage(), 150, 334, this);
				g.drawImage(new ImageIcon("images/opt6.pic").getImage(), 150, 366, this);
				g.drawImage(new ImageIcon("images/opt7.pic").getImage(), 150, 398, this);
				g.drawImage(new ImageIcon("images/opt8.pic").getImage(), 150, 430, this);
				g.drawImage(new ImageIcon("images/opt9.pic").getImage(), 150, 462, this);
				g.drawImage(new ImageIcon("images/opt2.pic").getImage(), 450, 20, this);
				g.drawImage(new ImageIcon("images/opt4.pic").getImage(), 500, 52, this);
				g.drawImage(new ImageIcon("images/opt5.pic").getImage(), 500, 84, this);
				g.drawImage(new ImageIcon("images/opt6.pic").getImage(), 500, 116, this);
				g.drawImage(new ImageIcon("images/opt7.pic").getImage(), 500, 148, this);
				g.drawImage(new ImageIcon("images/opt8.pic").getImage(), 500, 180, this);
				g.drawImage(new ImageIcon("images/opt9.pic").getImage(), 500, 212, this);
				g.drawImage(new ImageIcon("images/opt1.pic").getImage(), 450, 270, this);
				g.drawImage(new ImageIcon("images/opt4.pic").getImage(), 500, 302, this);
				g.drawImage(new ImageIcon("images/opt5.pic").getImage(), 500, 334, this);
				g.drawImage(new ImageIcon("images/opt6.pic").getImage(), 500, 366, this);
				g.drawImage(new ImageIcon("images/opt7.pic").getImage(), 500, 398, this);
				g.drawImage(new ImageIcon("images/opt8.pic").getImage(), 500, 430, this);
				g.drawImage(new ImageIcon("images/opt9.pic").getImage(), 500, 462, this);
				if (state <= 6) g.drawImage(new ImageIcon("images/select.pic").getImage(), 100, 12+32*state, this);
				else if (state <= 12) g.drawImage(new ImageIcon("images/select.pic").getImage(), 100, 262+32*(state-6), this);
				else if (state <= 18) g.drawImage(new ImageIcon("images/select.pic").getImage(), 450, 12+32*(state-12), this);
				else g.drawImage(new ImageIcon("images/select.pic").getImage(), 450, 262+32*(state-18), this);
			} else {
				g.drawImage(new ImageIcon("images/opt10.pic").getImage(), 60, 250, this);
			}
			repaint();
		}
	}

	public void setKey() {
		try {
			if (state == 0) {
				close();
			} else if (state == 1) {
				bw.write("Player_0 UP: " + Integer.toString(option.keyCode)); state = 2;;bw.newLine();bw.flush();
			} else if (state == 2) {
				bw.write("Player_0 DOWN: " + Integer.toString(option.keyCode)); state = 3;;bw.newLine();bw.flush();
			} else if (state == 3) {
				bw.write("Player_0 LEFT: " + Integer.toString(option.keyCode)); state = 4;;bw.newLine();bw.flush();
			} else if (state == 4) {
				bw.write("Player_0 RIGHT: " + Integer.toString(option.keyCode)); state = 5;;bw.newLine();bw.flush();
			} else if (state == 5) {
				bw.write("Player_0 PLANT: " + Integer.toString(option.keyCode)); state = 6;;bw.newLine();bw.flush();
			} else if (state == 6) {
				bw.write("Player_0 SPECIAL: " + Integer.toString(option.keyCode)); state = 7;;bw.newLine();bw.flush();
			} else if (state == 7) {
				bw.newLine();bw.write("Player_1 UP: " + Integer.toString(option.keyCode)); state = 8;;bw.newLine();bw.flush();
			} else if (state == 8) {
				bw.write("Player_1 DOWN: " + Integer.toString(option.keyCode)); state = 9;;bw.newLine();bw.flush();
			} else if (state == 9) {
				bw.write("Player_1 LEFT: " + Integer.toString(option.keyCode)); state = 10;;bw.newLine();bw.flush();
			} else if (state == 10) {
				bw.write("Player_1 RIGHT: " + Integer.toString(option.keyCode)); state = 11;;bw.newLine();bw.flush();
			} else if (state == 11) {
				bw.write("Player_1 PLANT: " + Integer.toString(option.keyCode)); state = 12;;bw.newLine();bw.flush();
			} else if (state == 12) {
				bw.write("Player_1 SPECIAL: " + Integer.toString(option.keyCode)); state = 13;;bw.newLine();bw.flush();
			} else if (state == 13) {
				bw.newLine();bw.write("Player_2 UP: " + Integer.toString(option.keyCode)); state = 14;;bw.newLine();bw.flush();
			} else if (state == 14) {
				bw.write("Player_2 DOWN: " + Integer.toString(option.keyCode)); state = 15;;bw.newLine();bw.flush();
			} else if (state == 15) {
				bw.write("Player_2 LEFT: " + Integer.toString(option.keyCode)); state = 16;;bw.newLine();bw.flush();
			} else if (state == 16) {
				bw.write("Player_2 RIGHT: " + Integer.toString(option.keyCode)); state = 17;;bw.newLine();bw.flush();
			} else if (state == 17) {
				bw.write("Player_2 PLANT: " + Integer.toString(option.keyCode)); state = 18;;bw.newLine();bw.flush();
			} else if (state == 18) {
				bw.write("Player_2 SPECIAL: " + Integer.toString(option.keyCode)); state = 19;;bw.newLine();bw.flush();
			} else if (state == 19) {
				bw.newLine();bw.write("Player_3 UP: " + Integer.toString(option.keyCode)); state = 20;;bw.newLine();bw.flush();
			} else if (state == 20) {
				bw.write("Player_3 DOWN: " + Integer.toString(option.keyCode)); state = 21;;bw.newLine();bw.flush();
			} else if (state == 21) {
				bw.write("Player_3 LEFT: " + Integer.toString(option.keyCode)); state = 22;;bw.newLine();bw.flush();
			} else if (state == 22) {
				bw.write("Player_3 RIGHT: " + Integer.toString(option.keyCode)); state = 23;;bw.newLine();bw.flush();
			} else if (state == 23) {
				bw.write("Player_3 PLANT: " + Integer.toString(option.keyCode)); state = 24;;bw.newLine();bw.flush();
			} else if (state == 24) {
				bw.write("Player_3 SPECIAL: " + Integer.toString(option.keyCode)); state = 0;;bw.newLine();bw.flush();
			}
		} catch (Exception e) {
		}
	}
	
	public void close() {
		try {
			main.setVisible(true);
			this.setVisible(false);
		} catch (Exception e) {
			
		}
	}
	
	private class OptionInput implements KeyListener {
		public int keyCode;
		
		@Override
		public void keyPressed(KeyEvent e) {
			keyCode = e.getKeyCode();
			setKey();
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			//TODO Auto-generated method stub
		}
		
		@Override
		public void keyTyped(KeyEvent arg0) {
			//TODO Auto-generated method stub
		}
	}
}
