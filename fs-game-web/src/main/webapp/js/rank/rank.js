$(function() {
	$("#userTable").bootstrapTable({
		cache : false,
		striped : true,
		pagination : true,
		toolbar : '#toolbar',
		pageSize : 10,
		pageNumber : 1,
		pageList : [ 10, 20, 30 ],
		clickToSelect : false,
		sidePagination : 'server',// 设置为服务器端分页
		columns : [ {
			field : "",
			checkbox : true
		}, {
			field : "accountname",
			title : "账号名称",
			align : "center",
			valign : "middle"
		}, {
			field : "nickname",
			title : "昵称",
			align : "center",
			valign : "middle"
		}, {
			field : "foreighid",
			title : "外部id",
			align : "center",
			valign : "middle"
		}, {
			field : "foreightype",
			title : "外部id类型",
			align : "center",
			valign : "middle"
		}, {
			field : "funds",
			title : "资产",
			align : "center",
			valign : "middle"
		}, {
			field : "createdate",
			title : "创建日期",
			align : "center",
			valign : "middle"
		}, {
			field : "password",
			title : "密码",
			align : "center",
			valign : "middle"
		}, {
			field : "score",
			title : "分数",
			align : "center",
			valign : "middle"
		} ],
		onPageChange : function(size, number) {
			searchUserInfo();
		},
		formatNoMatches : function() {
			return '无符合条件的记录';
		}

	});
});

$(document).ready(function() {
	searchUserInfo();
});

/**
 * 查询用户
 */
function searchUserInfo() {
	commonGetrowdatas("userTable", {}, contextPath + "/rank/getUserList",
			"commonCallback", true);
}
