/****************************************************************************
Copyright (c) 2014 UNITED,inc.
Copyright (c) 2012-2013 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.plugin;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.util.TypedValue;
import android.util.DisplayMetrics;

public class AdstirIconCocos2dx implements org.cocos2dx.plugin.InterfaceAds {
	private static String TAG = "AdstirIconCocos2dx";
	private Activity mContext = null;
	
	private String mMedia = null;
	private int mSpot = 0;

	private android.widget.FrameLayout fl = null;

	public AdstirIconCocos2dx(Context context) {
		mContext = (Activity) context;
	}

	@Override
	public void setDebugMode(boolean debug) {
		android.util.Log.e(TAG,"AdstirIconCocos2dx not support setDebugMode");
	}

	@Override
	public String getSDKVersion() {
		android.util.Log.e(TAG,"AdstirIconCocos2dx not support getSDKVersion");
		return "AdstirIconCocos2dx not support getSDKVersion";
	}

	@Override
	public void configDeveloperInfo(Hashtable<String, String> devInfo) {
		try {
            mMedia = devInfo.get("media");
            mSpot = Integer.parseInt(devInfo.get("spot"));
            mSpot = Integer.parseInt(devInfo.get("spot"));
		} catch (Exception e) {
			android.util.Log.e(TAG,"Exception",e);
		}
	}

	@Override
	public void showAds(Hashtable<String, String> info, int pos) {
		try {
            int width = Integer.parseInt(info.get("width"));
            int height = Integer.parseInt(info.get("height"));
            int slot = Integer.parseInt(info.get("slot"));
            boolean center = Boolean.parseBoolean(info.get("center"));
			this.showBannerAd(width,height,pos,slot,center);
		} catch (Exception e) {
			android.util.Log.e(TAG,"Exception",e);
		}
	}

	@Override
	public void spendPoints(int points) {
		android.util.Log.e(TAG,"AdstirIconCocos2dx not support spendPoints");
	}

	@Override
	public void hideAds(Hashtable<String, String> info) {
		try {
			hideBannerAd();
		} catch (Exception e) {
			android.util.Log.e(TAG,"Exception",e);
		}
	}

	private void showBannerAd(final int width, final int height, final int pos, final int slot, final boolean center) {
		final int curPos = pos;
		org.cocos2dx.plugin.PluginWrapper.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				WindowManager windowManager = (WindowManager) mContext.getSystemService("window");
				if(windowManager == null || mMedia == null || mSpot == 0) {
					return;
				}

				if (fl != null) {
					windowManager.removeView(fl);
					fl = null;
				}

				DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
				int wdp = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width, metrics);
				int hdp = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, height, metrics);
				
				fl = new android.widget.FrameLayout(mContext);
                
                com.ad_stir.icon.IconView adv = new com.ad_stir.icon.IconView(mContext, mMedia, mSpot, slot);
                adv.setCenter(center);
                adv.setLayoutParams(new android.widget.FrameLayout.LayoutParams(wdp, hdp));
                fl.addView(adv);
                
				org.cocos2dx.plugin.AdsWrapper.addAdView(windowManager, fl, curPos);
				org.cocos2dx.plugin.AdsWrapper.onAdsResult(AdstirIconCocos2dx.this, org.cocos2dx.plugin.AdsWrapper.RESULT_CODE_AdsReceived, "Ads request received success!");
			}
		});
	}

	private void hideBannerAd() {
		org.cocos2dx.plugin.PluginWrapper.runOnMainThread(new Runnable() {
			@Override
			public void run() {
				WindowManager windowManager = (WindowManager) mContext.getSystemService("window");
				if(windowManager == null || mMedia == null || mSpot == 0) {
					return;
				}

				if (fl != null) {
					windowManager.removeView(fl);
					fl = null;
				}
			}
		});
	}

	@Override
	public String getPluginVersion() {
		android.util.Log.e(TAG,"AdstirIconCocos2dx not support getPluginVersion");
		return "AdstirCocos2dx not support getPluginVersion";
	}

	@Override
	public void queryPoints() {
		android.util.Log.e(TAG,"AdstirIconCocos2dx not support queryPoints");
	}
}
