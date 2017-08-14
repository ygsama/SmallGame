package cn.edu.tlu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 推箱子-主面板
 * 	.map文件中，数字   = 方格
 * 	1 = 边界墙
 * 	2 = 路
 * 	3 = 箱子
 * 	4 = 目标位置
 * 	5 = 玩家位置
 * @author G
 *
 */
class MainPanel extends JPanel implements KeyListener {
	int maxLevel = 50;
	int[][] map, maptmp;
	int manX, manY, blanknum;
	Image[] myImage;
	Map Levelmap;
	Map Levelmaptmp;
	int px = 30;
	public int level = 1;
	Stack mystack = new Stack();
	

	MainPanel() {
		super.setBounds(0, 30, 600, 600);
		super.setBackground(Color.WHITE);
		super.addKeyListener(this);
		super.setVisible(true);

		myImage = new Image[10];
		for (int i = 0; i < 10; i++) {
			myImage[i] = Toolkit.getDefaultToolkit().getImage("pic/" + i + ".gif");
		}
	}

	/** 刷新地图 **/
	void getNewMap(int i) {
		Levelmap = new Map(i);
		Levelmaptmp = new Map(i);
		map = Levelmap.getmap();
		manX = Levelmap.getGamerX();
		manY = Levelmap.getGamerY();
		maptmp = Levelmaptmp.getmap();
		repaint();
	}

	/** 最高关卡等级 **/
	int getMaxLevel() {
		return maxLevel;
	}

	/** 画图 **/
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++) {
				g.drawImage(myImage[map[j][i]], i * px, j * px, this);
			}
		g.setColor(Color.BLACK);
		g.setFont(new Font("微软雅黑", Font.BOLD, 30));
		g.drawString("现在是第<"+level+">关", this.getWidth()/2-100, 50);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			moveUp();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			movedown();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			moveleft();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			moveright();
		}
		if (isWin()) {
			JLabel msg = new JLabel();
			msg.setFont(new Font("微软雅黑", Font.PLAIN, 14));
			if (level == maxLevel) {
				msg.setText("恭喜您通过最后一关！！！");
				JOptionPane.showMessageDialog(this, msg);
			} else {
				msg.setText("恭喜您通过第" + level + "关!!!\n是否要进入下一关？");
				int choice = JOptionPane.showConfirmDialog(this, msg, "通过", JOptionPane.YES_NO_OPTION);
				if (choice == 1)
					System.exit(0);
				else if (choice == 0) {
					level++;
					getNewMap(level);
				}
			}
			mystack.removeAllElements();
		}
	}


	boolean isMystackEmpty() {
		return mystack.isEmpty();
	}

	int back() {
		return (Integer) mystack.pop();
	}

	void remove() {
		mystack.removeAllElements();
	}

	/** 向上移动一格 **/
	void moveUp() {
		if (map[manY - 1][manX] == 2 || map[manY - 1][manX] == 4) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
				map[manY][manX] = 4;	// 移动前的格子-->目标格子
			else
				map[manY][manX] = 2;	// 将移动前的格子-->空白格子
			
			map[manY - 1][manX] = 8;	// 当前?
			repaint();					// 重绘面板地图
			manY--;						// 向上走了一步
			mystack.push(10);			// 10(向上走了一步) 推入栈
		} else if (map[manY - 1][manX] == 3) {
			if (map[manY - 2][manX] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 9;
				repaint();
				manY--;
				mystack.push(11);
			} else if (map[manY - 2][manX] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 3;
				repaint();
				manY--;
				mystack.push(11);
			} else {
				map[manY][manX] = 8;
				repaint();
			}
		} else if (map[manY - 1][manX] == 9) {
			if (map[manY - 2][manX] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 9;
				repaint();
				manY--;
				mystack.push(11);
			} else if (map[manY - 2][manX] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY - 1][manX] = 8;
				map[manY - 2][manX] = 3;
				repaint();
				manY--;
				mystack.push(11);
			} else {
				map[manY][manX] = 8;
				repaint();
			}
		}
		if (map[manY - 1][manX] == 1) {
			map[manY][manX] = 8;
			repaint();
		}
	}

	/** 向上走一格的回退   **/
	void backUp(int t) {
		int n = t;
		if (n == 10) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 4;
			} else
				map[manY][manX] = 2;
		} else if (n == 11) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (maptmp[manY - 1][manX] == 4 || maptmp[manY - 1][manX] == 9) {
				map[manY - 1][manX] = 4;
			} else
				map[manY - 1][manX] = 2;
		}
		map[manY + 1][manX] = 8;
		repaint();
		manY++;
	}

	/** 向下移动 **/
	void movedown() {
		if (map[manY + 1][manX] == 2 || map[manY + 1][manX] == 4) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
				map[manY][manX] = 4;
			else
				map[manY][manX] = 2;
			map[manY + 1][manX] = 5;
			repaint();
			manY++;
			mystack.push(20);
		} else if (map[manY + 1][manX] == 3) {
			if (map[manY + 2][manX] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 9;
				repaint();
				manY++;
				mystack.push(21);
			} else if (map[manY + 2][manX] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 3;
				repaint();
				manY++;
				mystack.push(21);
			} else {
				map[manY][manX] = 5;
				repaint();
			}
		} else if (map[manY + 1][manX] == 9) {
			if (map[manY + 2][manX] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 9;
				repaint();
				manY++;
				mystack.push(21);
			} else if (map[manY + 2][manX] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY + 1][manX] = 5;
				map[manY + 2][manX] = 3;
				repaint();
				manY++;
				mystack.push(21);
			} else {
				map[manY][manX] = 5;
				repaint();
			}
		} else if (map[manY + 1][manX] == 1) {
			map[manY][manX] = 5;
			repaint();
		}
	}

	void backdown(int t) {
		int n = t;
		if (n == 20) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 4;
			} else
				map[manY][manX] = 2;
		} else if (n == 21) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (maptmp[manY + 1][manX] == 4 || maptmp[manY + 1][manX] == 9) {
				map[manY + 1][manX] = 4;
			} else
				map[manY + 1][manX] = 2;
		}
		map[manY - 1][manX] = 5;
		repaint();
		manY--;
	}

	void moveleft() {
		if (map[manY][manX - 1] == 2 || map[manY][manX - 1] == 4) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
				map[manY][manX] = 4;
			else
				map[manY][manX] = 2;
			map[manY][manX - 1] = 6;
			repaint();
			manX--;
			mystack.push(30);
		} else if (map[manY][manX - 1] == 3) {
			if (map[manY][manX - 2] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 9;
				repaint();
				manX--;
				mystack.push(31);
			} else if (map[manY][manX - 2] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 3;
				repaint();
				manX--;
				mystack.push(31);
			} else {
				map[manY][manX] = 6;
				repaint();
			}
		} else if (map[manY][manX - 1] == 9) {
			if (map[manY][manX - 2] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 9;
				repaint();
				manX--;
				mystack.push(31);
			} else if (map[manY][manX - 2] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX - 1] = 6;
				map[manY][manX - 2] = 3;
				repaint();
				manX--;
				mystack.push(31);
			} else {
				map[manY][manX] = 6;
				repaint();
			}
		} else if (map[manY][manX - 1] == 1) {
			map[manY][manX] = 6;
			repaint();
		}
	}

	void backleft(int t) {
		int n = t;
		if (n == 30) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 4;
			} else
				map[manY][manX] = 2;
		} else if (n == 31) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (maptmp[manY][manX - 1] == 4 || maptmp[manY][manX - 1] == 9) {
				map[manY][manX - 1] = 4;
			} else
				map[manY][manX - 1] = 2;
		}
		map[manY][manX + 1] = 6;
		repaint();
		manX++;
	}

	void moveright() {
		if (map[manY][manX + 1] == 2 || map[manY][manX + 1] == 4) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
				map[manY][manX] = 4;
			else
				map[manY][manX] = 2;
			map[manY][manX + 1] = 7;
			repaint();
			manX++;
			mystack.push(40);
		} else if (map[manY][manX + 1] == 3) {
			if (map[manY][manX + 2] == 4) {
				if (maptmp[manY][manX] == 4)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 9;
				repaint();
				manX++;
				mystack.push(41);
			} else if (map[manY][manX + 2] == 2) {
				if (maptmp[manY][manX] == 4)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 3;
				repaint();
				manX++;
				mystack.push(41);
			} else {
				map[manY][manX] = 7;
				repaint();
			}
		} else if (map[manY][manX + 1] == 9) {
			if (map[manY][manX + 2] == 4) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 9;
				repaint();
				manX++;
				mystack.push(41);
			} else if (map[manY][manX + 2] == 2) {
				if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9)
					map[manY][manX] = 4;
				else
					map[manY][manX] = 2;
				map[manY][manX + 1] = 7;
				map[manY][manX + 2] = 3;
				repaint();
				manX++;
				mystack.push(41);
			} else {
				map[manY][manX] = 7;
				repaint();
			}
		} else if (map[manY][manX + 1] == 1) {
			map[manY][manX] = 7;
			repaint();
		}
	}

	void backright(int t) {
		int n = t;
		if (n == 40) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 4;
			} else
				map[manY][manX] = 2;
		} else if (n == 41) {
			if (maptmp[manY][manX] == 4 || maptmp[manY][manX] == 9) {
				map[manY][manX] = 9;
			} else
				map[manY][manX] = 3;
			if (maptmp[manY][manX + 1] == 4 || maptmp[manY][manX + 1] == 9) {
				map[manY][manX + 1] = 4;
			} else
				map[manY][manX + 1] = 2;
		}
		map[manY][manX - 1] = 7;
		repaint();
		manX--;
	}

	/** 是否通过当前关卡 **/
	boolean isWin() {
		boolean num = false;
		out: for (int i = 0; i < 20; i++)
			for (int j = 0; j < 20; j++) {
				if (maptmp[i][j] == 4 || maptmp[i][j] == 9)
					if (map[i][j] == 9)
						num = true;
					else {
						num = false;
						break out;
					}
			}
		return num;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}