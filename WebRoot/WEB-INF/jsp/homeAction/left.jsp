<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>导航菜单</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
<link type="text/css" rel="stylesheet" href="style/blue/menu.css" />
<script type="text/javascript">
	function menuClick(object) {
		$(object).next().toggle();
	}
	
</script>
</head>
<body style="margin: 0">
	<div id="Menu">
		<ul id="MenuUl">
			<s:iterator value="#application.topPrivilegeList">			<!-- 一级菜单，遍历顶层权限 -->
				<s:if test="#session.user.hasPrivilgeByName(name)">     <!-- 判断用户是否具有此顶层权限 -->
					<li class="level1">
						<div onClick="menuClick(this)" class="level1Style"> 
							<img src="style/images/MenuIcon/${id}.gif" class="Icon" />${name}
						</div>
						<ul class="MenuLevel2">
							<s:iterator value="children">							  <!-- 二级菜单，遍历子层权限 -->
								<s:if test="#session.user.hasPrivilgeByName(name)">   <!-- 判断用户是否具有此子层权限 -->
									<li class="level2">
										<div class="level2Style">
											<img src="style/images/MenuIcon/menu_arrow_single.gif" /> 
											<a target="right" href="${pageContext.request.contextPath}${url}.action">${name}</a>
										</div>
									</li>
								</s:if>
							</s:iterator>
						</ul>
					</li>
				</s:if>
			</s:iterator>
		</ul>
	</div>
</body>
</html>
