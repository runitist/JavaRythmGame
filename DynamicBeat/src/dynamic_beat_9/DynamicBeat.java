package dynamic_beat_9;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

//뷰&로직 부분.
//게임 로직은 각 부분이 뷰와 연계되어 무겁기 때문에 배치 순서가 중요.
public class DynamicBeat extends JFrame {

	private Image screenImage;
	private Graphics screenGraphic;
	private int screenWidth, screenHeight;

	// jframe 배경화면
	private Image background = new ImageIcon(this.getClass().getResource("../img/introBackground.jpg")).getImage();

	// 경로는 .class 파일 기준으로 정함.
	private JLabel menuBar = new JLabel(new ImageIcon(this.getClass().getResource("../img/menuBar.png")));
	private ImageIcon exitButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/exitbtn.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/exitbtnentered.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/on-button.png"));
	private ImageIcon startButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/on-buttonen.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/off-button.png"));
	private ImageIcon quitButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/off-buttonen.png"));

	// 곡 선택 버튼
	private ImageIcon leftButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/leftbasic.png"));
	private ImageIcon leftButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/leftentered.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/rightbasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/rightentered.png"));

	//난이도 버튼
	private ImageIcon easyButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/easybtnbasic.png"));
	private ImageIcon easyButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/easybtnentered.png"));
	private ImageIcon hardButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/hardbtnbasic.png"));
	private ImageIcon hardButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/hardbtnentered.png"));
	
	//돌아가기 버튼
	private ImageIcon gobackButtonBasicImage = new ImageIcon(this.getClass().getResource("../img/back.png"));
	private ImageIcon gobackButtonEnteredImage = new ImageIcon(this.getClass().getResource("../img/backent.png"));
	
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton startButton = new JButton(startButtonBasicImage);
	private JButton quitButton = new JButton(quitButtonBasicImage);
	private JButton leftButton = new JButton(leftButtonBasicImage);
	private JButton rightButton = new JButton(rightButtonBasicImage);
	private JButton easyButton = new JButton(easyButtonBasicImage);
	private JButton hardButton = new JButton(hardButtonBasicImage);
	private JButton gobackButton = new JButton(gobackButtonBasicImage);
	private int mouseX, mouseY;

	private boolean isStartScreen = true;
	private boolean isMainScreen = false;

	ArrayList<Track> trackList = new ArrayList<Track>();

	private Music selectedMusic;

	// 타이틀 이미지

	private Image titleImage;
	private Image selectedImage;
	private int nowSelected = 0;

	public DynamicBeat(int width, int height) {// 이 클래스의 인스턴스가 생성되면 자동 동작.
		
		//jframe 프로그램 아이콘 변경
		setIconImage(new ImageIcon(this.getClass().getResource("../img/game640.png")).getImage());
		setUndecorated(true);// 기본 메뉴바 삭제
		screenWidth = width;
		screenHeight = height;
		setTitle("Dynamic Beat");
		setSize(screenWidth, screenHeight);
		setResizable(false);// 사이즈 변경 금지
		setLocationRelativeTo(null);// jframe 창이 뜰때 생성되는 위치. null은 정중앙.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);// 프로그램 보이게 하려면 써야함.
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);// 컴포넌트 위치를 절대좌표로 만듬

		Music introMusic = new Music("Skyline  Chillhop.mp3", true);
		introMusic.start();

		trackList.add(new Track("Imagine Dragons - Believer ( cover by J.Fla ) title.png",
				"Imagine Dragons - Believer ( cover by J.Fla ) 600x450.jpg",
				"Imagine Dragons - Believer ( cover by J.Fla ) 1280x720.jpg",
				"Imagine Dragons - Believer ( cover by J.Fla ) sample.mp3",
				"Imagine Dragons - Believer ( cover by J.Fla ).mp3"));
		trackList.add(new Track("Imagine Dragons - Natural ( cover by J.Fla ) title.png",
				"Imagine Dragons - Natural ( cover by J.Fla ) 600x450.jpg",
				"Imagine Dragons - Natural ( cover by J.Fla ) 1280x720.jpg",
				"Imagine Dragons - Natural ( cover by J.Fla ) sample.mp3",
				"Imagine Dragons - Natural ( cover by J.Fla ).mp3"));
		trackList.add(new Track("Ed Sheeran - Shape Of You ( cover by J.Fla ) title.png",
				"Ed Sheeran - Shape Of You ( cover by J.Fla ) 600x450.jpg",
				"Ed Sheeran - Shape Of You ( cover by J.Fla ) 1280x720.jpg",
				"Ed Sheeran - Shape Of You ( cover by J.Fla ) sample.mp3",
				"Ed Sheeran - Shape Of You ( cover by J.Fla ).mp3"));

		// 메뉴바 x버튼용. 컴포넌트중 가장 위에 위치함.
		exitButton.setBounds(1245, 0, 30, 30);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				Music buttonEntered = new Music("btnpre.mp3", false);// 새로운 음악 재생 쓰레드 생성, 반복 없음.
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

		JLabel jl1 = new JLabel("시작하기");
		jl1.setBounds(1150, 200, 200, 50);
		jl1.setVisible(false);
		add(jl1);
		
		JLabel jl2 = new JLabel("종료하기");
		jl2.setBounds(1150, 350, 200, 50);
		jl2.setVisible(false);
		add(jl2);
		
		// 스타트 버튼용
		startButton.setBounds(1100, 100, 150, 150);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonEnteredImage);
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				jl1.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				jl1.setVisible(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				startButton.setIcon(startButtonBasicImage);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				gobackButton.setVisible(true);
				startButton.setIcon(startButtonEnteredImage);
				jl1.setVisible(false);
				// 게임 시작 이벤트
				introMusic.close();
				selectTrack(0);
				startButton.setVisible(false);
				quitButton.setVisible(false);
				leftButton.setVisible(true);
				rightButton.setVisible(true);
				easyButton.setVisible(true);
				hardButton.setVisible(true);
				background = new ImageIcon(this.getClass().getResource("../img/mainBackground.jpg")).getImage();
				isMainScreen = true;
				isStartScreen = false;
			}
		});
		add(startButton);

		// quit버튼용
		quitButton.setBounds(1100, 250, 150, 150);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jl2.setVisible(true);
				quitButton.setIcon(quitButtonEnteredImage);
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				jl2.setVisible(false);
				quitButton.setIcon(quitButtonBasicImage);
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				quitButton.setIcon(quitButtonBasicImage);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				jl2.setVisible(false);
				quitButton.setIcon(quitButtonEnteredImage);
				System.exit(0);
			}
		});
		add(quitButton);

		// 곡선택 left버튼용
		leftButton.setVisible(false);
		leftButton.setBounds(140, 310, 60, 60);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
		leftButton.setBorderPainted(false);
		leftButton.setContentAreaFilled(false);
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				leftButton.setIcon(leftButtonBasicImage);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				leftButton.setIcon(leftButtonEnteredImage);
				selectLeft();
			}
		});
		add(leftButton);

		// 곡선택 right버튼용
		rightButton.setVisible(false);
		rightButton.setBounds(1080, 310, 60, 60);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
		rightButton.setBorderPainted(false);
		rightButton.setContentAreaFilled(false);
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				rightButton.setIcon(rightButtonBasicImage);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				rightButton.setIcon(rightButtonEnteredImage);
				selectRight();
			}
		});
		add(rightButton);
		
		
		//easy버튼
		easyButton.setVisible(false);
		easyButton.setBounds(375, 580, 150, 150);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
		easyButton.setBorderPainted(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				easyButton.setIcon(easyButtonBasicImage);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				easyButton.setIcon(easyButtonEnteredImage);
				gameStart(nowSelected, "easy");
				gobackButton.setVisible(true);
			}
		});
		add(easyButton);
		
		//hard버튼
				hardButton.setVisible(false);
				hardButton.setBounds(755, 580, 150, 150);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
				hardButton.setBorderPainted(false);
				hardButton.setContentAreaFilled(false);
				hardButton.setFocusPainted(false);
				hardButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						hardButton.setIcon(hardButtonEnteredImage);
						hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						hardButton.setIcon(hardButtonBasicImage);
						hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

					@Override
					public void mousePressed(MouseEvent e) {
						hardButton.setIcon(hardButtonBasicImage);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						hardButton.setIcon(hardButtonEnteredImage);
						gameStart(nowSelected, "hard");
						gobackButton.setVisible(true);
					}
				});
				add(hardButton);
				
				
				// 뒤로가기버튼용
				gobackButton.setVisible(false);
				gobackButton.setBounds(5, 5, 150, 174);// setLayout이 null이여서 setbounds로 위치를 지정해야함.
				gobackButton.setBorderPainted(false);
				gobackButton.setContentAreaFilled(false);
				gobackButton.setFocusPainted(false);
				gobackButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						gobackButton.setIcon(gobackButtonEnteredImage);
						gobackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						gobackButton.setIcon(gobackButtonBasicImage);
						gobackButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}

					@Override
					public void mousePressed(MouseEvent e) {
						gobackButton.setIcon(gobackButtonBasicImage);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						gobackButton.setIcon(gobackButtonEnteredImage);
						
						//뒤로가기 기능
						if(!isMainScreen) {
							backMain();
						}else if(!isStartScreen) {
							backStart();
						}
					}
				});
				add(gobackButton);
				

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

	}

	public void paint(Graphics g) {
		screenImage = createImage(screenWidth, screenHeight);
		screenGraphic = screenImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}

	public void screenDraw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if (isMainScreen) {
			g.drawImage(selectedImage, 340, 100, null);
			g.drawImage(titleImage, 340, 70, null);
		}
		paintComponents(g);
		this.repaint();
	}

	public void selectTrack(int nowSelected) {
		if (selectedMusic != null) {
			selectedMusic.close();
		}
		titleImage = new ImageIcon(this.getClass().getResource("../img/" + trackList.get(nowSelected).getTitleImage()))
				.getImage();
		selectedImage = new ImageIcon(
				this.getClass().getResource("../img/" + trackList.get(nowSelected).getStartImage())).getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}

	public void selectLeft() {
		if (nowSelected == 0) {
			nowSelected = trackList.size() - 1;
		} else {
			nowSelected--;
		}
		selectTrack(nowSelected);
	}
	
	public void selectRight() {
		if (nowSelected == trackList.size()-1) {
			nowSelected = 0;
		} else {
			nowSelected++;
		}
		selectTrack(nowSelected);
	}
	
	public void gameStart(int nowSelected, String difficulty) {
		if(selectedMusic != null) selectedMusic.close();
		isMainScreen = false;
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		background = new ImageIcon(this.getClass().getResource("../img/"+trackList.get(nowSelected).getGameImage())).getImage();
		
	}
	
	public void backMain() {
		gobackButton.setVisible(true);
		selectTrack(nowSelected);
		startButton.setVisible(false);
		quitButton.setVisible(false);
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		background = new ImageIcon(this.getClass().getResource("../img/mainBackground.jpg")).getImage();
		isMainScreen = true;
	}
	
	public void backStart() {
		gobackButton.setVisible(false);
		selectTrack(0);
		selectedMusic.close();
		Music introMusic = new Music("Skyline  Chillhop.mp3", true);
		introMusic.start();
		startButton.setVisible(true);
		quitButton.setVisible(true);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		background = new ImageIcon(this.getClass().getResource("../img/introBackground.jpg")).getImage();
		isMainScreen = false;
		isStartScreen = true;
	}
}
