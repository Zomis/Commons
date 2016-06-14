package net.zomis.custommap.view.android;

import net.zomis.custommap.view.ZomisLog;
import android.util.Log;

public class AndroidLog extends ZomisLog {
	public AndroidLog(String tag) {
		this.tag = tag;
	}
	@Override
	public void v(String tag, String data) {
		Log.v(tag, data);
	}

	@Override
	public void d(String tag, String data) {
		Log.d(tag, data);
	}

	@Override
	public void i(String tag, String data) {
		Log.i(tag, data);
	}

	@Override
	public void w(String tag, String data) {
		Log.w(tag, data);
	}

	@Override
	public void e(String tag, String data) {
		Log.e(tag, data);
	}

	@Override
	public void e(String msg, Throwable exception) {
		Log.e("Zomis", msg, exception);
	}
}
