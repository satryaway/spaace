package com.jixstreet.spaace.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fachrifebrian on 9/2/15.
 */
public class Portofolio implements Serializable{
//
//    {
//        "id": 12,
//            "user_id": 2,
//            "title": "Rumah 1",
//            "description": "Deskripsi 1",
//            "design_fees": "New",
//            "renovation_duration": 1,
//            "size": 100,
//            "price": "100000.0",
//            "portofolio_images": [
//        {
//            "id": 2,
//                "image_ori": "http://res.cloudinary.com/hlzgu8fas/image/upload/v1449659346/knybjnlawc6nlezzefon.jpg",
//                "image_medium": "http://res.cloudinary.com/hlzgu8fas/image/upload/c_fit,h_400,w_400/v1449659346/knybjnlawc6nlezzefon.jpg",
//                "image_thumb": "http://res.cloudinary.com/hlzgu8fas/image/upload/c_fit,h_100,w_100/v1449659346/knybjnlawc6nlezzefon.jpg"
//        }
//        ],
//        "user": {
//        "id": 2,
//                "email": "kubido@gmail.com",
//                "username": null,
//                "profile_picture_thumb": null
//    }
//    }

    @SerializedName("id")
    public String id;

    @SerializedName("user_id")
    public String userId;

    @SerializedName("title")
    public String title;

    @SerializedName("description")
    public String description;

    @SerializedName("design_fees")
    public String designFees;

    @SerializedName("renovation_duration")
    public String renovationDuration;

    @SerializedName("size")
    public String size;

    @SerializedName("price")
    public String price;

    @SerializedName("portofolio_images")
    public List<PortofolioImages> portofolioImages;

    @SerializedName("user")
    public User user;

    public class PortofolioImages implements Serializable{
        @SerializedName("id")
        public String idPortofolioImages;

        @SerializedName("image_ori")
        public String imageOri;

        @SerializedName("image_medium")
        public String imageMedium;

        @SerializedName("image_thumb")
        public String imageThumb;
    }

    public class User implements Serializable {
        @SerializedName("id")
        public String idUser;

        @SerializedName("email")
        public String email;

        @SerializedName("username")
        public String username;

        @SerializedName("profile_picture_thumb")
        public String profilePictureThumb;
    }

}
