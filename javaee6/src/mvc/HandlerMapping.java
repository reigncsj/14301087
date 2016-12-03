package mvc;


import javaee.Request;

public interface HandlerMapping {
    public Object getControllers(Request request);
    public void initMapping();
}
