package com.example.wholesalewale;public class

showlike {

    public showlike(com.example.wholesalewale.uploadDetails uploadDetails, String sname) {
        this.uploadDetails = uploadDetails;
        this.sname = sname;
    }
    public showlike(com.example.wholesalewale.uploadDetails uploadDetails, String sname,Integer qnt) {
        this.uploadDetails = uploadDetails;
        this.sname = sname;
        this.qnt=qnt;
    }
    uploadDetails uploadDetails;

    public Integer getQnt() {
        return qnt;
    }

    public com.example.wholesalewale.uploadDetails getUploadDetails() {
        return uploadDetails;
    }

    public String getSname() {
        return sname;
    }
    Integer qnt;
    String sname;

}
