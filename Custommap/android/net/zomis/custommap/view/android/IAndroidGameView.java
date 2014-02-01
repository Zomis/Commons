package net.zomis.custommap.view.android;

import net.zomis.custommap.view.IGameView;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public interface IAndroidGameView extends IGameView {
	boolean onLongClick(View v);
	Rect getScrollBounds();
	ViewGroup getLayout();
	void addViewToGame(View image, boolean backGround);
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
