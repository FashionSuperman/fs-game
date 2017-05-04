<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/user/user.js"></script>
</head>

<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<div class="example">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">账号:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="accountname"
										id="accountnameQuery" />
								</div>
								<label class="col-sm-1 control-label">昵称:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="nickname"
										id="nicknameQuery" />
								</div>
								<button id="btn_query" type="button" class="btn btn-primary"
									onclick="searchUserInfo()">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>查询
								</button>
								<button id="btn_delete_query" type="button" class="btn btn-primary"
									onclick="resetSearch()">
									<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>撤销检索
								</button>
							</div>
						</form>
						
						<div class="col-md-12">
							<div id="toolbar" class="btn-group">
								<button id="btn_add" type="button"
									class="btn btn-outline btn-default" onclick="addNewUser()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加用户
								</button>
							</div>
							<table id="userTable">
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		<div class="modal fade" id="newUser" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeNewUserModal()">×</button>
						<h3>新增用户</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="newUserForm">
							<div class="form-group">
								<label class="col-sm-2 control-label">账号名称</label>
								<div class="col-sm-10">
									<input type="text" id="accountname" name="accountname"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">昵称</label>
								<div class="col-sm-10">
									<input type="text" id="nickname" name="nickname"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">外部id</label>
								<div class="col-sm-10">
									<input type="text" id="foreighid" name="foreighid"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">外部id类型</label>
								<div class="col-sm-10">
									<input type="text" id="foreightype" name="foreightype"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">资产</label>
								<div class="col-sm-10">
									<input type="text" id="funds" name="funds"
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
								<button type="button" class="btn btn-primary"
									onclick="closeNewUserModal()">取消</button>
								<button type="submit" id="newUserSaveButton"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
		
		
		
		
		<div class="modal fade" id="editUser" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeEditUserModal()">×</button>
						<h3>编辑用户信息</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="editUserForm">
							<input type="hidden" id="userid" name="userid"></input>
							<div class="form-group">
								<label class="col-sm-2 control-label">账号名称</label>
								<div class="col-sm-10">
									<input type="text" id="editaccountname" name="accountname"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">昵称</label>
								<div class="col-sm-10">
									<input type="text" id="editnickname" name="nickname"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">外部id</label>
								<div class="col-sm-10">
									<input type="text" id="editforeighid" name="foreighid"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">外部id类型</label>
								<div class="col-sm-10">
									<input type="text" id="editforeightype" name="foreightype"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">资产</label>
								<div class="col-sm-10">
									<input type="text" id="editfunds" name="funds"
										class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">密码</label>
								<div class="col-sm-10">
									<input type="text" id="editpassword" name="password"
										class="form-control">
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeEditUserModal()">取消</button>
								<button type="submit" id="editUserSaveButton"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
		
		
	</div>


</body>
</html>