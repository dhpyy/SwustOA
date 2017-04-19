package cn.swust.oa.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.swust.oa.domain.Department;

public class DepartmentUtils {

	/**
	 * 
	 * @param topList 遍历所需的树顶点集合
	 * @return        树状显示的集合
	 */
	public static List<Department> getTreeList(List<Department> topList) {   // 工具类的方法都为静态
		List<Department> list = new ArrayList<Department>();
		walkTreeList(topList, " ┣", list);
		return list;
	}
	
	/**
	 * 递归遍历树的集合，修改前缀后 加入 树状显示的集合
	 * @param topList                 遍历所需的树顶点集合（多肽:实体类的Set属性）
	 * @param prefix                  前缀
	 * @param departmentList          遍历加入的树状显示的集合
	 */
	private static void walkTreeList(Collection<Department> topList, String prefix, List<Department> list) {   
		for(Department top : topList) {
			Department copy = new Department();						// 使用session缓存model的副本，修改前缀
			copy.setName(prefix + top.getName());                   // 防止直接修改session缓存中的model
			copy.setId(top.getId());
			list.add(copy);											// 遍历树顶点
		    walkTreeList(top.getChildren(), "　" + prefix, list);	// 遍历子树，添加前缀值
		}                                                           // 通过配置order-by属性，得到有序的Set
	}

}
