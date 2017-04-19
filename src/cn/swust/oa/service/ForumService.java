package cn.swust.oa.service;

import cn.swust.oa.base.DaoSupport;
import cn.swust.oa.domain.Forum;

public interface ForumService extends DaoSupport<Forum> {
	void moveUp(Long id);
	void moveDown(Long id);
}
