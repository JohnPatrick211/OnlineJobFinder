package com.example.onlinejobfinder.model;

public class job {
    public job() {

    }

    public String getJoblogo() {
        return joblogo;
    }

    public void setJoblogo(String joblogo) {
        this.joblogo = joblogo;
    }

    public String getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(String jobtitle) {
        this.jobtitle = jobtitle;
    }

    public String getJobcompany() {
        return jobcompany;
    }

    public void setJobcompany(String jobcompany) {
        this.jobcompany = jobcompany;
    }

    public String getJobaddress() {
        return jobaddress;
    }

    public void setJobaddress(String jobaddress) {
        this.jobaddress = jobaddress;
    }

    public String getJobsalary() {
        return jobsalary;
    }

    public void setJobsalary(String jobsalary) {
        this.jobsalary = jobsalary;
    }

    public String getJobdateposted() {
        return jobdateposted;
    }

    public void setJobdateposted(String jobdateposted) {
        this.jobdateposted = jobdateposted;
    }

    public String getCompanyoverview() {
        return companyoverview;
    }

    public void setCompanyoverview(String companyoverview) {
        this.companyoverview = companyoverview;
    }

    public String getJobcategory() {
        return jobcategory;
    }

    public void setJobcategory(String jobcategory) {
        this.jobcategory = jobcategory;
    }

    public String getJoblocation() {
        return joblocation;
    }

    public void setJoblocation(String joblocation) {
        this.joblocation = joblocation;
    }


    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }


    public String getJobdescription() {
        return jobdescription;
    }

    public void setJobdescription(String jobdescription) {
        this.jobdescription = jobdescription;
    }


    public String getJobuniqueid() {
        return jobuniqueid;
    }

    public void setJobuniqueid(String jobuniqueid) {
        this.jobuniqueid = jobuniqueid;
    }

    public String getJobstatus() {
        return jobstatus;
    }

    public void setJobstatus(String jobstatus) {
        this.jobstatus = jobstatus;
    }


    public String getSavedid() {
        return savedid;
    }

    public void setSavedid(String savedid) {
        this.savedid = savedid;
    }

    public String getCountapplicants() {
        return countapplicants;
    }

    public void setCountapplicants(String countapplicants) {
        this.countapplicants = countapplicants;
    }

    public job(String savedid, String jobstatus, String jobuniqueid, String jobdescription, String jobid, String joblocation, String jobcategory, String joblogo, String jobtitle, String jobcompany, String jobaddress, String jobsalary, String companyoverview, String jobdateposted, String countapplicants) {
        this.savedid = savedid;
        this.jobstatus = jobstatus;
        this.jobuniqueid = jobuniqueid;
        this.jobdescription = jobdescription;
        this.jobid = jobid;
        this.joblocation = joblocation;
        this.jobcategory = jobcategory;
        this.joblogo = joblogo;
        this.jobtitle = jobtitle;
        this.jobcompany = jobcompany;
        this.jobaddress = jobaddress;
        this.jobsalary = jobsalary;
        this.companyoverview = companyoverview;
        this.jobdateposted = jobdateposted;
        this.countapplicants = countapplicants;
    }

    private String savedid;
    private String jobstatus;
    private String jobuniqueid;
    private String jobdescription;
    private String jobid;
    private String joblocation;
    private String jobcategory;
    private String joblogo;
    private String jobtitle;
    private String jobcompany;
    private String jobaddress;
    private String jobsalary;
    private String companyoverview;
    private String jobdateposted;
    private String countapplicants;
}
