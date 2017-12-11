package com.example.ali.roomapplication.core;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.ali.roomapplication.persistence.core.dbClass;

/**
 * Created by ali.azaz on 11/23/17.
 */

public class DatabaseHelper {

    dbClass roomDB;

    public DatabaseHelper(Context context){

        //        ROOM roomDB connection
        roomDB = Room.databaseBuilder(context,dbClass.class,"contact-directory-roomDB").build();

        /* If you modify the database after the installation of App then we've to use MIGRATION
        Refer: https://developer.android.com/training/data-storage/room/migrating-db-versions.html */

        /*dbClass roomDB = Room.databaseBuilder(context,dbClass.class,"contact-directory-roomDB")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3).build();*/
    }

    public dbClass ReturnDB(){
        return roomDB;
    }

/*    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Fruit` (`id` INTEGER, "
                    + "`name` TEXT, PRIMARY KEY(`id`))");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Book "
                    + " ADD COLUMN pub_year INTEGER");
        }
    };*/
}


