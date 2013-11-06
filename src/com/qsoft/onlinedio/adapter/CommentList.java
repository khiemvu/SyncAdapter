package com.qsoft.onlinedio.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.onlinedio.model.CommentModel;
import com.qsoft.onlinedio.R;

import java.util.List;

/**
 * User: khiemvx
 * Date: 10/18/13
 */
public class CommentList extends ArrayAdapter<CommentModel>
{
    private Context context;

    public CommentList(Context context, int resourceId, List<CommentModel> objects)
    {
        super(context, resourceId, objects);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder
    {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvComment;
        TextView tvTime;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        CommentModel rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.program_comment_item_layout, null);
            holder = new ViewHolder();

            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.comment_ivAvatarDetail);
            holder.tvName = (TextView) convertView.findViewById(R.id.comment_tvName);
            holder.tvComment = (TextView) convertView.findViewById(R.id.comment_tvContent);
            holder.tvTime = (TextView) convertView.findViewById(R.id.comment_tvTime);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ivAvatar.setImageResource(rowItem.getImageID());
        holder.tvName.setText(rowItem.getPerson());
        holder.tvComment.setText((rowItem.getComment()));
        holder.tvTime.setText(rowItem.getTime());
        return convertView;
    }
}
