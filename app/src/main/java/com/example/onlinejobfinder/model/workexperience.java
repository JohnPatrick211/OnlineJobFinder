package com.example.onlinejobfinder.model;

public class workexperience {

    public workexperience() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicantworkposition() {
        return applicantworkposition;
    }

    public void setApplicantworkposition(String applicantworkposition) {
        this.applicantworkposition = applicantworkposition;
    }

    public String getApplicantworkcompanyname() {
        return applicantworkcompanyname;
    }

    public void setApplicantworkcompanyname(String applicantworkcompanyname) {
        this.applicantworkcompanyname = applicantworkcompanyname;
    }

    public String getApplicantworkspecialization() {
        return applicantworkspecialization;
    }

    public void setApplicantworkspecialization(String applicantworkspecialization) {
        this.applicantworkspecialization = applicantworkspecialization;
    }

    public String getApplicantworkdate() {
        return applicantworkdate;
    }

    public void setApplicantworkdate(String applicantworkdate) {
        this.applicantworkdate = applicantworkdate;
    }

    public workexperience(String id, String applicantworkposition, String applicantworkcompanyname, String applicantworkspecialization, String applicantworkdate) {
        this.id = id;
        this.applicantworkposition = applicantworkposition;
        this.applicantworkcompanyname = applicantworkcompanyname;
        this.applicantworkspecialization = applicantworkspecialization;
        this.applicantworkdate = applicantworkdate;
    }

    private String id;
    private String applicantworkposition;
    private String applicantworkcompanyname;
    private String applicantworkspecialization;
    private String applicantworkdate;
}
