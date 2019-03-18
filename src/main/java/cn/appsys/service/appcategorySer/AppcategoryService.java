package cn.appsys.service.appcategorySer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_category;

public interface AppcategoryService {
	public List<App_category> getAppCategoryListByParentId(Integer parentId);
	public List<App_category> getAppCategoryList();
}
