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
				{field : "opration",title : "操作",align : "center",valign : "middle",
						formatter : function(value, row, index) {
							var t = "";
							var m = "";
							t = '<a href="#" onclick="editUserPackage(\'' + row.userid + '\')">编辑</a> | ';
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