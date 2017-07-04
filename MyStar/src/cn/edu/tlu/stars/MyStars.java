package cn.edu.tlu.stars;

import javax.swing.JFrame;
import javax.swing.event.TreeWillExpandListener;

import cn.edu.tlu.common.Utils;

/**
 * 满天星
 * @author G
 *
 */
public class MyStars {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		Utils.initFrame(frame, 800, 600);
		
		MyStarsPanel panel = new MyStarsPanel();
		frame.add(panel);
		
		Thread thread = new Thread(panel);
		thread.start();
	}
}
