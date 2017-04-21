$(function(){
	$("#commodityTable").bootstrapTable(
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
						{field : "commodityid",title : "商品id",align : "center",valign : "middle"},
						{field : "commodityname",title : "商品名称",align : "center",valign : "middle"},
						{field : "commoditydes",title : "商品描述",align : "center",valign : "middle"},
						{field : "opration",title : "操作",align : "center",valign : "middle",
								formatter : function(value, row, index) {
									var t = "";
									var m = "";
									t = '<a href="#" onclick="edit(\''+ row.commodityid + '\', \'' + row.commodityname + '\', \'' + row.commoditydes + '\', \'' + row.commoditycatagoryid +'\')">编辑</a> | ';
									m = '<a href="#" onclick="deleteCommodity(\''+ row.commodityid + '\')">删除</a>';
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

$(document).ready(function() {
	searchCommodity()
});


$(function(){
	
	$('#newCommodityForm').bootstrapValidator({
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
			commoditycatagoryid : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '商品分类' ])
					}
				}
			},
			commoditydes : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '商品描述' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("newCommodityForm");
		doAjax("post",contextPath + "/trade/addNewCommodity",data,addSuccess);
	});
	
});

function addSuccess(){
	closeNewCommodityModal();
	searchCommodity();
}


$(function(){
	
	$('#editCommodityForm').bootstrapValidator({
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
			editcommoditycatagoryid : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '商品分类' ])
					}
				}
			},
			commoditydes : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '商品描述' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("editCommodityForm");
		doAjax("post",contextPath + "/trade/editNewCommodity",data,editSuccess);
	});
	
});

function editSuccess(){
	closeEditCommodityModal();
	searchCommodity();
}

/**
 * 查询商品列表
 */
function searchCommodity(){
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("commodityTable", data, contextPath
			+ "/trade/queryCommodityList", "commonCallback", true);
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
	$('#newCommodityModel').modal({show:true,backdrop: 'static', keyboard: false});
}

/**
 * 关闭添加商品模态框
 */
function closeNewCommodityModal(){
	$('#newCommodityForm').data('bootstrapValidator').resetForm(true);
	$('#newCommodityModel').modal('hide');
}

/**
 * 关闭编辑商品模态框
 */
function closeEditCommodityModal(){
	$('#editCommodityForm').data('bootstrapValidator').resetForm(true);
	$('#editCommodityModel').modal('hide');
}


/**
 * 编辑
 */
function edit(commodityid,commodityname,commoditydes,commoditycatagoryid){
	//模态框
	$('#commodityid').val(commodityid);
	$('#editcommodityname').val(commodityname);
	$('#editcommoditydes').val(commoditydes);
	
	//将下拉选设置成正确的商品分类
//	var opetions = $("#editcommoditycatagoryid option");
//	for(var i = 0 ; i < opetions.length ; i++){
//		if($(opetions[i]).val() == commoditycatagoryid){
//			$(opetions[i]).attr("selected",true); 
//		}
//	}
	$("#editcommoditycatagoryid").val(commoditycatagoryid);
	
	$('#editCommodityModel').modal({show:true,backdrop: 'static', keyboard: true});
}
/**
 * 删除商品
 */
function deleteCommodity(commodityid){
	//提示
	var data = {
		"commodityid":commodityid
	};
	showConfirm(doAjax,"确认要删除该商品？","post",contextPath + "/trade/deleteCommodity", data, deleteSuccess);
}
/**
 * 删除商品
 */
function deleteSuccess(){
	searchCommodity();
}