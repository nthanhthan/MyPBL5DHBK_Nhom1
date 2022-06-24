package com.example.mypbl5dhbk.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

public class User implements Parcelable {
    private String time;
    private String name;
    private String avatar;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User(){
        avatar="";

    }

    protected User(Parcel in) {
        time = in.readString();
        name = in.readString();
        avatar = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getTime() {
        return time;
    }

    public void setTime(String id) {
        this.time = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public User(String id, String name, String avatar) {
        this.time = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(time);
        parcel.writeString(name);
        parcel.writeString(avatar);

    }
}
