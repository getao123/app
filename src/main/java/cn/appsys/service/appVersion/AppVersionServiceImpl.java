package cn.appsys.service.appVersion;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.mapper.appVersion.AppVersionMapper;
import cn.appsys.mapper.appinfo.AppInfoMapper;
import cn.appsys.pojo.App_version;

@Service("appVersionService")
@Transactional
public class AppVersionServiceImpl implements AppVersionService{
	@Resource(name="appVersionMapper")
	private AppVersionMapper appVersionMapper;
	@Resource(name="appInfoMapper")
	private AppInfoMapper appInfoMapper;
	@Override
	@Transactional(readOnly=true)
	public List<App_version> getAppVersion() {
		// TODO Auto-generated method stub
		return appVersionMapper.getAppVersion();
	}
	@Override
	@Transactional(readOnly=true)
	public App_version getAppVersionId(int vid) {
		// TODO Auto-generated method stub
		return appVersionMapper.getAppVersionId(vid);
	}
	@Override
	@Transactional(readOnly=true)
	public List<App_version> getAppVersionAppId(Integer appId) {
		return appVersionMapper.getAppVersionAppId(appId);
	}
	public int addVersion(App_version av) {
		// TODO Auto-generated method stub
		int num=appVersionMapper.addVersion(av);
		if(num>0){
		appInfoMapper.updateVersionId(av.getId(),1,av.getAppId());
		}
		return num;
	}
	public  App_version getVersionModify(@Param("id") int id,@Param("appId") int appId){
		return appVersionMapper.getVersionModify(id, appId);
	}
	@Override
	public int delApkLocPath(int modifyBy, Date modifyDate, int id) {
		// TODO Auto-generated method stub
		return appVersionMapper.delApkLocPath(modifyBy, modifyDate, id);
	}
	@Override
	public int modify(App_version app_version) {
		// TODO Auto-generated method stub
		return appVersionMapper.modify(app_version);
	}
	@Override
	public boolean deleteVersionByAppId(int appId) {
		// TODO Auto-generated method stub
		boolean bool=true;
		int num=appVersionMapper.deleteVersionByAppId(appId);
		if(num>0){
			int n=appInfoMapper.deleteAppInfoById(appId);
			bool=true;
		}else{
			bool=false;
		}
		return bool;
	}
	
}
