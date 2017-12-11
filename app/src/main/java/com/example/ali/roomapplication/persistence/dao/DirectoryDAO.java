package com.example.ali.roomapplication.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.ali.roomapplication.persistence.model.DirectoryModel;

import java.util.List;

/**
 * Created by ali.azaz on 11/22/17.
 */

@Dao
public interface DirectoryDAO {

    String selectAllDirectory = "Select * from directory ORDER BY _id ASC";
    String selectSpecificDirectory = "Select * from directory where _id =:id";

    @Query(selectAllDirectory)
    List<DirectoryModel> getAllData();

    @Query(selectSpecificDirectory)
    DirectoryModel getSpecificData(int id);

    @Insert
    void insertNewDirectory(DirectoryModel directory);

    @Update
    void updateNewDirectory(DirectoryModel directory);

}
