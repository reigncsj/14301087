package mvc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javaee.Request;

public class Adpater {
	
	public static ModelAndView doParse(Request request,Controllers c){
		Map<String,String> m=c.getMethod();
		String method=m.get(request.getUri());
		Map<String ,Object> par=request.getParameterMap();
		ModelAndView mav=new ModelAndView();
		mav.setMap(par);
		
		try {
			Method method1=c.getClass1().getMethod(method, ModelAndView.class);
			
				return (ModelAndView) method1.invoke(c.getObject(), mav);
		}
	    catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

}
