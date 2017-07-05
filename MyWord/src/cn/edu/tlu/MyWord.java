package cn.edu.tlu;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.edu.tlu.common.Utils;

/**
 * 打字游戏
 * @author G
 *
 */
public class MyWord {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Utils.initFrame(frame, 800, 600);
		
		final MyWordPanel panel = new MyWordPanel();
		frame.add(panel);
		
		
		
		
		frame.addKeyListener(panel);
		
		Thread thread = new Thread(panel);
		thread.start();
		
		
	}
}
