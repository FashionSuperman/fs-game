var MSG_LIST = new Array();

//获取js错误消息
function getMessageFromList(msgid, msgParams) {
			var msg = MSG_LIST[msgid];
		    if ($.trim(msg) == "") {
		        msg = msgid + "[该消息不存在。]";
		        return msg;
		    }
		    if (msgParams != null) { 
		    	for (var i=0; i<msgParams.length; i++) {
		            msg = msg.replace("{" + i + "}", msgParams[i]);
		        }  
		    }
		    return msg;
}


$(document).ready(function() {
	MSG_LIST["ErrorSystem"] = "系统异常，请与管理员联系。";
	MSG_LIST["ErrorServiceStop"] = "服务器停止运行，请与管理员联系。";
	MSG_LIST["ErrorMustInput"] = "请输入{0}。";
	MSG_LIST["ErrorTableDeleteSelect"] = "请选中要删除的数据。";
	MSG_LIST["ErrorSelectOption"] = "请选择{0}";
	MSG_LIST["ErrorTableChangeSelect"] = "请选择要修改的数据。";
	MSG_LIST["ErrorTableMultiSelect"] = "请选择一行数据进行操作。";
	MSG_LIST["ErrorNumber"] = "{0}请输入数字。";
	MSG_LIST["ErrorMobile"] = "手机号格式输入错误。"
	MSG_LIST["ErrorTableMustSelect"] = "请至少选择一行再进行操作。"
	MSG_LIST["ErrorExist"] = "{0}已经存在。"
	MSG_LIST["ErrorLength"] = "{0}请输入{1}位以内。"
	MSG_LIST["ErrorLength2"] = "{0}请输入{1}位以上，{2}位以内。"
	MSG_LIST["ErrorFormat"] = "{0}输入格式错误，请输入{1}。"	
	MSG_LIST["ErrorSame"] = "{0}和{1}请保持一致。"
	MSG_LIST["InfoResultEmpty"] = "未查询到数据。"
	MSG_LIST["ErrorDelAddress"] = "当前未选中地址，请选中后操作。";
});