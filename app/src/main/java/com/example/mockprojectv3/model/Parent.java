package com.example.mockprojectv3.model;

import java.util.List;

public class Parent {
    private int type;

    private List<Category> categoryList;
    private List<Popular> popularsList;

    public Parent(int type, List<Category> categoryList, List<Popular> popularsList) {
        this.type = type;
        this.categoryList = categoryList;
        this.popularsList = popularsList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Popular> getPopularsList() {
        return popularsList;
    }

    public void setPopularsList(List<Popular> popularsList) {
        this.popularsList = popularsList;
    }
}
