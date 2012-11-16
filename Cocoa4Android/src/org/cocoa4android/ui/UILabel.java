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
import org.cocoa4android.ns.NSString;
import org.cocoa4android.ns.NSTextAlignment;

import android.view.Gravity;
import android.widget.TextView;


public class UILabel extends UIView {
	private TextView label = null;

	private NSString text = new NSString();
	
	public UILabel(){
		TextView lbl = new TextView(context);
		this.setLabel(lbl);
		this.setView(lbl);
		this.setTextColor(UIColor.blackColor());
		this.setFontSize(16);
	}
	public UILabel(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public TextView getLabel() {
		return label;
	}
	public void setTextAlignment(NSTextAlignment alignment){
		switch (alignment) {
		case NSTextAlignmentLeft:
			this.label.setGravity(Gravity.LEFT);
			break;
		case NSTextAlignmentCenter:
			this.label.setGravity(Gravity.CENTER);
			break;
		case NSTextAlignmentRight:
			this.label.setGravity(Gravity.RIGHT);
			break;
		}
		
	}
	public void setLabel(TextView label) {
		this.label = label;
	}
	public void setText(NSString text){
		this.label.setText(text.getString());
		this.text = text;
	}
	public void setText(String text){
		this.label.setText(text);
		this.text.setString(text);
	}
	public NSString text(){
		return this.text;
	}
	public String getText(){
		return this.label.getText().toString();
	}
	public void setTextColor(UIColor color){
		this.label.setTextColor(color.getColor());
	}
	public void setFontSize(int size){
		this.label.setTextSize(size);
	}
	public void setNumberOfLines(int numberOfLines){
		this.label.setLines(numberOfLines);
	}
	public int getNumberOfLines(){
		return this.label.getLineCount();
	}
}
