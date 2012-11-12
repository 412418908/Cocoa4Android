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
package org.cocoa4android.ns;

import org.cocoa4android.extend.JSONHelper;
import org.cocoa4android.ui.UIApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class NSUserDefaults extends NSObject {
	private static NSUserDefaults userDefaults = null;
	private Editor editor = null;
	public static NSUserDefaults getStandardUserDefaults(){
		if(userDefaults==null){
			userDefaults = new NSUserDefaults();
		}
		return userDefaults;
	}
	
	private SharedPreferences mPerferences = null;
	
	public NSUserDefaults(){
		mPerferences = PreferenceManager     
		        .getDefaultSharedPreferences(UIApplication.getSharedApplication().getContext());     
	}
	public void setString(String value,String key){
		editor = mPerferences.edit();
		editor.putString(key, value);
	}
	public String stringForKey(String key){
		return mPerferences.getString(key, null);
	}
	public void setInteger(int value,String key){
		editor = mPerferences.edit();
		editor.putInt(key, value);
	}
	public int integerForKey(String key){
		return mPerferences.getInt(key, 0);
	}
	public void setFloat(float value,String key){
		editor = mPerferences.edit();
		editor.putFloat(key, value);
	}
	public float floatForKey(String key){
		return mPerferences.getFloat(key, 0.0f);
	}
	public void setBoolean(boolean value,String key){
		editor = mPerferences.edit();
		editor.putBoolean(key, value);
	}
	public boolean booleanForKey(String key){
		return mPerferences.getBoolean(key, false);
	}
	public void setArray(String[] value,String key){
		String array = JSONHelper.toJSON(value);
		this.setString(array, key);
	}
	public String[] arrayForKey(String key){
		String value = this.stringForKey(key);
		return JSONHelper.parseArray(value, String.class);
	}
	
	public void synchronize(){;
		editor.commit();
	}
}