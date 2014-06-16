package com.gimpel.android2048;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gimpel.android2048.database.Game;

public class SavedGamesAdapter extends BaseAdapter {
	Context mContext;
	List<Game> mGames;
    private static LayoutInflater mInflater = null;

    public SavedGamesAdapter(Context context, List<Game> data) {
        this.mContext = context;
        this.mGames = data;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    
	@Override
	public int getCount() {
		return mGames.size();
	}

	@Override
	public Object getItem(int position) {
		return mGames.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	static class ViewHolderItem {
		TextView mPlayerName;
		TextView mScore;
		ImageView mScreenShoot;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolderItem viewHolder;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row, null);
			
			viewHolder = new ViewHolderItem(); 
			
			viewHolder.mPlayerName = (TextView) convertView.findViewById(R.id.player_name);
			viewHolder.mScore = (TextView) convertView.findViewById(R.id.score);
			viewHolder.mScreenShoot = (ImageView) convertView.findViewById(R.id.gamescreenshoot);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		
		viewHolder.mPlayerName.setText(mGames.get(position).getPlayerName());
        viewHolder.mScore.setText(String.valueOf(mGames.get(position).getScore()));	        
        File f=new File(mGames.get(position).getUriToImage());
        Bitmap b = null;
        try {
			b = BitmapFactory.decodeStream(new FileInputStream(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
        viewHolder.mScreenShoot.setImageDrawable(new BitmapDrawable(mContext.getResources(), b));
		
        return convertView;
	}

}
