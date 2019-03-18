package cn.appsys.service.DataDictionary;

import java.util.List;

import cn.appsys.pojo.Data_dictionary;

public interface DataDictionaryService {
	public List<Data_dictionary> getDataDictionary();
	public List<Data_dictionary> getDataDictionaryStatus();
}
