package Aopproxy;

import java.util.List;

public class ProxyFactoryBean implements FooInterface{
	
	private String proxyInterfaces;
	private FooImpl target;
	private List interceptorNames;
	
	public void setProxyInterfaces(String proxyInterfaces){
		this.proxyInterfaces=proxyInterfaces;
	}
	
	public void setTarget(FooImpl target){
		this.target=target;
	}
	
	public void setInterceptorNames(List interceptorNames){
		this.interceptorNames=interceptorNames;
	}
	
	public String getProxyInterfaces(){
		return proxyInterfaces;
	}
	
	public FooImpl getTarget(){
		return target;
	}
	
	public List getInterceptorNames(){
		return interceptorNames;
	}
	@Override
	public void printFoo() {
		target.printFoo();
	}
	@Override
	public void dummyFoo() {
		// TODO Auto-generated method stub
		target.dummyFoo();
	}
	

}
