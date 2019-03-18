package cn.appsys.mapper.appVersion;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_version;

public interface AppVersionMapper {
	public List<App_version> getAppVersion();
	public App_version getAppVersionId(@Param("vid") Integer vid);
	public List<App_version>  getAppVersionAppId(@Param("appId")Integer appId);
	public int addVersion(App_version av);
	public App_version getVersionModify(@Param("id") int id,@Param("appId") int appId);
	public int delApkLocPath(@Param("modifyBy")int modifyBy,@Param("modifyDate")Date modifyDate,@Param("id") int id);
	public int modify(App_version app_version);
	public int deleteVersionByAppId(@Param("appId") int appId);
}
