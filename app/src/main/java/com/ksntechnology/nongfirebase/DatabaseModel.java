package com.ksntechnology.nongfirebase;

import android.os.Parcel;
import android.os.Parcelable;

public class DatabaseModel implements Parcelable{
    private String dateString;
    private String messageString;

    public DatabaseModel() {
        //Constructor Getter
    }

    public DatabaseModel(String dateString,
                         String messageString) {
        this.dateString = dateString;
        this.messageString = messageString;
    }   //Constructor Setter

    protected DatabaseModel(Parcel in) {
        dateString = in.readString();
        messageString = in.readString();
    }

    public static final Creator<DatabaseModel> CREATOR = new Creator<DatabaseModel>() {
        @Override
        public DatabaseModel createFromParcel(Parcel in) {
            return new DatabaseModel(in);
        }

        @Override
        public DatabaseModel[] newArray(int size) {
            return new DatabaseModel[size];
        }
    };

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dateString);
        parcel.writeString(messageString);
    }

}
