package cn.edu.tlu;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.edu.tlu.common.Utils;

/**
 * 扫雷主窗体
 * @author G
 *
 */
public class MyFrame extends JFrame implements ActionListener,Runnable,MouseListener{

	private JLabel mainLab;				// 主窗体
	private JLabel bar;					// 图标
	private int mineNum;				// 地雷计数器
	private int startMineNum;			// 初始地雷数
	private JLabel timeLab,mineLab; 	// 时间 和 地雷
	private JButton flush,min,close;
	private JButton style1,style2,style3;
	private long start;
	
	private int row, col;	// 行数列数
	private Mine[][] mines;	// 地雷按钮数组
	private JPanel jp;		// 放地雷的面板
	private int left,up;	// 面板在窗体上的位置
	
	public static final int MINE_SIZE = 22;
	
	// 存放不同风格的图片
	private ImageIcon[] mainLabs = {new ImageIcon("./img/back.png"),new ImageIcon("./img/style2/back.png"),new ImageIcon("./img/style3/back.png")};
	private ImageIcon[] style1s = {new ImageIcon("./img/style1.png"),new ImageIcon("./img/style2/style1.png"),new ImageIcon("./img/style3/style1.png")};
	private ImageIcon[] style2s = {new ImageIcon("./img/style2.png"),new ImageIcon("./img/style2/style2.png"),new ImageIcon("./img/style3/style2.png")};
	private ImageIcon[] style3s = {new ImageIcon("./img/style3.png"),new ImageIcon("./img/style2/style3.png"),new ImageIcon("./img/style3/style3.png")};
	private ImageIcon[] btn_backs = {new ImageIcon("./img/but_back.png"),new ImageIcon("./img/style2/but_back.png"),new ImageIcon("./img/style3/but_back.png")};
	private ImageIcon[] btn_opens = {new ImageIcon("./img/btn_open.png"),new ImageIcon("./img/style2/btn_open.png"),new ImageIcon("./img/style3/btn_open.png")};
	private ImageIcon[] btn_flags = {new ImageIcon("./img/hongqi.png"),new ImageIcon("./img/style2/hongqi.png"),new ImageIcon("./img/style3/hongqi.png")};
	private int index = 0;
	
	/* 添加菜单项  **/
	private JMenuBar menuBar;
	private JMenu menu;
	private JCheckBoxMenuItem easy,normal,hard;
	
	
	public MyFrame(int startMineNum ,int row, int col ,int left ,int up) {
		// 初始化
		mainLab = new JLabel(mainLabs[index]);
		style1 = new JButton(style1s[index]);
		style2 = new JButton(style2s[index]);
		style3 = new JButton(style3s[index]);
		bar = new JLabel(new ImageIcon("./img/bar.png"));
		flush = new JButton(new ImageIcon("./img/flush.png"));
		min = new JButton(new ImageIcon("./img/min.png"));
		close = new JButton(new ImageIcon("./img/close.png"));
		
		this.startMineNum = startMineNum;		// 初始雷数量
		mineNum = startMineNum;	// 雷计数器
		timeLab = new JLabel("00:00");
		mineLab = new JLabel(mineNum+"");
		
		this.row = row;		// 19
		this.col = col;		// 15
		this.left = left;	// 85
		this.up = up;		// 100
		mines = new Mine[row][col];
		
		// 设置面板对象
		jp = getMinePanel();
		
		// 设置菜单栏
		menuBar = getMyMenuBar();
		
		// 组装窗体
		init();
		
		// 注册监听器
		addEventHandler();
		
		// 窗体添加鼠标拖拽功能
		Utils.setFrameDrag(this);
		
		Utils.initFrame(this);
	}

	/** 生成菜单栏  **/
	private JMenuBar getMyMenuBar() {
		
		JMenuBar m = new JMenuBar();
		m.setOpaque(false);			// 不透明
		m.setBorderPainted(false);	// 不绘制边框
		
		menu = new JMenu();
		menu.setIcon(new ImageIcon("./img/bar.png"));
		m.add(menu);
		
		easy = new JCheckBoxMenuItem("初级",new ImageIcon("./img/1.gif"));
		normal = new JCheckBoxMenuItem("中级",new ImageIcon("./img/2.gif"));
		hard = new JCheckBoxMenuItem("高级",new ImageIcon("./img/3.gif"));
		
		menu.add(easy);
		menu.add(normal);
		menu.add(hard);
		
		return m;
	}

	/** 生成地雷面板  **/
	private JPanel getMinePanel() {
		JPanel jp = new  JPanel();
		// 设置网格布局管理器
		jp.setLayout(new GridLayout(row, col));
		
		// 创建地雷按钮，添加到数组，面板中
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				mines[i][j] = new Mine(i, j);
				mines[i][j].setIcon(btn_backs[index]);
				mines[i][j].addMouseListener(this);
				jp.add(mines[i][j]);
			}
		}
		
		// 随机设置地雷
		layMines();
		
		// 设置每个按钮的周围地雷数
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (mines[i][j].getValue()==0) {
					mines[i][j].setValue(calMineNum(i,j));
				}
			}
		}
		
		// 测试
		for (int j = 0; j < row; j++) {
			for (int k = 0; k < col; k++) {
				System.out.print(mines[j][k].getValue()>=0?"  "+mines[j][k].getValue():" "+mines[j][k].getValue());
			}
			System.out.println();
		}
		
		return jp;
	}

	/** 放地雷   **/
	private void layMines() {
		for (int i = 0; i < mineNum; i++) {
			int ii = (int)(Math.random() * row);
			int jj = (int)(Math.random() * col);
			if (mines[ii][jj].getValue() == -1) {
				i--;
				continue;
			}
			mines[ii][jj].setValue(-1);
		}
	}
	
	
	/** 计算周围的地雷数量  **/
	private int calMineNum(int i,int j) {
		int num = 0;
		for (int k = i-1; k <= i+1; k++) {
			for (int k2 = j-1; k2 <= j+1; k2++) {
				if ( k>=0 && k2>=0 && k<row && k2<col && mines[k][k2].getValue()==-1) {
					num++;
				}
			}
		}
		return num;
	}

	/** 组装窗体  **/
	private void init() {
		// 取消窗体边框
		super.setUndecorated(true);
		
		// 将mainLab添加到窗体中，当前类没有add方法，此时super比this效率高
		super.add(mainLab);
		
		flush.setBounds(716, 16, 31, 31);	// 设置大小 位置
		flush.setBorderPainted(false);		// 取消边框
		flush.setContentAreaFilled(false);	// 取消背景填充
		mainLab.add(flush);					// 按钮添加到mainLab中
		
		min.setBounds(766, 16, 31, 31);		// 最小化按钮
		min.setBorderPainted(false);
		min.setContentAreaFilled(false);
		mainLab.add(min);
		
		close.setBounds(816, 16, 31, 31);	// 关闭退出按钮
		close.setBorderPainted(false);
		close.setContentAreaFilled(false);
		mainLab.add(close);
		
		style1.setBounds(728, 322, 78, 39);
		style1.setBorderPainted(false);
		style1.setContentAreaFilled(false);
		mainLab.add(style1);
		
		style2.setBounds(728, 390, 78, 39);
		style2.setBorderPainted(false);
		style2.setContentAreaFilled(false);
		mainLab.add(style2);
		
		style3.setBounds(728, 458, 78, 39);
		style3.setBorderPainted(false);
		style3.setContentAreaFilled(false);
		mainLab.add(style3);
		
		menuBar.setBounds(20, 2, 110, 61);					// 扫雷图标
		mainLab.add(menuBar);
		
		timeLab.setFont(new Font("微软雅黑", Font.BOLD, 20));	// 计时器
		timeLab.setBounds(736, 129, 100, 60);
		mainLab.add(timeLab);
		
		mineLab.setFont(new Font("微软雅黑", Font.BOLD, 20));// 地雷数
		mineLab.setBounds(751, 202, 100, 60);
		mainLab.add(mineLab);
		
		jp.setBounds(left, up, col*MINE_SIZE, row*MINE_SIZE);// 地雷面板
		mainLab.add(jp);
		
		// 开启计时器线程
		new Thread(this).start();
		
		// 窗体最小化的图标
		super.setIconImage(new ImageIcon("./img/icon.jpg").getImage());
		
		// 窗体大小自适应
		super.pack();
	}

	/** 注册监听器  **/
	private void addEventHandler() {
		
		// 将当前对象作为监听器
		min.addActionListener(this);
		close.addActionListener(this);
		flush.addActionListener(this);
		style1.addActionListener(this);
		style2.addActionListener(this);
		style3.addActionListener(this);
		easy.addActionListener(this);
		normal.addActionListener(this);
		hard.addActionListener(this);
		
	}
	
	/** ActionListener接口中的方法  **/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == min) {
			this.setState(JFrame.ICONIFIED);
		}else if (source == close) {
			System.exit(0);
		}else if (source == flush) {
			flush();
		}else if (source == style1) {
			index = 0;
			changeStyle();
		}else if (source == style2) {
			index = 1;
			changeStyle();
		}else if (source == style3) {
			index = 2;
			changeStyle();
		}else if (source == easy) {
			new MyFrame(10,10,10,250,200);
			this.dispose();
			
		}else if (source == normal) {
			new MyFrame(50,15,20,120,165);
			this.dispose();
			
		}else if (source == hard) {
			new MyFrame(90,19,25,85,100);
			this.dispose();
			
		}
	}
	
	/** 重新开始游戏  **/
	private void flush() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				mines[i][j].setIcon(btn_backs[index]);
				mines[i][j].setFlag(false);
				mines[i][j].setOpen(false);
				mines[i][j].setValue(0);
				mines[i][j].setValue(calMineNum(i,j));
			}
		}
		
		layMines();
		
		// 设置每个按钮的周围地雷数
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (mines[i][j].getValue()==0) {
					mines[i][j].setValue(calMineNum(i,j));
				}
			}
		}
		start=System.currentTimeMillis();
		mineNum=startMineNum;
	}

	/** 重新设置界面风格图片  **/
	private void changeStyle() {

		mainLab.setIcon(mainLabs[index]);
		style1.setIcon(style1s[index]);
		style2.setIcon(style2s[index]);
		style3.setIcon(style3s[index]);
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (mines[i][j].isFlag()) {		// 旗子
					mines[i][j].setIcon(btn_flags[index]);
				}else {
					if (mines[i][j].isOpen()) {	// 打开
						if (mines[i][j].getValue()==0) {
							mines[i][j].setIcon(btn_opens[index]);
						}
					}else {						// 关闭
						mines[i][j].setIcon(btn_backs[index]);
					}
				}
				jp.add(mines[i][j]);
			}
		}
		
		
	}

	/** 计时器线程run方法  **/
	@Override
	public void run() {
		
		start = System.currentTimeMillis();
		
		DecimalFormat df = new DecimalFormat("00");
		while(true){
			long x = System.currentTimeMillis() - start;
			int minute = (int)(x / 1000 / 60);
			int second = (int)(x / 1000 % 60);
			timeLab.setText(df.format(minute) + ":" + df.format(second));
			mineLab.setText(mineNum + "");
			
			//休眠
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		Object source = e.getSource();
		if (!(source instanceof Mine)) {
			return;
		}
		
		Mine m = (Mine) source;
		
		if (e.getButton()==MouseEvent.BUTTON1) {
			// 鼠标左键点击
			if (m.isFlag() || m.isOpen()==true) {
				return;
			}
			
			m.setOpen(true);
			if (m.getValue()==-1) {			// 点到炸弹
				m.setIcon(new ImageIcon("./img/bomb.gif"));
				openAll();					// 打开全部
				gameover();					// gameover弹窗
				return;
			} else if (m.getValue()==0) {	// 点到空格
				m.setIcon(btn_opens[index]);
				openAround(m);
			} else {						// 点到数字
				m.setIcon(new ImageIcon("./img/"+ m.getValue() +".gif"));
			}
			
		}else if (e.getButton()==MouseEvent.BUTTON2) {
			open9(m);
		}else if (e.getButton()==MouseEvent.BUTTON3) {
			if (m.isOpen()==true) {
				return;
			}
			// 鼠标右键点击
			if (!m.isFlag()) {
				m.setIcon(btn_flags[index]);
				mineNum--;
				m.setFlag(true);
			}else {
				m.setIcon(btn_backs[index]);
				mineNum++;
				m.setFlag(false);
			}
		}
		
		// 判断是否胜利
		win();
	}

	/** 打开所有按钮  **/
	private void openAll() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				openIt(mines[i][j]);
			}
		}
	}

	/** 打开当前按钮  **/
	private void openIt(Mine m) {
		m.setOpen(true);
		if (m.getValue()==-1) {
			m.setIcon(new ImageIcon("./img/bomb.gif"));
		} else if (m.getValue()==0) {
			m.setIcon(btn_opens[index]);
		} else {
			m.setIcon(new ImageIcon("./img/"+ m.getValue() +".gif"));
		}
	}
	/** 打开9个  **/
	private void open9(Mine m) {
		int x = m.getRow();
		int y = m.getCol();
		int flag = 0;
		int num = 0;
		
		for (int k = x-1; k <= x+1; k++) {
			for (int k2 = y-1; k2 <= y+1; k2++) {
				if (k<row && k2 <col && k>=0 && k2>=0) {
					if (mines[k][k2].isFlag()) {
						flag++;
					}
					if (mines[k][k2].getValue()==-1) {
						num++;
					}
				}
			}
		}
		
		if (flag==num) {
			for (int k = x-1; k <= x+1; k++) {
				for (int k2 = y-1; k2 <= y+1; k2++) {
					if (k<row && k2 <col && k>=0 && k2>=0) {
						if (mines[k][k2].isFlag() || mines[k][k2].isOpen()) {
							continue;
						}
						m.setOpen(true);
						if (m.getValue()==-1) {
							m.setIcon(new ImageIcon("./img/bomb.gif"));
							openAll();					// 打开全部
							gameover();					// gameover弹窗
							return;
						} else if (m.getValue()==0) {
							m.setIcon(btn_opens[index]);
						} else {
							m.setIcon(new ImageIcon("./img/"+ m.getValue() +".gif"));
						}
					}
				}
			}
		}
	}
		
	
	/** 打开空格的周围  **/
	private void openAround(Mine m) {
		int x = m.getRow();
		int y = m.getCol();
		
		if (m.getValue() == -1) {
			return;
		}
		
		for (int k = x-1; k <= x+1; k++) {
			for (int k2 = y-1; k2 <= y+1; k2++) {
				if (k<row && k2 <col && k>=0 && k2>=0) {
					if (mines[k][k2].isFlag() || mines[k][k2].isOpen()) {
						continue;
					}
					if (mines[k][k2].getValue()==-1) {
						continue;
					} else if (mines[k][k2].getValue()==0) {
						mines[k][k2].setOpen(true);
						mines[k][k2].setIcon(btn_opens[index]);
						openAround(mines[k][k2]);
					} else {
						mines[k][k2].setOpen(true);
						mines[k][k2].setIcon(new ImageIcon("./img/"+ mines[k][k2].getValue() +".gif"));
					}
				}
			}
		}
	}

	/** 游戏结束  **/
	private void gameover() {
		// 弹出对话框 ----位置,内容消息,标题
		int showConfirmDialog = JOptionPane.showConfirmDialog(this, "GAMEOVER, 是否重新开始", "失败了", JOptionPane.YES_NO_OPTION);
		if (showConfirmDialog == 0) {
			flush();
		}else if (showConfirmDialog == 1) {
			System.exit(0);
		}
	}
	/** 游戏胜利  **/
	private void win() {
		
		int open=0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (mines[i][j].isOpen()) {
					open++;
				}
			}
		}
		if (open + 3 == row*col) {
			// 弹出对话框 ----位置,内容消息,标题
			int showConfirmDialog = JOptionPane.showConfirmDialog(this, "WIN, 是否重新开始", "胜利", JOptionPane.YES_NO_OPTION);
			if (showConfirmDialog == 0) {
				flush();
			}else if (showConfirmDialog == 1) {
				System.exit(0);
			}
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
}


