package com.gimpel.android2048;

public interface onDirectionSwype {
	public enum Direction { UP, DOWN, LEFT, RIGHT }
	public void onSwype(Direction dir);
}
