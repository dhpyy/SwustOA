package cn.swust.oa.service;

import java.util.Collection;
import java.util.List;

import cn.swust.oa.base.DaoSupport;
import cn.swust.oa.domain.Privilege;

public interface PrivilegeService extends DaoSupport<Privilege> {

	List<Privilege> findTopList();

	Collection<String> findAllUrls();


}
