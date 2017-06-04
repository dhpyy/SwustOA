package cn.swust.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import cn.swust.oa.cfg.Configuration;
import cn.swust.oa.domain.PageBean;
import cn.swust.oa.util.QueryHelper;


// 只需将子类交给容器管理（@Repository/Service）
// Transactional可以被子类继承，RoleService不用再写Transactional
@Transactional
@SuppressWarnings("unchecked")
public class DaoSupportImpl<T> implements DaoSupport<T> {

	@Resource
	private SessionFactory sessionFactory;    // 注入sessionFactory属性
	
	private Class<T> clazz;				      // Class<T> clazz ： clazz是一个Class类
	
	// 构造方法与泛型无关
	// 通过反射得到子类所传递来的泛型Class类
	public DaoSupportImpl() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();  // 得到父类的Class类
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];   // 得到父类的泛型Class类
	}

	// 得到session对象
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public List<T> findAll() {
		return getSession().createQuery(//
				"FROM " + clazz.getSimpleName())// 通过反射取得泛型的类名（即实体类）
				.list();                        // session方法返回的是List集合
	}                                           // 返回的List中封装的对象为HQL语句中实体类本身
	
	public T findById(Long id) {
		if(id == null) {                   			 //  id为空，返回空对象
			return null;
		} else{
			return (T) getSession().get(clazz, id);   // id不为空，执行查询操作
		}
	}

	public void deleteById(Long id) {
		Object o = findById(id);              //  先根据id找到删除对象
		if(o != null) {                       //  对象不为空，执行删除操作
			getSession().delete(o);     
		}
	}

	public void save(T t) {
		getSession().save(t);
	}

	public void update(T t) {
		getSession().update(t);
	}
	
	public List<T> findByIds(Long[] ids) {
		if(ids == null || ids.length == 0) {	 //  id为空，返回空集合对象
			return Collections.EMPTY_LIST;
		} else {
			return getSession().createQuery(// 
					"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
					.setParameterList("ids", ids)//  这里是设置List型的参数
					.list();
		}
	}

	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param queryHelper
	 *            查询语句 + 参数列表
	 * @return
	 */
	public PageBean getPageBean(int pageNum, QueryHelper queryHelper) {
		System.out.println("------------> DaoSupportImpl.getPageBean( int pageNum, QueryHelper queryHelper )");

		// 获取pageSize等信息
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = queryHelper.getParameters();

		// 查询一页的数据列表
		Query query = getSession().createQuery(queryHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		List list = query.list(); // 查询

		// 查询总记录数
		query = getSession().createQuery(queryHelper.getQueryCountHql()); // 注意空格！
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) query.uniqueResult(); // 查询

		return new PageBean(pageNum, pageSize, count.intValue(), list);
	}

}
