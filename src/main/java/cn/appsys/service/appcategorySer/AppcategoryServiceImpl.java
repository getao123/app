package cn.appsys.service.appcategorySer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.mapper.appcategory.AppcategoryMapper;
import cn.appsys.pojo.App_category;
@Service("appcategoryService")
@Transactional
public class AppcategoryServiceImpl implements AppcategoryService{
	@Resource(name="appcategoryMapper")
	private AppcategoryMapper appcategoryMapper;
	@Override
	@Transactional(readOnly=true)
	public List<App_category> getAppCategoryListByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		List<App_category> list=appcategoryMapper.getAppCategoryListByParentId(parentId);
		return list;
	}
	@Override
	@Transactional(readOnly=true)
	public List<App_category> getAppCategoryList() {
		// TODO Auto-generated method stub
		return appcategoryMapper.getAppCategoryList();
	}
}
