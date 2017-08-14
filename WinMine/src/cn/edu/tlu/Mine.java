package cn.edu.tlu;

import javax.swing.JButton;

/**
 * 雷
 * @author G
 *
 */
public class Mine extends JButton {

	private int row, col;	// 按钮的位置坐标
	private int value;		// -1 为地雷
	private boolean isOpen = false; // 是否被翻开
	private boolean isFlag = false; // 是否被翻开
	
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Mine(int row, int col) {
		super();
		this.col = col;
		this.row = row;
		
	}
	
}
