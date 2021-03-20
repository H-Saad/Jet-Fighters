import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
public class Spaceship {
	Random rng = new Random();
	private int x = 0;
	private int y = 0;
	private BufferedImage still;
	private double angle = 0;
	private double rotate = 0;
	private int hp = 100;
	private int player_number=0;
	public static ArrayList Bullets,Bullets2;	
	public Spaceship(int n) {
		this.player_number = n;
		Bullets = new ArrayList();
		Bullets2 = new ArrayList();
		File root = new File(System.getProperty("user.dir"));
		File img1 = new File(root, "src/ressources/white.png");
		File img2 = new File(root, "src/ressources/black.png");
		this.respawn();
		if(this.player_number == 1) {
			try {
				still = ImageIO.read(img1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(this.player_number == 2) {
			try {
				still = ImageIO.read(img2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public BufferedImage getImage() {
		return still;
	}
	public void move() {
		this.x += Common.P_SPEED * Math.cos(this.angle);
		this.y += Common.P_SPEED * Math.sin(this.angle);
	}
	public void check_screen_collision() {
		if(this.x > Common.SCREEN_WIDTH) this.x = 0;
		if(this.x < 0)this.x = Common.SCREEN_WIDTH;
		if(this.y > Common.SCREEN_HEIGHT)this.y = 0;
		if(this.y < 0)this.y = Common.SCREEN_HEIGHT;
	}
	public void update() {
		this.move();
		this.check_screen_collision();
		this.angle += this.rotate;
	}
	public void take_dmg() {
		this.hp -= 10;
	}
	public boolean die() {
		if (this.hp <= 0) { return true;}
		return false;
	}
	public void respawn() {
		this.x = 0;
		this.y = rng.nextInt((int)(Common.SCREEN_HEIGHT/Common.UNIT_SIZE))*Common.UNIT_SIZE;
		this.hp = 100;
	}
	public void fire() {
		if(this.player_number == 1) {
			Bullet b = new Bullet(this.x,this.y,this.angle);
			Bullets.add(b);
		}
		else if(this.player_number == 2) {
			Bullet b2 = new Bullet(this.x,this.y,this.angle);
			Bullets2.add(b2);
		}
	}
	public Rectangle hitbox() {
		return (new Rectangle(this.x-2,this.y,19,20));
	}
	public ArrayList getBullets() {
		if(player_number == 1) {
			return Bullets;
		}
		else
			return Bullets2;
	}
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getRotate() {
		return (int)this.rotate;
	}
	public void setRotate(double r) {
		this.rotate = r;
	}
	public double getAngle() {
		return this.angle;
	}
	public int gethp() {
		return this.hp;
	}
}
