//共同取得一览数据的方法
function commonGetrowdatas(gridid, data, url, success, boolean) {
	if (boolean) {
		var options = $('#' + gridid).bootstrapTable('getOptions');
		//获取当前页
		var currentPage = options.pageNumber;
		if (currentPage == 0) {
			currentPage = 1;
		}
		//获取当前页显示数据条数
		var pageSize = options.pageSize;
		data.currentPage = currentPage;
		data.pageSize = pageSize;
	}
	commonGridAjax("post", url, data, success, gridid, boolean);
}

//获取表格数据共通ajax方法，仅供内部调用
function commonGridAjax(type, url, data, success, gridid, boolean) {
	$.ajax({
		type : type,
		url : url,
		data : $.toJSON(data),
		dataType : "json",
		contentType: "application/json; charset=utf-8",
		success : function(response) {
			var result = response.responseData;
			//消息
			var errList = result.errList;
			//返回的消息类型
			var errType = result.errType;
			//后台有消息返回
			if (errType != undefined && errType != null && errType != "") {
				showErrMsgFromBack(errType, errList);//页面显示错误消息
				if (success != null && success != "commonCallback") {
					eval(success)(response);
				} else if (success == "commonCallback") {
					commonCallback(response, gridid, url, data, boolean);
				}
				return;
			} else {
				//如果回调函数=="commonCallback"则调用共通的回调函数，否则调用自定义回调函数
				if (success != null && success != "commonCallback") {
					eval(success)(response);
				} else if (success == "commonCallback") {
					commonCallback(response, gridid, url, data, boolean);
				}
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showMessage("error", getMessageFromList("ErrorServiceStop",null));
			}
			else {
				showMessage("error", getMessageFromList("ErrorSystem",null));
			}
		}

	});
}

//共同回掉函数
//response:后台传回的数据
//gridid：表格控件的id
//boolean 是否需要分页条件
function commonCallback(response, gridid, url, data, boolean) {
	if (gridid != null && gridid != "") {
		$("#" + gridid).bootstrapTable('load', response.responseData);
		//db中数据被删除了，检索的后一页没有数据，页面显示前一页的数据
		if(boolean)
		{
			if (response.responseData.rows != null && response.responseData.rows.length == 0 && response.total > 0) {
				data.currentPage = data.currentPage - 1;
				commonGetrowdatas(gridid, data, url, "commonCallback", boolean);
			}
		}
		
	}
}

//非表格数据用ajax请求
//调用共同ajax方法，外部接口
function doAjax(type, url, data, success) {
	docommonAjax(type, url, data, success);
}

//ajax共同方法
function docommonAjax(type, url, data, success) {
	$.ajax({
		type : type,
		url : url,
		data : $.toJSON(data),
		dataType : "json",
		contentType: "application/json; charset=utf-8",
		success : function(response) {
			var result = response.responseData;
			//消息
			var errList = result.errList;
			//返回的消息类型
			var errType = result.errType;
			var msg = response.message;
			//抛出bizexception异常
			if(msg!=undefined && msg != null && msg != "")
			{
				showMessage("error", msg);
				//回调函数
				eval(success)(response);
				return;
			}
			//后台有消息返回
			if (errType != undefined && errType != null && errType != "") {
				showErrMsgFromBack(errType, errList); //显示错误消息
				//回调函数
				eval(success)(response);
				return;
			} else {
				//回调函数
				eval(success)(response);
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showMessage("error", getMessageFromList("ErrorServiceStop",null));
				return;
			}
			else {
				showMessage("error", getMessageFromList("ErrorSystem",null));
				return;
			}

		}

	});
}

//获取form中的数据，并将其转换成ajax需要的数据格式
function getFormJson(formId) {
	var o = {};
	var fid = "#" + formId;
	var a = $(fid).serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value.trim() || '');
		} else {
			o[this.name] = this.value.trim() || '';
		}
	});
	return o;
}

//显示提示消息
function showMessage(type,message)
{
	//错误消息提示
	if (type == "error") {
		$.notify({
			icon: 'glyphicon glyphicon-remove-sign',
			title: '<strong>Error:</strong>',
			message: message
			},{
			type: 'danger',
			placement: {
				from: "top",
				align: "center"
			}
		});
	}
	//消息提示
	else if (type == "info") {
		$.notify({
			icon: 'glyphicon glyphicon-ok-sign',
			title: '<strong>Info:</strong>',
			message: message
		},{
			type: 'success',
			placement: {
				from: "top",
				align: "center"
			}
		});
	}
	//警告消息提示
	else if (type == "warning") {
		$.notify({
			icon: 'glyphicon glyphicon-warning-sign',
			title: '<strong>Warning:</strong>',
			message: message
		},{
			type: 'warning',
			placement: {
				from: "top",
				align: "center"
			}
		});
	}else{
		$.notify({
			icon: 'glyphicon glyphicon-ok-sign',
			title: '<strong>Info:</strong>',
			message: message
		},{
			type: 'success',
			placement: {
				from: "top",
				align: "center"
			}
		});
	}
}

//弹出后台传回的消息消息
function showErrMsgFromBack(type, errList) {
	var str = "";
	for ( var i = 0; i < errList.length; i++) {
		str += (errList[i] + "</br>");
	}
	//错误消息提示
	if (type == "error") {
		$.notify({
			icon : 'glyphicon glyphicon-remove-sign',
			title : '<strong>Error:</strong>',
			message : str
		}, {
			type : 'danger',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
	//消息提示
	else if (type == "info") {
		$.notify({
			icon : 'glyphicon glyphicon-ok-sign',
			title : '<strong>Info:</strong>',
			message : str
		}, {
			type : 'success',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
	//警告消息提示
	else if (type == "warning") {
		$.notify({
			icon : 'glyphicon glyphicon-warning-sign',
			title : '<strong>Warning:</strong>',
			message : str
		}, {
			type : 'warning',
			placement : {
				from : "top",
				align : "center"
			}
		});
	} else {
		$.notify({
			icon : 'glyphicon glyphicon-ok-sign',
			title : '<strong>Info:</strong>',
			message : str
		}, {
			type : 'success',
			placement : {
				from : "top",
				align : "center"
			}
		});
	}
}

//获取表格选中的行数
function GetDataGridRows(id) {
	var rows = $('#' + id).bootstrapTable('getAllSelections').length;
	return rows;
}

//获取选表格中行
function GetSelectedRowsObj(id) {
	var rowsObj = $('#' + id).bootstrapTable('getSelections');
	return rowsObj;
}

/**
 * 获取表格数据条数
 * @param id
 * @returns
 */
function getTableDataRows(id)
{
	return $('#' + id).bootstrapTable('getData').length;
}

/**
 * 获取表格数据
 * @param id
 * @returns
 */
function getTableDataRowsObj(id)
{
	return $('#' + id).bootstrapTable('getData');
}


//form表单中有file的form提交共通方法
//id:表单id,type:提交类型，get or post,url:提交路径,success:提交成功的回调函数
function doFileFormAjax(id,type, url,data, success) {
	$("#" + id).ajaxSubmit({
		type : type,
		url : url,
		data : data,
		dataType : "json",
		contentType:"application/json",
		success : function(response) {
			//返回结果转化成json格式
			var result = response;
			//消息
			var errList = result.errList;
			//返回的消息类型
			var errType = result.errType;
			if (errList.length != 0) {
				showErrMsgFromBack(errType, errList);
				if (success != null) {
					eval(success)(response);
				}
				return;
			} else {
				eval(success)(response);
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showMessage("error", "服务器停止运行，请与管理员联系");
			} 
			else {
				showMessage("error", "系统异常，请与管理员联系");
			}

		}
	});
}

//提交数组数据的ajax方法
function doListAjax(type, url, data, success)
{
	$.ajax({
		type : type,
		url : url,
		data : JSON.stringify(data),
		dataType : "json",
		contentType:"application/json", 
		success : function(response) {
			var result = response.responseData;
			//消息
			var errList = result.errList;
			//返回的消息类型
			var errType = result.errType;
			var msg = response.message;
			//抛出bizexception异常
			if(msg!=undefined && msg != null && msg != "")
			{
				showMessage("error", msg);
				//回调函数
				eval(success)(response);
				return;
			}
			//后台有消息返回
			if (errType != undefined && errType != null && errType != "") {
				showErrMsgFromBack(errType, errList); //显示错误消息
				//回调函数
				eval(success)(response);
				return;
			} else {
				//回调函数
				eval(success)(response);
			}
		},
		error : function(jqXHR, exception) {
			if (jqXHR.status === 0) {
				showMessage("error", getMessageFromList("ErrorServiceStop",null));
			}   else {
				showMessage("error", getMessageFromList("ErrorSystem",null));
			}

		}

	});
}


/**
 * 确认提示框
 * @param funName 确认后执行的回调函数
 * @param msg 确认提示框显示的内容
 * @param type 请求类型，post或者get
 * @param url 请求的url
 * @param data 请求参数
 * @param success 执行成功后的回调函数
 * @returns
 */
function showConfirm(funName, msg, type, url, data, success) {
	bootbox.setLocale("zh_CN");
	bootbox.confirm(msg, function(result) {
		if (result) {
			eval(funName)(type, url, data, success);
		}
	});
}


//获取checkboxtree所有选中的节点
function getSelectTree(treeId) {
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	var nodes = treeObj.getCheckedNodes(true);
	return nodes;
}

//范围日期控件初始化
function initRangeDate(id)
{
	// 日期返回控件初始化
	$('#' + id).daterangepicker({
		autoApply: true,// 时间选择完成后是否自动关闭，如果有时间选择的话不起作用
		singleDatePicker : false,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		startDate : moment(),// 初始化显示当前日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			//"format" : "YYYY-MM-DD",// 设置日期格式
			"separator" : " - ",// 设置日期范围的分隔符号，如果不是设置一个日期范围的话此属性不需要设置
		},
	    // 设置快捷输入的时间
	    ranges: {
	    	'今日': [moment(), moment()],
			'昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'明日': [moment().add(1, 'days'), moment().add(1, 'days')],
			'过去7天': [moment().subtract(6, 'days'), moment()],
			'过去30天': [moment().subtract(29, 'days'), moment()],
			'本月': [moment().startOf('month'), moment().endOf('month')],
			'上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
			'今日开始的一周': [moment(), moment().add(7, 'days')],
			'今日到本月末尾': [moment(), moment().endOf('month')]
		},
	});
	$('#' + id).val('');
}

//日期控件初始化
function initSingleDate(id)
{
	// 开始日期的日期控件初始化
	$('#' + id).daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
		}
	});
	
	$('#' + id).val('');
}

//单独上传文件ajax共通方法
function ajaxFileUpload(type, url, id, success) {
	//执行上传文件操作的函数
	$.ajaxFileUpload({
		//处理文件上传操作的服务器端地址
		url : url,
		type : type,
		secureuri : false, //是否启用安全提交,默认为false
		fileElementId : id, //文件选择框的id属性
		dataType : "json", //服务器返回的格式,可以是json或xml等
		contentType: "application/json; charset=utf-8",
		success : function(data, status) { //服务器响应成功时的处理函数
			//返回结果转化成json格式
			var result = data.responseData;
			//消息
			var errList = result.errList;
			//返回的消息类型
			var errType = result.errType;
			var msg = data.message;
			//抛出bizexception异常
			if(msg!=undefined && msg != null && msg != "")
			{
				showMessage("error", msg);
				//回调函数
				eval(success)(data);
				return;
			}
			if (errList != undefined && errList.length != 0) {
				showErrMsgFromBack(errType, errList);
				if (success != null) {
					eval(success)(data);
				}
				return;
			} else {
				eval(success)(data);
			}
		},
		error : function(data, status, e) { //服务器响应失败时的处理函数
			showMessage("error", "系统错误，请与管理员联系！");
		}
	});
}

//单独上传文件ajax共通方法,成功回调带参数
function ajaxFileUploadThenSubmit(type, url, id, success, formData) {
	//执行上传文件操作的函数
	$.ajaxFileUpload({
		//处理文件上传操作的服务器端地址
		url : url,
		type : type,
		secureuri : false, //是否启用安全提交,默认为false
		fileElementId : id, //文件选择框的id属性
		dataType : "json", //服务器返回的格式,可以是json或xml等
		success : function(data, status) { //服务器响应成功时的处理函数
			//返回结果转化成json格式
			var result = data;
			//消息
			var errList = result.errList;
			//返回的消息类型
			var errType = result.errType;
			if (errList != undefined && errList.length != 0) {
				showErrMsgFromBack(errType, errList);
				if (success != null) {
					eval(success)(data, formData);
				}
				return;
			} else {
				eval(success)(data, formData);
			}
		},
		error : function(data, status, e) { //服务器响应失败时的处理函数
			showMessage("error", "系统错误，请与管理员联系！");
		}
	});
}

function fileInputInit(id,hiddenid)
{
	$("#" + id).fileinput({
		language: "zh",//语言设置
		uploadUrl: contextPath + "/common/upload/upload", // server upload action
	    uploadAsync: true,
	    showUpload: false, // hide upload button
	    dropZoneEnabled:false,
	    autoReplace:true,
	    maxFileCount: 1,
	    allowedFileExtensions: ["jpg", "gif", "png"],//上传文件类型限制
	    resizeImage: true,
        maxImageWidth: 200,
        maxImageHeight: 200,
        dropZoneEnabled:false,
	    autoReplace:true,
	})
	.on("change", function(event, files) {
		$("#" + id).fileinput("upload");
	})
	.on("fileuploaded", function(event, data) {
	        if(data.response)
	        {
	        	if(data.response.responseData.resultType == 'error')
	        	{
	        		$("#" + id).fileinput("reset");
	        		showMessage("error", "文件上传失败，请重试");
	        		return;
	        	}
	        	$("#" + hiddenid).val(data.response.responseData.downloadUrl);
	        } 
        });
}

/**
 * 初始化日期范围控件
 * @param id
 * @returns
 */
function initRangeDate(id)
{
	// 日期返回控件初始化
	$('#' + id).daterangepicker({
		autoApply: true,// 时间选择完成后是否自动关闭，如果有时间选择的话不起作用
		singleDatePicker : false,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		//timePicker : true,// 是否有时间选择
		//timePicker24Hour: true,// 时间选择是否是24小时制
		startDate : moment(),// 初始化显示当前日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			//"format" : "YYYY-MM-DD",// 设置日期格式
			"separator" : " - ",// 设置日期范围的分隔符号，如果不是设置一个日期范围的话此属性不需要设置
		},
	    // 设置快捷输入的时间
	    ranges: {
	    	'今日': [moment(), moment()],
			'昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'明日': [moment().add(1, 'days'), moment().add(1, 'days')],
			'过去7天': [moment().subtract(6, 'days'), moment()],
			'过去30天': [moment().subtract(29, 'days'), moment()],
			'本月': [moment().startOf('month'), moment().endOf('month')],
			'上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
			'今日开始的一周': [moment(), moment().add(7, 'days')],
			'今日到本月末尾': [moment(), moment().endOf('month')]
		}
	});
	$('#' + id).val('');
}


/**
 * 初始化带有选择天数限制的日期控件
 * @param id
 * @param limit
 * @returns
 */
function initRangeDatewithlimit(id,limit)
{
	// 日期返回控件初始化
	$('#' + id).daterangepicker({
		autoApply: true,// 时间选择完成后是否自动关闭，如果有时间选择的话不起作用
		singleDatePicker : false,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		//timePicker : true,// 是否有时间选择
		//timePicker24Hour: true,// 时间选择是否是24小时制
		startDate : moment(),// 初始化显示当前日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			//"format" : "YYYY-MM-DD",// 设置日期格式
			"separator" : " - ",// 设置日期范围的分隔符号，如果不是设置一个日期范围的话此属性不需要设置
		},
		dateLimit: {
	        	days:limit
	    },
	    // 设置快捷输入的时间
	    ranges: {
	    	'今日': [moment(), moment()],
			'昨日': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
			'明日': [moment().add(1, 'days'), moment().add(1, 'days')],
			'过去7天': [moment().subtract(6, 'days'), moment()],
			'过去30天': [moment().subtract(29, 'days'), moment()],
			'本月': [moment().startOf('month'), moment().endOf('month')],
			'上月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')],
			'今日开始到本周五': [moment(), moment().endOf('week').subtract(1, 'days')],
			'今日开始到本周日': [moment(), moment().endOf('week').add(1, 'days')],
			'今日开始的一周': [moment(), moment().add(7, 'days')],
			'今日到本月末尾': [moment(), moment().endOf('month')]
		}
	});
}

/**
 * 初始化日期控件
 * @param id
 * @returns
 */
function initDateControl(id)
{
	$('#' + id).daterangepicker({
		singleDatePicker : true,// false选择一个日期范围，true选择一个单独的日期
		showDropdowns : true,// 是否有下拉列表选择日期
		showWeekNumbers: true,// 是否显示第几周的数字
		// 设置格式
		locale : {
			"format" : "YYYY-MM-DD",// 设置日期格式,默认为YYYY-MM-DD
			// "separator" : " - ",//设置日期范围的分隔符号
		}
	});
}