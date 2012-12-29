/*
 * Copyright (C) 2012 Wu Tong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cocoa4android.ui;

import java.util.Timer;
import java.util.TimerTask;

import org.cocoa4android.R;
import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSString;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

public abstract class UIAppDelegate extends Activity {
	protected static final boolean YES = true;
	protected static final boolean NO = false;
	
	
	
	protected static void NSLog(String format,Object...args){
		Log.i("Cocoa4Android",NSString.stringWithFormat(format, args).getString());
	}
	
	public static Context context;
	public UIWindow window;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		//work before onCreate
		//setTheme(android.R.style.Theme_Translucent_NoTitleBar);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        UIApplication.sharedApplication().setContext(this);
        UIApplication.sharedApplication().setDelegate(this);
        
        
        
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); 
        float density = dm.density;
        UIScreen.mainScreen().setDensity(density);
        UIScreen.mainScreen().setDensityDpi(dm.densityDpi);
        
        float width = dm.widthPixels;
        float height = dm.heightPixels;
        
        UIScreen.mainScreen().setBounds(new CGRect(0,0,width,height));
        UIScreen.mainScreen().setDensityText(dm.scaledDensity);
        
        
        this.window = new UIWindow();
        this.setContentView(this.window.getView());
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	if(this.window!=null){
	        	UIViewController viewController = this.window.rootViewController();
	        	if(viewController!=null){
	        		return viewController.backKeyDidClicked();
	        	}
        	}
            return true;
        }
        return false;
    }
	boolean isApplicationLaunched = NO;
	void launchApplication(){
		isApplicationLaunched = YES;
		final UIImageView imageView = new UIImageView();
        imageView.setImage(new UIImage(R.drawable.zz_c4a_default));
        this.window.addSubview(imageView);
        new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				UIAppDelegate.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						imageView.removeFromSuperView();
						UIAppDelegate.this.applicationDidFinishLaunching();
					}
				});
			}
		}, 3000);
	}
	public abstract void applicationDidFinishLaunching();
}
