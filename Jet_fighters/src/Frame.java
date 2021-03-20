import javax.swing.JFrame;

public class Frame extends JFrame {
	Frame(){
		Terrain t = new Terrain();
		this.add(t);
		this.setTitle("Jet Fighters");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}