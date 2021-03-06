var rows;
$(function(){
	$("#userTable").bootstrapTable({
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
					{field : "accountname",title : "账号名称",align : "center",valign : "middle"},
					{field : "nickname",title : "昵称",align : "center",valign : "middle"},
					{field : "foreighid",title : "外部id",align : "center",valign : "middle"},
					{field : "foreightype",title : "外部id类型",align : "center",valign : "middle"},
					{field : "funds",title : "资产",align : "center",valign : "middle"},
					{field : "createdate",title : "创建日期",align : "center",valign : "middle"},
					{field : "password",title : "密码",align : "center",valign : "middle"},
					{field : "opration",title : "操作",align : "center",valign : "middle",
							formatter : function(value, row, index) {
								var t = "";
								var m = "";
								var p = "";
								t = '<a href="#" onclick="editUser(\'' + row.userid + '\')">编辑</a> | ';
								m = '<a href="#" onclick="deleteUser(\'' + row.userid + '\')">删除</a> | ';
								p = '<a href="#" onclick="userPackage(\'' + row.userid + '\')">用户背包</a>';
//								return t + m + p;
								return t + p;
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
	$("#newUserForm").bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			accountname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '账号名称' ])
					}
				}
			},
			nickname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '昵称' ])
					}
				}
			},
			/*foreighid : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '外部id' ])
					}
				}
			},
			foreightype : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '外部id类型' ])
					}
				}
			},*/
			funds : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '资产' ])
					}
				}
			},
			password : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '密码' ])
					}
				}
			}
		}
	}).on("success.form.bv",function(e){
		e.preventDefault();
		var data = getFormJson("newUserForm");
		showConfirm(doAjax,"确认要添加该用户？","post",contextPath + "/user/registeUser", data, addSuccess);
//		doAjax("post",contextPath + "/user/registeUser",data,addSuccess);
	});
});


$(function(){
	$("#editUserForm").bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			editaccountname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '账号名称' ])
					}
				}
			},
			editnickname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '昵称' ])
					}
				}
			},
			/*foreighid : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '外部id' ])
					}
				}
			},
			foreightype : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '外部id类型' ])
					}
				}
			},*/
			editfunds : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '资产' ])
					}
				}
			},
			editpassword : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '密码' ])
					}
				}
			}
		}
	}).on("success.form.bv",function(e){
		e.preventDefault();
		var data = getFormJson("editUserForm");
		showConfirm(doAjax,"确认要更新该用户？","post",contextPath + "/user/updateUser", data, updateSuccess);
//		doAjax("post",contextPath + "/user/registeUser",data,addSuccess);
	});
});

function addSuccess(){
	closeNewUserModal();
	searchUserInfo();
}

function updateSuccess(){
	closeEditUserModal();
	searchUserInfo();
}

$(document).ready(function() {
	searchUserInfo();
});

/**
 * 查询用户信息
 */
function searchUserInfo(){
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("userTable", data, contextPath
			+ "/user/getUserList", "commonCallbackCustom", true);
	
}

function commonCallbackCustom(response, gridid, url, data, boolean){
	commonCallback(response, gridid, url, data, boolean);
	rows = response.responseData.rows;
}

/**
 * 重置搜索表单
 */
function resetSearch(){
	$("#accountnameQuery").val("");
	$("#nicknameQuery").val("");
	searchUserInfo();
}
/**
 * 添加用户
 */
function addNewUser(){
	$('#newUser').modal({show:true,backdrop: 'static', keyboard: false});
}
/**
 * 关闭添加用户模态框
 */
function closeNewUserModal(){
	$('#newUserForm').data('bootstrapValidator').resetForm(true);
	$('#newUser').modal('hide');
}
/**
 * 关闭编辑用户模态框
 */
function closeEditUserModal(){
	$('#editUserForm').data('bootstrapValidator').resetForm(true);
	$('#editUser').modal('hide');
}

/**
 * 编辑用户
 */
function editUser(userid){
	$("#userid").val(userid);
	$('#editUser').modal({show:true,backdrop: 'static', keyboard: false});
	for(var i = 0 ; i < rows.length ; i++){
		if(userid == rows[i].userid){
			$("#editaccountname").val(rows[i].accountname);
			$("#editnickname").val(rows[i].nickname);
			$("#editforeighid").val(rows[i].foreighid);
			$("#editforeightype").val(rows[i].foreightype);
			$("#editfunds").val(rows[i].funds);
			$("#editpassword").val(rows[i].password);
		}
	}
}

/**
 * 用户背包
 */
function userPackage(userid){
	window.location.href = contextPath + "/user/userPackageInit" + "?userId=" + userid;
}