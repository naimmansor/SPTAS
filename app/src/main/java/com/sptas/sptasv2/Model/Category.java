package com.sptas.sptasv2.Model;

/**
 * Created by Na'im Mansor on 16-Feb-18.
 */

public class Category {
    private String Name;
    private String Image;
    private String chapterId;

    public Category() {
    }

    public Category(String name, String image, String chapterId) {
        Name = name;
        Image = image;
        this.chapterId = chapterId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
