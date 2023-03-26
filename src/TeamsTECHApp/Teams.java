package TeamsTECHApp;


import channels.Channel;
import channels.PrivateChannel;
import users.Academician;
import users.User;

import java.util.ArrayList;

public class Teams {
    private String teamID;
    private String teamName;
    private ArrayList<Channel> channels;
    private ArrayList<Academician> owners;


    public Teams(String teamName, String teamID, ArrayList<Channel> channels, ArrayList<Academician> owners) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.channels = channels;
        this.owners = owners;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ArrayList<Academician> getOwners() {
        return owners;
    }

    public void setOwners(ArrayList<Academician> owners) {
        this.owners = owners;
    }

    @Override
    public String toString() {
        for (Channel c : channels
        ) {
            if (c instanceof PrivateChannel) {
                ((PrivateChannel) c).privateToString();
//                System.out.println("Channel Name:"+c.getChannelName()+"(Private Channel)"+"Meeting Day and Time:"+c.getMeetingDayAndTime());
//                ((PrivateChannel) c).participantToString();
            } else {
                String meetingTime = c.getMeetingDayAndTime();
                if (meetingTime == null || meetingTime =="") {
                    meetingTime="Not Assigned";
                }
                System.out.println("Channel Name:" + c.getChannelName() + " Meeting Day and Time:" +meetingTime);
            }
        }
        return "";
    }
}
