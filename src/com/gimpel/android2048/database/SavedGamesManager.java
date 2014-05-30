package com.gimpel.android2048.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SavedGamesManager extends SQLiteOpenHelper {
	/* All Static variables */
    /* Database Version */
    private static final int DATABASE_VERSION = 1;
    /* Database Name */
    private static final String DATABASE_NAME = "HIGHSCORES";
    /* Scores table name */
    private static final String TABLE_GAMES = "scores";
    /* Contacts Table Columns names */
    private static final String KEY_ID = "id";
    private static final String KEY_PLAYER_NAME = "player_name";
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_GAME_STATE = "game_state";
    private static final String KEY_SCORE = "score";
    
    // TODO log all db queries 
    public SavedGamesManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = 
        "CREATE TABLE " + TABLE_GAMES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
        		+ KEY_PLAYER_NAME + " TEXT,"
                + KEY_IMAGE_URI + " TEXT,"
        		+ KEY_GAME_STATE + " TEXT," 
        		+ KEY_SCORE + " INTEGER" 
        + ")";
        
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/* Drop older table if existed */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        /* Create tables again */
        onCreate(db);
	}
    
    // Adding new contact
    public void addSavedGame(SavedGame game) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME, game.getPlayerName());
        values.put(KEY_IMAGE_URI, game.getUriToImage());
        values.put(KEY_GAME_STATE, game.getGameState());
        values.put(KEY_SCORE, game.getScore());
     
        // Inserting Row
        db.insert(TABLE_GAMES, null, values);
        db.close(); // Closing database connection
    }
    
    public SavedGame getSavedGame(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_GAMES, new String[] { 
        		KEY_ID,
                KEY_PLAYER_NAME,
                KEY_IMAGE_URI,
                KEY_GAME_STATE,
                KEY_SCORE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
     
        SavedGame contact = new SavedGame(
        		Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Integer.parseInt(cursor.getString(4)));
        
        return contact;
    }
     
    public List<SavedGame> getAllSavedGames() {
    	List<SavedGame> contactList = new ArrayList<SavedGame>();
        /* Select All Query */
        String selectQuery = "SELECT  * FROM " + TABLE_GAMES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        /* looping through all rows and adding to list */
        if (cursor.moveToFirst()) {
            do {
            	SavedGame contact = new SavedGame();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setPlayerName(cursor.getString(1));
                contact.setUriToImage(cursor.getString(2));
                contact.setGameState(cursor.getString(3));
                contact.setID(Integer.parseInt(cursor.getString(0)));
                /* Adding contact to list */
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
     
        // return contact list
        return contactList;
    }
     
    // Getting contacts Count
    public int getSavedGamesCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_GAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
         /*return count */
        return cursor.getCount();
    }
     
    // Deleting single contact
    public void deleteSavedGame(SavedGame game) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAMES, KEY_ID + " = ?",
                new String[] { String.valueOf(game.getID()) });
        db.close();
    }
	
    public int getHighestScore() {
    	int result = -1;
    	List<SavedGame> games = getAllSavedGames();
    	
    	if (games == null) {
    		return result;
    	}
    	
    	for (SavedGame game : games) {
    		if (game.getScore() > result) result = game.getScore();
    	}
    	
    	return result;
    }
}
