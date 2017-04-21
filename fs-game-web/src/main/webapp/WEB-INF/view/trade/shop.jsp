<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商店</title>
<%@include file="../common/import.jsp"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/trade/shop.js"></script>
</head>

<body>
	<div class="ibox-content">
		<div class="row row-lg">
			<div class="col-sm-12">
				<div class="example-wrap">
					<div class="example">
						<form id="searchForm" method="post" class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-1 control-label">商品名称:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="commodityname"
										id="commodityname" />
								</div>
								<label class="col-sm-1 control-label">商品所属分类:</label>
								<div class="col-sm-2">
									<input type="text" class="form-control" name="catagoryname"
										id="catagoryname" />
								</div>
								<button id="btn_query" type="button" class="btn btn-primary"
									onclick="searchCommodity()">
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
							<table id="shopCommodityTable">
							</table>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		
		
		<!-- 添加商品  模态框 -->
		<div class="modal fade" id="newShopCommodityModel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeNewShopCommodityModal()">×</button>
						<h3>新增商品</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="newShopCommodityForm">
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
									<input type="text" id="number" name="number" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">价格</label>
								<div class="col-sm-10">
									<input type="text" id="price" name="price" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">折扣</label>
								<div class="col-sm-10">
									<input type="text" id="discount" name="discount" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">折扣描述</label>
								<div class="col-sm-10">
									<textarea class="form-control" rows="5" id="discountdes" name="discountdes" >
									</textarea> 
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeNewShopCommodityModal()">取消</button>
								<button type="submit" id="newShopCommoditySaveButton"
									class="btn btn-primary">保存</button>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
		
		
		
		<!-- 编辑商品模态框 -->
		<div class="modal fade" id="editShopCommodityModel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">

			<div class="modal-dialog" style="width: 800px;">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							onclick="closeEditShopCommodityModal()">×</button>
						<h3>编辑商品</h3>
					</div>

					<div class="ibox-content">
						<form class="form-horizontal" id="editShopCommodityForm">
							<input type="hidden" id="shopitemid" name="shopitemid"></input>
							<div class="form-group">
								<label class="col-sm-2 control-label">商品分类</label>
								<div class="col-sm-10">
									<select class="form-control m-b" id="editcommoditycatagoryid">
		                            	<option value=''>选择分类</option>
		                                <c:forEach items="${commodityCatagoryList}" var="commodityCatagory" varStatus="s">
					                    	<option value="${commodityCatagory.catagoryid}">${commodityCatagory.catagoryname}</option>
					                    </c:forEach>
	                                </select>
								</div>
								<label class="col-sm-2 control-label">商品名称</label>
								<div class="col-sm-10">
									<select class="form-control m-b" name="commodityid" id="editcommodityname">
		                            	<option value=''>选择商品</option>
	                                </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">数量</label>
								<div class="col-sm-10">
									<input type="text" id="editnumber" name="number" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">价格</label>
								<div class="col-sm-10">
									<input type="text" id="editprice" name="price" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">折扣</label>
								<div class="col-sm-10">
									<input type="text" id="editdiscount" name="discount" class="form-control">
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">折扣描述</label>
								<div class="col-sm-10">
									<textarea class="form-control" rows="5" id="editdiscountdes" name="discountdes" >
									</textarea> 
								</div>
							</div>
							
							<div class="modal-footer">
								<button type="button" class="btn btn-primary"
									onclick="closeEditShopCommodityModal()">取消</button>
								<button type="submit" id="editShopCatagorySaveButton"
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