package com.example.demo.model;

public class Data {
	
	String title;
	boolean is_background;
	String message;
	String image;
	Payload payload;
	String timestamp;
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public boolean isIs_background() {
		return is_background;
	}

	public void setIs_background(boolean is_background) {
		this.is_background = is_background;
	}

	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload payload) {
		this.payload = payload;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}




	@Override
	public String toString() {
		return "Data [title=" + title + ", is_background=" + is_background + ", message=" + message + ", image=" + image
				+ ", payload=" + payload + ", timestamp=" + timestamp + "]";
	}
	
	
	
	

}
