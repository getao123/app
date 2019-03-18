package cn.appsys.mapper.appcategory;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.App_category;

public interface AppcategoryMapper {
	public List<App_category> getAppCategoryListByParentId(@Param("parentId")Integer parentId);
	public List<App_category> getAppCategoryList();
}
