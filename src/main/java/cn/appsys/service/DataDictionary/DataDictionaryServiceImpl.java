package cn.appsys.service.DataDictionary;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.appsys.mapper.DataDictionary.DataDictionaryMapper;
import cn.appsys.pojo.Data_dictionary;
@Service("dataDictionaryService")
@Transactional
public class DataDictionaryServiceImpl implements DataDictionaryService{
	@Resource(name="dataDictionaryMapper")
	private DataDictionaryMapper dataDictionaryMapper;
	@Override
	@Transactional(readOnly=true)
	public List<Data_dictionary> getDataDictionary() {
		// TODO Auto-generated method stub
		return dataDictionaryMapper.getDataDictionary();
	}
	@Override
	@Transactional(readOnly=true)
	public List<Data_dictionary> getDataDictionaryStatus() {
		// TODO Auto-generated method stub
		return dataDictionaryMapper.getDataDictionaryStatus();
	}

}
