
public class Player {
	public String name;
	public int x, y;
	public int state;
	public int life;
	public int range;
	public int bomb;
	public int speed;
	public int score;
	public int key[] = new int[6];
	public boolean wall, active, dead;
	
	public Player(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		state = 1;
		life = 1;
		range = 2;
		bomb = 1;
		speed = 2;
	}
}
