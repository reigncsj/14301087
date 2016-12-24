package cn.edu.bjtu.weibo.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bjtu.weibo.dao.impl.UserDAOImpl;
import cn.edu.bjtu.weibo.dao.impl.WeiboDAOImpl;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.UserSendedPictureService;

public class UserSendedPictureServiceImpl implements UserSendedPictureService {

	@Override
	public List<Picture> getUserSendedPicture(String userId, int pageIndex, int numberPerPage) {
		UserDAOImpl udi=new UserDAOImpl();
		WeiboDAOImpl wdi=new WeiboDAOImpl();
		List<Picture> l=new ArrayList<Picture>();
		int start=(pageIndex-1)*numberPerPage+1;
		int end=pageIndex*numberPerPage;
		String num=udi.getWeiboNumber(userId);
		int count=0;
		int count1=0;
		for(int i=1;i<=Integer.parseInt(num);i++){
			List<String> list=udi.getWeibo(userId, i, 1);
			List<String> list2=wdi.getWeiboPicurlOr(list.get(0));
			count1=count+list2.size();
			if(count1>=start&&count<end){
				if(count<start){
					for(int j=start-count;j<=list2.size();j++){
						Picture p=new Picture();
						p.setPicurl(list2.get(j-1));
						l.add(p);
					}
				}
				else if(count1>=end){
					for(int j=1;j<=list2.size()+end-count1;j++){
						Picture p=new Picture();
						p.setPicurl(list2.get(j-1));
						l.add(p);
				     }
			    }
				else{
					for(int j=1;j<=list2.size();j++){
						Picture p=new Picture();
						p.setPicurl(list2.get(j-1));
						l.add(p);
				     }
				}
		    }
			count=count1;
		}
		
		return l;
	}
	
	

	
}
