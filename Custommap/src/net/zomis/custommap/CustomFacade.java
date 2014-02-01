package net.zomis.custommap;

import java.lang.reflect.Constructor;

import net.zomis.custommap.view.SwingLog;
import net.zomis.custommap.view.ZomisLog;
import net.zomis.custommap.view.ZomisTimer;
import net.zomis.events.EventExecutor;
import net.zomis.events.EventListener;

/**
 * 
 * @author Zomis
 *
 */
public class CustomFacade implements EventListener {
	private String tag = CustomFacade.LOG_TAG;

	private EventExecutor events = new EventExecutor();
	
    private static final String LOG_TAG = "Zomis";
	
	private static ZomisLog logger;
	
	public static EventExecutor getGlobalEvents() {
		if (instance == null) return null;
		return instance.events;
	}
	public static CustomFacade initializeIfNeeded(ZomisLog logger) {
		if (instance == null)
			instance = new CustomFacade(logger);
		return instance;
	}
	
	public EventExecutor getEventExecutor() {
		return this.events ;
	}
	
	public CustomFacade(ZomisLog log) {
		if (isInitialized()) {
			logger.w("CustomFacade already initialized. This is likely to produce bugs.");
			new IllegalStateException("CustomFacade already initialized. This is likely to produce bugs.").printStackTrace();
		}
		
		instance = this;
		logger = log;
		logger.i("CustomFacade initialized");
		this.events.registerListener(this);
		this.initialize();
	}
	
	protected void initialize() {}

	public static ZomisLog getLog() {
		if (logger == null)
			logger = new SwingLog();
		return logger;
	}

    protected static CustomFacade instance = null;

    public static boolean isInitialized() {
    	return instance != null;
    }
    public static CustomFacade getInst() {
        return instance;
    }

	public String getTag() {
		return this.tag;
	}
	
    public ZomisTimer createTimer(int delay, Runnable runnable) {
    	if (this.timerClass == null) throw new NullPointerException("CustomFacade.timerClass not set.");
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
    public CustomFacade setTimerClass(Class<? extends ZomisTimer> timerClass) {
    	this.timerClass = timerClass;
    	return this;
    }
	public CustomFacade setTag(String tag) {
		this.tag = tag;
    	return this;
	}
}