package com.clickygame.db;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by prashant.patel on 7/19/2017.
 */

public class DMainLocationModel extends RealmObject {
    @PrimaryKey
    public long id;
    public String name;
    public RealmList<DLocationModel> ralmListDLocationModel; // Declare one-to-many relationships

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<DLocationModel> getRalmListDLocationModel() {
        return ralmListDLocationModel;
    }

    public void setRalmListDLocationModel(RealmList<DLocationModel> ralmListDLocationModel) {
        this.ralmListDLocationModel = ralmListDLocationModel;
    }
}