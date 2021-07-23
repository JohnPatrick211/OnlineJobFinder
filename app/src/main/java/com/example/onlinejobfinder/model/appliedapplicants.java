package com.example.onlinejobfinder.model;

public class appliedapplicants {

    public appliedapplicants() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplicantid() {
        return applicantid;
    }

    public void setApplicantid(String applicantid) {
        this.applicantid = applicantid;
    }

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }

    public String getJobapplyid() {
        return jobapplyid;
    }

    public void setJobapplyid(String jobapplyid) {
        this.jobapplyid = jobapplyid;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public appliedapplicants(String id, String applicantid, String jobid, String jobapplyid, String profilepic, String name, String email, String address, String contactno, String gender, String status) {
        this.id = id;
        this.applicantid = applicantid;
        this.jobid = jobid;
        this.jobapplyid = jobapplyid;
        this.profilepic = profilepic;
        this.name = name;
        this.email = email;
        this.address = address;
        this.contactno = contactno;
        this.gender = gender;
        this.status = status;
    }

    private String id;
    private String applicantid;
    private String jobid;
    private String jobapplyid;
    private String profilepic;
    private String name;
    private String email;
    private String address;
    private String contactno;
    private String gender;
    private String status;
}
