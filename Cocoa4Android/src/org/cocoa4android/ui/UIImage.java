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

import android.graphics.drawable.Drawable;

public class UIImage {
	private Drawable drawable;
	
	private int resId;
	
	
	public UIImage(int resId){
		this.setResId(resId);
	}
	public UIImage(Drawable drawable){
		this.setDrawable(drawable);
	}

	public int getResId() {
		return resId;
	}


	public void setResId(int resId) {
		this.resId = resId;
	}
	public Drawable getDrawable() {
		return drawable;
	}
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
		

	
}