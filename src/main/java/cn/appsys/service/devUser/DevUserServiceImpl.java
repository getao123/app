package cn.appsys.service.devUser;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.devUser.DevUserMapper;
import cn.appsys.pojo.Dev_user;

@Service("devUserService")
public class DevUserServiceImpl implements DevUserService{
	@Resource(name="devUserMapper")
	private DevUserMapper devUserMapper;
	@Override
	public Dev_user getDevUserId(String devCode, String devPassword) {
		// TODO Auto-generated method stub
		Dev_user devU=devUserMapper.getDevUserId(devCode);
		if(devU!=null){
			if(!devU.getDevPassword().equals(devPassword)){
				devU=null;
			}
		}
		return devU;
	}
	
}
