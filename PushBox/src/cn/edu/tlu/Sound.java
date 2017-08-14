package cn.edu.tlu;

import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

/**
 * 推箱子-BGM类
 * @author G
 *
 */
class Sound {
	
	String path = new String("musics/");
	String file = new String("default.mid");
	Sequence sequence;
	Sequencer midi;
	boolean sign;		// 标记状态，是否正在播放bgm

	void loadSound() {
		try {
			sequence = MidiSystem.getSequence(new File(path + file));
			midi = MidiSystem.getSequencer();
			midi.open();
			midi.setTempoInBPM(76);	// 设置播放速度76拍/分钟
			midi.setSequence(sequence);
			midi.start();
			midi.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
			midi.close();
		}
		sign = true;
	}

	/** 停止播放bgm  **/
	void stop() {
		midi.stop();
		midi.close();
		sign = false;
	}

	/** 是否正在播放bgm  **/
	boolean isPlay() {
		return sign;
	}

	/** 修改bgm  **/
	void changeMusic(String bgm) {
		file = bgm;
	}
}
