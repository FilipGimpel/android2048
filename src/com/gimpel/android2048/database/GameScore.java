package com.gimpel.android2048.database;

public class GameScore {
	private int mID;
	private String mPlayerName;
	private String mUriToImage;
	private String mTime;
	private int mScore;
	
	public GameScore(String playerName, String uriToImage, String time, int score) {
		mPlayerName = playerName;
		mUriToImage = uriToImage;
		mTime = time;
		mScore = score;
	}
	
	public GameScore() { }
	
	public int getID() {
		return mID;
	}
	
	public void setID(int id) {
		mID = id;
	}
	
	public String getPlayerName() {
		return mPlayerName;
	}
	public void setPlayerName(String playerName) {
		mPlayerName = playerName;
	}
	
	public String getUriToImage() {
		return mUriToImage;
	}
	
	public void setUriToImage(String uriToImage) {
		mUriToImage = uriToImage;
	}
	
	public int getScore() {
		return mScore;
	}
	
	public void setScore(int score) {
		mScore = score;
	}

	public String getTime() {
		return mTime;
	}

	public void setTime(String time) {
		mTime = time;
	}
}
