package cordova.plugin.wirecard;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// 2018-1-8 - TK: copy imported libraries from sample code - Application.java
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.HashMap;

import de.wirecard.accept.sdk.AcceptSDK;
import de.wirecard.accept.sdk.AcceptSDKIntents;
import de.wirecard.accept.sdk.ApiResult;
import de.wirecard.accept.sdk.OnRequestFinishedListener;

/**
 * This class echoes a string called from JavaScript.
 */
public class WirecardPay extends CordovaPlugin {

	private static final String TAG = "WirecardPay";
	private Context applicationContext = null;
	
	String errorMessage = "";
	SessionTerminatedReceiver receiver = null;

	protected Boolean usb = false;// used for switch using usb or bt
	protected Boolean contactless = false;// used for switch using usb or bt

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("coolMethod")) {
			String message = args.getString(0);
			this.coolMethod(message, callbackContext);
			return true;
		}
		if (action.equals("testAndroid")) {
			String message = args.getString(0);
			this.testAndroid(message, callbackContext);
			return true;
		}
		return false;
	}
    
	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.e(TAG, "onCreate");
		initializeSDK();
	}
    
	private void initializeSDK() {
		cordova.getThreadPool().execute(new Runnable() {
			@Override
			public void run() {
				receiver = new SessionTerminatedReceiver();
				
// 				usb = getResources().getBoolean(R.bool.demo_communicate_with_spire_on_usb);
// 				contactless = getResources().getBoolean(R.bool.demo_support_contactless);
				// 2018-1-8 - TK: assume bluetooth only
				usb = false;
				contactless = true;

				try {
					applicationContext = cordova.getActivity().getApplicationContext();
					AcceptSDK.init(applicationContext,
					"d2fee3aa586b4b7062315742d29456effaf80636dfb89febcdc19f1bb4ff6111",	//Please obtain ClientID/Secret from Accept support team.
					"d29aab08958d6f8ab59c2e3a3348b41b0861823784b2d456b2ba9769464b6111",	// demo app is using external file for fill this attributes
					"https://sdk-integration.dev.up-next.io/");	//https://github.com/mposSVK/acceptSDK-Android/blob/master/demo/AcceptSDKAndroidDemo.properties
					AcceptSDK.loadExtensions(applicationContext, null, contactless, usb);
				} catch (IllegalArgumentException e) {
					errorMessage = e.getMessage();
				}

				AcceptSDK.setPrefTimeout(15);//timeout for requests
				if (AcceptSDK.isLoggedIn()) {
					//use this if you will stay on same session, just app is working on longer task
					AcceptSDK.sessionRefresh(new OnRequestFinishedListener<HashMap<String, String>>() {
						@Override
						public void onRequestFinished(ApiResult apiResult, HashMap<String, String> result) {
							if (!apiResult.isSuccess()) {
								sendLogoutIntentAndGoLogin();
							}
						}
					});
				}
				//!!! do not forget on session terminated receiver
				// is importand to be able detect situation which should logout from payment service
// 				LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(AcceptSDKIntents.SESSION_TERMINATED));
				// 2018-1-8 - TK: native to cordova - https://github.com/bsorrentino/cordova-broadcaster/blob/master/src/android/CDVBroadcaster.java
				LocalBroadcastManager.getInstance(applicationContext).registerReceiver(receiver, new IntentFilter(AcceptSDKIntents.SESSION_TERMINATED));
			}
		});
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void sendLogoutIntentAndGoLogin() {
		AcceptSDK.logout();
// 		Intent intent = new Intent(this, LoginActivity.class);
// 		intent.putExtra(BaseActivity.LOGOUT, true).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
// 		startActivity(intent);
// 
// 		intent = new Intent(BaseActivity.INTENT);
// 		intent.putExtra(BaseActivity.INTENT_TYPE, BaseActivity.TYPE_LOGOUT);
// 		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
		
		// 2018-1-8 - TK: native to cordova
		// TODO: what the hell is that? auto login?
	}
	
	private class SessionTerminatedReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.e("Session Timeout", "sending Log Out");
			sendLogoutIntentAndGoLogin();
		}
	}

	@Override
	//public void onTerminate() {
	public void onDestroy() {
// 		LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
		// 2018-1-8 - TK: native to cordova - https://github.com/bsorrentino/cordova-broadcaster/blob/master/src/android/CDVBroadcaster.java
		applicationContext = cordova.getActivity().getApplicationContext();
		LocalBroadcastManager.getInstance(applicationContext).unregisterReceiver(receiver);
		AcceptSDK.finish();
		//super.onTerminate();
		super.onDestroy();
	}

	private void coolMethod(String message, CallbackContext callbackContext) {
		if (message != null && message.length() > 0) {
			callbackContext.success(message);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}
	
	private void testAndroid(String message, CallbackContext callbackContext) {
		if (message != null && message.length() > 0) {
			callbackContext.success(message);
		} else {
			callbackContext.error("Expected one non-empty string argument.");
		}
	}
}
