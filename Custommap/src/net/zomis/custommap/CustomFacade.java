package net.zomis.custommap;

import java.lang.reflect.Constructor;

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
		return instance.events;
	}
	
	public EventExecutor getEventExecutor() {
		return this.events ;
	}
	
	public CustomFacade(ZomisLog log) {
		instance = this;
		logger = log;
		logger.i("CustomFacade initialized");
		this.events.registerListener(this);
		this.initialize();
	}
	
	protected void initialize() {}

	public static ZomisLog getLog() {
		return logger;
	}

    protected static CustomFacade instance = null;

    private static int nextId = 1;
    public static int getNextID() {
    	return nextId++;
    }

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
	public static <T> T objAs(Object object, Class<T> to) {
		if (object == null) return null;
		
        if (to.isAssignableFrom(object.getClass())) {
            return to.cast(object);
        }
        return null;
	}
	public CustomFacade setTag(String tag) {
		this.tag = tag;
    	return this;
	}
}