package com.example.sushma.shodatproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sushma on 7/8/16.
 */
public class detailObject implements Parcelable {
    int albumId;
    int ID;
    String title;
    String url;
    String thumbnailURL;

    public detailObject(int albumId,
            int ID,
            String title,
            String url,
            String thumbnailURL) {

        this.albumId = albumId;
        this.ID = ID;
        this.title = title;
        this.url = url;
        this.thumbnailURL = thumbnailURL;
    }

    protected detailObject(Parcel in) {
        albumId = in.readInt();
        ID = in.readInt();
        title = in.readString();
        url = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<detailObject> CREATOR = new Creator<detailObject>() {
        @Override
        public detailObject createFromParcel(Parcel in) {
            return new detailObject(in);
        }

        @Override
        public detailObject[] newArray(int size) {
            return new detailObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(albumId);
        dest.writeInt(ID);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(thumbnailURL);
    }
}
