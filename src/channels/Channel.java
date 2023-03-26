package channels;

import users.User;

import java.util.ArrayList;

public class Channel {
    private String channelName;
    private String meetingDayAndTime;


    public Channel(String channelName, String meetingDayAndTime) {
        this.channelName = channelName;
        this.meetingDayAndTime = meetingDayAndTime;
    }

    public Channel(String meetingDayAndTime) {
        this.meetingDayAndTime = meetingDayAndTime;
    }

    public Channel() {

    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getMeetingDayAndTime() {
        return meetingDayAndTime;
    }

    public void setMeetingDayAndTime(String meetingDayAndTime) {
        this.meetingDayAndTime = meetingDayAndTime;
    }

    public void aToString() {
        if(!meetingDayAndTime.equals("") && meetingDayAndTime!=null){
            System.out.println("Channel name is: " +channelName +" Meeting time is: "+meetingDayAndTime);
        }
        else{
            System.out.println("Channel name is: " +channelName +"\nMeeting time is: Not Assigned");
        }

    }
}

