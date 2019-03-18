package cn.appsys.mapper.backenduser;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Backend_user;

public interface Backend_userMapper {
	public Backend_user getBackendUser(@Param("userCode")String userCode);
}
