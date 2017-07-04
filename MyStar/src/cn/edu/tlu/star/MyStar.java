package cn.edu.tlu.star;

import javax.swing.JFrame;

import cn.edu.tlu.common.Utils;

/**
 * 满天星
 * @author G
 *
 */
public class MyStar {

//	int eye = 2;
//	String gender = "男";
	
	public static void main(String[] args) {
//		System.out.println("Hello World");
		
		JFrame frame = new JFrame();
		Utils.initFrame(frame, 800, 600);
		
		MyStarPanel panel = new MyStarPanel();
		frame.add(panel);
		
	}
}