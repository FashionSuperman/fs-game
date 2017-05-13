<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户背包</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/user/userPackage.js"></script>
</head>

<body>
	<div class="ibox-content">
		<input type="hidden" id=userId name="userId" value="${userId}"></input>
		<div class="row row-lg">
			<div class="col-sm-12">
				<!-- Example Card View -->
				<div class="example-wrap">
					<div class="example">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">商品分类:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="catagoryname"
										id="catagorynameQuery" />
								</div>
								<label class="col-sm-1 control-label">商品名称:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="commodityname"
										id="commoditynameQuery" />
								</div>
								<button id="btn_query" type="button" class="btn btn-primary"
									onclick="searchUserPackageInfo()">
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
									class="btn btn-outline btn-default" onclick="addNewUserPackage()">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>添加用户背包
								</button>
							</div>
							<table id="userPackageTable">
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
		
		
		<div class="modal fade" id="newUserPackage" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeNewUserPackageModal()">×</button>
						<h3>新增用户背包</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="newUserPackageForm">
						<div class="form-group">
							<label class="col-sm-2 control-label">商品分类</label>
							<div class="col-sm-10">
								<select class="form-control m-b" id="commoditycatagoryid">
		                            <option value=''>选择分类</option>
		                               <c:forEach items="${commodityCatagoryList}" var="commodityCatagory" varStatus="s">
					                    <option value="${commodityCatagory.catagoryid}">${commodityCatagory.catagoryname}</option>
					                   </c:forEach>
	                             </select>
							</div>
								
							<label class="col-sm-2 control-label">商品名称</label>
							<div class="col-sm-10">
								<select class="form-control m-b" name="commodityid" id="commodityname">
		                            <option value=''>选择商品</option>
	                              </select>
							</div>
						</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">数量</label>
								<div class="col-sm-10">
									<input type="text" id="number" name="number"
										class="form-control">
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeNewUserPackageModal()">取消</button>
								<button type="submit" id="newUserPackageSaveButton"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
		
		
		
		
		<div class="modal fade" id="editUserPackage" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeEditUserPackageModal()">×</button>
						<h3>编辑用户背包信息</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="editUserPackageForm">
							<input type="hidden" id="editpackageid" name="packageid"></input>
							<div class="form-group">
								<label class="col-sm-2 control-label">商品分类</label>
								<div class="col-sm-10">
									<input type="text" id="editcatagoryid" name="catagoryid"
										class="form-control"  readonly="true">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">商品名称</label>
								<div class="col-sm-10">
									<input type="text" id="editcommodityid" name="commodityid"
										class="form-control"  readonly="true">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">数量</label>
								<div class="col-sm-10">
									<input type="text" id="editnumber" name="number"
										class="form-control">
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeEditUserPackageModal()">取消</button>
								<button type="submit" id="editUserPackageSaveButton"
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