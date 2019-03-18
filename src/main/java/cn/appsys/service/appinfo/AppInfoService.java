package cn.appsys.service.appinfo;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_info;

public interface AppInfoService {
	public List<App_info> getAppInfoList(String querySoftwareName,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer queryFlatformId,
			Integer devId,
			Integer currentPageNo,
			Integer pageSize,
			Integer status)throws Exception;

	public int getAppInfoCount(String querySoftwareName,
			Integer queryCategoryLevel1,
			Integer queryCategoryLevel2,
			Integer queryCategoryLevel3,
			Integer queryFlatformId,
			Integer devId,
			Integer status)throws Exception;
	public App_info getAppInfo(Integer aId);
	public List<App_info> getAppInfoAPKName(String APKName);
	public int updateStatus(int status,int id);
	public int updateAppId(String versionInfo,
			double versionSize,
			int appId);
	public boolean add(App_info appinfo);
	public int modify(App_info appinfo);
	public int updateFile(int id);
	public boolean updateOnSaleDate(App_info ai);
}
