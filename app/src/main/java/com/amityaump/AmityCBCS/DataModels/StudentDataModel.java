package com.amityaump.AmityCBCS.DataModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentDataModel {

  @SerializedName("enroll_name")
  @Expose
  private String enrollName;
  @SerializedName("school_name")
  @Expose
  private String schoolName;
  @SerializedName("course_name")
  @Expose
  private String courseName;
  @SerializedName("batch")
  @Expose
  private String batch;
  @SerializedName("std_name")
  @Expose
  private String stdName;
  @SerializedName("pass")
  @Expose
  private String pass;
  @SerializedName("minor_track_subject")
  @Expose
  private String minorTrackSubject;
  //@SerializedName("minor_track_subject")
  @SerializedName("branch_name")
  @Expose
  //private String minorTrackSubject;

  private String branchName;

  //@Expose


  public String getBranchName(){
  	return branchName;
  }


  public void setBranchName(String branchName){
  	this.branchName = branchName;
  }


  public String getEnrollName() {
    return enrollName;
  }

  public void setEnrollName(String enrollName) {
    this.enrollName = enrollName;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getBatch() {
    return batch;
  }

  public void setBatch(String batch) {
    this.batch = batch;
  }

  public String getStdName() {
    return stdName;
  }

  public void setStdName(String stdName) {
    this.stdName = stdName;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getMinorTrackSubject() {
    return minorTrackSubject;
  }

  public void setMinorTrackSubject(String minorTrackSubject) {
    this.minorTrackSubject = minorTrackSubject;
  }

}