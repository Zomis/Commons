package net.zomis.custommap;

import java.lang.reflect.Constructor;

import net.zomis.custommap.view.IGameView;
import net.zomis.custommap.view.ZomisLog;
import net.zomis.custommap.view.ZomisTimer;
import net.zomis.custommap.view.general.TileInterface;
import net.zomis.custommap.view.general.ViewObject;

import org.puremvc.java.patterns.facade.Facade;

/**
 * 
 * @author Zomis
 *
 */
public abstract class CustomFacade extends Facade {
	private String tag = CustomFacade.LOG_TAG;
	
    /**
     * Sent when onSizeChanged is called for {@link NonLayoutingLayout}<br>
     * Sends {@link IGameView} as context.
     */
    public static final String GAME_INIT = "GAME_INIT";
    
    public static final String LOG_TAG = "Zomis";
    public static final String LOG_TAG_MVC = "PureMVC";
    
    @Deprecated
	public static final String USER_SCALE = "USER_SCALE";
	
	public static final String USER_DOUBLE_TAP = "USER_DOUBLE_TAP";
	
	public static final String USER_TOUCH_DOWN = "USER_TOUCH_DOWN";
	/**
	 * Sent when onClick is called on a {@link TileInterface}<br>
	 * Sends {@link TileInterface} as context
	 */
	public static final String USER_CLICK_TILE = "USER_CLICK_TILE";
	/**
	 * Sent when user swipes the screen (onFling is called in Android GestureListener)<br>
	 * Sends {@link IGameView} as context
	 */
	public static final String USER_FLING = "USER_FLING";
	/**
	 * Sent when user scrolls the {@link IGameView}.<br>
	 * Sends {@link IGameView} as context
	 */
	public static final String USER_SCROLL = "USER_SCROLL";
	/**
	 * Sent when user long presses the {@link IGameView}
	 * Sends some kind of FlingScrollEvent as context (depends on platform)
	 */
	public static final String USER_LONG_PRESS_MAP = "USER_LONG_PRESS_NEW";
	/**
	 * Sent when user long presses a MapPaintable
	 * Sends the {@link ViewObject} that was pressed as context
	 */
	public static final String USER_LONG_PRESS_PAINTABLE = "USER_LONG_PRESS_PAINTABLE";
	public static final String MAP_CENTER = "MAP_CENTER";
	public static final String GAME_CHANGE = "map_change";
	
	protected static ZomisLog logger;
	
	public CustomFacade(ZomisLog log) {
		instance = this;
		logger = log;
		logger.i("CustomFacade initialized");
	}
	
	public static ZomisLog getLog() {
		return logger;
	}

    protected static CustomFacade instance = null;

    private static int nextId = 1;
    public static int getNextID() {
    	return nextId++;
    }
    public CustomFacade() {
    	instance = this;
    }

    public static boolean isInitialized() {
    	return instance != null;
    }
    public static CustomFacade getInst() {
        return instance;
    }

    @Override
    protected void initializeController() {
    	super.initializeController();
    }
    
	public String getTag() {
		return this.tag;
	}
	
    public ZomisTimer createTimer(int delay, Runnable runnable) {
    	try {
    		Constructor<? extends ZomisTimer> c = this.timerClass.getDeclaredConstructor(Integer.class, Runnable.class);
//    		c.setAccessible(true);
//			timerClass.newInstance();
	    	return c.newInstance(delay, runnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    private Class<? extends ZomisTimer> timerClass;
    public void setTimerClass(Class<? extends ZomisTimer> timerClass) {
    	this.timerClass = timerClass;
    }
    
	public static <T> T objAs(Object object, Class<T> to) {
		if (object == null) return null;
		
        if (to.isAssignableFrom(object.getClass())) {
            return to.cast(object);
        }
        return null;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}