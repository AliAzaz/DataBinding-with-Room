package com.example.ali.roomapplication.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
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
    String selectSpecificDirectory = "Select * from directory where email =:email";
    String checkContact = "Select CAST(COUNT(*) AS BIT) from directory where email =:email";

    @Query(selectAllDirectory)
    List<DirectoryModel> getAllData();

    @Query(selectSpecificDirectory)
    DirectoryModel getSpecificData(String email);

    @Query(checkContact)
    Boolean checkContactExist(String email);

    @Insert
    void insertNewDirectory(DirectoryModel directory);

    @Update
    void updateNewDirectory(DirectoryModel directory);

    @Delete
    void deleteDirectory(DirectoryModel directory);

}
