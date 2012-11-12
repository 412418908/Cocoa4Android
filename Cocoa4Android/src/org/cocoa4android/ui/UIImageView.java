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

import org.cocoa4android.cg.CGRect;


import android.graphics.Bitmap;
import android.widget.ImageView;

public class UIImageView extends UIView {
	
	protected ImageView imageView;
	private UIImage image;
	
	public UIImageView(UIImage image){
		this();
		this.setImage(image);
	}
	public UIImageView(){
		imageView = new ImageView(this.context);
		//fix me
		imageView.setScaleType(ImageView.ScaleType.FIT_XY); 
		this.setView(imageView);
	}
	public UIImageView(Bitmap bitmap){
		this();
		imageView.setImageBitmap(bitmap);
	}
	public UIImageView(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public UIImage getImage() {
		return image;
	}
	public void setImage(UIImage image) {
		this.image = image;
		if(image!=null){
			imageView.setImageResource(image.getResId());
			/*
			Rect r = imageView.getDrawable().getBounds();
			this.setFrame(new CGRect(r));
			*/
		}
	}
}