package cn.appsys.service.appVersion;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_version;

public interface AppVersionService {
	public List<App_version> getAppVersion();
	public App_version getAppVersionId(int vid);
	public List<App_version>  getAppVersionAppId(@Param("appId")Integer appId);
	public int addVersion(App_version av);
	public int delApkLocPath(int modifyBy,Date modifyDate,int id);
	public int modify(App_version app_version);
	public App_version getVersionModify(int id, int appId);
	public boolean deleteVersionByAppId(int appId);
}
