package cn.appsys.service.devUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Dev_user;

public interface DevUserService {
	public Dev_user getDevUserId(String devCode,String devPassword);
}
