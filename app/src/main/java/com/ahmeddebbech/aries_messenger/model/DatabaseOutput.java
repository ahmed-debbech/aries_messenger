package com.ahmeddebbech.aries_messenger.model;

import com.ahmeddebbech.aries_messenger.database.DatabaseOutputKeys;

public class DatabaseOutput {
    private int databaseOutputkey;
    private Object obj;

    public DatabaseOutput(int key, Object obj){
        this.databaseOutputkey = key;
        this.obj = obj;
    }
    public int getDatabaseOutputkey() {
        return databaseOutputkey;
    }

    public void setDatabaseOutputkey(int databaseOutputkey) {
        databaseOutputkey = databaseOutputkey;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
