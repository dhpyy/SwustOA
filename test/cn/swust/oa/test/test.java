package cn.swust.oa.test;

public class test {
	public static void main(String[] args) {
		int m = 3;									// == 判断的是存储的值的关系
		int n = 3;				
		System.out.println("基本类型比较：" + (m == n)); 			    // 基本数据类型存储的是值
		
		String name = new String("aaa");			// 引用类型存储的是值的引用地址
		String name2 = "aaa";		  				 
		System.out.println("引用类型比较：" + (name == name2));         
		
		System.out.println("常量池中的引用类型比较：" + ("aaa" == name2));		    // 采用赋值方式声明的String类型默认在常量池中寻找相应对象，找不到则在常量池中创建
		
		System.out.println("使用equals比较：" + "aaa".equals(name2));   // equals判断的是实际表示的值
		
		//String cityname = request.getParameter("name");				// 使用方法得到的字符串进行赋值不在常量池中
		String cityname = null;
		System.out.println("中文字符串比较：" + ("北京" == cityname));         
	}
}
