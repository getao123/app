package cn.appsys.mapper.DataDictionary;

import java.util.List;

import cn.appsys.pojo.Data_dictionary;

public interface DataDictionaryMapper {
	public List<Data_dictionary> getDataDictionary();
	public List<Data_dictionary> getDataDictionaryStatus();
}
