// main class


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Nukerman extends JFrame {
	private int select;
	private MenuInput menu = new MenuInput();
	public Sound begin = new Sound("sounds/bg1.mp3");
	
	public Nukerman() {
		begin.start();
		this.setTitle("Nukerman : Fallout");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(new MenuPanel());
		this.setVisible(true);
	}
	
	public void enter(int select) {
		switch (select) {
		case 0:
			new Single(this);
			setVisible(false);
			break;
		case 1:
			new Thread(new Client(this)).start();
			setVisible(false);
			break;
		case 2:
			new Option(this);
			setVisible(false);
			break;
		case 3:
			new Help(this);
			setVisible(false);
			break;
		case 4:
			System.exit(0);
			break;
		}
	}
	
	public class MenuPanel extends JPanel {
		
		public MenuPanel() {
			this.setFocusable(true);
			addKeyListener(menu);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(new ImageIcon("images/background.pic").getImage(), 0, 0, this);
			g.drawImage(new ImageIcon("images/logo.pic").getImage(), 0, 20, this);
			g.drawImage(new ImageIcon("images/singleplay.pic").getImage(), 470, 320, this);
			g.drawImage(new ImageIcon("images/multiplay.pic").getImage(), 470, 370, this);
			g.drawImage(new ImageIcon("images/options.pic").getImage(), 470, 420, this);
			g.drawImage(new ImageIcon("images/help.pic").getImage(), 470, 470, this);
			g.drawImage(new ImageIcon("images/exit.pic").getImage(), 470, 520, this);
			g.drawImage(new ImageIcon("images/select.pic").getImage(), 420, 320+select*50, this);
			try {Thread.sleep(50);} catch (Exception e) {}
			repaint();
		}
	}
	
	private class MenuInput implements KeyListener {
		@SuppressWarnings("static-access")
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == e.VK_UP) select = (select+5-1)%5;
			if (e.getKeyCode() == e.VK_DOWN) select = (select+1)%5;
			if (e.getKeyCode() == e.VK_ENTER) enter(select);
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
	
	public static void main(String args[]){
		new Nukerman();
	}
}
