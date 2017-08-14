package cn.edu.tlu.mario;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.VolatileImage;
import java.util.Random;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JComponent;

import cn.edu.tlu.mario.sprites.Mario;
import cn.edu.tlu.sonar.FakeSoundEngine;
import cn.edu.tlu.sonar.SonarSoundEngine;

/**
 * 超级玛丽-组件类
 * @author G
 *
 */
public class MarioComponent extends JComponent implements Runnable ,KeyListener, FocusListener{

	public static final int TICKS_PER_SECOND = 24;	

	private int width, height;							// 窗口宽度、高度
	private boolean running = false;					// 
	private boolean focused = false;					// 是否有焦点
	private boolean useScale2x = false; 				// 是否增强图像分辨率
	private GraphicsConfiguration graphicsConfiguration;// 图像配置对象
	private SonarSoundEngine sound;						// 声音控制
	private Scene scene;								// 场景抽象类
	private MapScene mapScene;							// 地图场景
	private Scale2x scale2x = new Scale2x(320, 240);
	
	/** 构造方法   **/
	public MarioComponent(int width, int height) {
		
		super.setFocusable(true);
		super.setEnabled(true);
		this.width = width;
		this.height = height;

		Dimension size = new Dimension(width, height);	// 封装单个对象中组件的宽度和高度，方便管理和使用
		super.setPreferredSize(size);
		super.setMinimumSize(size);
		super.setMaximumSize(size);
		
		try {
			sound = new SonarSoundEngine(64);			// 
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			sound = new FakeSoundEngine();				// 
		}
	
		super.setFocusable(true);						// 得到焦点
		
	}

	/** 键盘按键处理  **/
	private void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_LEFT) {
			scene.toggleKey(Mario.KEY_LEFT, isPressed);		// ← 键
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			scene.toggleKey(Mario.KEY_RIGHT, isPressed);	// → 键 
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			scene.toggleKey(Mario.KEY_DOWN, isPressed);		// ↓ 键
		}
		if (keyCode == KeyEvent.VK_UP) {
			scene.toggleKey(Mario.KEY_UP, isPressed);		// ↑ 键
		}
		if (keyCode == KeyEvent.VK_X) {
			scene.toggleKey(Mario.KEY_SPEED, isPressed);	// X 键
		}
		if (keyCode == KeyEvent.VK_Z) {
			scene.toggleKey(Mario.KEY_JUMP, isPressed);		// Z 键
		}
		if (isPressed && keyCode == KeyEvent.VK_F1) {
			useScale2x = !useScale2x;						// F1 键
		}
	}
	
	@Override
	public void paint(Graphics g) {
	}

	@Override
	public void update(Graphics g) {
	}
	
	/** 开始线程  **/
	public void start() {
		if (!running) {
			running = true;
			new Thread(this, "Game Thread").start();
		}
	}
	
	/** 停止??   **/
	public void stop() {
		Art.stopMusic();
		running = false;
	}
	
	@Override
	public void run() {
		
		graphicsConfiguration = super.getGraphicsConfiguration();

		// scene = new
// LevelScene(graphicsConfiguration);
		mapScene = new MapScene(graphicsConfiguration, this, new Random().nextLong());
		scene = mapScene;		// 设置当前场景
		scene.setSound(sound);	// 设置声音

		Art.init(graphicsConfiguration, sound);

		VolatileImage image = createVolatileImage(320, 240);// 渲染图像类(硬件加速)
		Graphics g = getGraphics();
		Graphics og = image.getGraphics();

		int lastTick = -1;
		// double lastNow = 0;
		int renderedFrames = 0;
		int fps = 0;

		/*
		 * System.nanoTime()
		 * 	返回最准确的可用系统计时器的当前值，以纳秒为单位
		 * 	此方法只能用于测量已过的时间，与系统或钟表时间的其他任何时间概念无关，只能用作计时器
		 */
		double time = System.nanoTime() / 1000000000.0;
		double now = time;					// 计时器起点
		double averagePassedTime = 0;

		super.addKeyListener(this);
		super.addFocusListener(this);

		boolean naiveTiming = true;

		toTitle();

		while (running) {
			double lastTime = time;
			time = System.nanoTime() / 1000000000.0;
			double passedTime = time - lastTime;	// 已用时间

			if (passedTime < 0)
				naiveTiming = false; // Stop
// relying on nanotime if it starts skipping
// around in time (ie running backwards at
// least once). This sometimes happens on
// dual core amds.
			averagePassedTime = averagePassedTime * 0.9 + passedTime * 0.1;

			if (naiveTiming) {
				now = time;
			} else {
				now += averagePassedTime;
			}

			int tick = (int) (now * TICKS_PER_SECOND);
			if (lastTick == -1)
				lastTick = tick;
			while (lastTick < tick) {
				scene.tick();
				lastTick++;

				if (lastTick % TICKS_PER_SECOND == 0) {
					fps = renderedFrames;
					renderedFrames = 0;
				}
			}

			float alpha = (float) (now * TICKS_PER_SECOND - tick);
			sound.clientTick(alpha);

			int x = (int) (Math.sin(now) * 16 + 160);
			int y = (int) (Math.cos(now) * 16 + 120);

			og.setColor(Color.WHITE);
			og.fillRect(0, 0, 320, 240);

			scene.render(og, alpha);

			if (!this.hasFocus() && tick / 4 % 2 == 0) {
				String msg = "CLICK TO PLAY";

				drawString(og, msg, 160 - msg.length() * 4 + 1, 110 + 1, 0);
				drawString(og, msg, 160 - msg.length() * 4, 110, 7);
			}
			og.setColor(Color.BLACK);
			/*
			 * drawString(og, "FPS: " + fps,
			 * 5, 5, 0);
			 * drawString(og, "FPS: " + fps,
			 * 4, 4, 7);
			 */

			if (width != 320 || height != 240) {
				if (useScale2x) {
					g.drawImage(scale2x.scale(image), 0, 0, null);
				} else {
					g.drawImage(image, 0, 0, 640, 480, null);
				}
			} else {
				g.drawImage(image, 0, 0, null);
			}

			renderedFrames++;

			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
			}
		}

		Art.stopMusic();
	}
	
	private void drawString(Graphics g, String text, int x, int y, int c) {
		char[] ch = text.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			g.drawImage(Art.font[ch[i] - 32][c], x + i * 8, y, null);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void startLevel(long seed, int difficulty, int type) {
		scene = new LevelScene(graphicsConfiguration, this, seed, difficulty,
				type);
		scene.setSound(sound);
		scene.init();
	}

	public void levelFailed() {
		scene = mapScene;
		mapScene.startMusic();
		Mario.lives--;
		if (Mario.lives == 0) {
			lose();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		focused = true;
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		focused = false;
	}

	public void levelWon() {
		scene = mapScene;
		mapScene.startMusic();
		mapScene.levelWon();
	}

	public void win() {
		scene = new WinScene(this);
		scene.setSound(sound);
		scene.init();
	}

	public void toTitle() {
		Mario.resetStatic();
		scene = new TitleScene(this, graphicsConfiguration);
		scene.setSound(sound);
		scene.init();
	}

	/** 失败  **/
	public void lose() {
		scene = new LoseScene(this);
		scene.setSound(sound);
		scene.init();
	}

	/** 开始游戏  **/
	public void startGame() {
		scene = mapScene;
		mapScene.startMusic();
		mapScene.init();
	}
}
