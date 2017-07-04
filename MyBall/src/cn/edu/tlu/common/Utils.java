package cn.edu.tlu.common;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Utils {

	/**
	 * 初始化窗口
	 * @param frame 窗口实例
	 * @param width 窗口宽度
	 * @param height 窗口高度
	 */
	public static void initFrame(JFrame frame,int width , int height){
		// 获取一个与系统相关工具类对象
		Toolkit toolkit = Toolkit.getDefaultToolkit(); 
		// 获取屏幕的分辨率
		Dimension d = toolkit.getScreenSize();
		int x = (int) d.getWidth();
		int y = (int) d.getHeight();
		frame.setBounds((x-width)/2, (y-height)/2, width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private static void changeX() {
		
		
	}
}
