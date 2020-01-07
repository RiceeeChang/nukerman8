import java.io.*;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Level {
	public static int[][] map = new int[11][15];
	public Player[] player = new Player[4];
	public Timer remainer = new Timer();
	public Timer[][] bomb = new Timer[11][15];
	public float remain = 300;
	private Date now = new Date(); 
	private int[][] range = new int[11][15];
	private int[][] bomb_n = new int[11][15];
	private String temp;
	private Random random = new Random();
	private Control main;
	
	public Level(Control main) {
		this.main = main;
		config();
		remainer.schedule(new TimerTask() {

			@Override
			public void run() {
				if (remain > 0) remain -= 0.01;
			}
        	
        }, now, 10);
	}
	
	public void config() { // config.ini 檔是遊戲玩家按鍵設定
		player[0] = new Player("Player_1", 0, 0);
		player[1] = new Player("Player_2", 10, 0);
		player[2] = new Player("Player_3", 0, 14);
		player[3] = new Player("Player_4", 10, 14);
		try {
			BufferedReader br = new BufferedReader(new FileReader("config.ini"));
			for (int i = 0; i != 4; ++i) {
				for (int j = 0; j != 6; ++j) {
					temp = br.readLine();
					player[i].key[j] = Integer.parseInt(temp.split(": ")[1]);
				}
				temp = br.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 設定第n個玩家在地圖上的(x, y)位置
	public int setPlayer(int x, int y, int n) { 
		player[n].x = (x+20)/40;
		player[n].y = (y+20)/40;
		if(map[player[n].x][player[n].y] == -1) {
			return 1;
		}
		return 0;
	}
	// 載入地圖.txt檔
	public int loadMap(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			int x = 0;
			while((temp = br.readLine()) != null){
				for (int y = 0; y != 15; ++y) {
					map[x][y] = Integer.parseInt(temp.substring(y, y+1));
				}
				++x;
			}
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException");
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public int useItem(int i, int n) {
		switch(i) {
		case 5://射程
			++player[n].range;
			break;
		case 6://炸彈
			++player[n].bomb;
			break;
		case 7://走速
			if (player[n].speed == 2) {
				player[n].speed = 4;
				return 2;
			}
			if (player[n].speed == 4) {
				player[n].speed = 5;
				return 4;
			}
			break;
		case 8://穿牆
			player[n].wall = true;
			break;
		case 9://大決
			for (int j = 0; j != 4; ++j)
				player[j].dead = true;
			main.players = 0;
			break;
		}
		return 0;
	}
	// direction: 0: down
	//			  1: up
	//			  2: left
	//			  3: right
	public int move(int direction, int x, int y, int n) {
		int i = player[n].speed-1;
		try {
			switch (direction) {
			case 0:
				if (x-i < 0) break;
				if ((map[(x-i)/40][y/40] == 0 || map[(x-i)/40][y/40] == -1) && (y%40 == 0)) return 1;
				if ((map[(x-i)/40][y/40] == 3) && (player[n].x == (x-i)/40)) return 1;
				if ((map[(x-i)/40][y/40] >= 5) && (y%40 == 0)) {
					main.player_x[n] += useItem(map[(x-i)/40][y/40], n);
					map[(x-i)/40][y/40] = 0;
					return 1;
				}
				break;
			case 1:
				if (x+40+i > 440) break;
				if ((map[(x+40+i)/40][y/40] == 0 || map[(x+40+i)/40][y/40] == -1) && (y%40 == 0)) return 1;
				if ((map[(x+40+i)/40][y/40] == 3) && (player[n].x == (x+40+i)/40)) return 1;
				if ((map[(x+40+i)/40][y/40] >= 5) && (y%40 == 0)) {
					main.player_x[n] -= useItem(map[(x+40+i)/40][y/40], n);
					map[(x+40+i)/40][y/40] = 0;
					return 1;
				}
				break;
			case 2:
				if (y-i < 0) break;
				if ((map[x/40][(y-i)/40] == 0 || map[x/40][(y-i)/40] == -1) && (x%40 == 0)) return 1;
				if ((map[x/40][(y-i)/40] == 3) && (player[n].y == (y-i)/40)) return 1;
				if ((map[x/40][(y-i)/40] >= 5) && (x%40 == 0)) {
					main.player_y[n] += useItem(map[x/40][(y-i)/40], n);
					map[x/40][(y-i)/40] = 0;
					return 1;
				}
				break;
			case 3:
				if (y+40+i > 600) break;
				if ((map[x/40][(y+40+i)/40] == 0 || map[x/40][(y+40+i)/40] == -1) && (x%40 == 0)) return 1;
				if ((map[x/40][(y+40+i)/40] == 3) && (player[n].y == (y+40+i)/40)) return 1;
				if ((map[x/40][(y+40+i)/40] >= 5) && (x%40 == 0)) {
					main.player_y[n] -= useItem(map[x/40][(y+40+i)/40], n);
					map[x/40][(y+40+i)/40] = 0;
					return 1;
				}
				break;
			}
			return 0;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

	public void makeItem(final int bomb_x, final int bomb_y) {
		final int rand = random.nextInt(100);
		if (map[bomb_x][bomb_y] == 2) {
			Timer item = new Timer();
			item.schedule(new TimerTask() {
				//0~19火力20%
				//19~39炸彈20%
				//39~49提速10%
				//49~54穿牆5%
				@Override
				public void run() {
					if (rand < 20) map[bomb_x][bomb_y] = 5;
					else if (rand < 40) map[bomb_x][bomb_y] = 6;
					else if (rand < 50) map[bomb_x][bomb_y] = 7;
					else if (rand < 55) map[bomb_x][bomb_y] = 8;
					else if (rand < 57) map[bomb_x][bomb_y] = 9;
				}
			}, 1000);
		}
	}
	
	public void melt(final int x, final int y) {
		if (map[x][y] >= 5) {
			map[x][y] = -1;
		} else {
			map[x][y] = 4;
			Timer melting = new Timer();
			melting.schedule(new TimerTask() {
				@Override
				public void run() {
					map[x][y] = 0;
				}
			}, 1000);
		}
	}
	
	public void explode(final int bomb_x, final int bomb_y) {
		Sound start = new Sound("sounds/explode.mp3");
        start.start();
		++player[bomb_n[bomb_x][bomb_y]].bomb;
		map[bomb_x][bomb_y] = -1;
		for (int i = 1; i != range[bomb_x][bomb_y]; ++i) {
			if (bomb_x+i <= 10 && map[bomb_x+i][bomb_y] != 1) {
				if (map[bomb_x+i][bomb_y] == 3) {
					bomb[bomb_x+i][bomb_y].cancel();
					explode(bomb_x+i, bomb_y);
				}
				makeItem(bomb_x+i, bomb_y);
				if (map[bomb_x+i][bomb_y] == 2 || map[bomb_x+i][bomb_y] >= 4) {
					melt(bomb_x+i, bomb_y);
					if (player[bomb_n[bomb_x][bomb_y]].wall == false) break;
				} else map[bomb_x+i][bomb_y] = -1;
			} else break;
		}
		for (int i = 1; i != range[bomb_x][bomb_y]; ++i) {
			if (bomb_x-i >= 0 && map[bomb_x-i][bomb_y] != 1) {
				if (map[bomb_x-i][bomb_y] == 3) {
					bomb[bomb_x-i][bomb_y].cancel();
					explode(bomb_x-i, bomb_y);
				}
				makeItem(bomb_x-i, bomb_y);
				if (map[bomb_x-i][bomb_y] == 2 || map[bomb_x-i][bomb_y] >= 4) {
					melt(bomb_x-i, bomb_y);
					if (player[bomb_n[bomb_x][bomb_y]].wall == false) break;
				} else map[bomb_x-i][bomb_y] = -1;
			} else break;
		}
		for (int i = 1; i != range[bomb_x][bomb_y]; ++i) {
			if (bomb_y+i <= 14 && map[bomb_x][bomb_y+i] != 1) {
				if (map[bomb_x][bomb_y+i] == 3) {
					bomb[bomb_x][bomb_y+i].cancel();
					explode(bomb_x, bomb_y+i);
				}
				makeItem(bomb_x, bomb_y+i);
				if (map[bomb_x][bomb_y+i] == 2 || map[bomb_x][bomb_y+i] >= 4) {
					melt(bomb_x, bomb_y+i);
					if (player[bomb_n[bomb_x][bomb_y]].wall == false) break;
				} else map[bomb_x][bomb_y+i] = -1;
			} else break;
		}
		for (int i = 1; i != range[bomb_x][bomb_y]; ++i) {
			if (bomb_y-i >= 0 && map[bomb_x][bomb_y-i] != 1) {
				if (map[bomb_x][bomb_y-i] == 3) {
					bomb[bomb_x][bomb_y-i].cancel();
					explode(bomb_x, bomb_y-i);
				}
				makeItem(bomb_x, bomb_y-i);
				if (map[bomb_x][bomb_y-i] == 2 || map[bomb_x][bomb_y-i] >= 4) {
					melt(bomb_x, bomb_y-i);
					if (player[bomb_n[bomb_x][bomb_y]].wall == false) break;
				} else map[bomb_x][bomb_y-i] = -1;
			} else break;
		}
		Timer reset = new Timer();
		reset.schedule(new TimerTask() {
			@Override
			public void run() {
				for (int i = 0; i != range[bomb_x][bomb_y]; ++i) {
					if (bomb_x+i <= 10 && map[bomb_x+i][bomb_y] >= 5) continue;
					else if (bomb_x+i <= 10 && map[bomb_x+i][bomb_y] != 1) {
						if (map[bomb_x+i][bomb_y] == 2 || map[bomb_x+i][bomb_y] == 3) break;
						map[bomb_x+i][bomb_y] = 0;
					} else break;
				}
				for (int i = 0; i != range[bomb_x][bomb_y]; ++i) {
					if (bomb_x-i >= 0 && map[bomb_x-i][bomb_y] >= 5) continue;
					else if (bomb_x-i >= 0 && map[bomb_x-i][bomb_y] != 1) {
						if (map[bomb_x-i][bomb_y] == 2 || map[bomb_x-i][bomb_y] == 3) break;
						map[bomb_x-i][bomb_y] = 0;
					} else break;
				}
				for (int i = 0; i != range[bomb_x][bomb_y]; ++i) {
					if (bomb_y+i <= 14 && map[bomb_x][bomb_y+i] >= 5) continue;
					else if (bomb_y+i <= 14 && map[bomb_x][bomb_y+i] != 1) {
						if (map[bomb_x][bomb_y+i] == 2 || map[bomb_x][bomb_y+i] == 3) break;
						map[bomb_x][bomb_y+i] = 0;
					} else break;
				}
				for (int i = 0; i != range[bomb_x][bomb_y]; ++i) {
					if (bomb_y-i >= 0 && map[bomb_x][bomb_y-i] >= 5) continue;
					else if (bomb_y-i >= 0 && map[bomb_x][bomb_y-i] != 1) {
						if (map[bomb_x][bomb_y-i] == 2 || map[bomb_x][bomb_y-i] == 3) break;
						map[bomb_x][bomb_y-i] = 0;
					} else break;
				}
			}
		}, 1000);
	}
	
	public int plant(final int n) {
		if (map[player[n].x][player[n].y] == 0 && player[n].bomb != 0) {
			bomb_n[player[n].x][player[n].y] = n;
			--player[n].bomb;
			map[player[n].x][player[n].y] = 3;
			range[player[n].x][player[n].y] = player[n].range;
			final int bomb_x = player[n].x, bomb_y = player[n].y;
			bomb[bomb_x][bomb_y] = new Timer();
			bomb[bomb_x][bomb_y].schedule(new TimerTask() {
				@Override
				public void run() {
					explode(bomb_x, bomb_y);
				}
	        }, 4000);
			return 1;
		}
		return 0;
	}
}
