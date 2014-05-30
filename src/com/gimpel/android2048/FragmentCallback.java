package com.gimpel.android2048;

public interface FragmentCallback {
	public enum Direction { UP, DOWN, LEFT, RIGHT }
	public void onSwype(Direction dir);
	public void onBackKeyPressed();
}
