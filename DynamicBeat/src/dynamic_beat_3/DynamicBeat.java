package dynamic_beat_3;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class DynamicBeat extends JFrame{
	
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image introBackground;
	private int screenWidth, screenHeight;
	
	public DynamicBeat(int width, int height){
		screenWidth = width;
		screenHeight = height;
		setTitle("Dynamic Beat");
		setSize(screenWidth, screenHeight);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		introBackground = new ImageIcon(this.getClass().getResource("../img/introBackground.jpg")).getImage();//경로는 .class 파일을 기준으로 정함.
		
		Music introMusic = new Music("Skyline  Chillhop.mp3", true);
		introMusic.start();
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(screenWidth, screenHeight);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics g) {
		g.drawImage(introBackground, 0, 0, null);
		this.repaint();
	}
}
