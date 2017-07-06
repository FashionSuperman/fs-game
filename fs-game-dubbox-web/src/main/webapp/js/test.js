
function testPay() {
	var OrderUrl = contextPath
			+ "/fs-game-service/Order/generateUserOrder";
	var ApplyWXPayUrl = contextPath
			+ "/fs-game-service/DogBiz/applyWXPay";

	// 生成订单
	var OrderUrlData = {
		"userid" : 3,
		"note" : "充值"
	};
	sendRequest(OrderUrl, "POST", OrderUrlData, function(data) {
		
		data = JSON.parse(data);
		
		var orderid = data.responseData.orderid;
		// 调用服务获取调起微信支付的参数
		var ApplyWXPayUrlData = {
			"total_fee" : 0.1,
			"orderid" : orderid
		};
		sendRequest(ApplyWXPayUrl, "POST", ApplyWXPayUrlData, function(data) {
			data = JSON.parse(data);
			
			
			var resApplyWXPayUrl = data;
			resApplyWXPayUrl.package = resApplyWXPayUrl.backage;
			delete resApplyWXPayUrl.backage;
			wxpay(resApplyWXPayUrl);
		});
	});

}

function wxpay(data) {
	if (typeof WeixinJSBridge == "undefined") {
		if (document.addEventListener) {
			document.addEventListener('WeixinJSBridgeReady', onBridgeReady,
					false);
		} else if (document.attachEvent) {
			document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
			document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
		}
	} else {
		onBridgeReady(data);
	}
}

function onBridgeReady(data) {
	WeixinJSBridge.invoke('getBrandWCPayRequest', data, function(res) {
		if (res.err_msg == "get_brand_wcpay_request:ok") {// 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回
															// ok，但并不保证它绝对可靠。

		}
	});
}

/**
 * ajax方法封装
 */
function sendRequest(path, method, data, callback) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && (xhr.status >= 200 && xhr.status < 400)) {
			var response = xhr.responseText;
			// console.log(response);
			if (typeof callback == "function") {
				callback(response);
			}

		}
	};
	xhr.open(method, path, true);
	if ("POST" == method || method == undefined) {
		xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xhr.send(JSON.stringify(data));
	} else if ("GET" == method) {// 拼接参数,暂时不定义

	}

}