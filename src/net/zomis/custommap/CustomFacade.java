package net.zomis.custommap;

import net.zomis.custommap.view.SwingLog;
import net.zomis.custommap.view.ZomisLog;
import net.zomis.custommap.view.ZomisTimer;
import net.zomis.events.EventListener;
import net.zomis.events.IEventExecutor;

/**
 * 
 * @author Zomis
 *
 */
public class CustomFacade implements EventListener {
	private String tag = CustomFacade.LOG_TAG;
	private TimerFactory timerFactory;
	private EventFactory eventFactory;

	private IEventExecutor events;
	
    private static final String LOG_TAG = "Zomis";
	
	private static ZomisLog logger;
	
	public static IEventExecutor getGlobalEvents() {
		if (instance == null) 
			return null;
		return instance.getEventExecutor();
	}
	
	public IEventExecutor getEventExecutor() {
		if (events == null)
			events = createEvents();
		return this.events;
	}
	
	public IEventExecutor createEvents() {
		if (eventFactory == null)
			throw new NullPointerException("No Event Factory set");
		return eventFactory.createEventExecutor();
	}
	public CustomFacade(ZomisLog log) {
		if (isInitialized()) {
			logger.w("CustomFacade already initialized. This is likely to produce bugs.");
			new IllegalStateException("CustomFacade already initialized. This is likely to produce bugs.").printStackTrace();
		}
		
		instance = this;
		logger = log;
		logger.i("CustomFacade initialized");
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

    @Deprecated
	public String getTag() {
		return this.tag;
	}
	
    public ZomisTimer createTimer(int delay, Runnable runnable) {
    	if (this.timerFactory == null)
    		throw new NullPointerException("timerFactory not set.");
    	return timerFactory.createTimer(delay, runnable);
    }
    
    public void setEventFactory(EventFactory eventFactory) {
		this.eventFactory = eventFactory;
	}
    
    @Deprecated
	public CustomFacade setTag(String tag) {
		this.tag = tag;
    	return this;
	}
	
	public void setTimerFactory(TimerFactory timerFactory) {
		this.timerFactory = timerFactory;
	}
}