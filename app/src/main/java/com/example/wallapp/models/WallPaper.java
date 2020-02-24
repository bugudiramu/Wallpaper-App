package com.example.wallapp.models;

public class WallPaper {
    private String mSmallImageUrl, mAvatarUrl, mAvatarName, mRawImageUrl;

    public WallPaper(String smallImageUrl,
//                     String rawImageUrl,
                     String avatarImageUrl, String avatarName) {
        this.mSmallImageUrl = smallImageUrl;
//        this.mRawImageUrl = rawImageUrl;
        this.mAvatarUrl = avatarImageUrl;
        this.mAvatarName = avatarName;
    }

    // * Getters for the properties of WallPaper class
    public String getSmallImageUrl() {
        return mSmallImageUrl;
    }

    public String getRawImageUrl() {
        return mRawImageUrl;
    }

    public String getAvatarImageUrl() {
        return mAvatarUrl;
    }

    public String getAvatarName() {
        return mAvatarName;
    }


}
