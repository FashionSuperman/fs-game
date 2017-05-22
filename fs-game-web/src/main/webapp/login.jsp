<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录</title>
<%@include file="WEB-INF/view/common/import.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/user/login.js"></script>
</head>

<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<div class="example">
						<div class="ibox-content">
							<form class="form-horizontal" id="loginForm">
								<div class="form-group">
									<label class="col-sm-2 control-label">账号</label>
									<div class="col-sm-10">
										<input type="text" id="accountname" name="accountname"
											class="form-control">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">密码</label>
									<div class="col-sm-10">
										<input type="text" id="password" name="password"
											class="form-control">
									</div>
								</div>
								<div class="modal-footer">
									<button type="submit" id="loginButton" class="btn btn-primary">登录</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>
