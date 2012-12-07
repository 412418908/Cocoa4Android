/*
 * Copyright (C) 2012 chao
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

import java.util.ArrayList;
import java.util.List;

import org.cocoa4android.cg.CGRect;
import org.cocoa4android.ns.NSArray;
import org.cocoa4android.ns.NSIndexPath;
import org.cocoa4android.ui.UITableViewCell.UITableViewCellSeparatorStyle;
import org.cocoa4android.ui.UITableViewCell.UITableViewCellShapeType;


import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class UITableView extends UIView {
	private ListView listView = null;
	private refreshableAdapter adapter = null;
	private UITableViewDataSource dataSource = null;
	private UITableViewDelegate delegate = null;
	
	private UITableViewStyle style;
	UITableViewCellSeparatorStyle cellSeparatorStyle;
	private boolean isGrouped;
	
	private List<NSIndexPath> mappingList = null;//mapping position to indexPath
	private List<UIView> cellsList = null;
	
	private UIView selectedView = null;
	private NSIndexPath selectedIndexPath = null;

	public UITableView() {
		this(UITableViewStyle.UITableViewStylePlain);
	}
	public UITableViewCell reuseCell;
	
	public UITableView(UITableViewStyle style) {
		
		this.style = style;
		listView = new ListView(context);
		listView.setBackgroundColor(Color.GRAY);
		listView.setDrawingCacheBackgroundColor(Color.WHITE);
		listView.setCacheColorHint(0);
		isGrouped = (style == UITableViewStyle.UITableViewStyleGrouped) ? true : false;
		if(isGrouped) {
			listView.setPadding((int)(10*densityX), (int)(10*densityY), (int)(10*densityX), (int)(25*densityY));
		}
		adapter = new refreshableAdapter(mappingList);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				listView.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
				
				if(selectedView != null){
					selectedView.setBackgroundColor(UIColor.whiteColor());
					listView.invalidate();
					if (delegate != null) {
						delegate.disDeselectRowAtIndexPath(UITableView.this, selectedIndexPath);
					}
				}
				selectedView = cellsList.get(position);
				selectedIndexPath = mappingList.get(position);
				selectedView.setBackgroundColor(new UIColor(0xff0378f0));
				listView.invalidate();
				if(delegate != null) {
					delegate.didSelectRowAtIndexPath(UITableView.this, selectedIndexPath);
				}
				
			}
		});
		
		listView.setScrollBarStyle(ScrollView.SCROLLBARS_OUTSIDE_OVERLAY);
		listView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		
		RelativeLayout layout = new RelativeLayout(context);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layout.setLayoutParams(params);
		this.setView(layout);
		
		LayoutParams listparams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		listView.setLayoutParams(listparams);
		layout.addView(listView);
		
	}
	public UITableView(CGRect frame) {
		this();
		this.setFrame(frame);
	}
	public UITableViewStyle style() {
		return style;
	}
	
	
	@Override
	public void setBackgroundColor(UIColor backgroundColor){
		super.setBackgroundColor(backgroundColor);
		listView.setBackgroundColor(backgroundColor.getColor());
		if (style==UITableViewStyle.UITableViewStyleGrouped) {
			listView.setDivider(new ColorDrawable(backgroundColor.getColor()));
			listView.setDividerHeight(1);
			if (cellSeparatorStyle!=null) {
				this.setSeparatorStyle(cellSeparatorStyle);
			}
		}
	}
	
	private ArrayList<NSIndexPath> initMapping() {
		if(dataSource != null && delegate != null) {
			mappingList = new ArrayList<NSIndexPath>();
			
			cellsList = new ArrayList<UIView>();
			int numberOfSection = dataSource.numberOfSectionsInTableView(this);
			int numberOfRowInSection = 0;
			NSIndexPath indexPath = null;
			for(int section = 0;section < numberOfSection;section++) {
				mappingList.add(new NSIndexPath(section,-1));//header
				cellsList.add(null);//header
				
				numberOfRowInSection = dataSource.numberOfRowsInSection(this, section);
				for(int row = 0;row < numberOfRowInSection;row++) {
					indexPath = new NSIndexPath(section, row);
					mappingList.add(indexPath);
					cellsList.add(null);
					
				}
				mappingList.add(new NSIndexPath(section,Integer.MAX_VALUE));//footer
				cellsList.add(null);//footer
				
			}
		}
		return (ArrayList<NSIndexPath>) mappingList;
	}
	public void reloadData() {
		this.initMapping();
		this.sourceChanged(mappingList);
	}
	
	public void setDataSource(UITableViewDataSource dataSource) {
		this.dataSource = dataSource;
		this.initMapping();
	}
	public void setDelegate(UITableViewDelegate delegate) {
		this.delegate = delegate;
		this.initMapping();
	
	}
	public void selectRowAtIndexPath(NSIndexPath indexPath) {
		if (cellsList != null) {
			int position = mappingList.indexOf(indexPath);
			if (selectedView != null) {
				selectedView.setBackgroundColor(UIColor.whiteColor());
				listView.invalidate();
			}
			selectedIndexPath = indexPath;
			selectedView = cellsList.get(position);
		}
	}
	public void deselectRowAtIndexPath(NSIndexPath indexPath) {
		if (indexPath.isEqual(selectedIndexPath)) {
			selectedView.setBackgroundColor(UIColor.whiteColor());
			listView.invalidate();
			selectedIndexPath = null;
			selectedView = null;
		}
	}
	
	public void setSeparatorStyle(UITableViewCellSeparatorStyle style) {
		switch(style) {
		case UITableViewCellSeparatorStyleNone:
			listView.setDividerHeight(0);
			break;
		case UITableViewCellSeparatorStyleSingleLine:
			listView.setDividerHeight(1);
			break;
		case UITableViewCellSeparatorStyleSingleLineEtched:
			break;
		}
		this.cellSeparatorStyle = style;
	}
	
	public void insertRowsAtIndexPaths(NSArray indexPaths,UITableViewRowAnimation animation) {
		NSIndexPath indexPath = null;
		NSIndexPath tmpIndexPath = null;
		for (int i = 0; i < indexPaths.count(); i++) {
			indexPath = (NSIndexPath) indexPaths.objectAtIndex(i);
			int position = 0;
			int count = mappingList.size();
			while (true) {
				int middle = (position + count) / 2;
				if (middle == position) {
					if (indexPath.compareTo(mappingList.get(middle)) > 0) {//indexPath is greater than last one
						position = count;
					}
					break;
				}
				tmpIndexPath = mappingList.get(middle);
				if (indexPath.compareTo(tmpIndexPath) > 0) {//indexPath > tmpIndexPath
					position = middle;
				}
				else if (indexPath.compareTo(tmpIndexPath) < 0) {//indexPath < tmpIndexPath
					count = middle;
				}
				else {//indexPath == tmpIndexPath
					position = middle;
					break;
				}
			}
			mappingList.add(position, indexPath);
			cellsList.add(position, null);
			for(int j = position+1;j < mappingList.size();j++) {
				tmpIndexPath = mappingList.get(j);
				if (indexPath.section() == tmpIndexPath.section() && tmpIndexPath.row() != Integer.MAX_VALUE) {
					tmpIndexPath.setRow(tmpIndexPath.row()+1);
				}
				else {
					break;
				}
			}
		}
		this.sourceChanged(mappingList);
	}
	
	public void deleteRowsAtIndexPaths(NSArray indexPaths,UITableViewRowAnimation animation) {
		NSIndexPath indexPath = null;
		mappingList.indexOf(indexPath);
		for (int i = 0; i < indexPaths.count(); i++) {
			indexPath = (NSIndexPath) indexPaths.objectAtIndex(i);
			int position = mappingList.indexOf(indexPath);
			mappingList.remove(position);
			cellsList.remove(position);
		}
		this.sourceChanged(mappingList);
	}
	protected void sourceChanged(List<NSIndexPath> localMappingList) {
		adapter.mappingList = localMappingList;
		adapter.notifyDataSetChanged();
		//listView.requestLayout();
		
		//listView.refreshDrawableState();
		listView.invalidateViews();
		//listView.invalidate();
	}
	public UITableViewCell dequeueReusableCellWithIdentifier(String identifier){
		if (reuseCell!=null&&reuseCell.reuseIdentifier()!=null&&reuseCell.reuseIdentifier().equals(identifier)) {
			UITableViewCell cell = reuseCell;
			this.reuseCell = null;
			return cell;
		}
		return null;
	}
	public interface UITableViewDataSource {
		int numberOfRowsInSection(UITableView tableView,int section);
		UITableViewCell cellForRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		
		int numberOfSectionsInTableView(UITableView tableView);
	}
	
	public interface UITableViewDelegate {
		float heightForRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		float heightForHeaderInSection(UITableView tableView,int section);
		float heightForFooterInSection(UITableView tableView,int section);
		UIView viewForHeaderInSection(UITableView tableView,int section);
		UIView viewForFooterInSection(UITableView tableView,int section);
		void didSelectRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		void disDeselectRowAtIndexPath(UITableView tableView,NSIndexPath indexPath);
		void willDisplayCellForRowAtIndexPath(UITableView tableView,UITableViewCell cell,NSIndexPath indexPath);
	}
	
	public enum UITableViewStyle {
		UITableViewStylePlain,                  // regular table view
	    UITableViewStyleGrouped                 // preferences style table view
	}
	
	public enum UITableViewRowAnimation {
		UITableViewRowAnimationFade,
	    UITableViewRowAnimationRight,           // slide in from right (or out to right)
	    UITableViewRowAnimationLeft,
	    UITableViewRowAnimationTop,
	    UITableViewRowAnimationBottom,
	    UITableViewRowAnimationNone,            // available in iOS 3.0
	    UITableViewRowAnimationMiddle,          // available in iOS 3.2.  attempts to keep cell centered in the space it will/did occupy
	    UITableViewRowAnimationAutomatic        // available in iOS 5.0.  chooses an appropriate animation style for you
	}
	
	public class refreshableAdapter extends BaseAdapter{
		public List<NSIndexPath> mappingList;
		public refreshableAdapter(List<NSIndexPath> mappingList){
			this.mappingList = mappingList;
		}
		
		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			
		}
		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			
		}
		@Override
		public boolean isEmpty() {
			return false;
		}
		
		@Override
		public boolean hasStableIds() {
			return false;
		}
		@Override
		public int getViewTypeCount() {
			return 2;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			UIView view = null;
			if(dataSource != null && delegate != null) {
				NSIndexPath indexPath = mappingList.get(position);
				if(indexPath.row() == -1) {//header
					view = delegate.viewForHeaderInSection(UITableView.this, indexPath.section);
					float height = delegate.heightForHeaderInSection(UITableView.this, indexPath.section());
					if(height <= 0 || view == null) {
						view = new UIView();
					}
					view.setBackgroundColor(UIColor.clearColor());
					AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(height*densityY));
					view.getView().setLayoutParams(params);
					return view.getView();
				}
				else if(indexPath.row() == Integer.MAX_VALUE) {//footer
					view = delegate.viewForFooterInSection(UITableView.this, indexPath.section);
					float height = delegate.heightForFooterInSection(UITableView.this, indexPath.section());
					if(height <= 0 || view == null) {
						view = new UIView();
					}
					view.setBackgroundColor(UIColor.clearColor());
					AbsListView.LayoutParams params = new AbsListView.LayoutParams(LayoutParams.FILL_PARENT, (int)(height*densityY));
					view.getView().setLayoutParams(params);
					return view.getView();
				}
				else {
					//UITableViewCell cell = (UITableViewCell) cellsList.get(position);
					UITableViewCell cell = null;
					if (cell==null) {
						if (convertView!=null&&UITableViewCell.class.isInstance(convertView.getTag())) {
							reuseCell = (UITableViewCell) convertView.getTag();
						}	
						cell = dataSource.cellForRowAtIndexPath(UITableView.this, indexPath);
						float height = delegate.heightForRowAtIndexPath(UITableView.this, indexPath);
						cell.setHeight(height);
						int section = indexPath.section();
						int row = indexPath.row();
						int numberOfRowsInSection = dataSource.numberOfRowsInSection(UITableView.this, section);
						if (row == 0 && row == numberOfRowsInSection-1) {
							cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeAllRound);
						}
						else if (row == 0) {
							cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeTopRound);
						}
						else if (row == numberOfRowsInSection-1) {
							cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeBottomRound);
						}
						else {
							cell.setShapeType(UITableViewCellShapeType.UITableViewCellShapeNoRound);
						}
					}
					delegate.willDisplayCellForRowAtIndexPath(UITableView.this, cell, indexPath);
					view = cell;
					
					reuseCell = null;
				}
			}
			
			if (view==null) {
				return null;
			}else{
				cellsList.set(position, view);
				return view.getView();
			}

		}
		
		@Override
		public int getItemViewType(int position) {
			return 0;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public int getCount() {
			if(dataSource != null) {
				if(mappingList == null) {
					mappingList = initMapping();
				}
				
				return mappingList.size();
			}
			return 0;
		}
		@Override
		public boolean areAllItemsEnabled() {
			return false;
		}
		@Override
		public boolean isEnabled(int position) {
			if(mappingList == null) {
				mappingList = initMapping();
			}
			NSIndexPath indexPath = mappingList.get(position);
			if(indexPath.row() < 0) {
				return false;
			}
			return true;
		}
	}
	
}
