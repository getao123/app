package cn.appsys.controller.backend;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import cn.appsys.pojo.App_category;
import cn.appsys.pojo.App_info;
import cn.appsys.pojo.App_version;
import cn.appsys.pojo.Backend_user;
import cn.appsys.pojo.Data_dictionary;
import cn.appsys.service.DataDictionary.DataDictionaryService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.appcategorySer.AppcategoryService;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.tools.PageSupport;

@Controller("appCheckController")
@RequestMapping("/manager/backend/app")
public class AppCheckController {
	@Resource(name="appInfoService")
	private AppInfoService appInfoService;
	@Resource(name="appcategoryService")
	private AppcategoryService appcategoryService;
	@Resource(name="appVersionService")
	private AppVersionService appVersionService;
	@Resource(name="dataDictionaryService")
	private DataDictionaryService dataDictionaryService;
	@RequestMapping(value="/list")
	public String list(@RequestParam(required=false)String querySoftwareName,
			@RequestParam(required=false)String queryFlatformId,
			@RequestParam(required=false)String queryCategoryLevel1,
			@RequestParam(required=false)String queryCategoryLevel2,
			@RequestParam(required=false)String queryCategoryLevel3,
			@RequestParam(value="pageIndex",required=false,defaultValue="1") String pageIndex,
			HttpSession session,Model model){
			Integer CategoryLevel1=null;
			if(queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")){
				CategoryLevel1=Integer.parseInt(queryCategoryLevel1);
			}
			Integer CategoryLevel2=null;
			if(queryCategoryLevel2 != null && !queryCategoryLevel2.equals("")){
				CategoryLevel2=Integer.parseInt(queryCategoryLevel2);
			}
			Integer CategoryLevel3=null;
			if(queryCategoryLevel3 != null && !queryCategoryLevel3.equals("")){
				CategoryLevel3=Integer.parseInt(queryCategoryLevel3);
			}
			Integer FlatformId=null;
			if(queryFlatformId != null && !queryFlatformId.equals("")){
				FlatformId=Integer.parseInt(queryFlatformId);
			}
			Integer currentPageNo=null;
			if(pageIndex != null && !pageIndex.equals("")){
				currentPageNo=Integer.parseInt(pageIndex);
			}
			PageSupport ps=new PageSupport();
			ps.setCurrentPageNo(currentPageNo);
			Integer devId=null;
			Integer status=null;
			try {
			ps.setTotalCount(appInfoService.getAppInfoCount(querySoftwareName,
						CategoryLevel1, CategoryLevel2,
						CategoryLevel3, FlatformId ,devId,1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<App_info> appInfoList=null;
			try {
				appInfoList=appInfoService.getAppInfoList(querySoftwareName,
						CategoryLevel1,CategoryLevel2, CategoryLevel3, FlatformId,
						devId, (currentPageNo-1)*ps.getPageSize(), ps.getPageSize(),1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(queryCategoryLevel1 != null && !queryCategoryLevel1.equals("")){
			List<App_category> categoryLevel2List=appcategoryService.getAppCategoryListByParentId(CategoryLevel1);
			model.addAttribute("categoryLevel2List", categoryLevel2List);
			}
			if(queryCategoryLevel2 !=null && !queryCategoryLevel2.equals("")){
				List<App_category> categoryLevel3List=appcategoryService.getAppCategoryListByParentId(CategoryLevel2);
				model.addAttribute("categoryLevel3List", categoryLevel3List);
			}
			List<App_category> AppcategoryList=appcategoryService.getAppCategoryList();
			List<App_category> categoryLevel1List=appcategoryService.getAppCategoryListByParentId(null);
			List<App_version> appVersionList=appVersionService.getAppVersion();
			List<Data_dictionary>  flatFormList=dataDictionaryService.getDataDictionary();
			List<Data_dictionary> statusList=dataDictionaryService.getDataDictionaryStatus();
			session.setAttribute("statusList", statusList);
			session.setAttribute("AppcategoryList", AppcategoryList);
			session.setAttribute("appVersionList", appVersionList);
			session.setAttribute("flatFormList", flatFormList);
			model.addAttribute("categoryLevel1List", categoryLevel1List);
			model.addAttribute("querySoftwareName", querySoftwareName);
			model.addAttribute("queryFlatformId", queryFlatformId);
			model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
			model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
			model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
			model.addAttribute("appInfoList", appInfoList);
			model.addAttribute("pages", ps);
		return "backend/applist";
	}
	
	@RequestMapping(value="/check")
	public String check(Model model,@RequestParam String aid,@RequestParam String vid){
		Integer aId=Integer.parseInt(aid);
		Integer vId=Integer.parseInt(vid);
		App_info appInfo=appInfoService.getAppInfo(aId);
		App_version appVersion= appVersionService.getAppVersionId(vId);
		model.addAttribute("appInfo",appInfo);
		model.addAttribute("appVersion",appVersion);
		return "backend/appcheck";
	}
	
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody()
	public List<App_category> categorylevellist(@RequestParam(required=false)Integer pid){
		return appcategoryService.getAppCategoryListByParentId(pid);
	}
	@RequestMapping(value="/checksave")
	public String checksave(@RequestParam() String id,@RequestParam String status){
		int appId=Integer.parseInt(id);
		int valueId=Integer.parseInt(status);
		int num= appInfoService.updateStatus(valueId, appId);
		if(num>0){
			return "redirect:/manager/backend/app/list";
		}else{
			return "backend/appcheck";
		}
		}
}
