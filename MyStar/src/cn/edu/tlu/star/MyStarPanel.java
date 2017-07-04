package cn.edu.tlu.star;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 满天星-面板类
 * @author G
 *
 */
public class MyStarPanel extends JPanel {

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 画板背景颜色
		this.setBackground(Color.BLACK);
		
		// 字体及大小
		g.setFont(new Font("微软雅黑", Font.BOLD, 13));
		
		for (int i = 0; i < 300; i++) {
			g.drawString("★", (int)(Math.random()*800), (int)(Math.random()*600));
			g.setColor(new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255)));
		}
	}
}