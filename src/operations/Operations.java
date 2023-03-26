package operations;

import TeamsTECHApp.Teams;
import channels.Channel;
import channels.DefaultChannel;
import channels.PrivateChannel;
import exception.UnauthorizedUserOperationException;
import users.Academician;
import users.Instructor;
import users.User;

import java.util.ArrayList;

public class Operations {
    private void addTeam(ArrayList<Teams> teams, Teams team){
        if (isTeamExist(teams,team.getTeamID())==null){
            teams.add(team);
            System.out.println("New team "+team.getTeamName()+" is added");
        }else{
            System.out.println("Team could not be added");
        }
    }
    private Teams isTeamExist(ArrayList<Teams> teams,String teamID){
        for (Teams team:teams) {
            if (team.getTeamID().equals(teamID)){
                System.out.println(team.getTeamName()+" found");
                return team;
            }


        }
        System.out.println("Team is not found");
        return null;
    }
    private Channel isChannelExist(ArrayList<Teams> teams, String channelName){
        for (Teams team:teams) {
            for (Channel channel:team.getChannels()) {
                if (channel.getChannelName().equals(channelName)){
                    System.out.println(channel.getChannelName()+" channel found");
                    return channel;
                }

            }
        }return null;
    }
    public void removeChannel(Teams team,Channel channel){
        if ((channel!=null) && !(channel.getChannelName().equals("General"))){
            team.getChannels().remove(channel);
            System.out.println(channel.getChannelName()+" is deleted from "+ team.getTeamID());
        }
    }
    private void addChannel(ArrayList<Teams> teams,Teams team,Channel channel){
        if (isChannelExist(teams, channel.getChannelName())==null){
            team.getChannels().add(channel);
            System.out.println(channel.getChannelName()+" added in "+team.getTeamName());
        }else{
            System.out.println("Channel could not be added");
        }
    }
    private Teams findTeam(ArrayList<Teams> teams,Channel channel){ //returns the team that we want to remove channel in it
        for (Teams team:teams){
            for (Channel channel1:team.getChannels()) {
                if (channel1.equals(channel)){
                    return team;
                }
            }
            ;
        }
        System.out.println("This team not have this channel");
        return null;
    }
    public Channel createChannel(ArrayList<Teams> teams,Teams team,String channelName,String meetingDayAndTime,User user,ArrayList<User> users){
        ArrayList<User> members = getMembers(team,users);
        ArrayList<User> userParticipant = new ArrayList<>();
        userParticipant.add(user);
        if (team != null) {
            if (members.contains(user)) {
                Channel channel = new PrivateChannel(channelName, meetingDayAndTime, userParticipant);
                addChannel(teams, team, channel);
                return channel;
            }
            else {
                System.out.println("You can not create channel because of you are not a member of this team");
            }
        }
        return null;
    }
    public Teams createTeam(ArrayList<Teams> teams, String teamName, String teamID, User user){
        if (user instanceof Instructor){
            ArrayList<Academician> owners=new ArrayList<>();
            owners.add((Academician) user);
            ArrayList<Channel> channels = new ArrayList<>();
            DefaultChannel defaultChannel = new DefaultChannel();
            channels.add(defaultChannel);
            Teams team = new Teams(teamName,teamID,channels,owners);
            addTeam(teams,team);
            user.getTeamID().add(teamID);
            return team;
        }
        return null;
    }
    public void removeTeam(ArrayList<Teams> teams,String teamID,User user) {
        Teams deleteTeam=isTeamExist(teams,teamID);
        if (deleteTeam!=null) {
            if (deleteTeam.getOwners().contains(user)) {
                teams.remove(deleteTeam);
                System.out.println(deleteTeam.getTeamName() + " is deleted");
            }else{
                try {
                    throw new UnauthorizedUserOperationException("You are not authorized to remove a team");
                } catch (UnauthorizedUserOperationException e) {
                    e.getMessage();
                }
            }
        }
    }
    private int getChannelIndex(Teams team,Channel channel){
        for (int i = 0; i < team.getChannels().size(); i++) {
            if (team.getChannels().get(i).equals(channel)){
                return i;
            }
        }
        System.out.println("Channel Not Found");
        return -1;
    }
    public void addParticipant(Teams team,User participant,User user,PrivateChannel privateChannel){

        if (privateChannel instanceof PrivateChannel){
            if(team.getOwners().contains(user)){
                if(!privateChannel.getParticipants().contains(participant)){
                    privateChannel.getParticipants().add(participant);
                    System.out.println(participant.getUserName()+" is added in "+privateChannel.getChannelName());
                }else {
                    System.out.println(participant.getUserName()+" is already exist in"+privateChannel.getChannelName());
                }
            }

        }else{
            System.out.println("Channel should be private");
        }
    }
    public User findUserFromEmail(ArrayList<User> users,String email){
        for (User user:users) {
            if (user.geteMail().equals(email)){
                return user;
            }
        }
        System.out.println(email + "not found");
        return null;
    }
    public boolean isAuthorizedForAddingATeam(User user){
        return (user instanceof Instructor);
    }
    public void removeParticipant(Teams team,User participant,User user,PrivateChannel privateChannel){
        if (privateChannel instanceof PrivateChannel){
            if(team.getOwners().contains(user)){
                if(privateChannel.getParticipants().contains(participant)){
                    privateChannel.getParticipants().remove(participant);
                    System.out.println(participant.getUserName()+" is removed from "+privateChannel.getChannelName());
                }else {
                    System.out.println(participant.getUserName()+" is not exist in"+privateChannel.getChannelName());
                }
            }

        }else{
            System.out.println("Channel should be private");
        }
    }
    public void updateMeetingTime(ArrayList<Teams> teams, String channelName, String meetingTime){
        Channel channel = isChannelExist(teams, channelName);
        if (channel!=null){
            Teams team = findTeam(teams,channel);
            int indexOfChannel = getChannelIndex(team,channel);
            if (indexOfChannel>=0){
                team.getChannels().get(indexOfChannel).setMeetingDayAndTime(meetingTime);
            }
        }
    }
    public void displayChannelsInformation(Teams team){
        int i=1;
        System.out.println("CHANNELS:");
        for (Channel ch:team.getChannels()) {
            if(ch instanceof PrivateChannel){
                System.out.print(i+")");
                ((PrivateChannel) ch).privateToString();
                i++;
            }
        }
    }
    public void displayTeams(ArrayList<Teams> teams){
        int i = 1;
        System.out.println("TEAMS");
        for (Teams team:teams) {
            System.out.println(i + " " + team.getTeamName() + " " + team.getTeamID());
            i++;
        }
    }
    public void addMemberToATeam(Teams team ,User user, User member, ArrayList<User> users) {
        ArrayList<User> members = getMembers(team, users);
        if (isAuthorizedForAddOrRemoveAMember(user)) {
            if (member != null) {
                if (!members.contains(member)) {
                    member.getTeamID().add(team.getTeamID());
                    System.out.println(member.getUserName()+" is added to "+team.getTeamID());
                } else {
                    System.out.println(member.getUserName() + " is already a member of " + team.getTeamID());
                }
            }
        }
    }

    public void removeMemberFromATeam(Teams team ,User user, User member, ArrayList<User> users) {
        ArrayList<User> members = getMembers(team, users);
        if (isAuthorizedForAddOrRemoveAMember(user)) {
            if (member != null) {
                if (members.contains(member)) {
                    member.getTeamID().remove(team.getTeamID());
                } else {
                    System.out.println(member.getUserName() + " is not a member of " + team.getTeamID());
                }
            }
        }
    }
    public ArrayList<User> getMembers(Teams team, ArrayList<User> users) {

        ArrayList<User> members = new ArrayList<>();
        for (User user : users) {
            if (user.getTeamID().contains(team.getTeamID())) {
                members.add(user);
            }
        }
        return members;
    }
    public boolean isAuthorizedForAddOrRemoveAMember(User user) {
        return (user instanceof Academician);
    }
    public void displayUsers(ArrayList<User> users){
        int i = 1;
        for (User user:users) {
            System.out.print(i+")");
            System.out.println(user.getUserName() + user.getUserID());
            i++;
        }
    }
    public void displayMembers(ArrayList<User> members){
        int i = 1;
        for (User user :members) {
            System.out.println(i++ +")"+ user.getUserName()+ " " + user.getUserID());

        }
    }
    public boolean isOwner(Teams team,User user){
        return team.getOwners().contains(user);
    }
    public void giveOwnerToAcademician(User user,Teams team, User owner) {
        if ((user instanceof Academician) && (owner instanceof Academician)) { //controls if both of them are acedemicians
            if (team.getOwners().contains(user)) {                             //because Students are can not be an owner
                if (owner.getTeamID().contains(team.getTeamID())) { //checking if user is member of the team
                    if (!team.getOwners().contains(owner)) {        //checking if user is already a owner
                        team.getOwners().add((Academician) owner);
                        System.out.println( owner.getUserName() + " is an owner now");
                    } else {
                        System.out.println(owner.getUserName() + "is already a owner");
                    }
                }else{
                    System.out.println(owner.getUserName() + " is not a member of " + team.getTeamID());
                }
            }
        }else {
            System.out.println(owner.getUserName()+" is not a academician so he/she can not be a owner for this team");
        }

    }
    public void monitoringTeamsInformation(Teams team){
        System.out.println(team.getTeamName()+" "+ team.getTeamID());
        team.toString();
    }
    public void countUserTypes(Teams team, ArrayList<User> users){
        int student=0;
        int teachingAssistant=0;
        int instructor=0;
        ArrayList<User> members = getMembers(team, users);
        for (User user:members) {
            if (user.getUserType().equals("Student")){
                student++;
            }else if (user.getUserType().equals("Teaching Assistant")){
                teachingAssistant++;
            }else if (user.getUserType().equals("Instructor")){
                instructor++;
            }
        }
        System.out.println("There are "+ student +" Students ," + teachingAssistant + " Teaching Assistant," + instructor + " Instructors in the " + team.getTeamID());
    }
}
