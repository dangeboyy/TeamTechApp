package ıo;


import TeamsTECHApp.Teams;
import channels.Channel;
import channels.PrivateChannel;
import users.Student;
import users.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileIO {
    public ArrayList<String[]> readFile(String fileName) {
        BufferedReader objReader;
        ArrayList<String[]> arrayList = new ArrayList<>();
        try {
            String strCurrentLine;
            objReader = new BufferedReader(new FileReader(fileName));
            objReader.readLine();
            while ((strCurrentLine = objReader.readLine()) != null) {
                String lines[] = strCurrentLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);//found from web to read teamList.csv file
                arrayList.add(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private String line, fileLine;

    public void writeTeamsFile(ArrayList<Teams> teams) {
        String indexValues = "Team Name,Team ID,Default Channel,Default Meeting Day and Time,Meeting Channel,Meeting Day and Time,Participant ID\n";
        try {
            FileWriter fw = new FileWriter("teamList.csv");
            fw.write(indexValues);
            for (Teams teams1 : teams) {
                fw.write(getLine(teams1));
            }

            fw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void writeUserList(ArrayList<User> users) {
        try {
            FileWriter fw = new FileWriter("userList.csv");
            fileLine = "User Type,User Name,User ID,Email,Password,Team ID,\n";
            fw.write(fileLine);
            for (int i = 0; i < users.size(); i++) {
                User user = users.get(i);
                fileLine = getLineUser(user);
                fw.write(fileLine);
                if (i + 1 != users.size()) {
                    fw.write("\n");
                }
            }
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private String getLineUser(User user) {
        String line = user.getUserType() + "," + user.getUserName() + "," + user.getUserID() + "," + user.geteMail() + "," + user.getPassword() + "," + getTeamIDs(user);
        return line;
    }

    private String getTeamIDs(User user) {
        String teamIds = "";
        for (String id : user.getTeamID()) {
            teamIds = teamIds + id + ",";

        }
        return teamIds;
    }

    private String getLine(Teams team) {
        String meetingDayAndTime = team.getChannels().get(0).getMeetingDayAndTime();
        if (meetingDayAndTime == null) {
            meetingDayAndTime = "";
        }
        String line = team.getTeamName() + "," +
                team.getTeamID() + "," + team.getChannels().get(0).getChannelName() +
                "," + meetingDayAndTime + "," + getChannels(team) +
                "," + getMeetingTime(team) + "," + getParticipantId(team) + "\n";
        return line;

    }

    private String getChannels(Teams team) {
        String allChannels ="";
        if(team.getChannels().size()<=1){
            return allChannels;
        }
        for (int i = 1; i < team.getChannels().size(); i++) {
            String channelName=team.getChannels().get(i).getChannelName();
            if(channelName==null){
                channelName="";
            }

            String concat = allChannels + channelName + "-";
            allChannels = concat;
        }
        allChannels = removeLastChar(allChannels);
        return allChannels;
    }

    private String getMeetingTime(Teams team) {
        String allMeetingTime ="";
        if(team.getChannels().size()<=1){
            return allMeetingTime;
        }
        for (int i = 1; i < team.getChannels().size(); i++) {
            String meetingTime=team.getChannels().get(i).getMeetingDayAndTime();
            if(meetingTime==null){
                meetingTime="";
            }
            String concat = allMeetingTime + meetingTime + "-";
            allMeetingTime = concat;
        }
        allMeetingTime = removeLastChar(allMeetingTime);
        return allMeetingTime;
    }

    private String getParticipantId(Teams team) {
        String participantId ="";
        if(team.getChannels().size()<=1){
            return participantId;
        }
        for (int i = 1; i < team.getChannels().size(); i++) {
            PrivateChannel privateChannel = (PrivateChannel) team.getChannels().get(i);
            String concat = participantId + "\"" + getParticipantAsStringLine(privateChannel) + "\"-";
            participantId = concat;
        }
        participantId = removeLastChar(participantId);
        return participantId;
    }

    private String getParticipantAsStringLine(PrivateChannel privateChannel) {
        String StringParticipantLine ="";
        for (int i = 0; i < privateChannel.getParticipants().size(); i++) {
            String participantId=privateChannel.getParticipants().get(i).getUserID();
            if(participantId==null){
                participantId="";
            }
            String concat = StringParticipantLine + participantId + ",";
            StringParticipantLine = concat;
        }
        StringParticipantLine = removeLastChar(StringParticipantLine);
        return StringParticipantLine;
    }

    private static String removeLastChar(String s) {//remove string of last index of character -
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }
}
