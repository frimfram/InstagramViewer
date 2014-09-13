package com.codepath.frimfram.instagramviewer;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}

	public int getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(int likesCount) {
		this.likesCount = likesCount;
	}

	public String getPrifilePicUrl() {
		return prifilePicUrl;
	}

	public void setPrifilePicUrl(String prifilePicUrl) {
		this.prifilePicUrl = prifilePicUrl;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public static InstagramPhoto fromJson(JSONObject photoJSON) {
		InstagramPhoto photo = new InstagramPhoto();
		try {
			if (photoJSON.getJSONObject("user") != null) {
				photo.setUsername(photoJSON.getJSONObject("user").getString(
						"username"));
				photo.setPrifilePicUrl(photoJSON.getJSONObject("user")
						.getString("profile_picture"));
			}
			if (photoJSON.getJSONObject("caption") != null) {
				photo.caption = photoJSON.getJSONObject("caption").getString(
						"text");
			}
			if (photoJSON.getJSONObject("images") != null
					&& photoJSON.getJSONObject("images").getJSONObject(
							"standard_resolution") != null) {
				photo.imageUrl = photoJSON.getJSONObject("images")
						.getJSONObject("standard_resolution").getString("url");
				photo.imageHeight = photoJSON.getJSONObject("images")
						.getJSONObject("standard_resolution").getInt("height");
			}
			if (photoJSON.getJSONObject("likes") != null) {
				photo.likesCount = photoJSON.getJSONObject("likes").getInt(
						"count");
			}

			if (photoJSON.getJSONObject("comments") != null) {
				
				JSONArray commentsJsonArray = photoJSON.getJSONObject("comments")
						.getJSONArray("data");
				int commentsCount = commentsJsonArray.length();
				JSONObject commentObj;
				Comment comment;
				JSONObject fromObj;
				String commentUsername = "";
				photo.comments = new ArrayList<Comment>(2);
				for (int i = commentsCount - 1; (i > (commentsCount - 3) && i >= 0); i--) {
					commentObj = (JSONObject) commentsJsonArray.get(i);
					fromObj = (JSONObject) commentObj.getJSONObject("from");
					if (fromObj != null) {
						commentUsername = fromObj.getString("username");
					}
					comment = new Comment(commentUsername,
							commentObj.getString("text"));
					photo.comments.add(comment);

				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return photo;
	}

	public static ArrayList<InstagramPhoto> fromJson(JSONArray jsonArray) {
		ArrayList<InstagramPhoto> photos = new ArrayList<InstagramPhoto>(
				jsonArray.length());
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject photoJson = null;
			try {
				photoJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			InstagramPhoto photo = InstagramPhoto.fromJson(photoJson);
			if (photo != null) {
				photos.add(photo);
			}
		}
		return photos;
	}

	private String username;
	private String caption;
	private String imageUrl;
	private int imageHeight;
	private int likesCount;
	private String prifilePicUrl;
	private String locationName;
	private ArrayList<Comment> comments;
}
