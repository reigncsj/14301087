package test;

import ioc.MyBeanFactory;

public class test {

    public static void main(String[] args) {
        String[] locations = {"bean.xml"};
        MyBeanFactory mbf=new MyBeanFactory();
        boss boss = (boss) mbf.getBean("boss");
        System.out.println(boss.tostring());
    }
}