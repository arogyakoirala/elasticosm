package com.arogyak.api.model;

public class Bounds {
	Double top;
	Double right;
	Double left;
	Double bottom;

	public Bounds(Double top, Double right, Double left, Double bottom) {
		super();
		this.top = top;
		this.right = right;
		this.left = left;
		this.bottom = bottom;
	}

	public Double getTop() {
		return top;
	}

	public void setTop(Double top) {
		this.top = top;
	}

	public Double getRight() {
		return right;
	}

	public void setRight(Double right) {
		this.right = right;
	}

	public Double getLeft() {
		return left;
	}

	public void setLeft(Double left) {
		this.left = left;
	}

	public Double getBottom() {
		return bottom;
	}

	public void setBottom(Double bottom) {
		this.bottom = bottom;
	}

	public Bounds() {
		super();
		// TODO Auto-generated constructor stub
	}

}
