package com.ahmeddebbech.aries_messenger.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.auth.FirebaseUser;

public class LoggedInUser implements Parcelable {
    private FirebaseUser userRef;

    protected LoggedInUser(Parcel in) {
        userRef = in.readParcelable(FirebaseUser.class.getClassLoader());
        usr = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<LoggedInUser> CREATOR = new Creator<LoggedInUser>() {
        @Override
        public LoggedInUser createFromParcel(Parcel in) {
            return new LoggedInUser(in);
        }

        @Override
        public LoggedInUser[] newArray(int size) {
            return new LoggedInUser[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userRef, flags);
        dest.writeParcelable(usr, flags);
    }

    public static class User implements Parcelable {
        private String uid;
        private String username;
        private String displayName;
        private String email;
        public User(){

        }
        public User(String uid, String username, String displayName, String email){
            this.uid = uid;
            this.username = username;
            this.displayName = displayName;
            this.email = email;
        }

        protected User(Parcel in) {
            uid = in.readString();
            username = in.readString();
            displayName = in.readString();
            email = in.readString();
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

        public String getEmail(){
            return email;
        }
        public String getUid(){
            return uid;
        }
        public String getUsername(){
            return username;
        }
        public String getDisplayName(){
            return displayName;
        }
        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }
        public void setUsername(String username){
            this.username = username;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setUid(String uid){
            this.uid = uid;
        }
        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(uid);
            dest.writeString(username);
            dest.writeString(displayName);
            dest.writeString(email);
        }
    }
    private User usr;
    public LoggedInUser(FirebaseUser user){
        userRef = user;
        usr = new User(userRef.getUid(),"@xxxx",userRef.getDisplayName(),userRef.getEmail());
    }
    public FirebaseUser getFirebaseUserObject(){
        return userRef;
    }

    public User getUsr() {
        return usr;
    }
}
