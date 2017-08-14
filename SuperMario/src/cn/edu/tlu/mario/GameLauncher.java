package cn.edu.tlu.mario;

import javax.swing.JFrame;

/**
 * 超级玛丽-游戏入口
 * @author G
 *
 */
public class GameLauncher {
	
	public static void main(String[] args) {
		MarioComponent mario = new MarioComponent(640, 480);	// 内容组件
		JFrame frame = new JFrame("Mario Test");
		frame.setContentPane(mario);							// 给窗体设置内容组件
		frame.pack();											// 窗体大小自适应
		frame.setResizable(false);								// 窗体边框不可变
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 退出
		frame.setLocationRelativeTo(null);						// 窗体位置在屏幕中间
		frame.addKeyListener(mario);							// 添加键盘监听器
		frame.addFocusListener(mario);							// 添加焦点监听器
		frame.setVisible(true);									// 窗体设置可见
		mario.start();											// 线程开启
	}
}
