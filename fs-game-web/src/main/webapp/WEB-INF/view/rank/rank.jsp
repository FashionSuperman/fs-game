<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>排名</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/rank/rank.js"></script>
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
								<label class="col-sm-1 control-label">分数:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="score"
										id="score" />
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
							<table id="userTable">
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
	</div>


</body>
</html>