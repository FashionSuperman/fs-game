$(function(){
	$("#shopCommodityTable").bootstrapTable(
			{
				cache : false,
				striped : true,
				pagination : true,
				toolbar : '#toolbar',
				pageSize : 10,
				pageNumber : 1,
				pageList : [ 10, 20, 30],
				clickToSelect : false,
				sidePagination : 'server',// 设置为服务器端分页
				columns : [
				        {field : "",checkbox : true},
						{field : "commodityname",title : "商品名称",align : "center",valign : "middle"},
						{field : "commoditydes",title : "商品描述",align : "center",valign : "middle"},
						{field : "discount",title : "折扣",align : "center",valign : "middle"},
						{field : "discountdes",title : "折扣说明",align : "center",valign : "middle"},
						{field : "price",title : "商品价格",align : "center",valign : "middle"},
						{field : "number",title : "数量",align : "center",valign : "middle"},
						{field : "opration",title : "操作",align : "center",valign : "middle",
								formatter : function(value, row, index) {
									var t = "";
									var m = "";
									t = '<a href="#" onclick="edit(\'' + row.shopitemid + '\', \'' + row.commodityid + '\', \'' + row.number + '\', \'' + row.price + '\', \'' + row.commoditycatagoryid + '\', \'' + row.discount + '\', \'' + row.discountdes + '\')">编辑</a> | ';
									m = '<a href="#" onclick="deleteCommodity(\''+ row.shopitemid + '\')">下架</a>';
									return t + m
								}
							}
						],
				onPageChange : function(size, number) {
					searchCommodity();
				},
				formatNoMatches : function() {
					return '无符合条件的记录';
				}
			});
});

$(function(){
	$('#newShopCommodityForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			commodityname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '商品名称' ])
					}
				}
			},
			number : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '数量' ])
					}
				}
			},
			price : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '价格' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("newShopCommodityForm");
		doAjax("post",contextPath + "/trade/addNewShopCommodity",data,addSuccess);
	});
});



$(function(){
	$('#editShopCommodityForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			editcommodityname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '商品名称' ])
					}
				}
			},
			editnumber : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '数量' ])
					}
				}
			},
			editprice : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '价格' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("editShopCommodityForm");
		showConfirm(doAjax,"确认要修改该商品？","post",contextPath + "/trade/editNewShopCommodity", data, editSuccess);
//		doAjax("post",contextPath + "/trade/editNewShopCommodity",data,editSuccess);
	});
});

function addSuccess(){
	closeNewShopCommodityModal();
	searchCommodity();
}
function editSuccess(){
	closeEditShopCommodityModal();
	searchCommodity();
}


function editSuccess(){
	closeEditShopCommodityModal();
	searchCommodity();
}

$(document).ready(function() {
	searchCommodity();
	//商品分类select绑定事件
	$("#commoditycatagoryid").change(catagoryChangeFun);
	$("#editcommoditycatagoryid").change(catagoryChangeFun);
	
});

function catagoryChangeFun(){
	var selected
	selected = $(this).val();//分类id
	id = $(this).attr("id");
	
	var data = {
			"catagoryid":selected
	};
	if(selected && selected != ""){
		//查询该分类下的所有未上线的商品
		doAjax("post",contextPath + "/trade/queryFooCatagoryCommodities",data,querySuccess);
	}
}

function catagoryChangeFun2(ele){
	var selected
	selected = ele.val();//分类id
	id = $(this).attr("id");
	
	var data = {
			"catagoryid":selected
	};
	if(selected && selected != ""){
		//查询该分类下的所有未上线的商品
		doAjax("post",contextPath + "/trade/queryFooCatagoryCommoditiesAll",data,querySuccess);
	}
}

function querySuccess(response){
	//构造options添加到商品select
	var option1 = "<option value=\'";
	var option2 = "\'>";
	var option3 = "</option>";
	
	var data = response.responseData;
	var opetionele;
	for(var i = 0 ; i < data.length ; i++){
		opetionele = option1 + data[i].commodityid + option2 + data[i].commodityname + option3;
		
		if("commoditycatagoryid" == id){
			$("#newShopCommodityForm #commodityname").append(opetionele);
		}else if("editcommoditycatagoryid" == id){
			$("#editShopCommodityForm #editcommodityname").append(opetionele);
		}
	}
	if("editcommoditycatagoryid" == id){
		$("#editcommodityname").val(commodityidEdit);
	}
}

/**
 * 查询商品列表
 */
function searchCommodity(){
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("shopCommodityTable", data, contextPath
			+ "/trade/queryShopCommodityList", "commonCallback", true);
}

/**
 * 重置检索
 */
function resetSearch(){
	$("#commodityname").val("");
	$("#catagoryname").val("");
	searchCommodity();
}

/**
 * 添加分类
 */
function addNew(){
	$('#newShopCommodityModel').modal({show:true,backdrop: 'static', keyboard: false});
}
/**
 * 关闭添加模态框
 */
function closeNewShopCommodityModal(){
	$('#newShopCommodityForm').data('bootstrapValidator').resetForm(true);
	$('#newShopCommodityModel').modal('hide');
}
/**
 * 关闭编辑模态框
 */
function closeEditShopCommodityModal(){
	$('#editShopCommodityForm').data('bootstrapValidator').resetForm(true);
	$('#editShopCommodityModel').modal('hide');
}

/**
 * 编辑商品
 * @param commodityid
 * @param commodityname
 * @param commoditydes
 * @param commoditycatagoryid
 */
function edit(shopitemid,commodityid,number,price,commoditycatagoryid,discount,discountdes){
	id = "editcommoditycatagoryid";
	//回显
	$("#shopitemid").val(shopitemid);
	$("#editcommoditycatagoryid").val(commoditycatagoryid);
	
	$("#editnumber").val(number);
	$("#editprice").val(price);
	$("#editdiscount").val(discount);
	$("#editdiscountdes").val(discountdes);
	
	commodityidEdit = commodityid;
	
//	$("#editcommodityname").val(commodityid);
	catagoryChangeFun2($("#editcommoditycatagoryid"));
	
	$('#editShopCommodityModel').modal({show:true,backdrop: 'static', keyboard: false});
	
}


/**
 * 下架商品
 */
function deleteCommodity(shopitemid){
	var data = {
		"shopitemid":shopitemid
	};
	showConfirm(doAjax,"确认要下架该商品？","post",contextPath + "/trade/deleteNewShopCommodity", data, deleteSuccess);
}



function deleteSuccess(){
	searchCommodity();
}