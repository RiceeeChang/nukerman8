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
public class Control extends JFrame {
	public int[] player_x = new int[4];
	public int[] player_y = new int[4];
	public Level level;
	public int players = 4;
	public boolean end;
	private int mode;
	private Nukerman main;
	private Sound bg = new Sound("sounds/bg2.mp3");
	private Server host = new Server(this);
	private AI ai = new AI(this, 1);
	public boolean[] key_right = new boolean[4],
						key_left = new boolean[4],
						key_down = new boolean[4],
						key_up = new boolean[4],
						key_space = new boolean[4];
	
	public Control(Nukerman main, int mode) {
		this.mode = mode;
        bg.start();
		player_x[0] = 0;	player_y[0] = 0;
		player_x[1] = 400;	player_y[1] = 0;
		player_x[2] = 0;	player_y[2] = 560;
		player_x[3] = 400;	player_y[3] = 560;
		this.main = main;
		level = new Level(this);
		level.loadMap("maps/1.map");
		if (mode == 0) {
			ai.start();
			players = 2;
			level.player[2].dead = true;
			level.player[3].dead = true;
		} else if (mode == 2) {
			host.start();
			players = 2;
			level.player[2].dead = true;
			level.player[3].dead = true;
		}
		this.setTitle("Nukerman : Fallout");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(new BorderLayout()); 
		this.getContentPane().add(new GamePanel());
		this.setVisible(true);
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
		
		@SuppressWarnings("static-access")
		public void movePlayer(int direction, int n) { try{
			level.player[n].state = direction; 
			if (!level.player[n].dead) { 
				int i = level.player[n].speed;
				switch(direction) {
				case 0:
					if (level.move(0, player_x[n], player_y[n], n) == 1) {
						player_x[n] -= i;
						if(level.setPlayer(player_x[n], player_y[n], n) == 1) {
							level.player[n].dead = true;
							Sound dead = new Sound("sounds/dead.mp3");
							dead.start();
							--players;
						}
					} else if (level.map[level.player[n].x-1][level.player[n].y] != 1 && level.map[level.player[n].x-1][level.player[n].y] != 2) {
						if ((player_y[n]+39)/40 != level.player[n].y) movePlayer(2, n);
						else movePlayer(3, n);
					}
					break;
				case 1:
					if (level.move(1, player_x[n], player_y[n], n) == 1) {
						player_x[n] += i;
						if(level.setPlayer(player_x[n], player_y[n], n) == 1) {
							level.player[n].dead = true;
							Sound dead = new Sound("sounds/dead.mp3");
							dead.start();
							--players;
						}
					} else if (level.map[level.player[n].x+1][level.player[n].y] != 1 && level.map[level.player[n].x+1][level.player[n].y] != 2) {
						if ((player_y[n]+39)/40 != level.player[n].y) movePlayer(2, n);
						else movePlayer(3, n);
					}
					break;
				case 2:
					if (level.move(2, player_x[n], player_y[n], n) == 1) {
						player_y[n] -= i;
						if(level.setPlayer(player_x[n], player_y[n], n) == 1) {
							level.player[n].dead = true;
							Sound dead = new Sound("sounds/dead.mp3");
							dead.start();
							--players;
						}
					} else if (level.map[level.player[n].x][level.player[n].y-1] != 1 && level.map[level.player[n].x][level.player[n].y-1] != 2) {
						if ((player_x[n]+39)/40 != level.player[n].x) movePlayer(0, n);
						else movePlayer(1, n);
					}
					break;
				case 3:
					if (level.move(3, player_x[n], player_y[n], n) == 1) {
						player_y[n] += i;
						if(level.setPlayer(player_x[n], player_y[n], n) == 1) {
							level.player[n].dead = true;
							Sound dead = new Sound("sounds/dead.mp3");
							dead.start();
							--players;
						}
					} else if (level.map[level.player[n].x][level.player[n].y+1] != 1 && level.map[level.player[n].x][level.player[n].y+1] != 2) {
						if ((player_x[n]+39)/40 != level.player[n].x) movePlayer(0, n);
						else movePlayer(1, n);
					}
					break;
				}
			} } catch (Exception e) {}
		}
		
		@SuppressWarnings("static-access")
		public void paintComponent(Graphics g) {
			float remain = level.remain;
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
					switch (level.map[i][j]) {
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
			for (int i = 0; i != 4; ++i)
				if (level.map[level.player[i].x][level.player[i].y] == -1 && !level.player[i].dead) {
					level.player[i].dead = true;
					Sound dead = new Sound("sounds/dead.mp3");
					dead.start();
					--players;
				}
			for (int i = 0; i != 4; ++i) {
				if (key_up[i]) movePlayer(0, i);
				else if (key_down[i]) movePlayer(1, i);
				else if (key_left[i]) movePlayer(2, i);
				else if (key_right[i]) movePlayer(3, i);
				if (key_space[i] && !level.player[i].dead) level.plant(i);
			}
			for (int i = 0; i != 4; ++i) if (!level.player[i].dead) {
				switch (level.player[i].state) {
				case 0:
					g.drawImage(new ImageIcon("images/ch_old_up.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 1:
					g.drawImage(new ImageIcon("images/ch_old_down.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 2:
					g.drawImage(new ImageIcon("images/ch_old_left.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 3:
					g.drawImage(new ImageIcon("images/ch_old_right.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 4:
					g.drawImage(new ImageIcon("images/ch_old_up.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 5:
					g.drawImage(new ImageIcon("images/ch_old_down.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 6:
					g.drawImage(new ImageIcon("images/ch_old_left.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
					break;
				case 7:
					g.drawImage(new ImageIcon("images/ch_old_right.pic").getImage(), 100+player_y[i], 65+player_x[i], this);
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
			if (level.player[0].dead) g.drawImage(new ImageIcon("images/del.pic").getImage(), 0, 0, this);
			g.drawImage(new ImageIcon("images/ch_old.pic").getImage(), 0, 500, this);
			if (level.player[1].dead) g.drawImage(new ImageIcon("images/del.pic").getImage(), 0, 500, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/ch_old.pic").getImage(), 755, 0, this);
				if (level.player[2].dead) g.drawImage(new ImageIcon("images/del.pic").getImage(), 755, 0, this);
				g.drawImage(new ImageIcon("images/ch_old.pic").getImage(), 755, 500, this);
				if (level.player[3].dead) g.drawImage(new ImageIcon("images/del.pic").getImage(), 755, 500, this);
			}
			g.drawImage(new ImageIcon("images/ch_bomb3.pic").getImage(), 0, 50, this);
			g.drawImage(new ImageIcon("images/ch_bomb3.pic").getImage(), 0, 460, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/ch_bomb3.pic").getImage(), 755, 50, this);
				g.drawImage(new ImageIcon("images/ch_bomb3.pic").getImage(), 755, 460, this);
			}
			g.drawImage(new ImageIcon("images/ch_leg_2.pic").getImage(), 0, 95, this);
			g.drawImage(new ImageIcon("images/ch_leg_2.pic").getImage(), 0, 420, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/ch_leg_2.pic").getImage(), 755, 95, this);
				g.drawImage(new ImageIcon("images/ch_leg_2.pic").getImage(), 755, 420, this);
			}
			g.drawImage(new ImageIcon("images/ch_power.pic").getImage(), 0, 130, this);
			g.drawImage(new ImageIcon("images/ch_power.pic").getImage(), 0, 375, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/ch_power.pic").getImage(), 755, 130, this);
				g.drawImage(new ImageIcon("images/ch_power.pic").getImage(), 755, 375, this);
			}
			g.drawImage(new ImageIcon("images/"+level.player[0].bomb+".pic").getImage(), 0, 50, this);
			g.drawImage(new ImageIcon("images/"+level.player[1].bomb+".pic").getImage(), 0, 460, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/"+level.player[2].bomb+".pic").getImage(), 755, 50, this);
				g.drawImage(new ImageIcon("images/"+level.player[3].bomb+".pic").getImage(), 755, 460, this);
			}
			g.drawImage(new ImageIcon("images/"+level.player[0].speed+".pic").getImage(), 0, 95, this);
			g.drawImage(new ImageIcon("images/"+level.player[1].speed+".pic").getImage(), 0, 420, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/"+level.player[2].speed+".pic").getImage(), 755, 95, this);
				g.drawImage(new ImageIcon("images/"+level.player[3].speed+".pic").getImage(), 755, 420, this);
			}
			g.drawImage(new ImageIcon("images/"+(level.player[0].range-1)+".pic").getImage(), 0, 130, this);
			g.drawImage(new ImageIcon("images/"+(level.player[1].range-1)+".pic").getImage(), 0, 375, this);
			if (mode == 1) {
				g.drawImage(new ImageIcon("images/"+(level.player[2].range-1)+".pic").getImage(), 755, 130, this);
				g.drawImage(new ImageIcon("images/"+(level.player[3].range-1)+".pic").getImage(), 755, 375, this);
			}
			
			try {Thread.sleep(30); host.refresh(level.map);} catch (Exception e) {}
			repaint();
			if (end) {
				if (mode == 0) ai.stop();
				close();
			}
			else if (players <= 1 || (int)(remain*100) == 0) {
				level.remainer.cancel();
				if (!level.player[0].dead) g.drawImage(new ImageIcon("images/win_0.pic").getImage(), 0, 0, this);
				else if (!level.player[1].dead) g.drawImage(new ImageIcon("images/win_1.pic").getImage(), 0, 0, this);
				else if (!level.player[2].dead) g.drawImage(new ImageIcon("images/win_2.pic").getImage(), 0, 0, this);
				else if (!level.player[3].dead) g.drawImage(new ImageIcon("images/win_3.pic").getImage(), 0, 0, this);
				else g.drawImage(new ImageIcon("images/draw.pic").getImage(), 0, 0, this);
				bg.close();
				new Sound("sounds/end.mp3").start();
				end = true;
			}
		}
		
		private class GameInput implements KeyListener {
			
			public void keyPressed(KeyEvent e) {
				if (mode == 1) {
					for (int i = 0; i != 4; ++i) {
						if (e.getKeyCode() == level.player[i].key[0] && !level.player[i].dead) {
							key_up[i] = true;
						}
						if (e.getKeyCode() == level.player[i].key[1] && !level.player[i].dead) {
							key_down[i] = true;
						}
						if (e.getKeyCode() == level.player[i].key[2] && !level.player[i].dead) {
								key_left[i] = true;
						}
						if (e.getKeyCode() == level.player[i].key[3] && !level.player[i].dead) {
							key_right[i] = true;
						}
						if (e.getKeyCode() == level.player[i].key[4] && !level.player[i].dead) key_space[i] = true;
						if (e.getKeyCode() == level.player[i].key[5] && !level.player[i].dead) key_space[i] = true;
					}
				} else {
					if (e.getKeyCode() == level.player[0].key[0] && !level.player[0].dead) {
						key_up[0] = true;
					}
					if (e.getKeyCode() == level.player[0].key[1] && !level.player[0].dead) {
						key_down[0] = true;
					}
					if (e.getKeyCode() == level.player[0].key[2] && !level.player[0].dead) {
							key_left[0] = true;
					}
					if (e.getKeyCode() == level.player[0].key[3] && !level.player[0].dead) {
						key_right[0] = true;
					}
					if (e.getKeyCode() == level.player[0].key[4] && !level.player[0].dead) key_space[0] = true;
					if (e.getKeyCode() == level.player[0].key[5] && !level.player[0].dead) key_space[0] = true;
				}
			}
			
			public void keyReleased(KeyEvent e) {
				if (mode == 1) {
					for (int i = 0; i != 4; ++i) {
						if (e.getKeyCode() == level.player[i].key[0] && !level.player[i].dead) {
							key_up[i] = false;
						}
						if (e.getKeyCode() == level.player[i].key[1] && !level.player[i].dead) {
							key_down[i] = false;
						}
						if (e.getKeyCode() == level.player[i].key[2] && !level.player[i].dead) {
							key_left[i] = false;
						}
						if (e.getKeyCode() == level.player[i].key[3] && !level.player[i].dead) {
							key_right[i] = false;
						}
						if (e.getKeyCode() == level.player[i].key[4] && !level.player[i].dead) key_space[i] = false;
						if (e.getKeyCode() == level.player[i].key[5] && !level.player[i].dead) key_space[i] = false;
					}
				} else {
					if (e.getKeyCode() == level.player[0].key[0] && !level.player[0].dead) {
						key_up[0] = false;
					}
					if (e.getKeyCode() == level.player[0].key[1] && !level.player[0].dead) {
						key_down[0] = false;
					}
					if (e.getKeyCode() == level.player[0].key[2] && !level.player[0].dead) {
							key_left[0] = false;
					}
					if (e.getKeyCode() == level.player[0].key[3] && !level.player[0].dead) {
						key_right[0] = false;
					}
					if (e.getKeyCode() == level.player[0].key[4] && !level.player[0].dead) key_space[0] = false;
					if (e.getKeyCode() == level.player[0].key[5] && !level.player[0].dead) key_space[0] = false;
				}
			}
			
			public void keyTyped(KeyEvent arg0) {
				//TODO Auto-generated method stub
			}
		}
	}
}
