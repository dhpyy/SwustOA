package cn.swust.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.swust.oa.base.DaoSupportImpl;
import cn.swust.oa.domain.Forum;
import cn.swust.oa.service.ForumService;

@Service
public class ForumServiceImpl extends DaoSupportImpl<Forum> implements ForumService {

	@Override
	public List<Forum> findAll() {
		return getSession().createQuery(//
				"FROM Forum f ORDER BY f.position")//
				.list();
	}

	@Override
	public void save(Forum forum) {					  // 保存版块对象时存入position属性
		super.save(forum);					          // 传入save方法的对象会自动变为sessiobn的持久化对象
		forum.setPosition(forum.getId().intValue());  // 持久化对象能进行级联操作
	}
	
	// 版块对象根据position属性升序显示
	public void moveUp(Long id) {
		Forum forum = findById(id);					             // 读交换对象
		Forum topForum = (Forum) getSession().createQuery(//    
				"FROM Forum f WHERE f.position<? ORDER BY f.position DESC")//  
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)// 使用mysql分页查询
				.setMaxResults(1)//
				.uniqueResult();
		
		if(topForum == null) {
			return;
		} else {
			int temp = forum.getPosition();					    // 写交换对象
			forum.setPosition(topForum.getPosition());           
			topForum.setPosition(temp);
		}
	}
	
	public void moveDown(Long id) {
		Forum forum = findById(id);					             // 读交换对象
		Forum downForum = (Forum) getSession().createQuery(//    
				"FROM Forum f WHERE f.position>? ORDER BY f.position")//  
				.setParameter(0, forum.getPosition())//
				.setFirstResult(0)// 使用mysql分页查询
				.setMaxResults(1)//
				.uniqueResult();
		
		if(downForum == null) {
			return;
		} else {
			int temp = forum.getPosition();					    // 写交换对象
			forum.setPosition(downForum.getPosition());           
			downForum.setPosition(temp);
		}
	}
}
