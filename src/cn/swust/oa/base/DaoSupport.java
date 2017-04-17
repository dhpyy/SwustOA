package cn.swust.oa.base;

import java.util.List;


public interface DaoSupport<T> {
	
	List<T> findAll();
	
	T findById(Long id);
	
	void deleteById(Long id);
	
	void save(T t);     // T t：t是一个对象
	
	void update(T t);
	
	// 根据id数组 查询
	
	List<T> findByIds(Long ids[]);
}
