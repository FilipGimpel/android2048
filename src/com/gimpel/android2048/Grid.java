package com.gimpel.android2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Point;


/**
 * Model-Controler class
 */
public class Grid {
	private int[][] mArray = new int[4][4];
	private int mScore = 0;
	
	public Grid() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				mArray[i][j] = 0;
			}
		}
		
		addRandom();
		addRandom();
	}
	
	public String getScore() {
		return String.valueOf(mScore);
	}
	
	private List<Point> getEmptyElements() {
		List<Point> emptyElements = new ArrayList<Point>();
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (mArray[i][j] == 0) {
					emptyElements.add(new Point(i, j));
				}
			}
		}
		
		return emptyElements;
	}
	
	/**
	 * Checks if we can make any more moves, or if the game is over
	 */
	public boolean isGameLost() {
		// if there are still some empty spots on the grid, game is surely not over
		if (getEmptyElements().size() != 0) return false;
		
		// checking whether there are two elements in grid
		// next to each other horizontally or vertically
		// if so, return false, cause we can still play
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (j!=3) { /*check right */
					if (mArray[i][j] == mArray[i][j+1]) return false; 
				}
				if (i!=3) { /*check bottom*/
					if (mArray[i][j] == mArray[i+1][j]) return false;
				}
			}
		}
		
		// otherwise, game over!
		return true;
	}
	
	/**
	 * Adds random element to the grid if there is an empty slot available
	 *  
	 * @return Position of random element added to the grid in empty slot, 
	 * -1 if there are no such slots left
	 */
	public int addRandom() {
		List<Point> emptyElements = getEmptyElements();
		
		int result;
		
		if (emptyElements.size() != 0) {
			int randomPosition = new Random().nextInt(emptyElements.size());
			Point randomPoint = emptyElements.get(randomPosition);
			mArray[randomPoint.x][randomPoint.y] = 2;
			result = randomPoint.x*4 + randomPoint.y;
		} else {
			result = -1;
		}
		
		return result;
	}
	
	public int getElementAt(int i) {
		return mArray[i/4][i%4];
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(/*"====\n"*/);
		sb.append(String.format("SCORE: %d \n", mScore));
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sb.append(mArray[i][j]);
			}
			
			sb.append("\n");
		}
		
		sb.append("\n");
		
		return sb.toString();
	}
	
	/* --------- LEFT -------- */
	public void moveLeft() {
		moveRowElementsLeft();
		sumRowElementsLeft();
		moveRowElementsLeft();
	}
	
	private void moveRowElementsLeft() {
		for (int h = 0; h < 4; h++) {
			for (int i = 1; i < 4; i++) {
				int k = i;
				int z = i - 1;
				while (z > -1) {
					if (mArray[h][z] == 0) {
						mArray[h][z] = mArray[h][k];
						mArray[h][k] = 0;
						
						k--;
					}
					z--;
				}
			}
		}
	}
	
	private void sumRowElementsLeft() {
		for (int h = 0; h < 4; h++) {
			for (int i = 1; i < 4; i++) {
				if (mArray[h][i-1] == mArray[h][i] ) {
					mArray[h][i-1] = mArray[h][i-1] + mArray[h][i];
					mArray[h][i] = 0;
					
					mScore += mArray[h][i-1]; 
					
					i++;
				}
			}
		}
	}
	
	/* --------- RIGHT -------- */
	public void moveRight() {
		moveRowElementsRight();
		sumRowElementsRight();
		moveRowElementsRight();
	}
	
	private void moveRowElementsRight() {
		for (int h = 0; h < 4; h++) {
			for (int i = 2; i > -1; i--) {
				int k = i;
				int z = i + 1;
				while (z < 4) {
					if (mArray[h][z] == 0) {
						mArray[h][z] = mArray[h][k];
						mArray[h][k] = 0;
						
						k++;
					}
					z++;
				}
			}
		}
	}
	
	private void sumRowElementsRight() {
		for (int h = 0; h < 4; h++) {
			for (int i = 3; i > 0; i--) {
				if (mArray[h][i-1] == mArray[h][i] ) {
					mArray[h][i-1] = mArray[h][i-1] + mArray[h][i];
					mArray[h][i] = 0;
					
					mScore += mArray[h][i-1];
					
					i--;
				}
			}
		}
	}
	
	/* --------- DOWN -------- */
	public void moveDown() {
		moveColumnElementsDown();
		sumColumnElementsDown();
		moveColumnElementsDown();
	}
	
	private void moveColumnElementsDown() {
		for (int h = 0; h < 4; h++) {
			for (int i = 2; i > -1; i--) {
				int k = i;
				int z = i + 1;
				while (z < 4) {
					if (mArray[z][h] == 0) {
						mArray[z][h] = mArray[k][h];
						mArray[k][h] = 0;
						
						k++;
					}
					z++;
				}
			}
		}
	}
	
	private void sumColumnElementsDown() {
		for (int h = 0; h < 4; h++) {
			for (int i = 3; i > 0; i--) {
				if (mArray[i-1][h] == mArray[i][h] ) {
					mArray[i-1][h] = mArray[i-1][h] + mArray[i][h];
					mArray[i][h] = 0;
					
					mScore += mArray[i-1][h]; 
					
					i--;
				}
			}
		}
	}
	
	
	
	/* --------- UP -------- */
	public void moveUp() {
		moveColumnElementsUp();
		sumColumnElementsUp();
		moveColumnElementsUp();
	}
	
	private void moveColumnElementsUp() {
		for (int h = 0; h < 4; h++) {
			for (int i = 1; i < 4; i++) {
				int k = i;
				int z = i - 1;
				while (z > -1) {
					if (mArray[z][h] == 0) {
						mArray[z][h] = mArray[k][h];
						mArray[k][h] = 0;
						
						k--;
					}
					z--;
				}
			}
		}
	}
	
	private void sumColumnElementsUp() {
		for (int h = 0; h < 4; h++) {
			for (int i = 1; i < 4; i++) {
				if (mArray[i-1][h] == mArray[i][h] ) {
					mArray[i-1][h] = mArray[i-1][h] + mArray[i][h];
					mArray[i][h] = 0;
					
					mScore += mArray[i-1][h];
					
					i++;
				}
			}
		}
	}
}
