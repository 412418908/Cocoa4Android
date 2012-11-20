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

import android.content.Context;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.cocoa4android.ns.NSString;
import org.cocoa4android.ns.NSTextAlignment;


public class UITextField extends UIView {
	private EditText textField = null;
	public NSString placeholder;
	
	public UITextField(){
		textField = new EditText(context);
		textField.setFocusable(YES);
		this.setTextField(textField);
		this.setView(textField);
		
		ShapeDrawable background = new ShapeDrawable(new RoundRectShape(new float[] {25,25, 25,25, 25,25, 25,25},null,null));
		background.getPaint().setColor(UIColor.whiteColor().getColor());
		background.getPaint().setStrokeWidth(2);
		this.textField.setBackgroundDrawable(background);
		
	}
	public UITextField(CGRect frame){
		this();
		this.setFrame(frame);
	}
	public EditText getTextField() {
		return textField;
	}
	public void setTextField(EditText textField) {
		this.textField = textField;
	}
	public void setText(String text){
		this.textField.setText(text);
	}

	public String text(){
		return this.textField.getText().toString();
	}
	public void setTextColor(UIColor color){
		this.textField.setTextColor(color.getColor());
	}
	public void setTextAlignment(NSTextAlignment alignment){
		switch (alignment) {
		case NSTextAlignmentLeft:
			this.textField.setGravity(Gravity.LEFT);
			break;
		case NSTextAlignmentCenter:
			this.textField.setGravity(Gravity.CENTER);
			break;
		case NSTextAlignmentRight:
			this.textField.setGravity(Gravity.RIGHT);
			break;
		}
		
	}
	public void setFont(UIFont font) {
		this.textField.setTextSize(font.fontSize());
		this.textField.setTypeface(font.getFont());
	}
	public void setFontSize(float fontSize){
		this.textField.setTextSize(fontSize);
	}
	public void setSecureTextEntry(boolean secureTextEntry){
		if(secureTextEntry){
			String temporary_stored_text = textField.getText().toString().trim();
			textField.setTransformationMethod(PasswordTransformationMethod.getInstance());
			textField.setText(temporary_stored_text);
		}else{
			textField.setTransformationMethod(null);
		}
	}
	public boolean isSecureTextEntry(){
		return false;
	}
	public void becomeFirstResponder(){
		InputMethodManager imm = (InputMethodManager)textField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (!imm.isActive()) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	public void resignFirstResponder(){
		textField.clearFocus();
		InputMethodManager imm = (InputMethodManager)textField.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	public NSString placeholder() {
		return placeholder;
	}
	public void setPlaceholder(NSString placeholder) {
		this.placeholder = placeholder;
		this.textField.setHint(placeholder.getString());
	}
}
