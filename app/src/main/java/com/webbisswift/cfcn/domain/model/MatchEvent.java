package com.webbisswift.cfcn.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by apple on 12/5/17.
 */

public class MatchEvent implements Parcelable {
    public String away, home, minute, type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.away);
        dest.writeString(this.home);
        dest.writeString(this.minute);
        dest.writeString(this.type);
    }

    public MatchEvent() {
    }

    protected MatchEvent(Parcel in) {
        this.away = in.readString();
        this.home = in.readString();
        this.minute = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<MatchEvent> CREATOR = new Parcelable.Creator<MatchEvent>() {
        @Override
        public MatchEvent createFromParcel(Parcel source) {
            return new MatchEvent(source);
        }

        @Override
        public MatchEvent[] newArray(int size) {
            return new MatchEvent[size];
        }
    };
}
