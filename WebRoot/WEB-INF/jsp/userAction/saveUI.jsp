<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
	<title>用户信息</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 用户信息
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>
    <s:form action="user_%{ id==null ? 'add' : 'edit' }">
    	<s:hidden name="id"></s:hidden>    <!-- 从对象栈中读出id 以便传到下一个页面 -->
        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 用户信息 </div> 
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
                    	<td width="100">所属部门</td>
                        <td>
                        	<s:select name="departmentId" list="#departmentList" listValue="name" listKey="id" 
                        	   headerKey="" headerValue="请选择部门" cssClass="SelectStyle"/>
                        </td>
                    </tr>
                    <tr>
                    	<td>登录名</td>
                        <td><s:textfield name="loginName" cssClass="InputStyle"/> 
						</td>
                    </tr>
                    <tr>
                    	<td>姓名</td>
                        <td><s:textfield name="name" cssClass="InputStyle"/> </td>
                    </tr>
					<tr>
						<td>性别</td>
                        		<!-- <s:radio name="gender" list="#{'男':'男', '女':'女'}"></s:radio> -->
                        <td>     		  				 <!-- 显示的集合，显示的元素key, 提交的元素value -->  
							<s:radio name="gender" list="{'男', '女'}"></s:radio>
						</td>                            <!-- 简写形式 -->
						
                    </tr>
					<tr>
						<td>联系电话</td>
                        <td><s:textfield name="phoneNumber" cssClass="InputStyle"/></td>
                    </tr>
                    <tr><td>E-mail</td>
                        <td><s:textfield name="email" cssClass="InputStyle"/></td>
                    </tr>
                    <tr><td>备注</td>
                        <td><s:textarea name="description" cssClass="TextareaStyle"></s:textarea></td>
                    </tr>
                </table>
            </div>
        </div>
        
		<div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 岗位设置 </div> 
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
                    <tr>
						<td width="100">岗位</td>
                        <td><s:select name="roleIds" list="#roleList" listValue="name" listKey="id"
                        	 multiple="true" size="10" cssClass="SelectStyle"/>
                            按住Ctrl键可以多选或取消选择
                        </td>
                    </tr>
                </table>
            </div>
        </div>		
		
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </s:form>
</div>

<div class="Description">
	说明：<br />
	1，新建用户后，密码被初始化为"1234"。<br />
	2，密码在数据库中存储的是MD5摘要（不是存储明文密码）。<br />
</div>

</body>
</html>
