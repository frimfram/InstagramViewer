package com.codepath.frimfram.instagramviewer;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
	// View lookup cache
	private static class ViewHolder {
		ImageView image;
		TextView caption;
		TextView username;
		ImageView profileImage;
		TextView likes;
		TextView comment1;
		TextView comment2;
	}

	public InstagramPhotosAdapter(Context context, List<InstagramPhoto> photos) {
		super(context, R.layout.item_photo, photos);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get the data item
		InstagramPhoto photo = getItem(position);
		// check if we are using the recycled view
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.item_photo, parent, false);
			viewHolder.image = (ImageView) convertView
					.findViewById(R.id.imgPhoto);
			DisplayMetrics metrics = getContext().getResources()
					.getDisplayMetrics();
			int screenWidth = metrics.widthPixels;
			viewHolder.image.getLayoutParams().height = screenWidth;
			viewHolder.caption = (TextView) convertView
					.findViewById(R.id.tvCaption);
			viewHolder.username = (TextView) convertView
					.findViewById(R.id.tvUsername);
			viewHolder.likes = (TextView) convertView
					.findViewById(R.id.tvLikes);
			viewHolder.profileImage = (ImageView) convertView
					.findViewById(R.id.imgProfile);
			viewHolder.comment1 = (TextView) convertView
					.findViewById(R.id.tvComment1);
			viewHolder.comment2 = (TextView) convertView
					.findViewById(R.id.tvComment2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		// populate the sub-views with the correct data
		String captionText = "<font color=\"#19528C\">" + photo.getUsername()
				+ "</font>" + "  " + photo.getCaption();
		viewHolder.caption.setText(Html.fromHtml(captionText));

		if (photo.getComments() != null && photo.getComments().size() > 0) {
			String commentText1 = "<font color=\"#19528C\">"
					+ photo.getComments().get(0).getUsername() + "</font>"
					+ "  " + photo.getComments().get(0).getText();
			viewHolder.comment1.setVisibility(View.VISIBLE);
			viewHolder.comment1.setText(Html.fromHtml(commentText1));
			if(photo.getComments().size() >1) {
				String commentText2 = "<font color=\"#19528C\">"
						+ photo.getComments().get(1).getUsername() + "</font>"
						+ "  " + photo.getComments().get(1).getText();
				viewHolder.comment2.setVisibility(View.VISIBLE);
				viewHolder.comment2.setText(Html.fromHtml(commentText2));
			}else{
				viewHolder.comment2.setVisibility(View.GONE);
			}
		} else {
			viewHolder.comment1.setVisibility(View.GONE);
			viewHolder.comment2.setVisibility(View.GONE);
		}

		// reset the image from the recycled view
		viewHolder.image.setImageResource(0);
		viewHolder.profileImage.setImageResource(0);
		viewHolder.username.setText(photo.getUsername());
		if (photo.getLikesCount() > 0) {
			viewHolder.likes.setText(photo.getLikesCount() + " "
					+ getContext().getResources().getString(R.string.likes));
		}

		Picasso.with(getContext()).load(photo.getImageUrl())
				.into(viewHolder.image);
		if (photo.getPrifilePicUrl() != null) {
			Picasso.with(getContext()).load(photo.getPrifilePicUrl())
					.into(viewHolder.profileImage);
		}
		// return the view
		return convertView;
	}

}
