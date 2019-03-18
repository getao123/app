package cn.appsys.mapper.appinfo;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.appsys.pojo.App_info;

public interface AppInfoMapper {
	public List<App_info> getAppInfoList(@Param(value="softwareName")String querySoftwareName,@Param(value="categoryLevel1")Integer queryCategoryLevel1,
										@Param(value="categoryLevel2")Integer queryCategoryLevel2,
										@Param(value="categoryLevel3")Integer queryCategoryLevel3,
										@Param(value="flatformId")Integer queryFlatformId,
										@Param(value="devId")Integer devId,
										@Param(value="from")Integer currentPageNo,
										@Param(value="pageSize")Integer pageSize,
										@Param(value="status")Integer status)throws Exception;
	
	public int getAppInfoCount(@Param(value="softwareName")String querySoftwareName,
							   @Param(value="categoryLevel1")Integer queryCategoryLevel1,
							   @Param(value="categoryLevel2")Integer queryCategoryLevel2,
							   @Param(value="categoryLevel3")Integer queryCategoryLevel3,
							   @Param(value="flatformId")Integer queryFlatformId,
							   @Param(value="devId")Integer devId,
							   @Param(value="status")Integer status)throws Exception;
	public App_info getAppInfo(@Param("aId")Integer aId);
	public List<App_info> getAppInfoAPKName(@Param("APKName") String APKName);
	public int updateStatus(@Param("status")int status,@Param("id")int id);
	public int updateAppId(@Param("versionInfo")String versionInfo,
			@Param("versionSize")double versionSize,
			@Param("appId")int appId);
	public int add(App_info appinfo);
	public int updateVersionId(@Param("versionId") int versionId,@Param("status")int status,
			@Param("id")int id);
	public int modify(App_info app_info);
	public int updateFile(@Param("id")int id);
	public int deleteAppInfoById(@Param("id") int id);
	
	public int updateOnSaleDate(App_info ai);
}
