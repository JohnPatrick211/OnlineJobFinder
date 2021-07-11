package com.example.onlinejobfinder.model;

public class educationalbackground {
    public educationalbackground() {

    }
    public String getApplicantschool() {
        return applicantschool;
    }

    public void setApplicantschool(String applicantschool) {
        this.applicantschool = applicantschool;
    }

    public String getApplicantcourse() {
        return applicantcourse;
    }

    public void setApplicantcourse(String applicantcourse) {
        this.applicantcourse = applicantcourse;
    }

    public String getApplicantyeargraduated() {
        return applicantyeargraduated;
    }

    public void setApplicantyeargraduated(String applicantyeargraduated) {
        this.applicantyeargraduated = applicantyeargraduated;
    }

    public educationalbackground(String applicantschool, String applicantcourse, String applicantyeargraduated) {
        this.applicantschool = applicantschool;
        this.applicantcourse = applicantcourse;
        this.applicantyeargraduated = applicantyeargraduated;
    }

    private String applicantschool;
    private String applicantcourse;
    private String applicantyeargraduated;

}
