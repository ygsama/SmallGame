package cn.edu.tlu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 推箱子-读取地图
 * @author G
 *
 */
class Map {
	private int level, gamerX, gamerY;	// 关卡等级, 玩家X坐标, 玩家Y坐标
	private int[][] map = new int[20][20];
	FileReader fReader;
	BufferedReader bufReader;
	String content = "";
	int[] temp;
	int index = 0;

	public Map(int level) {
		
		this.level = level;
		String s;
		try {
			fReader = new FileReader(new File("maps/" + level + ".map"));
			bufReader = new BufferedReader(fReader);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			while ((s = bufReader.readLine()) != null) {
				content = content + s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] cont = content.getBytes();
		int len = content.length();
		temp = new int[len];
		
		for (int i = 0; i < content.length(); i++)
			temp[i] = cont[i] - 48;
		
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				map[i][j] = temp[index];
				if (map[i][j] == 5) {		// 5的坐标为玩家初始位置
					gamerX = j;
					gamerY = i;
				}
				index++;
			}
		}
		
	}
	int[][] readMap(){
		return map;
	}
	
	int[][] getmap() {
		return map;
	}

	int getGamerX() {
		return gamerX;
	}

	int getGamerY() {
		return gamerY;
	}
}