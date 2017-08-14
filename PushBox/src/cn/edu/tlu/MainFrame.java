package cn.edu.tlu;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 * 推箱子-主窗体
 * @author G
 *
 */
class MainFrame extends JFrame implements ActionListener, ItemListener {
	
	Sound sound;
	JLabel lible;
	MainPanel mainPanel;
	JButton btnReNew, btnLast, btnNext, btnChoose, btnFirst, btNover, btnMusic, btnBack;
	final Font font = new Font("微软雅黑", Font.BOLD, 14);
	
	JMenuItem itemRenew = new JMenuItem("重新开始");
	JMenuItem itemBack = new JMenuItem("退一步");
	JMenuItem itemLast = new JMenuItem("上一关");
	JMenuItem itemNext = new JMenuItem("下一关");
	JMenuItem itemChoose = new JMenuItem("选关");
	JMenuItem itemExit = new JMenuItem("退出");
	JMenuItem itemAbout = new JMenuItem("关于推箱子...");
	
	JComboBox jComboBox = new JComboBox();
	JMenuItem itemBGM1 = new JMenuItem("琴萧合奏");
	JMenuItem itemBGM2 = new JMenuItem("泡泡堂");
	JMenuItem itemBGM3 = new JMenuItem("灌篮高手");
	JMenuItem itemBGM4 = new JMenuItem("eyes on me");
	JMenuItem itemDefualtBGM = new JMenuItem("默认");

	JMenu choiceMenu = new JMenu("选项");		// 菜单栏选项
	JMenu musicMenu = new JMenu("设置音乐");	// 菜单栏选项
	JMenu helpMenu = new JMenu("帮助");		// 菜单栏选项
	JMenuBar menuBar = new JMenuBar();		// 菜单栏
	
	MainFrame() {
		super("推箱子");
		super.setSize(720, 630);
		super.setVisible(true);
		super.setResizable(false);
		super.setLocation(300, 20);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setLayout(null);
		super.setLocationRelativeTo(null);
		
		// 窗体初始化
		init();
		
		super.validate();
	}

	/** 窗体初始化  **/
	private void init() {
		
		
		/************** 添加最小化图标   **************/
		super.setIconImage(new ImageIcon("pic/3.gif").getImage());
		
		/************** 菜单栏字体   **************/
		itemRenew.setFont(font);
		itemLast.setFont(font);
		itemNext.setFont(font);
		itemChoose.setFont(font);
		itemBack.setFont(font);
		itemExit.setFont(font);
		itemDefualtBGM.setFont(font);
		
		itemBGM1.setFont(font);
		itemBGM2.setFont(font);
		itemBGM3.setFont(font);
		itemBGM4.setFont(font);
		itemAbout.setFont(font);
		
		choiceMenu.setFont(font);
		musicMenu.setFont(font);
		helpMenu.setFont(font);
		menuBar.setFont(font);
		
		/************** 添加菜单栏   **************/
		
		choiceMenu.add(itemRenew);
		choiceMenu.add(itemLast);
		choiceMenu.add(itemNext);
		choiceMenu.add(itemChoose);
		choiceMenu.add(itemBack);
		choiceMenu.addSeparator();// 添加分割线
		choiceMenu.add(itemExit);
		
		musicMenu.add(itemDefualtBGM);
		musicMenu.add(itemBGM1);
		musicMenu.add(itemBGM2);
		musicMenu.add(itemBGM3);
		musicMenu.add(itemBGM4);
		itemDefualtBGM.setEnabled(false);
		
		helpMenu.add(itemAbout);
		
		menuBar.add(choiceMenu);
		menuBar.add(musicMenu);
		menuBar.add(helpMenu);
		menuBar.setBounds(1, 1, this.getWidth(), 30);
		this.add(menuBar);
		
		/**************** 添加右侧按钮  ****************/
		
		lible = new JLabel("换音乐:", SwingConstants.CENTER);
		lible.setBounds(600, 500, 80, 20);
		lible.setForeground(Color.BLACK);
		lible.setFont(font);
		super.add(lible);
		
		btnReNew = new JButton("重来");
		btnReNew.setFont(font);
		btnReNew.setBounds(620, 100, 80, 30);
		super.add(btnReNew);
		
		btnBack = new JButton("退一步");
		btnBack.setBounds(620, 150, 80, 30);
		btnBack.setFont(font);
		super.add(btnBack);

		btnLast = new JButton("上一关");
		btnLast.setBounds(620, 250, 80, 30);
		btnLast.setFont(font);
		super.add(btnLast);

		btnNext = new JButton("下一关");
		btnNext.setBounds(620, 300, 80, 30);
		btnNext.setFont(font);
		super.add(btnNext);
		
		btnChoose = new JButton("选关卡");
		btnChoose.setBounds(620, 400, 80, 30);
		btnChoose.setFont(font);
		super.add(btnChoose);
		
		btnFirst = new JButton("第1关");
		btnFirst.setBounds(620, 200, 80, 30);
		btnFirst.setFont(font);
		super.add(btnFirst);
		
		btNover = new JButton("最终关");
		btNover.setBounds(620, 350, 80, 30);
		btNover.setFont(font);
		super.add(btNover);
		
		btnMusic = new JButton("关音乐");
		btnMusic.setBounds(620, 450, 80, 30);
		btnMusic.setFont(font);
		super.add(btnMusic);
		
		jComboBox.setBounds(610, 530, 100, 20);
		jComboBox.setFont(font);
		jComboBox.addItem("默认");
		jComboBox.addItem("琴萧合奏");
		jComboBox.addItem("泡泡堂");
		jComboBox.addItem("灌篮高手");
		jComboBox.addItem("eyes on me");
		super.add(jComboBox);

		sound = new Sound();
		sound.loadSound();
		
		/**************** 添加主面板   ******************/
		
		mainPanel = new MainPanel();
		mainPanel.getNewMap(mainPanel.level);
		super.add(mainPanel);
		
		super.paint(getGraphics());
		mainPanel.requestFocus();
		
		// 添加监听器
		addEventHandler();
	}

	/** 注册监听器  **/
	private void addEventHandler() {
		
		itemRenew.addActionListener(this);
		itemLast.addActionListener(this);
		itemNext.addActionListener(this);
		itemChoose.addActionListener(this);
		itemExit.addActionListener(this);
		itemBack.addActionListener(this);
		itemAbout.addActionListener(this);
		
		itemBGM1.addActionListener(this);
		itemBGM2.addActionListener(this);
		itemBGM3.addActionListener(this);
		itemBGM4.addActionListener(this);
		itemDefualtBGM.addActionListener(this);
		
		btnReNew.addActionListener(this);
		btnBack.addActionListener(this);
		btnLast.addActionListener(this);
		btnNext.addActionListener(this);
		btnChoose.addActionListener(this);
		btnFirst.addActionListener(this);
		btNover.addActionListener(this);
		btnMusic.addActionListener(this);
		jComboBox.addItemListener(this);
		
	}
	
	/** 监听动作事件   **/
	public void actionPerformed(ActionEvent e) {
		JLabel msg = new JLabel();
		msg.setFont(font);
		if (e.getSource() == btnReNew || e.getSource() == itemRenew) {
			// 重新开始
			mainPanel.getNewMap(mainPanel.level);
			mainPanel.requestFocus();
			mainPanel.remove();
			
		} else if (e.getSource() == btnLast || e.getSource() == itemLast) {
			// 上一关
			mainPanel.level--;
			if (mainPanel.level < 1) {
				mainPanel.level++;
				msg.setText("本关是第一关");
				JOptionPane.showMessageDialog(this, msg, "哎呀", JOptionPane.YES_OPTION);
				mainPanel.requestFocus();
			} else {
				mainPanel.getNewMap(mainPanel.level);
				mainPanel.requestFocus();
			}
			mainPanel.remove();
			
		} else if (e.getSource() == btnNext || e.getSource() == itemNext) {
			// 下一关
			mainPanel.level++;
			if (mainPanel.level > mainPanel.getMaxLevel()) {
				mainPanel.level--;
				msg.setText("本关已是最后一关");
				JOptionPane.showMessageDialog(this, msg, "哎呀", JOptionPane.YES_OPTION);
				mainPanel.requestFocus();
			} else {
				mainPanel.getNewMap(mainPanel.level);
				mainPanel.requestFocus();
			}
			mainPanel.remove();
			
		} else if (e.getSource() == itemExit)
			System.exit(0);
		
		else if (e.getSource() == itemAbout) {
			msg.setText("Java版 推箱子");
			JOptionPane.showMessageDialog(this, msg);
			
		} else if (e.getSource() == btnChoose || e.getSource() == itemChoose) {
			// 选择关卡
			msg.setText("请输入目标关卡号：(1~50)");
			String lel = JOptionPane.showInputDialog(this, msg);
			try {
				int level = Integer.parseInt(lel);
				mainPanel.remove();
				if ( mainPanel.level < 1 || level > 50) {
					msg.setText("没有这一关！！！");
					JOptionPane.showMessageDialog(this, msg, "哎呀", JOptionPane.YES_OPTION);
				} else {
					mainPanel.level = level;
					mainPanel.getNewMap(mainPanel.level);
				}
				mainPanel.requestFocus();
			} catch (NumberFormatException ex) {
				mainPanel.requestFocus();
				return;
			}
			
		} else if (e.getSource() == btnFirst) {
			// 第一关
			mainPanel.level = 1;
			mainPanel.getNewMap(mainPanel.level);
			mainPanel.requestFocus();
			mainPanel.remove();
		} else if (e.getSource() == btNover) {
			// 最后一关
			mainPanel.level = mainPanel.getMaxLevel();
			mainPanel.getNewMap(mainPanel.level);
			mainPanel.requestFocus();
			mainPanel.remove();
		} else if (e.getSource() == btnMusic) {
			// 音乐开关
			if (sound.isPlay()) {
				sound.stop();
				btnMusic.setText("开音乐");
			} else {
				sound.loadSound();
				btnMusic.setText("关音乐");
			}
			mainPanel.requestFocus();
			
		} else if (e.getSource() == btnBack || e.getSource() == itemBack) {
			// 退一步
			if (mainPanel.isMystackEmpty()){
				msg.setText("您还未移动！！！");
				JOptionPane.showMessageDialog(this, msg);
			}
			else {
				switch (mainPanel.back()) {
				case 10:
					mainPanel.backUp(10);
					break;
				case 11:
					mainPanel.backUp(11);
					break;
				case 20:
					mainPanel.backdown(20);
					break;
				case 21:
					mainPanel.backdown(21);
					break;
				case 30:
					mainPanel.backleft(30);
					break;
				case 31:
					mainPanel.backleft(31);
					break;
				case 40:
					mainPanel.backright(40);
					break;
				case 41:
					mainPanel.backright(41);
					break;
				}
			}
			mainPanel.requestFocus();
		} else if (e.getSource() == itemDefualtBGM) {
			jComboBox.setSelectedIndex(0);
		} else if (e.getSource() == itemBGM1) {
			jComboBox.setSelectedIndex(1);
		} else if (e.getSource() == itemBGM3) {
			jComboBox.setSelectedIndex(3);
		} else if (e.getSource() == itemBGM4) {
			jComboBox.setSelectedIndex(4);
		} else if (e.getSource() == itemBGM2) {
			jComboBox.setSelectedIndex(2);
		}
	}

	/** 换BGM   **/
	@Override
	public void itemStateChanged(ItemEvent e) {
		int itemIndex = jComboBox.getSelectedIndex();
		switch (itemIndex) {
		case 0:
			setBGM("default.mid",new boolean[]{false,true,true,true,true});
			break;
		case 1:
			setBGM("nor.mid",new boolean[]{true,false,true,true,true});
			break;
		case 2:
			setBGM("popo.mid",new boolean[]{true,true,false,true,true});
			break;
		case 3:
			setBGM("guang.mid",new boolean[]{true,true,true,false,true});
			break;
		case 4:
			setBGM("eyes on me.mid",new boolean[]{true,true,true,true,false});
			break;
		}
	}

	/** 设置BGM  **/
	private void setBGM(String bgmName,boolean[] swicth) {
		sound.changeMusic(bgmName);
		if (sound.isPlay())
			sound.stop();
		sound.loadSound();
		btnMusic.setText("关音乐");
		itemDefualtBGM.setEnabled(swicth[0]);
		itemBGM1.setEnabled(swicth[1]);
		itemBGM2.setEnabled(swicth[2]);
		itemBGM3.setEnabled(swicth[3]);
		itemBGM4.setEnabled(swicth[4]);
		mainPanel.requestFocus();
	}
}