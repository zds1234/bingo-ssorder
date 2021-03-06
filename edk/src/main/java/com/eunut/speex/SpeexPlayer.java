package com.eunut.speex;

import java.io.File;

import android.os.Handler;

import com.eunut.speex.encode.SpeexDecoder;

public class SpeexPlayer {
	private String fileName = null;
	private SpeexDecoder speexdec = null;
	private Handler callback = null;
	public SpeexPlayer(String fileName) {
		this.fileName = fileName;
		try {
			speexdec = new SpeexDecoder(new File(this.fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void startPlay() {
		RecordPlayThread rpt = new RecordPlayThread();
		Thread th = new Thread(rpt);
		th.start();
	}
	boolean isPlay = true;
	class RecordPlayThread extends Thread {
		public void run() {
			try {
				if (speexdec != null)
					speexdec.decode();
			} catch (Exception t) {
				t.printStackTrace();
			}
			if(callback!= null)callback.sendEmptyMessage(0);
		}
	};
	public void setCallback(Handler callback) {
		this.callback = callback;
	}
}
