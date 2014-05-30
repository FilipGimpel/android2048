package com.gimpel.android2048.database;


public class SavedGame {
	private int mID;
	private String mPlayerName;
	private String mUriToImage;
	private String mGameState;
	private int mScore;
	
	public SavedGame(int ID, String playerName, String uriToImage, String gameState, int score) {
		mID = ID;
		mPlayerName = playerName;
		mUriToImage = uriToImage;
		mGameState = gameState;
		mScore = score;
	}
	
	public SavedGame() {}
	
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
	public String getGameState() {
		return mGameState;
	}
	public void setGameState(String gameState) {
		mGameState = gameState;
	}
	public int getScore() {
		return mScore;
	}
	public void setScore(int score) {
		mScore = score;
	}
}
