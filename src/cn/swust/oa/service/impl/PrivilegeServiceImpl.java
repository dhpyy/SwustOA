package cn.swust.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.swust.oa.base.DaoSupportImpl;
import cn.swust.oa.domain.Privilege;
import cn.swust.oa.service.PrivilegeService;

@Service
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements PrivilegeService {


	public List<Privilege> findTopList() {
		return getSession().createQuery(//
				"FROM Privilege p WHERE p.parent IS NULL")//   读数据库(顶层权限)
				.list();
	}

	// 返回权限表所有不重复的url集合
	public Collection<String> findAllUrls() {
		return getSession().createQuery(//
				"SELECT DISTINCT p.url FROM Privilege p WHERE p.url IS NOT NULL")//  读数据库(权限urls)
				.list();
	}
}
