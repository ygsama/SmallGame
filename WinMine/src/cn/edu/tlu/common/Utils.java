package cn.edu.tlu.common;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Utils {

	// 鼠标按下时的坐标
	private static int xx,yy;
	
	/**
	 * 初始化窗口
	 * @param frame
	 */
	public static void initFrame(JFrame frame){
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * 添加鼠标拖拽窗口功能
	 * @param frame
	 */
	public static void setFrameDrag(final JFrame frame) {
		
		frame.addMouseListener(new MouseAdapter() {
			// 鼠标按下事件
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				xx = e.getX();
				yy = e.getY();
			}
		});
		
		frame.addMouseMotionListener(new MouseAdapter() {
			// 鼠标拖拽事件
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				// 拖拽过程中，鼠标的瞬时坐标
				int x1 = e.getX();
				int y1 = e.getY();
				
				// 记录窗体初始位置
				int x2 = frame.getX();
				int y2 = frame.getY();
				
				// 实时设置窗口位置坐标
				frame.setLocation(x2+x1-xx, y2+y1-yy);
			}
		});
	}
	
}
