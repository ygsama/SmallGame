package cn.edu.tlu.ball;

import javax.swing.JFrame;

import cn.edu.tlu.common.Utils;

/**
 * 弹球
 * @author G
 *
 */
public class MyBall {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Utils.initFrame(frame, 800, 600);
		
		MyBallPanel panel = new MyBallPanel();
		frame.add(panel);
		
		Thread thread = new Thread(panel);
		thread.start();
	}
	
}
