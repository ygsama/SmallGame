package cn.edu.tlu.ball;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 弹球-面板类
 * @author G
 *
 */
public class MyBallPanel extends JPanel implements Runnable {

	// 球的坐标
	int[] xx = new int[10];
	int[] yy = new int[10];

	// 球的运动方向	0↘，1↙，2，↖，3↗
	int[] tag = new int[10];

	// 球的颜色
	static int[] R = new int[10];
	static int[] G = new int[10];
	static int[] B = new int[10];
	
	// 球的运动速度
	int[] speed = new int[10];

	public MyBallPanel() {
		// 初始化
		for (int i = 0; i < 10; i++) {
			tag[i] = (int) (Math.random() * 4);
			xx[i] = (int) (Math.random() * 787);
			yy[i] = (int) (Math.random() * 587);
			speed[i] = (int) (Math.random() * 8)+1;
			changeRGB(i);
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (int i = 0; i < xx.length; i++) {
			// 设置颜色
			g.setColor(new Color(R[i], G[i], B[i]));
			// 画球
			g.fillOval(xx[i], yy[i], 31, 31);
		}

	}

	@Override
	public void run() {

		while (true) {
			for (int i = 0; i < 10; i++) {
				switch (tag[i]) { // 0↘ 1↙ 2↖ 3↗
				case 0:
					xx[i]+=speed[i];
					yy[i]+=speed[i];
					if (xx[i] >= 750 && yy[i] <= 550) {
						tag[i] = 1;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					if (xx[i] < 750 && yy[i] >= 550) {
						tag[i] = 3;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					break;
				case 1:
					xx[i]-=speed[i];
					yy[i]+=speed[i];
					if (xx[i] <= 0 && yy[i] <= 550) {
						tag[i] = 0;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					if (xx[i] < 750 && yy[i] >= 550) {
						tag[i] = 2;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					break;
				case 2:
					xx[i]-=speed[i];
					yy[i]-=speed[i];
					if (xx[i] <= 0 && yy[i] <= 550) {
						tag[i] = 3;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					if (xx[i] <= 750 && yy[i] <= 0) {
						tag[i] = 1;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					break;
				case 3:
					xx[i]+=speed[i];
					yy[i]-=speed[i];
					if (xx[i] >= 750 && yy[i] <= 550) {
						tag[i] = 2;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					if (xx[i] <= 750 && yy[i] <= 0) {
						tag[i] = 0;
						changeRGB(i);
						speed[i] = (int) (Math.random() * 8)+1;
					}
					break;
				}

				repaint();
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static void changeRGB(int i) {
		R[i] = (int) (Math.random() * 200) + 55;
		G[i] = (int) (Math.random() * 200) + 55;
		B[i] = (int) (Math.random() * 200) + 55;
	}
}
