package cn.swust.oa.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.swust.oa.service.ProcessDefinitionService;


@Service
@Transactional
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

	@Resource
	private SessionFactory sessionFactory;

	@Resource
	private ProcessEngine processEngine;

	/**
	 * C : 部署对象（使用zip包的方式）
	 */
	public void deployByZip(File zipFile) {
		ZipInputStream zipInputStream = null;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(zipFile));		// 准备资源（输入流类型）
			processEngine.getRepositoryService()//								             
						 .createDeployment()//
						 .addResourcesFromZipInputStream(zipInputStream)//
						 .deploy();												    // 部署
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * D : 指定key的所有版本的流程定义
	 * @param key
	 */
	public void deleteByKey(String key) {
		List<ProcessDefinition> list = processEngine.getRepositoryService()// 查询指定key的   所有版本的流程定义
													.createProcessDefinitionQuery()//
													.processDefinitionKey(key)// 过滤条件
													.list();// 执行查询
		
		for (ProcessDefinition pd : list) {	// 循环刪除所有版本的流程定义
			processEngine.getRepositoryService()//
						 .deleteDeploymentCascade(pd.getDeploymentId());
		}
	}

	
	/**
	 * R : 所有最新版本的流程定义列表
	 */
	public List<ProcessDefinition> findAllLatestVersionList() {
		// 查询所有的流程定义（包含所有的版本）
		List<ProcessDefinition> all = processEngine.getRepositoryService()//
												   .createProcessDefinitionQuery()//
												   .orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)//按版本升序排列
												   .list();// 执行查询

		// 过滤出所有最新的版本的流程定义列表
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>(); 
		for (ProcessDefinition pd : all) {
			map.put(pd.getKey(), pd);			// 覆盖相同key的流程定义对象（一个key只有一个最新的版本）
		}

		return new ArrayList<ProcessDefinition>(map.values());		
	}

	/**
	 * R : 部署对象中的图片
	 */
	public InputStream getImageResourceAsStreamByPdId(String pdId) {
		ProcessDefinition pd = processEngine.getRepositoryService()//			获取流程定义对象
											.createProcessDefinitionQuery()//
											.processDefinitionId(pdId)//
											.uniqueResult();
		String deploymentId = pd.getDeploymentId();							  // 获取部署对象id
		String resourceName = pd.getImageResourceName();					  // 获取流程定义对象中的图片名
		// 得到输入流
		return processEngine.getRepositoryService()//
							.getResourceAsStream(deploymentId, resourceName); // 查询图片
	}

}
