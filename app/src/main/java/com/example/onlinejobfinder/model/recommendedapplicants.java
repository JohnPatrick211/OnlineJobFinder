package com.example.onlinejobfinder.model;

public class recommendedapplicants {
    public recommendedapplicants() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getApplicant_id() {
        return applicant_id;
    }

    public void setApplicant_id(String applicant_id) {
        this.applicant_id = applicant_id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getWorkexp_id() {
        return workexp_id;
    }

    public void setWorkexp_id(String workexp_id) {
        this.workexp_id = workexp_id;
    }

    public String getEducational_id() {
        return educational_id;
    }

    public void setEducational_id(String educational_id) {
        this.educational_id = educational_id;
    }

    public recommendedapplicants(String id, String name, String email, String applicant_id, String specialization, String profile_pic, String contactno, String address, String gender, String resume, String priority, String created_at, String updated_at, String workexp_id, String educational_id) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.applicant_id = applicant_id;
        this.specialization = specialization;
        this.profile_pic = profile_pic;
        this.contactno = contactno;
        this.address = address;
        this.gender = gender;
        this.resume = resume;
        this.priority = priority;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.workexp_id = workexp_id;
        this.educational_id = educational_id;
    }

    private String id;
    private String name;
    private String email;
    private String applicant_id;
    private String specialization;
    private String profile_pic;
    private String contactno;
    private String address;
    private String gender;
    private String resume;
    private String priority;
    private String created_at;
    private String updated_at;
    private String workexp_id;
    private String educational_id;
}
