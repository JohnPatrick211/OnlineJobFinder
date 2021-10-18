package com.example.onlinejobfinder;


public class Constant {
    //local URL
    //public static final String URL="http://192.168.254.104";
    //Online URL
    public static final String URL="https://peso-ojfss.000webhostapp.com";
    public static final String home=URL+"/api";
    public static final String login=home+"/login";
    public static final String register=home+"/register";
    public static final String registeremployer=home+"/registeremployer";
    public static final String checkemail=home+"/checkemail";
    public static final String checkspecialization=home+"/checkspecialization";
    //job posts
    public static final String jobposts=home+"/jobpost";
    public static final String employerjobposts=home+"/employerjobpost";
    public static final String employerjobpostcount=home+"/employerjobpostcount";
    public static final String employerjobpostapproved=home+"/employerjobpostapproved";
    public static final String addjob=home+"/addjob";
    public static final String updatejob=home+"/updatejobpost";
    public static final String deletejob=home+"/deletesavedjobpost";
    public static final String savedjob=home+"/addsavedjobpost";
    public static final String postsavedjob=home+"/postsavedjobpost";
    public static final String unsavedjob=home+"/deletejobpost";
    //apply job posts
    public static final String applyjob=home+"/applyjob";
    public static final String employerapplyjobpost=home+"/employerapplyjobpost";
    public static final String employerapplyhiredjobpost=home+"/employerapplyhiredjobpost";
    public static final String applicanthired=home+"/applicanthired";
    public static final String deleteapplicantapply=home+"/deleteapplicantapply";
    public static final String validateemployerapplyjobpost=home+"/validateemployerapplyjobpost";
    //Education Background
    public static final String geteducation=home+"/posteducationalbackground";
    public static final String deleteeducation=home+"/deleteeducationalbackground";
    public static final String updateeducation=home+"/updateeducationalbackground";
    public static final String addeducation=home+"/addeducationalbackground";
    //sendOTP
    public static final String sendOTP=home+"/sendOTP";
    //Work Experience
    public static final String getworkexperience=home+"/postworkexperience";
    public static final String deleteworkexperience=home+"/deleteworkexperience";
    public static final String updateworkexperience=home+"/updateworkexperience";
    public static final String addworkexperience=home+"/addworkexperience";
    //filter
    public static final String categoryfilter=home+"/categoryfilter";
    public static final String locationfilter=home+"/locationfilter";
    public static final String SAVE_USER_PROFILE=home+"/saveimage";
    // applicant post profile
    public static final String MY_POST=home+"/mypost";
    // employer post profile
    public static final String EMPLOYER_POST=home+"/employerpost";
    //update applicant profile
    public static final String updateprofile =home+"/update";
    //update employer profile
    public static final String updateemployer=home+"/updateemployerprofile";
    //recommended applicants
    public static final String recommendedapplicants=home+"/recommendedapplicants";
    public static final String intenttoapprovejobpost=home+"/intenttoapprovejobpost";
    //recommended job
    public static final String recommendedjobpost=home+"/recommendedjobpost";
    //validate recommended applicants
    public static final String checkrecommendedapplicants=home+"/checkrecommendedapplicants";
    //applicant history applicationhistory
    public static final String applicationhistory=home+"/applicationhistory";
    //public static final String joblogo="https://peso-ojfs.000webhostapp.com";
}
