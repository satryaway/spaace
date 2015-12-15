package com.jixstreet.spaace.model;

/**
 * Created by fachrifebrian on 9/2/15.
 */
public class Explore {
    String idExplore;
    String titleExplore;
    String descExplore;
    int type;

    public Explore(String idExplore, String titleExplore, String descExplore, int type) {
        this.idExplore = idExplore;
        this.titleExplore = titleExplore;
        this.descExplore = descExplore;
        this.type=type;
    }

    public String getIdExplore() {
        return idExplore;
    }

    public String getTitleExplore() {
        return titleExplore;
    }

    public String getDescExplore() {
        return descExplore;
    }

    public int getType() {
        return type;
    }
}
