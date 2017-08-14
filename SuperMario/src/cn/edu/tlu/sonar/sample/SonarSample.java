package cn.edu.tlu.sonar.sample;

/**
 * .wav 音乐资源 => 数据+采样率(samplerate)
 * @author G
 *
 */
public class SonarSample {

	public final float[] buf;
	public final float rate;

	/**.wav 音乐资源 => 数据+采样率(samplerate)***/
	public SonarSample(float[] buf, float rate) {
		this.buf = buf;
		this.rate = rate;
	}
}
