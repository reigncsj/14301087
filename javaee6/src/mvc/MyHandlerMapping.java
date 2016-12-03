package mvc;

import java.util.HashMap;
import java.util.Map;

import javaee.Request;

public class MyHandlerMapping implements HandlerMapping {
	private Map<String,Controllers> controller=new HashMap<String,Controllers>() ;
	private Map<String,String> map=new HashMap<String,String>() ;
	public MyHandlerMapping(){
		initMapping();
	}

	@Override
	public Controllers getControllers(Request request) {
		String name=map.get(request.getUri());
		return controller.get(name);
	}

	@Override
	public void initMapping() {
		Scan.doScan(controller, map);
	}

}
