package com.gc02.wordsofafeather.adapter;

import java.util.List;

import com.gc02.wordsofafeather.activity.GeneralGameActivity;
import com.gc02.wordsofafeather.activity.R;
import com.gc02.wordsofafeather.bean.Setting;
import com.gc02.wordsofafeather.control.SetGameControl;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter {
	
	private Context context = null;  
    private List<ChatEntity> chatList = null;  
    private LayoutInflater inflater = null;  
    private SetGameControl setGameControl;
    
	public ChatAdapter(GeneralGameActivity generalGameActivity,
			List<ChatEntity> chatList) {
		this.context = generalGameActivity;  
        this.chatList = chatList;  
        inflater = LayoutInflater.from(this.context);  
	}

	@Override
	public int getCount() {
		return chatList.size();
	}

	@Override
	public Object getItem(int position) {
		return chatList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

    @Override  
    public int getViewTypeCount() {         
        return 1;  
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	       ChatHolder chatHolder = null;  
	       setGameControl=SetGameControl.getInstance(context);
	       Setting setting=setGameControl.readSetting();
           if (convertView == null) {  
               chatHolder = new ChatHolder();  
               if(setting.getCartoonCharacter()==true){
                   convertView = inflater.inflate(R.layout.game_chat_male, null);       	   
               }
               else{
            	   convertView = inflater.inflate(R.layout.game_chat_female, null);
               }                 
               chatHolder.contentTextView = (TextView) convertView.findViewById(R.id.avatar_content);  
               chatHolder.userImageView = (ImageView) convertView.findViewById(R.id.avatar_image);  
               convertView.setTag(chatHolder);  
           }else {  
               chatHolder = (ChatHolder)convertView.getTag();  
           }         
           chatHolder.contentTextView.setText(chatList.get(position).getContent());  
           chatHolder.userImageView.setImageResource(chatList.get(position).getAvatar());  
             
           return convertView;  
	}

}
