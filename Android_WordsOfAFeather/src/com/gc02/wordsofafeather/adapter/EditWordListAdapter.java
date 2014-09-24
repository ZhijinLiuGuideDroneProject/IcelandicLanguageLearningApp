package com.gc02.wordsofafeather.adapter;

import java.util.List;

import com.gc02.wordsofafeather.activity.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class EditWordListAdapter extends ArrayAdapter<EditWordListModel>{
	private final Activity context;
	private List<EditWordListModel> list;
	
	public EditWordListAdapter(Activity context, List<EditWordListModel> list){
		super(context, R.layout.edit_word_list, list);
		this.context=context;
		this.list=list;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View view=null;
		if(convertView == null){
			LayoutInflater inflator=context.getLayoutInflater();
			view=inflator.inflate(R.layout.edit_word_list, null);
			final EditWordListViewHolder viewHolder=new EditWordListViewHolder();
			viewHolder.question=(TextView) view.findViewById(R.id.question);
			viewHolder.answer=(TextView) view.findViewById(R.id.answer);
			viewHolder.checkbox=(CheckBox) view.findViewById(R.id.checkbox);
			viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					EditWordListModel element=(EditWordListModel) viewHolder.checkbox.getTag();
					element.setSelected(buttonView.isChecked());	
				}
			});
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));			
		} else {
			view=convertView;
			((EditWordListViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		EditWordListViewHolder holder = (EditWordListViewHolder) view.getTag();
        holder.question.setText(list.get(position).getQuestion());
        holder.answer.setText(list.get(position).getAnswer());
        holder.checkbox.setChecked(list.get(position).isSelected());
        return view;
	}		
}
