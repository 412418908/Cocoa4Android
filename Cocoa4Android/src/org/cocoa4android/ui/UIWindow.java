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

public class UIWindow extends UIView {
	public UIWindow(CGRect frame){
		super(frame);
		this.setBackgroundColor(UIColor.getWhiteColor());
	}
	public UIWindow(){
		super();
		this.setBackgroundColor(UIColor.getWhiteColor());
	}
	public UIWindow(int viewid) {
		super(viewid);
		this.setBackgroundColor(UIColor.getWhiteColor());
	}
	private UIViewController rootViewController;
	public void makeKeyAndVisible(){
		this.setHidden(false);
	}
	public UIViewController getRootViewController() {
		return rootViewController;
	}
	public void setRootViewController(UIViewController rootViewController) {
		this.rootViewController = rootViewController;
		this.addSubView(rootViewController.getView());
	}
}