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
public class Single extends JFrame {
	private int select;
	private Nukerman main;
	
	public Single(Nukerman main) {
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
			addKeyListener(new SingleInput());
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.white);
			g.drawImage(new ImageIcon("images/single.pic").getImage(), 0, 0, this);
			switch (select) {
			case 0:
				g.drawImage(new ImageIcon("images/select.pic").getImage(), 45, 205, this);
				break;
			case 1:
				g.drawImage(new ImageIcon("images/select.pic").getImage(), 100, 275, this);
				break;
			case 2:
				g.drawImage(new ImageIcon("images/select.pic").getImage(), 170, 340, this);
				break;
			}
			repaint();
		}
	}
	
	public void enter(int select) {
		main.begin.close();
		Sound start = new Sound("sounds/start.mp3");
        start.start();
        switch (select) {
		case 0:
			new Control(main, 0);
			break;
		case 1:
			new Control(main, 1);
			break;
		case 2:
			new Control(main, 2);
			break;
		}
		setVisible(false);
	}
	
	private class SingleInput implements KeyListener {
		
		@SuppressWarnings("static-access")
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == e.VK_UP) select = (select+3-1)%3;
			if (e.getKeyCode() == e.VK_DOWN) select = (select+1)%3;
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
}
