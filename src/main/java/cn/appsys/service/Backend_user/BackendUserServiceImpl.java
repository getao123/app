package cn.appsys.service.Backend_user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.mapper.backenduser.Backend_userMapper;
import cn.appsys.pojo.Backend_user;
@Service("backendUserService")
@Transactional
public class BackendUserServiceImpl implements BackendUserService{
	@Resource(name="backend_userMapper")
	private Backend_userMapper backendUserMapper;
	@Override
	@Transactional(readOnly=true)
	public Backend_user getBackendUser(String userCode,String passWord) {
		// TODO Auto-generated method stub
		Backend_user bu=backendUserMapper.getBackendUser(userCode);
		if(bu!=null){
			if(!bu.getUserPassword().equals(passWord)){
				bu=null;
			}
		}
		return bu;
	}

}
