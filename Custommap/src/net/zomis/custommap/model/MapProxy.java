package net.zomis.custommap.model;

import org.puremvc.java.interfaces.IProxy;
import org.puremvc.java.patterns.proxy.Proxy;
/**
 * A proxy should work as an API between application and Model.
 * Put some methods here for ON_CLICK on gates?
 * @author Zomis
 *
 */
@Deprecated
public class MapProxy extends Proxy implements IProxy {

	public MapProxy(String proxyName, Object data) {
		super(proxyName, data);
	}
	
}