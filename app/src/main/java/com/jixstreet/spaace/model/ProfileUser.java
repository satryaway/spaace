package com.jixstreet.spaace.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author fachrifaul - fachripaul@gmail.com
 */
public class ProfileUser {

//    {
//        "id": 322,
//            "email": "fachripaul@gmail.com",
//            "username": null,
//            "uid": null,
//            "provider": null,
//            "fb_access_token": null,
//            "profile": {
//        "fullName": "password",
//                "region": null
//    },
//        "profile_picture_ori": null,
//            "profile_picture_medium": null,
//            "profile_picture_thumb": null,
//            "created_at": "2015-12-15T17:46:14.000Z",
//            "updated_at": "2015-12-15T17:48:24.000Z"
//    }

    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;

    @SerializedName("username")
    public String username;

    @SerializedName("provider")
    public String provider;

    @SerializedName("fb_access_token")
    public String fbAccessToken;

    @SerializedName("profile")
    public Profile profile;

    @SerializedName("profile_picture_ori")
    public String profile_PictureOri;

    @SerializedName("profile_picture_medium")
    public String profilePictureMedium;

    @SerializedName("profile_picture_thumb")
    public String profilePictureThumb;

    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("updated_at")
    public String updatedAt;


    public class Profile {
        @SerializedName("full_name")
        public String fullName;

        @SerializedName("region")
        public String region;
    }
}
