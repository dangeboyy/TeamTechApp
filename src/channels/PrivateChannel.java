package channels;

import users.User;

import java.util.ArrayList;

public class PrivateChannel extends Channel {

    private ArrayList<User> participants;

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }

    public PrivateChannel(String channelName, String meetingDayAndTime, ArrayList<User> participants) {
        super(channelName, meetingDayAndTime);
        this.participants=participants;
    }
    public void privateToString() {
        aToString();
        participantToString();
    }
    public void participantToString(){
        System.out.println("Participants:");
        for (User user:participants) {
            System.out.println("Name:"+user.getUserName()+" User Id:"+user.getUserID());
        }
    }
}
