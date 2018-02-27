package speedPlugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;  
import android.net.TrafficStats;  
import android.os.Handler;  
import android.os.Message;  

import java.util.Timer;  
import java.util.TimerTask;  

/**
 * This class echoes a string called from JavaScript.
 */
public class speedPlugin extends CordovaPlugin {

    private long lastTotalRxBytes = 0;  
    private long lastTimeStamp = 0;  

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }else if(action.equals("showNetSpeed")) {
            String message = args.getString(0);
            this.showNetSpeed(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
    private long getTotalRxBytes() {  
         Context context = this.cordova.getActivity().getApplicationContext();
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes()/1024);//转为KB  
    }  
    private void showNetSpeed(String message, CallbackContext callbackContext) {
     long nowTotalRxBytes = getTotalRxBytes();  
     long nowTimeStamp = System.currentTimeMillis();  
        long speed = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//毫秒转换  
        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换  
        lastTimeStamp = nowTimeStamp;  
        lastTotalRxBytes = nowTotalRxBytes; 
        String speed_s=String.valueOf(speed) + "." + String.valueOf(speed2);
        callbackContext.success(speed_s);
    }
}
