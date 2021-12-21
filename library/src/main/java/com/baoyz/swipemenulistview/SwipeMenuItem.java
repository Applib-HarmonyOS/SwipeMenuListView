package com.baoyz.swipemenulistview;

import ohos.agp.components.element.Element;
import ohos.agp.components.element.ElementScatter;
import ohos.agp.utils.Color;
import ohos.app.Context;

/**
 * 
 * @author baoyz
 * @date 2014-8-23
 * 
 */
public class SwipeMenuItem {

	private int id;
	private Context mContext;
	private String menuTitle;
	private String title;
	private int icon1 = 0;
	private int icon2 = 0;
	private Element background1;
	private Element background2;
	private Color titleColor;
	private int titleSize;
	private int width;

	public SwipeMenuItem(Context context) {
		mContext = context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Color getTitleColor() {
		return titleColor;
	}

	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public void setTitleColor(Color titleColor) {
		this.titleColor = titleColor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(int resId) {
		setTitle(mContext.getString(resId));
	}

	public int getIcon1() {
		return icon1;
	}

	public void setIcon1(int resId) {
		this.icon1 = resId;
	}

	public int getIcon2() {
		return icon2;
	}

	public void setIcon2(int resId) {
		this.icon2 = resId;
	}

	public Element getBackground1() {
		return background1;
	}
	public Element getBackground2() {
		return background2;
	}

	public void setBackground1(Element background1) {
		this.background1 = background1;
	}

	public void setBackground2(Element background2) {
		this.background2 = background2;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}


}
