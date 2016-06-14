package net.zomis.custommap.view.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

@Deprecated
public abstract class MenuActivity extends Activity implements OnClickListener {
	private View oldView = null;
	private RelativeLayout buttonLayout;
	
	protected void addButton(String text) {
		Button button = new Button(buttonLayout.getContext());
		RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		buttonParams.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        button.setText(text);
		button.setOnClickListener(this);
		
		if (oldView != null) {
			button.setId(oldView.getId() + 1);
			buttonParams.setMargins(0, dpToPixels(20), 0, 0);
			buttonParams.addRule(RelativeLayout.BELOW, oldView.getId());
		}
		else {
			button.setId(1);
			buttonParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		}
		
		button.setLayoutParams(buttonParams);
		buttonLayout.addView(button);
		oldView = button;
	}
	
	protected abstract void addButtons();
    
	public static int dpToPixels(int dp, Context context ) {
    	DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		return dp * (metrics.densityDpi / 160);
	}
	private int dpToPixels(int dp) {
		return dp * (this.dpi / 160);
	}
	private int dpi;
	public int getDpi() { return this.dpi; }

	protected RelativeLayout fixOuterLayout() {
		int innerPadding = dpToPixels(42);
		
		RelativeLayout outerLayout = new RelativeLayout(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		outerLayout.setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
		outerLayout.setLayoutParams(params);
		return outerLayout;
	}
	protected RelativeLayout fixInnerLayout() {
		int innerPadding = dpToPixels(42);
		
		RelativeLayout innerLayout = new RelativeLayout(this);
		RelativeLayout.LayoutParams innerParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		innerLayout.setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
		innerParams.addRule(RelativeLayout.CENTER_VERTICAL, 1);
		innerLayout.setBackgroundColor(0xFFFF00FF);
		innerLayout.setLayoutParams(innerParams);
		return innerLayout;
	}
	
	protected void fixLayout() {
    	DisplayMetrics metrics = getResources().getDisplayMetrics();
    	this.dpi = metrics.densityDpi;
    	
		RelativeLayout outerLayout = this.fixOuterLayout();
		RelativeLayout innerLayout = this.fixInnerLayout();
		outerLayout.addView(innerLayout);
		this.buttonLayout = innerLayout;
		
		this.addButtons();
    	this.setContentView(outerLayout);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	this.fixLayout();
    }
    
	@Override
    public abstract boolean onCreateOptionsMenu(Menu menu);
	
	@Override
	public void onClick(View view) {
		if (view instanceof Button) {
			this.buttonClicked(view.getId(), ((Button) view).getText());
		}
	}
	
	@Override
	public abstract boolean onOptionsItemSelected(MenuItem item);
    
	protected abstract void buttonClicked(int buttonId, CharSequence buttonText);
}
