package com.jixstreet.spaace.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author fachrifaul - fachripaul@gmail.com
 */
public class ProfileUser {

    /*{
            "id": 2,
            "email": "kubido@gmail.com",
            "username": null,
            "uid": null,
            "provider": null,
            "fb_access_token": null,
            "profile": {
                "full_name": null,
                "region": null
            },
            "company_profile": null,
            "profile_picture_ori": null,
            "profile_picture_medium": null,
            "profile_picture_thumb": null,
            "created_at": "2015-11-25T10:14:57.000Z",
            "updated_at": "2015-11-25T13:52:10.000Z"
    }*/

    @SerializedName("id")
    public String id;

    @SerializedName("email")
    public String email;

    @SerializedName("username")
    public String username;

    @SerializedName("uid")
    public String uid;

    @SerializedName("provider")
    public String provider;

    @SerializedName("fb_access_token")
    public String fbAccessToken;

    @SerializedName("profile")
    public Profile profile;

    @SerializedName("company_profile")
    public String companyProfile;

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
