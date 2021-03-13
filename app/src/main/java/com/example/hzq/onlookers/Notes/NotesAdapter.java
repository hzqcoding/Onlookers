package com.example.hzq.onlookers.Notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hzq.onlookers.R;

import java.util.List;

//自定义适配器
public class NotesAdapter extends ArrayAdapter<Notes> {
    private List<Notes> notesList;
    private Context context;
    private int layoutId;
    public NotesAdapter(Context context, int layoutId, List<Notes> notesList) {
        super(context, layoutId, notesList);
        this.context = context;
        this.layoutId = layoutId;
        this.notesList = notesList;
    }

    //获取当前位置的Notes实例
    @Override
    public Notes getItem(int position) {
        if (notesList != null && notesList.size() > 0)
            return notesList.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //使用ViewHolder提升ListView的运行效率
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        class ViewHolder{
            TextView title;
            TextView time;
        }
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(
                    context.getApplicationContext()).inflate
                    (R.layout.notes_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String title = notesList.get(position).getTitle();
        viewHolder.title.setText(title);
        viewHolder.time.setText(notesList.get(position).getTime());
        return convertView;
    }
    public void removeItem(int position){
        this.notesList.remove(position);
    }

}

