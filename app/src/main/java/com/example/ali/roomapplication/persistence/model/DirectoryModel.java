package com.example.ali.roomapplication.persistence.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by ali.azaz on 11/22/17.
 */

@Entity(tableName = "directory")
public class DirectoryModel {

    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String contactNo;

    @ColumnInfo
    public String name;
    public String email;

    public DirectoryModel(String name, String email, String contactNo) {
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
    }


    @Ignore /*Use Ignore here cause compiler couldn't recognizing which constructor it'll have to use during compiling */
    public DirectoryModel(DirectoryModel dm) {
        this._id = dm.get_id();
        this.name = dm.getName();
        this.email = dm.getEmail();
        this.contactNo = dm.getContactNo();
    }

    @Ignore /*Use Ignore here cause compiler couldn't recognizing which constructor it'll have to use during compiling */
    public DirectoryModel(int _id, String name, String email, String contactNo) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }
}
