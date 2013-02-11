package net.zomis.custommap.view.android;

import net.zomis.custommap.view.IGameView;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

public interface IAndroidGameView extends IGameView {
	void setPerformClick(boolean value);
	boolean getPerformClick();
	
	boolean onLongClick(View v);
	Rect getScrollBounds();
	NonLayoutingLayout getLayout();
	void addViewToGame(View image, boolean backGround);
	View getLastTouchedView();
	boolean onTouch(View view, MotionEvent event);

	void resize();
	void setZoom(float f);
	int getTileSizeScaled();
	float getMinScaleFactor();
	void setAllowInput(boolean allowInput);
	boolean isInputEnabled();
	float getScaleFactor();
	void updateScrollBounds(boolean reset);
	void playSound(int resId);
}
