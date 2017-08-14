package cn.edu.tlu.mario.level;

import java.util.*;


/**
 * 改善噪声 类
 * 	程序中一般使用“随机数产生器”，以使得物体的运动行为更加自然，或者用来生成纹理
 * 	Perlin函数,它常用在模拟自然物体的地方，比如地形，海水
 * 	Perlin噪声函数 通过噪声函数来模拟这些自然景观
 * 
 * 	理论：Perlin噪声由多个coherent noise组成，每一个coherent noise称为一个octave.
 * 		octave越多，Perlin噪声就越细致，但计算时间也越长
 * 
 * 	中文资料：
 * 		http://www.cnblogs.com/mikewolf2009/articles/1608087.html
 * 		http://blog.sina.com.cn/s/blog_68f6e8a901013t7d.html
 * 		http://www.cnblogs.com/babyrender/archive/2008/10/27/BabyRender.html
 * 		http://www.fx114.net/qa-23-86109.aspx
 * @author G
 *
 */
public final class ImprovedNoise {
	public ImprovedNoise(long seed) {
		shuffle(seed);
	}

	public double noise(double x, double y, double z) {
		int X = (int) Math.floor(x) & 255, // FIND UNIT CUBE THAT
				Y = (int) Math.floor(y) & 255, // CONTAINS POINT.
				Z = (int) Math.floor(z) & 255;
		x -= Math.floor(x); // FIND RELATIVE X,Y,Z
		y -= Math.floor(y); // OF POINT IN CUBE.
		z -= Math.floor(z);
		double u = fade(x), // COMPUTE FADE CURVES
				v = fade(y), // FOR EACH OF X,Y,Z.
				w = fade(z);
		int A = p[X] + Y, AA = p[A] + Z, AB = p[A + 1] + Z, // HASH COORDINATES
															// OF
				B = p[X + 1] + Y, BA = p[B] + Z, BB = p[B + 1] + Z; // THE 8
																	// CUBE
																	// CORNERS,

		return lerp(w,
				lerp(v, lerp(u, grad(p[AA], x, y, z), // AND ADD
						grad(p[BA], x - 1, y, z)), // BLENDED
						lerp(u, grad(p[AB], x, y - 1, z), // RESULTS
								grad(p[BB], x - 1, y - 1, z))), // FROM 8
				lerp(v, lerp(u, grad(p[AA + 1], x, y, z - 1), // CORNERS
						grad(p[BA + 1], x - 1, y, z - 1)), // OF CUBE
						lerp(u, grad(p[AB + 1], x, y - 1, z - 1), grad(p[BB + 1], x - 1, y - 1, z - 1))));
	}

	/**
	 * 改变透明度？？
	 * @param t
	 * @return
	 */
	double fade(double t) {
		return t * t * t * (t * (t * 6 - 15) + 10);
	}

	/**
	 * 插值函数
	 * @param t
	 * @param a
	 * @param b
	 * @return
	 */
	double lerp(double t, double a, double b) {
		return a + t * (b - a);
	}

	/**
	 * 梯度
	 * @param hash
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	double grad(int hash, double x, double y, double z) {
		int h = hash & 15; // CONVERT LO 4 BITS OF HASH CODE 转换成四bit的哈希码
		double u = h < 8 ? x : y, // INTO 12 GRADIENT DIRECTIONS. 分成12个梯度方向
				v = h < 4 ? y : h == 12 || h == 14 ? x : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	int p[] = new int[512];// , permutation[] = {151, 160, 137, 91, 90, 15, 131,
							// 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36,
							// 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23,
							// 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62,
							// 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33,
							// 88, 237, 149, 56, 87, 174, 20, 125, 136, 171,
							// 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166,
							// 77, 146, 158, 231, 83, 111, 229, 122, 60, 211,
							// 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244,
							// 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73,
							// 209, 76, 132, 187, 208, 89, 18, 169, 200, 196,
							// 135, 130, 116, 188, 159, 86, 164, 100, 109, 198,
							// 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5,
							// 202, 38, 147, 118, 126, 255, 82, 85, 212, 207,
							// 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42,
							// 223, 183, 170, 213, 119, 248, 152, 2, 44, 154,
							// 163, 70, 221, 153, 101, 155, 167, 43, 172, 9,
							// 129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224,
							// 232, 178, 185, 112, 104, 218, 246, 97, 228, 251,
							// 34, 242, 193, 238, 210, 144, 12, 191, 179, 162,
							// 241, 81, 51, 145, 235, 249, 14, 239, 107, 49,
							// 192, 214, 31, 181, 199, 106, 157, 184, 84, 204,
							// 176, 115, 121, 50, 45, 127, 4, 150, 254, 138,
							// 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141,
							// 128, 195, 78, 66, 215, 61, 156, 180};

	/**
	 * perlin噪声 | 柏林噪声
	 * 	各种各样不同频率和幅度的光滑函数(smooth function)，组合在一起
	 * 	就能产生一个比较好的Perlin噪声函数
	 * @param x
	 * @param y
	 * @return
	 */
	public double perlinNoise(double x, double y) {
		double n = 0;

		for (int i = 0; i < 8; i++) {
			double stepSize = 64.0 / ((1 << i));
			n += noise(x / stepSize, y / stepSize, 128) * 1.0 / (1 << i);
		}

		return n;
	}

	/**
	 * 噪声函数 
	 * 	本质上就是一个基于种子的随机数产生器。输入参数为一个整数，输出结果为基于输入参数的随机数。
	 * 	如果你两次输入同样的参数，则结果都是一样的
	 * @param seed
	 */
	public void shuffle(long seed) {
		Random random = new Random(seed);
		int permutation[] = new int[256];
		
		for (int i = 0; i < 256; i++) {
			permutation[i] = i;
		}

		for (int i = 0; i < 256; i++) {
			int j = random.nextInt(256 - i) + i;
			int tmp = permutation[i];
			permutation[i] = permutation[j];
			permutation[j] = tmp;
			p[i + 256] = p[i] = permutation[i];
		}
	}
}