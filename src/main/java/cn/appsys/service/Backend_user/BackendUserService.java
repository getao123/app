package cn.appsys.service.Backend_user;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.Backend_user;

public interface BackendUserService {

	public Backend_user getBackendUser(String userCode,String passWord);
}
