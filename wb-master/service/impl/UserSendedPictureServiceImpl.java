package cn.edu.bjtu.weibo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.bjtu.weibo.dao.ImgDAO;
import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.WeiboDAO;
import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.UserSendedPictureService;

@Service("UserSendedPictureService")
public class UserSendedPictureServiceImpl implements UserSendedPictureService {
	@Autowired
	private UserDAO userDao;
	@Autowired
	private WeiboDAO weiboDao;
	@Autowired
	private ImgDAO imgDao;

	@Override
	public List<Picture> getUserSendedPicture(String userId, int pageIndex, int numberPerPage) {
		List<Picture> l=new ArrayList<Picture>();
		int start=(pageIndex-1)*numberPerPage+1;
		int end=pageIndex*numberPerPage;
		int num=userDao.getWeiboNumber(userId);
		int count=0;
		int count1=0;
		for(int i=1;i<=num;i++){
			List<String> list=userDao.getWeibo(userId, i, 1);
			List<String> list2=weiboDao.getWeiboPicurl(list.get(0));
			count1=count+list2.size();
			if(count1>=start&&count<end){
				if(count<start){
					for(int j=start-count;j<=list2.size();j++){
						Picture p=new Picture();
						p.setPicurl(imgDao.getimgThUrl(list2.get(j-1)));
						p.setPicurlor(imgDao.getimgOrUrl(list2.get(j-1)));
						l.add(p);
					}
				}
				else if(count1>=end){
					for(int j=1;j<=list2.size()+end-count1;j++){
						Picture p=new Picture();
						p.setPicurl(imgDao.getimgThUrl(list2.get(j-1)));
						p.setPicurlor(imgDao.getimgOrUrl(list2.get(j-1)));
						l.add(p);
				     }
			    }
				else{
					for(int j=1;j<=list2.size();j++){
						Picture p=new Picture();
						p.setPicurl(imgDao.getimgThUrl(list2.get(j-1)));
						p.setPicurlor(imgDao.getimgOrUrl(list2.get(j-1)));
						l.add(p);
				     }
				}
		    }
			count=count1;
		}
		
		return l;
		
	}

}
