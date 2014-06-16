package com.gimpel.android2048.database;

import android.os.Parcel;
import android.os.Parcelable;


public class Game implements Parcelable{
	private int mID;
	private String mPlayerName;
	private String mUriToImage;
	private String mGameState;
	private String mTime;
	private int mScore;
	
	public Game(int ID, String playerName, String uriToImage, String gameState, String time, int score) {
		mID = ID;
		mPlayerName = playerName;
		mUriToImage = uriToImage;
		mGameState = gameState;
		mTime = time;
		mScore = score;
	}
	
	public Game() {}
	
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

	public String getTime() {
		return mTime;
	}

	public void setTime(String time) {
		mTime = time;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.mGameState);
		dest.writeString(this.mPlayerName);
		dest.writeString(this.mTime);
		dest.writeString(this.mUriToImage);
		
		dest.writeInt(this.mID);
		dest.writeInt(this.mScore);
	}
	
	Game(Parcel save) {
		readFromParcel(save);
	}

	public static Creator<Game> CREATOR = new Creator<Game>() {
        public Game createFromParcel(Parcel parcel) {
            return new Game(parcel);
        }

        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
	
	public void readFromParcel(Parcel src) {
		this.mGameState = src.readString();
		this.mPlayerName = src.readString();
		this.mTime = src.readString();
		this.mUriToImage = src.readString();
	
		this.mID = src.readInt();
		this.mScore = src.readInt();
	}
	
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeStringArray(new String[] {this.id,
//                                            this.name,
//                                            this.grade});
//    }
//    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
//        public Student createFromParcel(Parcel in) {
//            return new Student(in); 
//        }
//
//        public Student[] newArray(int size) {
//            return new Student[size];
//        }
//    };
}
