package net.zomis.custommap.view.android;

import net.zomis.custommap.view.ZomisLog;
import android.util.Log;

@Deprecated
public class AndroidLog extends ZomisLog {
	public void v(String tag, String data) {
		Log.v(tag, data);
	}

	public void d(String tag, String data) {
		Log.d(tag, data);
	}

	public void i(String tag, String data) {
		Log.i(tag, data);
	}

	public void w(String tag, String data) {
		Log.w(tag, data);
	}

	public void e(String tag, String data) {
		Log.e(tag, data);
	}
}
