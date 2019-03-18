package cn.appsys.service.appinfo;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.mapper.appinfo.AppInfoMapper;
import cn.appsys.pojo.App_info;
@Service("appInfoService")
@Transactional
public class AppInfoServiceImpl implements AppInfoService{
	@Resource(name="appInfoMapper")
	private AppInfoMapper appInfoMapper;
	@Override
	@Transactional(readOnly=true)
	public List<App_info> getAppInfoList(String querySoftwareName,
			 Integer queryCategoryLevel1,
			Integer queryCategoryLevel2, Integer queryCategoryLevel3,
			Integer queryFlatformId, Integer devId, Integer currentPageNo,
			Integer pageSize,Integer status) throws Exception {
		// TODO Auto-generated method stub
		
		return appInfoMapper.getAppInfoList(querySoftwareName, 
				queryCategoryLevel1,
				queryCategoryLevel2, queryCategoryLevel3, 
				queryFlatformId, devId, currentPageNo, pageSize,status);
	}

	@Override
	@Transactional(readOnly=true)
	public int getAppInfoCount(String querySoftwareName, 
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId,Integer devId,Integer status)
			throws Exception {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoCount(querySoftwareName,  
				queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3,
				queryFlatformId, devId,status);
	}

	@Override
	@Transactional(readOnly=true)
	public App_info getAppInfo(Integer aId) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfo(aId);
	}

	@Override
	public int updateStatus(int status, int id) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateStatus(status, id);
	}

	@Override
	public int updateAppId(String versionInfo, double versionSize, int appId) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateAppId(versionInfo, versionSize, appId);
	}

	@Override
	public List<App_info> getAppInfoAPKName(String APKName) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoAPKName(APKName);
	}

	@Override
	public boolean add(App_info appinfo) {
		// TODO Auto-generated method stub
		int num=appInfoMapper.add(appinfo);
		boolean bool=true;
		if(num>0){
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}

	@Override
	public int modify(App_info appinfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.modify(appinfo);
	}

	@Override
	public int updateFile(int id) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateFile(id);
	}

	@Override
	public boolean updateOnSaleDate(App_info ai) {
		// TODO Auto-generated method stub
		boolean bool=true;
		int num=appInfoMapper.updateOnSaleDate(ai);
		if(num>0){
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}

}
