package cn.appsys.controller.developer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;

import cn.appsys.pojo.App_category;
import cn.appsys.pojo.App_info;
import cn.appsys.pojo.App_version;
import cn.appsys.pojo.Data_dictionary;
import cn.appsys.pojo.Dev_user;
import cn.appsys.service.DataDictionary.DataDictionaryService;
import cn.appsys.service.appVersion.AppVersionService;
import cn.appsys.service.appcategorySer.AppcategoryService;
import cn.appsys.service.appinfo.AppInfoService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller("appController")
@RequestMapping("/dev/flatform/app")
public class AppController {
	@Resource(name="appInfoService")
	private AppInfoService appInfoService;
	@Resource(name="appcategoryService")
	private AppcategoryService appcategoryService;
	@Resource(name="appVersionService")
	private AppVersionService appVersionService;
	@Resource(name="dataDictionaryService")
	private DataDictionaryService dataDictionaryService;
	
	@RequestMapping("/list")
	public String list(@RequestParam(required=false)String querySoftwareName,
			@RequestParam(required=false)String queryFlatformId,
			@RequestParam(required=false)String queryCategoryLevel1,
			@RequestParam(required=false)String queryCategoryLevel2,
			@RequestParam(required=false)String queryCategoryLevel3,
			@RequestParam(required=false)String queryStatus,
			@RequestParam(value="pageIndex",required=false,defaultValue="1") String pageIndex,
			HttpSession session,Model model){
		Integer status=null;
		if(queryStatus != null && !queryStatus.equals("")){
			status=Integer.parseInt(queryStatus);
		}
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
		Dev_user devU =(Dev_user) session.getAttribute("devUserSession");
		PageSupport ps=new PageSupport();
		ps.setCurrentPageNo(currentPageNo);
		try {
		ps.setTotalCount(appInfoService.getAppInfoCount(querySoftwareName,CategoryLevel1, CategoryLevel2, CategoryLevel3, FlatformId,devU.getId(),status));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<App_info> appInfoList=null;
		try {
			appInfoList=appInfoService.getAppInfoList(querySoftwareName,CategoryLevel1,
					CategoryLevel2, CategoryLevel3, FlatformId,devU.getId(),
					(currentPageNo-1)*ps.getPageSize(), ps.getPageSize(),status);
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
		model.addAttribute("queryStatus",queryStatus);
		model.addAttribute("pages", ps);
		return "developer/appinfolist";
	}
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody()
	public List<App_category> categorylevellist(@RequestParam(required=false)Integer pid){
		return appcategoryService.getAppCategoryListByParentId(pid);
	}
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody()
	public List<Data_dictionary> datadictionarylist(@RequestParam(required=false)Integer pid){
		return dataDictionaryService.getDataDictionary();
	}
	@RequestMapping(value="/apkexist.json")
	@ResponseBody()
	public Object apkexist(@RequestParam(required=false) String APKName){
		Map<String,String> map=new HashMap<String, String>();
		if(APKName!=null && !APKName.equals("")){
			List<App_info> ap=appInfoService.getAppInfoAPKName(APKName);
			if(ap.size()>0){
				map.put("APKName", "exist");
			}else{
				map.put("APKName", "noexist");	
			}
		}else{
			map.put("APKName", "empty");
		}
		
		return map;
	}
	@RequestMapping(value="/appinfoadd")
	public String appinfoadd(){
		return "developer/appinfoadd";
	}
	
	
	@RequestMapping(value="/appinfoaddsave")
	public String appinfoaddsave(App_info ai,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_logoPicPath",required=false) MultipartFile a_logoPicPath){
		String logoPicPath =  null;
		String logoLocPath =  null;
		if(!a_logoPicPath.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=a_logoPicPath.getOriginalFilename();
			String prefix=FilenameUtils.getExtension(oldFileName);
			int filesize=500000;
			if(a_logoPicPath.getSize()>filesize){
				request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
				return "developer/appinfoadd";
			}else if(prefix.equalsIgnoreCase("jpg")||
					prefix.equalsIgnoreCase("png")||
					prefix.equalsIgnoreCase("pneg")||prefix.equalsIgnoreCase("bmp")){
				String fileName= ai.getAPKName() + ".jpg";
				File file=new File(path, fileName);
				if(!file.exists()){
					file.mkdirs();
				}
				try{
					a_logoPicPath.transferTo(file);
				}catch(Exception e){
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					return "developer/appinfoadd";
				}
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
			}
		}
		ai.setCreatedBy(((Dev_user)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		ai.setCreationDate(new Date());
		ai.setLogoPicPath(logoPicPath);
		ai.setLogoLocPath(logoLocPath);
		ai.setDevId(((Dev_user)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		ai.setStatus(1);
		try {
			if(appInfoService.add(ai)){
				return "redirect:/dev/flatform/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appinfoadd";
	}
	@RequestMapping("/appversionadd")
	public String appversionadd(@RequestParam(required=false)String id,Model model){
		List<App_version> appVersionList=appVersionService.getAppVersionAppId(Integer.parseInt(id));
		App_info av=appInfoService.getAppInfo(Integer.parseInt(id));
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", av);
	 	return "developer/appversionadd";
	}
	
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addVersionSave(App_version av,@RequestParam(required=false) String appIds,HttpSession session,HttpServletRequest request,
						@RequestParam(value="a_downloadLink",required= false) MultipartFile a_downloadLink){
        String downloadLink=null;
		String apkLocPath=null;
		String apkFileName=null;
		int appId=Integer.parseInt(appIds);
		if(!a_downloadLink.isEmpty()){
			String path=request.getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=a_downloadLink.getOriginalFilename();
			String prefix=FilenameUtils.getExtension(oldFileName);
			int filesize=1000000000;
			if(a_downloadLink.getSize()>filesize){
				request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
				return "developer/appversionadd";
			}else if(prefix.equalsIgnoreCase("apk")){
				 String fileName=av.getApkFileName()+".jpg";
				 String apkName = null;
				 try {
					apkName = appInfoService.getAppInfo(appId).getAPKName();
				 } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/dev/flatform/app/appversionadd?id="+av.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +av.getVersionNo() + ".apk";
				File file=new File(path, fileName);
				if(!file.exists()){
					file.mkdirs();
				}
				try {
					a_downloadLink.transferTo(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					return "developer/appversionadd";
				}
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				apkLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
			}
		}
		av.setApkFileName(apkFileName);
		av.setDownloadLink(downloadLink);
		av.setApkLocPath(apkLocPath);
		av.setCreatedBy(((Dev_user)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		
		av.setAppId(appId);
		Date date=new Date();
		av.setCreationDate(date);
		try {
			if(appVersionService.addVersion(av)>0){
				return "redirect:/dev/flatform/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appversionadd";
	}
	@RequestMapping("appinfomodify")
	public String appinfomodify(Model model,@RequestParam String id){
		Integer aId=Integer.parseInt(id);
		App_info Appi= appInfoService.getAppInfo(aId);
		model.addAttribute("appInfo", Appi);
		return "developer/appinfomodify";
	}
	@RequestMapping(value="delfile.json")
	@ResponseBody()
	public Object delfile(@RequestParam String id,@RequestParam String flag,HttpSession session){
		Integer idd=Integer.parseInt(id);
		Map<String,String> map=new HashMap<String, String>();
		int num=0;
		File file=null;
		if(flag.equals("apk")){
			int dev=((Dev_user)session.getAttribute(Constants.DEV_USER_SESSION)).getId();
		 	App_version app_version=appVersionService.getAppVersionId(idd);
		 	file=new File(app_version.getApkLocPath());
		 	num=appVersionService.delApkLocPath(dev, new Date(),idd);
		}else if(flag.equals("logo")){
			App_info app_info=appInfoService.getAppInfo(idd);
			file=new File(app_info.getLogoLocPath());
			num=appInfoService.updateFile(idd);
		}
		if(file.delete()){
		if(num>0){
			map.put("result", "success");
		}else{
			map.put("result", "failed");
		}
		}
		return map;
	}
	@RequestMapping("appinfomodifysave")
	public String appinfomodifysave(App_info ai,HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required=false) MultipartFile attach){
		String logoPicPath =  null;
		String logoLocPath =  null;
		if(!attach.isEmpty()){
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName=attach.getOriginalFilename();
			String prefix=FilenameUtils.getExtension(oldFileName);
			int filesize=500000;
			if(attach.getSize()>filesize){
				request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_4);
				return "developer/appinfomodify";
			}else if(prefix.equalsIgnoreCase("jpg")||
					prefix.equalsIgnoreCase("png")||
					prefix.equalsIgnoreCase("pneg")||prefix.equalsIgnoreCase("bmp")){
				String fileName= ai.getAPKName() + ".jpg";
				File file=new File(path, fileName);
				if(!file.exists()){
					file.mkdirs();
				}
				try{
					attach.transferTo(file);
				}catch(Exception e){
					e.printStackTrace();
					request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_2);
					return "developer/appinfomodify";
				}
				 logoPicPath = request.getContextPath()+"/statics/uploadfiles/"+fileName;
				 logoLocPath = path+File.separator+fileName;
			}else{
				request.setAttribute("fileUploadError", Constants.FILEUPLOAD_ERROR_3);
			}
		}
		ai.setModifyBy(((Dev_user)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		ai.setModifyDate(new Date());
		ai.setLogoPicPath(logoPicPath);
		ai.setLogoLocPath(logoLocPath);
		ai.setDevId(((Dev_user)session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		ai.setStatus(1);
		try {
			if(appInfoService.modify(ai)>0){
				return "redirect:/dev/flatform/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appinfomodify";
	}
	@RequestMapping("/appversionmodify")
	public String appversionmodify(Model model,@RequestParam String vid,@RequestParam String aid){
		int id=Integer.parseInt(vid);
		int appId=Integer.parseInt(aid);
		App_version appVersion=appVersionService.getVersionModify(id, appId);
		List<App_version> appVersionList=appVersionService.getAppVersionAppId(Integer.parseInt(aid));
		model.addAttribute("appVersion", appVersion);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appversionmodify";
	}
	@RequestMapping(value="/appversionmodifysave",method=RequestMethod.POST)
	public String modifyAppVersionSave(App_version appVersion,@RequestParam String appIds,HttpSession session,HttpServletRequest request,
					@RequestParam(value="attach",required= false) MultipartFile attach){	
		
		String downloadLink =  null;
		String apkLocPath = null;
		String apkFileName = null;
		if(!attach.isEmpty()){
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			if(prefix.equalsIgnoreCase("apk")){
				 String apkName = null;
				 try {
					apkName = appInfoService.getAppInfo(Integer.parseInt(appIds)).getAPKName();
				 } catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				 }
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
							 +"&aid="+appVersion.getAppId()
							 +"&error=error2";
				} 
				downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
						 +"&aid="+appVersion.getAppId()
						 +"&error=error3";
			}
		}
		appVersion.setModifyBy(((Dev_user) session.getAttribute(Constants.DEV_USER_SESSION)).getId());
		appVersion.setModifyDate(new Date());
		appVersion.setDownloadLink(downloadLink);
		appVersion.setApkLocPath(apkLocPath);
		appVersion.setApkFileName(apkFileName);
		try {
			if(appVersionService.modify(appVersion)>0){
				return "redirect:/dev/flatform/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "developer/appversionmodify";
	}
	@RequestMapping("/appview")
	public String appview(@RequestParam int id,Model model){
		App_info av=appInfoService.getAppInfo(id);
		List<App_version> appVersionList=appVersionService.getAppVersionAppId(id);
		model.addAttribute("appInfo", av);
		model.addAttribute("appVersionList", appVersionList);
		return "developer/appinfoview";
	}
	@RequestMapping(value="/delapp.json")
	@ResponseBody
	public Object delApp(@RequestParam String id){
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if(StringUtils.isNullOrEmpty(id)){
			resultMap.put("delResult", "notexist");
		}else{
			try {
				if(appVersionService.deleteVersionByAppId(Integer.parseInt(id)))
					resultMap.put("delResult", "true");
				else
					resultMap.put("delResult", "false");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return JSONArray.toJSONString(resultMap);
	}
	@RequestMapping(value="/sale.json")
	@ResponseBody
	public Object sale(@RequestParam String app,HttpSession session){
		int aId=Integer.parseInt(app);
		App_info ai=appInfoService.getAppInfo(aId);
		boolean bool=true;
		Dev_user dev=(Dev_user) session.getAttribute(Constants.DEV_USER_SESSION);
		App_info aif=new App_info();
		aif.setId(aId);
		aif.setModifyBy(dev.getId());
		if(ai.getStatus()==2 || ai.getStatus()==5){
			aif.setOnSaleDate(new Date());
			aif.setStatus(4);
			bool=appInfoService.updateOnSaleDate(aif);
		}else if(ai.getStatus()==4){
			aif.setOffSaleDate(new Date());
			aif.setStatus(5);
			bool=appInfoService.updateOnSaleDate(aif);
		}
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("errorCode", "0");
		resultMap.put("appId", app);
		if(aId>0){
			try {
				if(bool){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "success");
				}		
			} catch (Exception e) {
				resultMap.put("errorCode", "exception000001");
			}
		}else{
			resultMap.put("errorCode", "param000001");
		}
		return resultMap;
	}
}
