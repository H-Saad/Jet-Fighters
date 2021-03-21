import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Menu extends MouseAdapter {
	public boolean active = true;
	// boutton jouer
	private Rectangle playbtn;
	private String pstring = "Jouer !";
	private boolean phighlight = false;
	// boutton quiter
	private Rectangle quitbtn;
	private String qstring = "Quitter";
	private boolean qhighlight = false;

	private Font font;

	public Menu(Terrain t) {
		int w, h, x, y;

		w = 100;
		h = 50;

		y = Common.SCREEN_HEIGHT / 2 - h / 2;
		x = Common.SCREEN_WIDTH / 4 - w / 2;

		playbtn = new Rectangle(x, y, w, h);

		x = Common.SCREEN_WIDTH * 3 / 4 - w / 2;

		quitbtn = new Rectangle(x, y, w, h);

		font = new Font("Ink Free", Font.BOLD, 20);
	}

	public void drawMenu(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrics2 = g.getFontMetrics(g.getFont());
		g.drawString("Bienvenue dans Jet Fighters!", Common.SCREEN_WIDTH / 2
				- metrics2.stringWidth("Bienvenue dans Jet Fighters!") / 2, 120);
		
		g.setColor(Color.black);
		g.setFont(new Font("Ink Free",Font.BOLD, 15));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		g.drawString("Realise par: Saad Hadiche",Common.SCREEN_WIDTH - 20 - metrics.stringWidth("Realise par: Saad Hadiche"),Common.SCREEN_HEIGHT-20);
		
		Graphics2D g2d = (Graphics2D) g;
		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		if (phighlight)
			g.setColor(Color.white);
		g2d.fill(playbtn);

		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		if (qhighlight)
			g.setColor(Color.white);
		g2d.fill(quitbtn);

		g.setColor(Color.white);
		g2d.draw(playbtn);
		g2d.draw(quitbtn);
		
		int strw,strh;
		strw = g.getFontMetrics(font).stringWidth(pstring);
		strh = g.getFontMetrics(font).getHeight();
		
		g.setColor(Color.black);
		g.drawString(pstring, (int)(playbtn.getX() + playbtn.getWidth() / 2 - strw / 2), 
				(int) (playbtn.getY() + playbtn.getHeight() / 2 + strh / 4));
		
		strw = g.getFontMetrics(font).stringWidth(qstring);
		strh = g.getFontMetrics(font).getHeight();
		
		g.setColor(Color.black);
		g.drawString(qstring, (int)(quitbtn.getX() + quitbtn.getWidth() / 2 - strw / 2), 
				(int) (quitbtn.getY() + quitbtn.getHeight() / 2 + strh / 4));
	}

	public void mouseClicked(MouseEvent e) {
		Point p = e.getPoint();
		if (playbtn.contains(p))
			active = false;
		if (quitbtn.contains(p))
			System.exit(0);

	}

	public void mouseMoved(MouseEvent e) {
		Point p = e.getPoint();

		this.phighlight = this.playbtn.contains(p);
		this.qhighlight = this.quitbtn.contains(p);
	}
}
