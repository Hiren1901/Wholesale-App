package com.example.wholesalewale;

import java.io.Serializable;

public class

showlike  implements Serializable {
    String category;
    String key;

    public String getCategory() {
        return category;
    }

    public String getKey() {
        return key;
    }

    public showlike(com.example.wholesalewale.uploadDetails uploadDetails, String sname, String category, String key) {
        this.uploadDetails = uploadDetails;
        this.sname = sname;
        this.category=category;
        this.key=key;
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
