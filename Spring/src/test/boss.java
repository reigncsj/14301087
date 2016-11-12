package test;

import ioc.Autowired;

public class boss {
	
  @Autowired
  private office office;
  @Autowired
  private car car;

  public String tostring(){
	  return "this boss has "+car.toString()+"and in "+office.toString();
  }
}
