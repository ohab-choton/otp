package com.example.oppt;

public class AddData {

    private  String name,fname,mname,nid,add;

    public  AddData()
    {


    }

    public AddData(String name, String fname, String mname, String nid, String add) {
        this.name = name;
        this.fname = fname;
        this.mname = mname;
        this.nid = nid;
        this.add = add;


    }

    public String getName() {

        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }
    public void setMname(String mname) {
        this.mname = mname;
    }


    public String getNid() {
        return nid;
    }
    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getAdd() {
        return add;
    }
    public void setAdd(String add) {
        this.add = add;
    }











}
