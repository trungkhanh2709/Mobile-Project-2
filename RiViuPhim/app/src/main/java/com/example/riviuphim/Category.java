package com.example.riviuphim;

import java.util.ArrayList;

public class Category {
    private String nameCategory;
    private ArrayList<Phim> phims;

    public Category(ArrayList<Phim> phims) {
        this.phims = phims;
    }

    public Category(String nameCategory, ArrayList<Phim> phims) {
        this.nameCategory = nameCategory;
        this.phims = phims;
    }

    // Getters and Setters
    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public ArrayList<Phim> getPhims() {
        return phims;
    }

    public void setPhims(ArrayList<Phim> phims) {
        this.phims = phims;
    }
}
