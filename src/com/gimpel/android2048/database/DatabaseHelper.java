package com.gimpel.android2048.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    /* Database Version */
    private static final int DATABASE_VERSION = 1;
    /* Database Name */
    private static final String DATABASE_NAME = "GAMESMANAGER";
    /* Saved games table name */
    private static final String TABLE_SAVED_GAMES = "saved_games";
    /* High scores table name */
    private static final String TABLE_HIGH_SCORES = "scores";
    
    /* Table Columns names */
    private static final String KEY_ID = "id";
    private static final String KEY_PLAYER_NAME = "player_name";
    private static final String KEY_IMAGE_URI = "image_uri";
    private static final String KEY_GAME_STATE = "game_state";
    private static final String KEY_ELAPSED_TIME = "elapsed_time";
    private static final String KEY_SCORE = "score";
    
    /* highscores table create statement */
    private static final String CREATE_HIGHSCORES_TABLE = 
			"CREATE TABLE " + TABLE_HIGH_SCORES + "("
					+ KEY_ID + " INTEGER PRIMARY KEY,"
					+ KEY_PLAYER_NAME + " TEXT,"
					+ KEY_SCORE + " TEXT"
					+ KEY_ELAPSED_TIME + " TEXT" 
					+ KEY_IMAGE_URI + " TEXT" 
					+ ")";
    
    /* highscores table create statement */
    private static final String CREATE_CONTACTS_TABLE = 
            "CREATE TABLE " + TABLE_SAVED_GAMES + "("
                    + KEY_ID + " INTEGER PRIMARY KEY,"
            		+ KEY_PLAYER_NAME + " TEXT,"
                    + KEY_IMAGE_URI + " TEXT,"
            		+ KEY_GAME_STATE + " TEXT,"
            		+ KEY_ELAPSED_TIME + " TEXT,"
            		+ KEY_SCORE + " TEXT" 
            + ")";
    
    // TODO log all db queries 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB_QUERY", CREATE_CONTACTS_TABLE);
        Log.d("DB_QUERY", CREATE_HIGHSCORES_TABLE);
        
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_HIGHSCORES_TABLE);
    }
    
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		/* Drop older table if existed */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
        /* Create tables again */
        onCreate(db);
	}
    
    public void addHighScore(GameScore score) {
    	SQLiteDatabase db = this.getWritableDatabase();
   	 
    	Log.d("GIMPELLI", String.valueOf(score.getScore()));
    	
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME, score.getPlayerName());
        values.put(KEY_IMAGE_URI, score.getUriToImage());
        values.put(KEY_SCORE, String.valueOf(score.getScore()));
        values.put(KEY_ELAPSED_TIME, score.getTime());
     
        // Inserting Row
        db.insert(TABLE_HIGH_SCORES, null, values);
        db.close(); // Closing database connection	
    }
    
 // Deleting single game
    public void deleteSavedGame(GameScore score) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIGH_SCORES, KEY_ID + " = ?",
                new String[] { String.valueOf(score.getID()) });
        db.close();
    }
    
    public List<GameScore> getAllHighScores() {
    	List<GameScore> highScoresList = new ArrayList<GameScore>();
        /* Select All Query */
        String selectQuery = "SELECT  * FROM " + TABLE_HIGH_SCORES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        /* looping through all rows and adding to list */
        if (cursor.moveToFirst()) {
            do {
            	GameScore score = new GameScore();
                score.setID(Integer.parseInt(cursor.getString(0)));
                score.setPlayerName(cursor.getString(1));
                score.setUriToImage(cursor.getString(2));
                score.setTime(cursor.getString(4));
                score.setScore(Integer.parseInt(cursor.getString(5)));
                score.setID(Integer.parseInt(cursor.getString(0)));
                /* Adding score to list */
                highScoresList.add(score);
            } while (cursor.moveToNext());
        }
     
        // return scores list
        return highScoresList;
    }
    
    // Adding new game
    public void addSavedGame(Game game) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
    	Log.d("GIMPELLI", String.valueOf(game.getScore()));
    	
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME, game.getPlayerName());
        values.put(KEY_IMAGE_URI, game.getUriToImage());
        values.put(KEY_GAME_STATE, game.getGameState());
        values.put(KEY_SCORE, String.valueOf(game.getScore()));
        values.put(KEY_ELAPSED_TIME, game.getTime());
     
        // Inserting Row
        db.insert(TABLE_SAVED_GAMES, null, values);
        db.close(); // Closing database connection
    }
    
    public Game getSavedGame(int id) {
    	SQLiteDatabase db = this.getReadableDatabase();
    	 
        Cursor cursor = db.query(TABLE_SAVED_GAMES, new String[] { 
        		KEY_ID,
                KEY_PLAYER_NAME,
                KEY_IMAGE_URI,
                KEY_GAME_STATE,
                KEY_ELAPSED_TIME,
                KEY_SCORE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        
        if (cursor != null)
            cursor.moveToFirst();
     
        Game game = new Game(
        		Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                Integer.parseInt(cursor.getString(4)));
        
        return game;
    }
     
    public List<Game> getAllSavedGames() {
    	List<Game> savedGamesList = new ArrayList<Game>();
        /* Select All Query */
        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_GAMES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        /* looping through all rows and adding to list */
        if (cursor.moveToFirst()) {
            do {
            	Game game = new Game();
                game.setID(Integer.parseInt(cursor.getString(0)));
                game.setPlayerName(cursor.getString(1));
                game.setUriToImage(cursor.getString(2));
                game.setGameState(cursor.getString(3));
                game.setTime(cursor.getString(4));
                game.setScore(Integer.parseInt(cursor.getString(5)));
                game.setID(Integer.parseInt(cursor.getString(0)));
                /* Adding game to list */
                savedGamesList.add(game);
            } while (cursor.moveToNext());
        }
     
        // return games list
        return savedGamesList;
    }
     
    // Getting games Count
    public int getSavedGamesCount() {
    	String countQuery = "SELECT  * FROM " + TABLE_SAVED_GAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
         /*return count */
        return cursor.getCount();
    }
     
    // Deleting single game
    public void deleteSavedGame(Game game) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SAVED_GAMES, KEY_ID + " = ?",
                new String[] { String.valueOf(game.getID()) });
        db.close();
    }
	
    public GameScore getHighestScore() {
    	GameScore result = null;
    	List<GameScore> scores = getAllHighScores();
    	
    	if (scores == null) {
    		return result;
    	}
    	
    	for (GameScore game : scores) {
    		if (game.getScore() > -1) result = game;
    	}
    	
    	return result;
    }
}
