package cn.swust.oa.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.jbpm.api.ProcessDefinition;

public interface ProcessDefinitionService {
	
	/**
	 * C : 部署对象（使用zip包的方式）
	 */
	void deployByZip(File zipFile);

	/**
	 * R : 所有最新版本的流程定义列表
	 */
	List<ProcessDefinition> findAllLatestVersionList();

	/**
	 * D : 指定key的所有版本的流程定义
	 * @param key
	 */
	void deleteByKey(String key);

	/**
	 * R : 获取指定流程定义中的流程图片
	 */
	InputStream getImageResourceAsStreamByPdId(String id);

}
