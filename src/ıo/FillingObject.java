package ıo;

import TeamsTECHApp.Teams;
import channels.Channel;
import channels.DefaultChannel;
import channels.PrivateChannel;
import generator.PasswordGenerator;
import generator.idGenerator;
import users.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class FillingObject {
    idGenerator idGeneratorgenerator = new idGenerator();
    PasswordGenerator passwordGenerator=new PasswordGenerator();
    FileIO fileIO = new FileIO();
    ArrayList<String[]> userList = fileIO.readFile("userList.csv");
    int[] usableIdList = idGeneratorgenerator.generateId();
    ArrayList<String[]> teamList=fileIO.readFile("teamList.csv");


    public void fillingUsersObject(ArrayList<User> users,ArrayList<Student> students,ArrayList<User> instructors,ArrayList<TeachingAssintant> teachingAssintants){
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i)[0].equals("Student")) {
                if (!userList.get(i)[2].equals("") && !userList.get(i)[4].equals("")) {
                    Student student = new Student(userList.get(i)[0], userList.get(i)[1], userList.get(i)[2], userList.get(i)[3], userList.get(i)[4], getTeamId(userList.get(i)));
                    users.add(student);
                    students.add(student);
                }
                else {
                    String password=passwordGenerator.generatePassword();
                    Random random = new Random();
                    while (true) {
                        int id = random.nextInt(1000);
                        if (usableIdList[id] != 0) {
                            String assId =String.valueOf(usableIdList[id]);
                            Student student = new Student(userList.get(i)[0], userList.get(i)[1], assId, userList.get(i)[3], password, getTeamId(userList.get(i)));
                            users.add(student);
                            students.add(student);
                            usableIdList[id] = 0;
                            break;
                        }
                    }
                }

            } else if (userList.get(i)[0].equals("Instructor")) {
                if (!userList.get(i)[2].equals("") && !userList.get(i)[4].equals("")) {
                    Instructor instructor = new Instructor((userList.get(i)[0]), userList.get(i)[1], userList.get(i)[2], userList.get(i)[3], userList.get(i)[4], getTeamId(userList.get(i)));
                    users.add(instructor);
                    instructors.add(instructor);
                }
                else {
                    String password=passwordGenerator.generatePassword();
                    Random random = new Random();
                    while (true) {
                        int id = random.nextInt(1000);
                        if (usableIdList[id] != 0) {
                            String assId =String.valueOf(usableIdList[id]);
                            Instructor instructor = new Instructor((userList.get(i)[0]), userList.get(i)[1], assId, userList.get(i)[3], password,getTeamId(userList.get(i)));
                            users.add(instructor);
                            instructors.add(instructor);
                            usableIdList[id] = 0;
                            break;

                        }
                    }
                }
            }
            else {
                if (!userList.get(i)[2].equals("") && !userList.get(i)[4].equals("")) {
                    TeachingAssintant teachingAssintant = new TeachingAssintant((userList.get(i)[0]), userList.get(i)[1], userList.get(i)[2], userList.get(i)[3], userList.get(i)[4], getTeamId(userList.get(i)));
                    users.add(teachingAssintant);
                    teachingAssintants.add(teachingAssintant);
                }else {
                    String password=passwordGenerator.generatePassword();
                    Random random = new Random();
                    while (true) {
                        int id = random.nextInt(1000);
                        if (usableIdList[id] != 0) {
                            String assId =String.valueOf(usableIdList[id]);
                            TeachingAssintant teachingAssintant = new TeachingAssintant((userList.get(i)[0]), userList.get(i)[1], assId, userList.get(i)[3], password, getTeamId(userList.get(i)));
                            users.add(teachingAssintant);
                            teachingAssintants.add(teachingAssintant);
                            usableIdList[id] = 0;
                            break;

                        }
                    }

                }
            }
        }

    }
    private ArrayList<String> getTeamId(String[] line){
        ArrayList<String> teamIds=new ArrayList<>();
            for(int i=5;i<line.length;i++){//i starts from 5 because teamIds starts from index 5
                if(line[i]!=""){
                    teamIds.add(line[i]);
                }

            }
        return teamIds;
    }
    //"98,45"-"68,604"-"68"
    private ArrayList<String[]> getParticipants(String[] line){
        ArrayList<String> participantIds=new ArrayList<>();
        ArrayList<String[]> participantsWithComma=new ArrayList<>();
        String[] participantLines=line[6].split("-",-1);
        for(int i=0;i<participantLines.length;i++){
            participantIds.add(participantLines[i]);
        }
        participantsWithComma = getParticipantsHelper(participantIds);
        return participantsWithComma;
    }
    private ArrayList<String[]> getParticipantsHelper(ArrayList<String> participantIds){
        ArrayList<String[]> participantsWithComma=new ArrayList<>();
        for (String participant:participantIds//participantIds=("43,21","23,47",....)
             ) {
            String replacePart=participant.replaceAll("\"","");
            participantsWithComma.add(replacePart.split(","));//
        }
        return participantsWithComma;//([43,21],[23,47],.....)
    }

    private User getUserForTeamOwner(String teamID , ArrayList<User> users){
        for (User user: users) {
            for(int i=0;i<user.getTeamID().size();i++){
                if (user.getTeamID().get(i).equals(teamID)) {
                    return user;
                }
            }
        }
        return null;
    }
    private User getUserForParticipant(String userID , ArrayList<User> users){
        for (User user: users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }


    public void fillingTeamObject(ArrayList<Teams> teams,ArrayList<User> instructors,ArrayList<User> users){

        for (String[] s:teamList
        ) {
            int i=0;
            ArrayList<Channel> channels=new ArrayList<>();
            ArrayList<String[]> participants;

            DefaultChannel defaultChannel=new DefaultChannel(s[3]);
            channels.add(defaultChannel);

            participants=getParticipants(s);

            ArrayList<String> privateChannels=getPrivateChannelArray(s);

            ArrayList<String> meetingTimes=getMeetingTimes(s);

            for (String[] part:participants
            ) {
                ArrayList<User> participant=new ArrayList<>();
                for (String partId : part
                ) {
                    String trimtedPartId=partId.trim();
                    participant.add(getUserForParticipant(trimtedPartId, users));
                }
                if(!s[4].equals("")){
                    PrivateChannel privateChannel=new PrivateChannel(privateChannels.get(i),meetingTimes.get(i),participant);//s[4] for private channels s[5] for meetingTime
                    channels.add(privateChannel);

                }
            }
            i++;
            ArrayList<Academician> owner=new ArrayList<>();
            User ownerr=getUserForTeamOwner(s[1],instructors);
            owner.add((Instructor) ownerr);
            Teams teams1=new Teams(s[0],s[1],channels,owner);
            teams.add(teams1);
        }
    }

    private ArrayList<String> getPrivateChannelArray(String[] line){
        ArrayList<String> privateChannels=new ArrayList<>();
        String[] privateChannelLines=line[4].split("-");
        for(int i=0;i<privateChannelLines.length;i++){
            privateChannels.add(privateChannelLines[i]);
        }
        return privateChannels;
    }
    private ArrayList<String> getMeetingTimes(String[] line){
        ArrayList<String> meetingTimes=new ArrayList<>();
        String[] meetingTimeLines=line[5].split("-");
        for(int i=0;i<meetingTimeLines.length;i++){
            meetingTimes.add(meetingTimeLines[i]);
        }
        return meetingTimes;
    }
}
