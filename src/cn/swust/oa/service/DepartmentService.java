package cn.swust.oa.service;

import java.util.List;

import cn.swust.oa.base.DaoSupport;
import cn.swust.oa.domain.Department;

public interface DepartmentService extends DaoSupport<Department> {

	List<Department> findChildrenList(Long parentId);

	List<Department> findTopList();

}
