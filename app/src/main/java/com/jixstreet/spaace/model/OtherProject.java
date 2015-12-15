package com.jixstreet.spaace.model;

/**
 * Created by fachrifebrian on 12/15/15.
 */
public class OtherProject {
    String idProject;
    String titleProject;
    String descExplore;
    String imageProject;

    public OtherProject(String idProject, String titleProject, String descExplore, String imageProject) {
        this.idProject = idProject;
        this.titleProject = titleProject;
        this.descExplore = descExplore;
        this.imageProject = imageProject;
    }

    public String getIdProject() {
        return idProject;
    }

    public void setIdProject(String idProject) {
        this.idProject = idProject;
    }

    public String getTitleProject() {
        return titleProject;
    }

    public void setTitleProject(String titleProject) {
        this.titleProject = titleProject;
    }

    public String getDescExplore() {
        return descExplore;
    }

    public void setDescExplore(String descExplore) {
        this.descExplore = descExplore;
    }

    public String getImageProject() {
        return imageProject;
    }

    public void setImageProject(String imageProject) {
        this.imageProject = imageProject;
    }
}
