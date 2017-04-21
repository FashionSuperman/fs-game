<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品分类</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/trade/catagory.js"></script>
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
								<label class="col-sm-1 control-label">分类名称:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="catagoryname"
										id="catagoryname" />
								</div>
								<button id="btn_query" type="button" class="btn btn-primary"
									onclick="searchCatagoryInfo()">
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
									class="btn btn-outline btn-default" onclick="addNew()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
								</button>
							</div>
							<table id="catagoryTable">
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		<div class="modal fade" id="newCatagory" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeNewCatagoryModal()">×</button>
						<h3>新增商品分类</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="newCatagoryForm">
							<div class="form-group">
								<label class="col-sm-2 control-label">分类名称</label>
								<div class="col-sm-10">
									<input type="text" id="catagoryname" name="catagoryname"
										class="form-control">
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeNewCatagoryModal()">取消</button>
								<button type="submit" id="newCatagorySaveButton"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
		
		
		
		
		<div class="modal fade" id="editCatagory" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeEditCatagoryModal()">×</button>
						<h3>编辑商品分类</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="editCatagoryForm">
							<input type="hidden" id="catagoryId" name="catagoryId"></input>
							<div class="form-group">
								<label class="col-sm-2 control-label">分类名称</label>
								<div class="col-sm-10">
									<input type="text" id="editcatagoryname" name="catagoryname"
										class="form-control">
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeNewCatagoryModal()">取消</button>
								<button type="submit" id="newCatagorySaveButton"
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