package cn.edu.tlu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cn.edu.tlu.common.Utils;

/**
 * 面板类
 * @author G
 *
 */
public class MyWordPanel extends JPanel implements Runnable,KeyListener{

	int[] xx = new int[10];
	int[] yy = new int[10];
	char[] words = new char[10];
	int count = 0;
	boolean tag = true;
	Color[] colors = new Color[10];
	JLabel lblCount = new JLabel("得分："+count+"    速度："+count/30+1);
	JLabel lblGameOver = new JLabel("GameOver");
	
	
	public MyWordPanel() {
		
		for (int i = 0; i < words.length; i++) {
			xx[i] = (int) (Math.random() * 780);
			yy[i] = -(int) (Math.random() * 600);
			words[i] = (char) ((Math.random() * 26)+'A');
			colors[i] = new Color((int)(Math.random()*200)+55, (int)(Math.random()*200)+55, (int)(Math.random()*200)+55);
		}
	}	
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int i = 0; i < 10; i++) {
			g.setFont(new Font("微软雅黑", Font.BOLD,20));
			g.setColor(colors[i]);
			g.drawString(words[i]+"", xx[i], yy[i]);
		}
		
		add(lblCount);
		lblCount.setBounds(340, 30, 200, 40);
		lblCount.setForeground(Color.red);
		
		if (count<0) {
			add(lblGameOver);
			lblCount.setBounds(340, 200, 200, 40);
			lblCount.setForeground(Color.red);
		}
		
	}

	@Override
	public void run() {
		
		A:while (true) {
			
			lblCount.setText("得分："+count+"    速度："+(count/30+1));
			
			for (int i = 0; i < 10; i++) {
				yy[i]+=count/30+1;
				if (yy[i]>600) {
					changeWords(i);
					count--;
				}
				if (count<=0 && tag==true) {
					break A;
				}
			}
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			repaint();
		}
	}


	public void changeWords(int i) {
		yy[i]=0;
		xx[i]=(int) (Math.random() * 750);
		char w=(char) ((Math.random() * 26)+'A');
		for (int j = 0; j < words.length; j++) {
			if (w==words[j]) {
				w=(char) ((Math.random() * 26)+'A');
				j=0;
			}
		}
		words[i]=w;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		char word = e.getKeyChar();

		for (int i = 0; i < words.length; i++) {
			if (words[i]==word) {
				changeWords(i);
				colors[i] = new Color((int)(Math.random()*200)+55, (int)(Math.random()*200)+55, (int)(Math.random()*200)+55);
				count++;
				break;
			}
		}
		
		System.out.println("得分："+count);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
}
