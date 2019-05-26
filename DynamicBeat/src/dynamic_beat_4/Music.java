package dynamic_beat_4;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

//음악 재생 외부 라이브러리 사용
public class Music extends Thread {

	private Player player;
	private boolean isloop;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;

	public Music(String name, boolean isLoop) {
		try {
			this.isloop = isLoop;
			file = new File(this.getClass().getResource("../music/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getTime() {
		if (player == null) {
			return 0;
		}
		return player.getPosition();
	}

	public void close() {
		isloop = false;
		player.close();
		this.interrupt();
	}

	@Override
	public void run() {
		try {
			do {
				player.play();
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				player = new Player(bis);
			} while (isloop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
