package cn.swust.oa.util;

import cn.swust.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor {

	public String intercept(ActionInvocation invocation) throws Exception {
		/*测试拦截器
		System.out.println("----------------------拦截前");
		String result = invocation.invoke();
		System.out.println("----------------------拦截后");
		return result;*/
		
		// 获取当前登录用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		// 获取请求信息
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String url = namespace + actionName;
		
		// 用户未登录,转到登录页面
		if(user == null) {
			if(url.startsWith("/user_login")){		// 如果是申请登录请求，则不拦截
				return invocation.invoke();
			} else{
				return "loginUI";					// 否则转到登录页面
			}
		}
		
		// 用户已登录,根据url判断该用户是否具有该权限
		else {
			if(user.hasPrivilgeByUrl(url)) {
				return invocation.invoke();			// 用户拥有该权限，不拦截
			} else{
				return "noPrivilegeError";			// 用户没有该权限，转到错误页面
			}
		}
	}

}
