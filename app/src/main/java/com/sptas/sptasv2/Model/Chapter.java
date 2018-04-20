package com.sptas.sptasv2.Model;

public class Chapter {
    String id, categoryId, chapterId, nameChapter, noChapter;

    public Chapter() {
    }

    public Chapter(String id, String categoryId, String chapterId, String nameChapter, String noChapter) {
        this.id = id;
        this.categoryId = categoryId;
        this.chapterId = chapterId;
        this.nameChapter = nameChapter;
        this.noChapter = noChapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getNameChapter() {
        return nameChapter;
    }

    public void setNameChapter(String nameChapter) {
        this.nameChapter = nameChapter;
    }

    public String getNoChapter() {
        return noChapter;
    }

    public void setNoChapter(String noChapter) {
        this.noChapter = noChapter;
    }
}
