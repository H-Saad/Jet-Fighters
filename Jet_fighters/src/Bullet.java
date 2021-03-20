import java.awt.*;
import javax.swing.*;

public class Bullet extends JPanel{
	private int x=0;
	private int y=0;
	private int timeAlive = 0;
	private double angle = 0;
	private boolean visible;
	public Bullet(int x1, int y1, double ang) {
		this.x = x1+9;
		this.y = y1+9;
		this.angle = ang;
		this.visible = true;
	}
	public void update() {
		this.x += Common.BULLET_SPEED * Math.cos(this.angle);
		this.y += Common.BULLET_SPEED * Math.sin(this.angle);
	    this.check_screen_collision();
	    this.timeAlive++;
	    this.expire();
	}
	public void check_screen_collision() {
		if(this.x > Common.SCREEN_WIDTH) this.x = 0;
		if(this.x < 0)this.x = Common.SCREEN_WIDTH;
		if(this.y > Common.SCREEN_HEIGHT)this.y = 0;
		if(this.y < 0)this.y = Common.SCREEN_HEIGHT;
	}
	public void expire() {
		if(this.timeAlive>25) {
			this.visible = false;
		}
	}
	public Rectangle hitbox() {
		return (new Rectangle(this.x,this.y,Common.BULLET_SIZE,Common.BULLET_SIZE));
	}
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean arg) {
		this.visible = arg;
	}
}