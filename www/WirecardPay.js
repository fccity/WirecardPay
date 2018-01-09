/*var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'WirecardPay', 'coolMethod', [arg0]);
};

exports.doLogin = function(arg0, arg1, success, error) {
    exec(success, error, 'WirecardPay', 'coolMethod', [arg0,arg1]);
}
*/

// Take reference from: https://stackoverflow.com/questions/40974950/custom-cordova-plugin-creation-for-ionic2-project
function WirecardPay() {
}

/**
* test a cool method
* @param arg0: string - string for echo test
*/
WirecardPay.prototype.coolMethod = function (arg0, successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, 'WirecardPay', 'coolMethod', [arg0]);
};

WirecardPay.prototype.testAndroid = function (arg0, successCallback, errorCallback) {
	cordova.exec(successCallback, errorCallback, 'WirecardPay', 'testAndroid', [arg0]);
};

/**
* Plugin constructor
*/
WirecardPay.install = function () {
	if (!window.plugins) {
		window.plugins = {};
	}

	window.plugins.WirecardPay = new WirecardPay();
	return window.plugins.WirecardPay;
};

cordova.addConstructor(WirecardPay.install);