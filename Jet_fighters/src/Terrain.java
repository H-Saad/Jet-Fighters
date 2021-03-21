import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import javax.swing.*;
import java.util.ArrayList;

public class Terrain extends JPanel implements ActionListener{
	int p1_score = 0;
	int p2_score = 0;
	boolean run = false;
	Timer clock;
	Spaceship p1 = new Spaceship(1);
	Spaceship p2 = new Spaceship(2);
	Menu menu = new Menu(this);
	int locationX = p2.getImage().getWidth()/2;
	int locationY = p2.getImage().getHeight()/2;
	
	
	Terrain(){
		this.setPreferredSize(new Dimension(Common.SCREEN_WIDTH,Common.SCREEN_HEIGHT));
		this.setBackground(Color.darkGray);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyListener());
		this.addMouseListener(menu);
		this.addMouseMotionListener(menu);
		clock = new Timer(Common.DELAY,this);
		clock.start();
		startGame();
		
	}
	public void startGame() {
		run = true;
	}
	public void paint(Graphics g) {
		super.paintComponent(g);
		if(menu.active) {
			menu.drawMenu(g);
		}
		if(run && !menu.active) {
			drawScores(g);
			drawHealth(g);
			drawPlayers(g);
			drawBullets(g);
		}
		if(!run && !menu.active)
			gameOver(g);
	}
	public void drawScores(Graphics g) {
		//p1
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free",Font.BOLD, 15));
		g.drawString("Score J1: "+p1_score, 20, 15);
		//p2
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.BOLD, 15));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score J2: "+p2_score, (Common.SCREEN_WIDTH - metrics.stringWidth("Score J2: "+p2_score))-20, 15);
	}
	public void drawHealth(Graphics g) {
		//p1
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free",Font.BOLD, 15));
		g.drawString("Vie J1: "+p1.gethp(), 15, Common.SCREEN_WIDTH-20);
		//p2
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.BOLD, 15));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Vie J2: "+p2.gethp(),Common.SCREEN_WIDTH - 20 - metrics.stringWidth("Vie J2: "+p2.gethp()),Common.SCREEN_HEIGHT-20);
	}
	public void drawPlayers(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//p1 rotation
		AffineTransform tx = AffineTransform.getRotateInstance(p1.getAngle(),locationX,locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		//p2 rotation
		AffineTransform tx1 = AffineTransform.getRotateInstance(p2.getAngle(),locationX,locationY);
		AffineTransformOp op1 = new AffineTransformOp(tx1, AffineTransformOp.TYPE_BILINEAR);
		//player1 draw
		g2d.drawImage(op.filter(p1.getImage(), null), p1.getX(), p1.getY(), null);
		//player 2 draw
		g2d.drawImage(op1.filter(p2.getImage(), null), p2.getX(), p2.getY(), null);
	}
	public void drawBullets(Graphics g) {
		//bullets1
		ArrayList bullets = p1.getBullets();
		for(int i = 0; i<bullets.size(); i++) {
			Bullet b = (Bullet)bullets.get(i);
			g.setColor(Color.white);
			g.fillOval(b.getX(), b.getY(), Common.BULLET_SIZE, Common.BULLET_SIZE);
		}
		//bullets2
		ArrayList bullets2 = p2.getBullets();
		for(int i = 0; i<bullets2.size(); i++) {
			Bullet b2 = (Bullet)bullets2.get(i);
			g.setColor(Color.black);
			g.fillOval(b2.getX(), b2.getY(), Common.BULLET_SIZE, Common.BULLET_SIZE);
		}
	}
	public void gameOver(Graphics g) {
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		if(p1_score > p2_score) {
			g.setColor(Color.white);
			g.drawString("Joueur 1 Gagne!", (Common.SCREEN_WIDTH - metrics.stringWidth("Joueur 1 Gagne!"))/2, Common.SCREEN_HEIGHT/2);
		}
		else if(p1_score < p2_score) {
			g.setColor(Color.black);
			g.drawString("Joueur 2 Gagne!", (Common.SCREEN_WIDTH - metrics.stringWidth("Joueur 2 Gagne!"))/2, Common.SCREEN_HEIGHT/2);
		}
		else {
			g.setColor(Color.green);
			g.drawString("Egalite!", (Common.SCREEN_WIDTH - metrics.stringWidth("Egalite!"))/2, Common.SCREEN_HEIGHT/2);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(run) {
			p1.update();
			p2.update();
			//p1 bullets and dmg
			ArrayList bullets = p1.getBullets();
			for(int i = 0; i<bullets.size(); i++) {
				Bullet b = (Bullet)bullets.get(i);
				Rectangle r1 = b.hitbox();
				Rectangle r2 = p2.hitbox();
				if(b.getVisible()) {
					if(r1.intersects(r2)) {
						b.setVisible(false);
						p2.take_dmg();
					}
					if(p2.die()) {
						p1_score++;
						p2.respawn();
					}
					b.update();
				}
				else bullets.remove(i);
			}
			//p2 bullets and dmg
			ArrayList bullets2 = p2.getBullets();
			for(int i = 0; i<bullets2.size(); i++) {
				Bullet b2 = (Bullet)bullets2.get(i);
				Rectangle r1 = b2.hitbox();
				Rectangle r2 = p1.hitbox();
				if(b2.getVisible()) {
					if(r1.intersects(r2)) {
						b2.setVisible(false);
						p1.take_dmg();
					}
					if(p1.die()) {
						p2_score++;
						p1.respawn();
					}
					b2.update();
				}
				else bullets2.remove(i);
			}
			if(p1_score == 10 || p2_score == 10) {
				run = false;
				clock.stop();
			}
		}
		repaint();
		
	}
	public class MyKeyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			switch(key) {
			case 38:
				//up1
				p1.fire();
				break;
			case 37:
				//left1
				p1.setRotate(-Common.ROTATE);
				break;
			case 39:
				//right1
				p1.setRotate(Common.ROTATE);
				break;
			case 87:
				//up2
				p2.fire();
				break;
			case 65:
				//left2
				p2.setRotate(-Common.ROTATE);
				break;
			case 68:
				//right2
				p2.setRotate(Common.ROTATE);
				break;
			}	
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			int key = e.getKeyCode();
			switch(key) {
			case 38:
				//up1
				break;
			case 37:
				//left1
				p1.setRotate(0);
				break;
			case 39:
				//right1
				p1.setRotate(0);
				break;
			case 87:
				//up2
				break;
			case 65:
				//left2
				p2.setRotate(0);
				break;
			case 68:
				//right2
				p2.setRotate(0);
				break;
			}
		}
		
	}
}