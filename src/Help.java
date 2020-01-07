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
public class Help extends JFrame {
	private Nukerman main;
	
	public Help(Nukerman main) {
		this.main = main;
		this.setTitle("Nukerman : Fallout");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(new OptionPanel());
		this.setVisible(true);
	}
	
public class OptionPanel extends JPanel {
		
		public OptionPanel() {
			this.setFocusable(true);
			addKeyListener(new HelpInput());
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(new ImageIcon("images/story.pic").getImage(), 0, 0, this);
			repaint();
		}
	}
	
	public void close() {
		try {
			main.setVisible(true);
			this.setVisible(false);
		} catch (Exception e) {
			
		}
	}
	
	private class HelpInput implements KeyListener {
		
		@Override
		public void keyPressed(KeyEvent e) {
			close();
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
