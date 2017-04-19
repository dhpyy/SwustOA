package cn.swust.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.swust.oa.base.DaoSupportImpl;
import cn.swust.oa.domain.Department;
import cn.swust.oa.service.DepartmentServcie;

@Service
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements DepartmentServcie {

	public List<Department> findTopList() {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent IS NULL")//   读数据库(顶级部门)
				.list();
	}

	public List<Department> findChildrenList(Long parentId) {
		return getSession().createQuery(//
				"FROM Department d WHERE d.parent.id=?")//	         读数据库(该parentId下的子部门)
				.setParameter(0, parentId)//
				.list();
	}
}
