package com.hrd.clientserverdemo;

import android.graphics.Bitmap;

public class Item {
    private String article_id;
    private String article_title;
    private String article_image;
    private Bitmap bitmap;
    
    public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public Item(){
    	
    }
    
    public Item(String id,String title, String image) {
        this.article_id=id;
        this.article_title=title;
        this.article_image=image;
    }

    
	public String getArticle_id() {
		return article_id;
	}

	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}

	public String getArticle_title() {
		return article_title;
	}

	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}

	public String getArticle_image() {
		return article_image;
	}

	public void setArticle_image(String article_image) {
		this.article_image = article_image;
	}


	


}