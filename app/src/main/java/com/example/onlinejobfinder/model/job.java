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

    public job(String joblogo, String jobtitle, String jobcompany, String jobaddress, String jobsalary, String jobdateposted) {
        this.joblogo = joblogo;
        this.jobtitle = jobtitle;
        this.jobcompany = jobcompany;
        this.jobaddress = jobaddress;
        this.jobsalary = jobsalary;
        this.jobdateposted = jobdateposted;
    }

    private String joblogo;
    private String jobtitle;
    private String jobcompany;
    private String jobaddress;
    private String jobsalary;
    private String jobdateposted;
}
