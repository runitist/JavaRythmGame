package dynamic_beat_4;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DynamicBeat extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	private int screenWidth, screenHeight;

	private Image introBackground = new ImageIcon(this.getClass().getResource("../img/introBackground.jpg")).getImage();
	// 경로는 .class 파일 기준으로 정함.
	private JLabel menuBar = new JLabel(new ImageIcon(this.getClass().getResource("../img/menuBar.png")));
	private ImageIcon exitButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/exitbtn.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/exitbtnentered.png"));
	
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private int mouseX, mouseY;
	

	public DynamicBeat(int width, int height) {//이 클래스의 인스턴스가 생성되면 자동 동작.
		setUndecorated(true);// 기본 메뉴바 삭제
		screenWidth = width;
		screenHeight = height;
		setTitle("Dynamic Beat");
		setSize(screenWidth, screenHeight);
		setResizable(false);//사이즈 변경 금지
		setLocationRelativeTo(null);//jframe 창이 뜰때 생성되는 위치. null은 정중앙.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);//프로그램 보이게 하려면 써야함.
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);// 컴포넌트 위치를 절대좌표로 만듬

		exitButton.setBounds(1245, 0, 30, 30);//setLayout이 null이여서 setbounds로 위치를 지정해야함.
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEntered = new Music("btnpre.mp3", false);//새로운 음악 재생 쓰레드 생성, 반복 없음.
				buttonEntered.start();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				exitButton.setIcon(exitButtonBasicImage);
				Music buttonPress = new Music("btnpre.mp3", false);
				buttonPress.start();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				System.exit(0);
			}
		});
		add(exitButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);

		

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
		paintComponents(g);
		this.repaint();
	}
}
