package com.sptas.sptasv2.Model;

/**
 * Created by Na'im Mansor on 20-Feb-18.
 */

public class QuestionScore {
    private String Question_Score;
    private String User;
    private String UserName;
    private String Score;
    private String CategoryId;
    private String CategoryName;
    private String ChapterId;
    private String ChapterName;

    public QuestionScore() {
    }

    public QuestionScore(String question_Score, String user, String userName, String score, String categoryId, String categoryName, String chapterId, String chapterName) {
        Question_Score = question_Score;
        User = user;
        UserName = userName;
        Score = score;
        CategoryId = categoryId;
        CategoryName = categoryName;
        ChapterId = chapterId;
        ChapterName = chapterName;
    }

    public String getQuestion_Score() {
        return Question_Score;
    }

    public void setQuestion_Score(String question_Score) {
        Question_Score = question_Score;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getChapterId() {
        return ChapterId;
    }

    public void setChapterId(String chapterId) {
        ChapterId = chapterId;
    }

    public String getChapterName() {
        return ChapterName;
    }

    public void setChapterName(String chapterName) {
        ChapterName = chapterName;
    }
}
