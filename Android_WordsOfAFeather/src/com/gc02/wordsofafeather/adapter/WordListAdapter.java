package com.gc02.wordsofafeather.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gc02.wordsofafeather.activity.R;

public class WordListAdapter extends ArrayAdapter<WordListModel>{
	private final Activity context;
	private List<WordListModel> list;
	
	public WordListAdapter(Activity context, List<WordListModel> list){
		super(context, R.layout.word_list, list);
		this.context=context;
		this.list=list;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view=null;
		if(convertView == null){
			LayoutInflater inflator=context.getLayoutInflater();
			view=inflator.inflate(R.layout.word_list, null);
			final EditWordListViewHolder viewHolder=new EditWordListViewHolder();
			viewHolder.question=(TextView) view.findViewById(R.id.question);
			viewHolder.answer=(TextView) view.findViewById(R.id.answer);	
			view.setTag(viewHolder);	
		} else {
			view=convertView;
		}
		EditWordListViewHolder holder = (EditWordListViewHolder) view.getTag();
        holder.question.setText(list.get(position).getQuestion());
        holder.answer.setText(list.get(position).getAnswer());
        return view;
	}

}
