package cn.edu.tlu.mario;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import cn.edu.tlu.sonar.SonarSoundEngine;
import cn.edu.tlu.sonar.sample.SonarSample;

/**
 * 图片与音乐 工具类
 * 
 * @author G
 *
 */
public class Art {

	public static final int SAMPLE_BREAK_BLOCK = 0;		// 破坏？
	public static final int SAMPLE_GET_COIN = 1;		// 得到金币
	public static final int SAMPLE_MARIO_JUMP = 2;		// 向上跳
	public static final int SAMPLE_MARIO_STOMP = 3;		// 踩
	public static final int SAMPLE_MARIO_KICK = 4;		// 踢
	public static final int SAMPLE_MARIO_POWER_UP = 5;	// 力量增加
	public static final int SAMPLE_MARIO_POWER_DOWN = 6;// 力量下降
	public static final int SAMPLE_MARIO_DEATH = 7;		// 死亡
	public static final int SAMPLE_ITEM_SPROUT = 8;		// 物品获得
	public static final int SAMPLE_CANNON_FIRE = 9;		// 炮火 
	public static final int SAMPLE_SHELL_BUMP = 10;		// 碰撞
	public static final int SAMPLE_LEVEL_EXIT = 11;		// ？退出
	public static final int SAMPLE_MARIO_1UP = 12;		// ？
	public static final int SAMPLE_MARIO_FIREBALL = 13;	// 火球

	public static Image[][] mario;		// 马里奥图片
	public static Image[][] smallMario;	// 缩小的马里奥
	public static Image[][] fireMario;	// 消防员马里奥
	public static Image[][] enemies;	// 敌人
	public static Image[][] items;		// 蘑菇和花朵
	public static Image[][] particles;	// 子弹|火球和石头
	public static Image[][] font;		// 特殊字体
	public static Image[][] level;		// 游戏界面装饰素材
	public static Image[][] bg;			// 游戏界面背景素材
	public static Image[][] map;		// 关卡界面背景素材
	public static Image[][] endScene;	// 马里奥胜利图片
	public static Image[][] gameOver;	// 游戏结束小幽灵
	public static Image logo;			// 初始界面logo
	public static Image titleScreen;	// 初始界面标题

	public static SonarSample[] samples = new SonarSample[100];	// 音乐资源=>数据+采样率(samplerate)
	
	private static Sequence[] songs = new Sequence[10];			// 音乐队列
	private static Sequencer sequencer;							// 播放类

	/** 初始化图片和音乐素材   **/
	public static void init(GraphicsConfiguration gc, SonarSoundEngine sound) {
		try {
			/* 初始化图片   **/
			mario = cutImage(gc, "/mariosheet.png", 32, 32);
			smallMario = cutImage(gc, "/smallmariosheet.png", 16, 16);
			fireMario = cutImage(gc, "/firemariosheet.png", 32, 32);
			enemies = cutImage(gc, "/enemysheet.png", 16, 32);
			items = cutImage(gc, "/itemsheet.png", 16, 16);
			level = cutImage(gc, "/mapsheet.png", 16, 16);
			map = cutImage(gc, "/worldmap.png", 16, 16);
			particles = cutImage(gc, "/particlesheet.png", 8, 8);
			bg = cutImage(gc, "/bgsheet.png", 32, 32);
			logo = getImage(gc, "/logo.gif");
			titleScreen = getImage(gc, "/title.gif");
			font = cutImage(gc, "/font.gif", 8, 8);
			endScene = cutImage(gc, "/endscene.gif", 96, 96);
			gameOver = cutImage(gc, "/gameovergost.gif", 96, 64);

			/* 初始化音效  **/
			if (sound != null) {
				samples[SAMPLE_BREAK_BLOCK] = sound.loadSample("/snd/breakblock.wav");
				samples[SAMPLE_GET_COIN] = sound.loadSample("/snd/coin.wav");
				samples[SAMPLE_MARIO_JUMP] = sound.loadSample("/snd/jump.wav");
				samples[SAMPLE_MARIO_STOMP] = sound.loadSample("/snd/stomp.wav");
				samples[SAMPLE_MARIO_KICK] = sound.loadSample("/snd/kick.wav");
				samples[SAMPLE_MARIO_POWER_UP] = sound.loadSample("/snd/powerup.wav");
				samples[SAMPLE_MARIO_POWER_DOWN] = sound.loadSample("/snd/powerdown.wav");
				samples[SAMPLE_MARIO_DEATH] = sound.loadSample("/snd/death.wav");
				samples[SAMPLE_ITEM_SPROUT] = sound.loadSample("/snd/sprout.wav");
				samples[SAMPLE_CANNON_FIRE] = sound.loadSample("/snd/cannon.wav");
				samples[SAMPLE_SHELL_BUMP] = sound.loadSample("/snd/bump.wav");
				samples[SAMPLE_LEVEL_EXIT] = sound.loadSample("/snd/exit.wav");
				samples[SAMPLE_MARIO_1UP] = sound.loadSample("/snd/1-up.wav");
				samples[SAMPLE_MARIO_FIREBALL] = sound.loadSample("/snd/fireball.wav");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			/* 初始化BGM  **/
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			songs[0] = MidiSystem.getSequence(Art.class.getResourceAsStream("/mus/smb3map1.mid"));
			songs[1] = MidiSystem.getSequence(Art.class.getResourceAsStream("/mus/smwovr1.mid"));
			songs[2] = MidiSystem.getSequence(Art.class.getResourceAsStream("/mus/smb3undr.mid"));
			songs[3] = MidiSystem.getSequence(Art.class.getResourceAsStream("/mus/smwfortress.mid"));
			songs[4] = MidiSystem.getSequence(Art.class.getResourceAsStream("/mus/smwtitle.mid"));
		} catch (Exception e) {
			sequencer = null;
			e.printStackTrace();
		}
	}
	
	/** 直接获取图片 **/
	private static Image getImage(GraphicsConfiguration gc, String imageName) throws IOException {
		BufferedImage source = ImageIO.read(Art.class.getResourceAsStream(imageName));
		Image image = gc.createCompatibleImage(source.getWidth(), source.getHeight(), Transparency.BITMASK);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return image;
	}

	/** 切割获取图片数组   **/
	private static Image[][] cutImage(GraphicsConfiguration gc, String imageName, int xSize, int ySize)
			throws IOException {
		Image source = getImage(gc, imageName);
		Image[][] images = new Image[source.getWidth(null) / xSize][source.getHeight(null) / ySize];
		for (int x = 0; x < source.getWidth(null) / xSize; x++) {
			for (int y = 0; y < source.getHeight(null) / ySize; y++) {
				Image image = gc.createCompatibleImage(xSize, ySize, Transparency.BITMASK);
				Graphics2D g = (Graphics2D) image.getGraphics();
				g.setComposite(AlphaComposite.Src);
				g.drawImage(source, -x * xSize, -y * ySize, null);
				g.dispose();
				images[x][y] = image;
			}
		}

		return images;
	}

	/** 播放音乐  **/
	public static void startMusic(int song) {
		stopMusic();
		if (sequencer != null) {// 避免空指针异常
			try {
				sequencer.open();
				sequencer.setSequence((Sequence) null);// 清空队列
				sequencer.setSequence(songs[song]);
				sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
				sequencer.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/** 停止音乐   **/
	public static void stopMusic() {
		if (sequencer != null) {
			try {
				sequencer.stop();
				sequencer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
