package com.example.bufetemanolousersmanual;

public class LanguageItem {
    private int imageResource;
    private String languageId;

    public LanguageItem(int imageResource, String languageId) {
        this.imageResource = imageResource;
        this.languageId = languageId;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getLanguageId() {
        return languageId;
    }
}