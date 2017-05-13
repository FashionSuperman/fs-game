$(function(){
	$("#userPackageTable").bootstrapTable({
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
				{field : "catagoryname",title : "商品分类",align : "center",valign : "middle"},
				{field : "commodityname",title : "商品名称",align : "center",valign : "middle"},
				{field : "number",title : "数量",align : "center",valign : "middle"},
				{field : "opration",title : "操作",align : "center",valign : "middle",
						formatter : function(value, row, index) {
							var t = "";
							var m = "";
							t = '<a href="#" onclick="editNewUserPackage(\'' 
								+ row.packageid + '\',\'' 
								+ row.catagoryname + '\',\'' 
								+  row.commodityname + '\',\'' 
								+ row.number + '\')">编辑</a> | ';
							
							m = '<a href="#" onclick="deleteUserPackage(\'' + row.userid + '\')">删除</a>';
							return t + m;
						}
					}
				],
		onPageChange : function(size, number) {
			searchUserInfo();
		},
		formatNoMatches : function() {
			return '无符合条件的记录';
		}
	
	});
});

$(function(){
	$('#newUserPackageForm').bootstrapValidator({
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
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("newUserPackageForm");
		data.userid = $("#userId").val();
		data.shopitemid = data.commodityid;
		delete data.commodityid;
		showConfirm(doAjax,"确认要给改用户添加该商品？","post",contextPath + "/user/buyShopCommodity", data, buySuccess);
	});
});


$(function(){
	$('#editUserPackageForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			number : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '数量' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("editUserPackageForm");
		delete data.commodityid;
		delete data.catagoryid;
		
		
		
		showConfirm(doAjax,"确认要修改？","post",contextPath + "/user/editPackageNum", data, editSuccess);
	});
});

function editSuccess(){
	closeEditUserPackageModal();
	searchUserPackageInfo();
}

function buySuccess(){
	closeNewUserPackageModal();
	searchUserPackageInfo();
}

$(document).ready(function() {
	searchUserPackageInfo();
});

function searchUserPackageInfo(){
	var data = {
			"userid":$("#userId").val()
	};
	commonGetrowdatas("userPackageTable", data, contextPath
			+ "/user/getUserPackageList", "commonCallback", true);
	
}


$(document).ready(function() {
	//商品分类select绑定事件
	$("#commoditycatagoryid").change(catagoryChangeFun);
//	$("#editcommoditycatagoryid").change(catagoryChangeFun);
});

function catagoryChangeFun(){
	//清除联动的select的option
	var tempOpe = "<option value=''>选择分类</option>";
	$("#newUserPackageForm #commodityname").empty().append(tempOpe);
	$("#editUserPackageForm #editcommodityname").empty().append(tempOpe);
	
	var selected
	selected = $(this).val();//分类id
	id = $(this).attr("id");
	
	var data = {
			"catagoryid":selected
	};
	if(selected && selected != ""){
		//查询该分类下的所有未上线的商品
		doAjax("post",contextPath + "/trade/queryFooCatagoryCommoditiesShop",data,querySuccess);
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
		opetionele = option1 + data[i].shopitemid + option2 + data[i].commodityname + option3;
		
		if("commoditycatagoryid" == id){
			$("#newUserPackageForm #commodityname").append(opetionele);
		}else if("editcommoditycatagoryid" == id){
			$("#editUserPackageForm #editcommodityname").append(opetionele);
		}
	}
	if("editcommoditycatagoryid" == id){
		$("#editcommodityname").val(commodityidEdit);
	}
}

/**
 * 添加用户背包
 */
function addNewUserPackage(){
	$("#newUserPackage").modal({show:true,backdrop:'static',keyboard:false});
}

function editNewUserPackage(packageid,catagoryname,commodityname,number){
	//回显
	$("#editpackageid").val(packageid);
	$("#editcatagoryid").val(catagoryname);
	$("#editcommodityid").val(commodityname);
	$("#editnumber").val(number);
	
	$("#editUserPackage").modal({show:true,backdrop:'static',keyboard:false});
}
/**
 * 关闭用户背包页面
 */
function closeNewUserPackageModal(){
	$('#newUserPackageForm').data('bootstrapValidator').resetForm(true);
	$("#newUserPackage").modal('hide');
}
/**
 * 关闭编辑用户背包页面
 */
function closeEditUserPackageModal(){
	$('#editUserPackageForm').data('bootstrapValidator').resetForm(true);
	$("#editUserPackage").modal('hide');
}