package com.eunut.speex;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import com.eunut.speex.encode.SpeexEncoder;

public class SpeexRecorder implements Runnable {
	private volatile boolean isRecording;
	private final Object mutex = new Object();
	private static final int frequency = 8000;
	private static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	public static int packagesize = 160;
	private String fileName = null;
	private Handler callback;
	public SpeexRecorder() {
		super();
	}
	public SpeexRecorder(String fileName) {
		super();
		this.fileName = fileName;
	}
	public void run() {
		// 启动编码线程
		SpeexEncoder encoder = new SpeexEncoder(this.fileName);
		Thread encodeThread = new Thread(encoder);
		encoder.setRecording(true);
		encodeThread.start();
		synchronized (mutex) {
			while (!this.isRecording) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					throw new IllegalStateException("Wait() interrupted!", e);
				}
			}
		}
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
		int bufferRead = 0;
		int bufferSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_MONO, audioEncoding);
		short[] tempBuffer = new short[packagesize];
		AudioRecord recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, AudioFormat.CHANNEL_IN_MONO, audioEncoding, bufferSize);
		recordInstance.startRecording();
		while (this.isRecording) {
			bufferRead = recordInstance.read(tempBuffer, 0, packagesize);
			if (bufferRead == AudioRecord.ERROR_INVALID_OPERATION) {
				throw new IllegalStateException("read() returned AudioRecord.ERROR_INVALID_OPERATION");
			} else if (bufferRead == AudioRecord.ERROR_BAD_VALUE) {
				throw new IllegalStateException("read() returned AudioRecord.ERROR_BAD_VALUE");
			} else if (bufferRead == AudioRecord.ERROR_INVALID_OPERATION) {
				throw new IllegalStateException("read() returned AudioRecord.ERROR_INVALID_OPERATION");
			}
			// 计算白噪声值用于界面上的语音波动显示
			if (callback != null) {
				Message msg = new Message();
				msg.what = 0;
				msg.obj = calculateVolume(tempBuffer);
				callback.sendMessage(msg);
			}
			encoder.putData(tempBuffer, bufferRead);
		}
		recordInstance.stop();
		Message msg = new Message();
		msg.what = 1;
		callback.sendMessage(msg);
		// tell encoder to stop.
		encoder.setRecording(false);
	}
	private double calculateVolume(short[] buffer) {
		double sumVolume = 0.0;
		double avgVolume = 0.0;
		double volume = 0.0;
		for (short b : buffer) {
			sumVolume += Math.abs(b);
		}
		avgVolume = sumVolume / buffer.length;
		volume = Math.log10(1 + avgVolume) * 10;
		return volume;
	}
	public void setRecording(boolean isRecording) {
		synchronized (mutex) {
			this.isRecording = isRecording;
			if (this.isRecording) {
				mutex.notify();
			}
		}
	}
	public boolean isRecording() {
		synchronized (mutex) {
			return isRecording;
		}
	}
	public void setCallback(Handler callback) {
		this.callback = callback;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
