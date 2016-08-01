package com.netcracker.solutions.kpi.persistence.model.enums;

import com.netcracker.solutions.kpi.persistence.model.SocialNetwork;

/**
 * Created by IO on 27.05.2016.
 */
public enum SocialNetworkEnum {
    FaceBook(1L, "FaceBook");

    private final Long id;
    private final String title;


    SocialNetworkEnum(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public static SocialNetwork getSocialNetwork(Long id){
        switch (Math.toIntExact(id)){
            case 1:
                return new SocialNetwork(FaceBook.getId(), "FaceBook");
            default:
                throw new IllegalStateException("Social network not found");
        }
    }

    public static SocialNetwork getSocialNetwork(String title){
        switch (title){
            case "facebookAuth":
                return new SocialNetwork(FaceBook.getId(), "FaceBook");
            default:
                throw new IllegalStateException("Social network not found");
        }
    }

    public String getTitle() {
        return title;
    }
}
