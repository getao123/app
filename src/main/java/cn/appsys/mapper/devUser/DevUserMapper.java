package cn.appsys.mapper.devUser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Dev_user;

public interface DevUserMapper {
	public Dev_user getDevUserId(@Param("devCode") String devCode);
}
