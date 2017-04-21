$(function(){
	//分类列表格式化
	$("#catagoryTable").bootstrapTable(
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
						{field : "catagoryid",title : "商品分类id",align : "center",valign : "middle"},
						{field : "catagoryname",title : "商品分类名称",align : "center",valign : "middle"},
						{field : "opration",title : "操作",align : "center",valign : "middle",
								formatter : function(value, row, index) {
									var t = "";
									var m = "";
									t = '<a href="#" onclick="edit(\''+ row.catagoryid + '\', \'' + row.catagoryname + '\')">编辑</a> | ';
									m = '<a href="#" onclick="deleteCatagory(\''+ row.catagoryid + '\')">删除</a>';
									return t + m
								}
							}
						],
				onPageChange : function(size, number) {
					searchCatagoryInfo();
				},
				formatNoMatches : function() {
					return '无符合条件的记录';
				}
			});
	
	
});


$(function(){
	
	$('#newCatagoryForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			catagoryname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '分类名称' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("newCatagoryForm");
		doAjax("post",contextPath + "/trade/addNewCatagory",data,saveSuccess);
	});
	
});


$(function(){
	$('#editCatagoryForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		
		fields : {
			editcatagoryname : {
				validators : {
					notEmpty : {
						message :getMessageFromList("ErrorMustInput",[ '分类名称' ])
					}
				}
			}
		}
		
	}).on('success.form.bv', function(e) {
		e.preventDefault();
		var data = getFormJson("editCatagoryForm");
		doAjax("post",contextPath + "/trade/addNewCatagory",data,saveSuccess);
	});
});

function saveSuccess(){
	closeNewCatagoryModal();
	closeEditCatagoryModal();
	//重新查询数据
	searchCatagoryInfo();
}

function closeNewCatagoryModal(){
	$('#newCatagoryForm').data('bootstrapValidator').resetForm(true);
	$('#newCatagory').modal('hide');
}


$(document).ready(function() {
	searchCatagoryInfo()
});

/**
 * 查询分类数据
 */
function searchCatagoryInfo()
{
	var data = getFormJson("searchForm");//获取查询条件
	commonGetrowdatas("catagoryTable", data, contextPath
			+ "/trade/queryCatagoryList", "commonCallback", true);
}
/**
 * 重置检索
 * @returns
 */
function resetSearch()
{
	$("#catagoryname").val('');
	searchCatagoryInfo();
}
/**
 * 添加商品分类
 */
function addNew()
{
	$('#newCatagory').modal({show:true,backdrop: 'static', keyboard: false});
}
/**
 * 编辑
 */
function edit(catagoryid,catagoryname){
	//模态框
	$('#editcatagoryname').val(catagoryname);
	$('#catagoryId').val(catagoryid);
	$('#editCatagory').modal({show:true,backdrop: 'static', keyboard: true});
}

function closeEditCatagoryModal(){
	$('#editcatagoryname').val('');
	$('#catagoryId').val('');
	$('#editCatagoryForm').data('bootstrapValidator').resetForm(true);
	$('#editCatagory').modal('hide');
}

/**
 * 删除
 * @param catagoryid
 */
function deleteCatagory(catagoryid){
	//提示
	var data = {
		"catagoryId":catagoryid
	};
	showConfirm(doAjax,"确认要删除该分类？","post",contextPath + "/trade/deleteCatagory", data, deleteSuccess);
}

function deleteSuccess(){
	searchCatagoryInfo();
}






