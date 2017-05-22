$(function(){
	$("#loginForm").bootstrapValidator({
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
		var data = getFormJson("loginForm");
		doAjax("post",contextPath + "/user/login", data, loginSuccess);
	});
});

function loginSuccess(){
	window.location.href = contextPath + "/index/index";
}