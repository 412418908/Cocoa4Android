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
package org.cocoa4android.cg;

import org.cocoa4android.ui.UIScreen;

import android.graphics.Rect;

public class CGRect {
	private CGPoint origin;
	private CGSize size;
	
	public CGRect(float x,float y,float width,float height){
		this.origin = new CGPoint(x,y);
		this.size = new CGSize(width,height);
	}
	public CGRect(Rect r){
		float density = UIScreen.getMainScreen().getDensity();
		this.origin = new CGPoint(r.left/density,r.top/density);
		this.size = new CGSize((r.right-r.left)/density,(r.bottom - r.top)/density);
	}
	public CGPoint getOrigin() {
		return origin;
	}
	public void setOrigin(CGPoint origin) {
		this.origin = origin;
	}
	public CGSize getSize() {
		return size;
	}
	public void setSize(CGSize size) {
		this.size = size;
	}
	public Rect getRect(){
		float density = UIScreen.getMainScreen().getDensity();
		return new Rect((int)(this.origin.getX()*density),(int)(this.origin.getY()*density),(int)((this.origin.getX()+this.size.getWidth())*density),(int)((this.origin.getY()+this.size.getHeight())*density));
	}
}