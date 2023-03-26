package program;

import TeamsTECHApp.Teams;
import channels.Channel;
import channels.PrivateChannel;
import exception.UnauthorizedUserOperationException;
import input.Input;
import menu.OperationsMenu;
import operations.Operations;
import sign.Sign;
import users.Student;
import users.TeachingAssintant;
import users.User;
import ıo.FileIO;
import ıo.FillingObject;


import java.util.ArrayList;

public class Program {
    public void Program() throws Exception {
        ArrayList<User> users = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<User> instructors = new ArrayList<>();
        ArrayList<TeachingAssintant> teachingAssintants = new ArrayList<>();
        ArrayList<Teams> teams = new ArrayList<>();
        OperationsMenu operationsMenu = new OperationsMenu();
        Sign sign = new Sign();
        Operations operations = new Operations();
        while (true) {
            FillingObject fillingObject = new FillingObject();
            fillingObject.fillingUsersObject(users, students, instructors, teachingAssintants);
            fillingObject.fillingTeamObject(teams, instructors, users);
            System.out.println(users.get(0).geteMail());
            System.out.println(users.get(0).getPassword());
            Input ınput = new Input();
            System.out.print("Please enter your e-mail:");
            String email = ınput.strInput();
            System.out.print("Please enter your password:");
            String password = ınput.strInput();
            User user = operations.findUserFromEmail(users, email);
            boolean signIn = sign.signIn(users, email, password);
            if (signIn) {
                while (true) {
                    operationsMenu.operationsMenu();
                    Input input = new Input();
                    String selectOp = input.strInput();
                    if (selectOp.equals("0")) {//For sign out
                        FileIO fileIO=new FileIO();
                        fileIO.writeTeamsFile(teams);
                        fileIO.writeUserList(users);
                        users.clear();
                        students.clear();
                        instructors.clear();
                        teachingAssintants.clear();
                        teams.clear();
                        return;
                    }
                    else if (selectOp.equals("1")) {//For adding team
                        if(operations.isAuthorizedForAddingATeam(user)){
                            System.out.println("Please enter a team name:");
                            String teamName = input.strInput();
                            System.out.println("Please enter a team id");
                            String teamId = input.strInput();
                            operations.createTeam(teams, teamName, teamId, user);
                        }else {
                            try {
                                throw new UnauthorizedUserOperationException("You are not authorized to add a team");
                            }catch (Exception ex){
                                ex.getMessage();

                            }
                        }
                    }
                    else if(selectOp.equals("2")){//For remove team
                        if (operations.isAuthorizedForAddingATeam(user)) {
                            operations.displayTeams(teams);
                            System.out.println("Please select the team you want to remove");
                            int selected = input.intInput();
                            operations.removeTeam(teams, teams.get(selected - 1).getTeamID(), user);
                        } else {
                            try {
                                throw new UnauthorizedUserOperationException("You are not authorized to remove a team");
                            } catch (Exception ex) {
                                ex.getMessage();

                            }
                        }

                    }
                    else if(selectOp.equals("3")){//For update a team
                        operationsMenu.updateOperationsMenu();
                        String uptadeSelection=input.strInput();
                        if(uptadeSelection.equals("1")){//Add participant
                            operations.displayTeams(teams);
                            System.out.println("Please select an team to add participant");
                            int selectTeam=input.intInput();
                            if(operations.isOwner(teams.get(selectTeam-1),user)){
                                operations.displayChannelsInformation(teams.get(selectTeam-1));
                                if(teams.get(selectTeam-1).getChannels().size()>1){
                                    System.out.print("Please select channel that you want to add participant in:");
                                    int channel=input.intInput();
                                    ArrayList<User> members=operations.getMembers(teams.get(selectTeam-1),users);
                                    operations.displayMembers(members);
                                    System.out.print("Please select a user that you want to add channel as participant:");
                                    int select=input.intInput();
                                    operations.addParticipant(teams.get(selectTeam-1),members.get(select-1),user,(PrivateChannel) teams.get(selectTeam-1).getChannels().get(channel));
                                }else {
                                    System.out.println("There is no private channel in this team");
                                }
                                }
                            else{
                                try {
                                    throw new UnauthorizedUserOperationException("You are not owner for this team so you can not add participant to team");
                                }catch (Exception ex){
                                    ex.getMessage();

                                }
                            }
                             }
                        else if(uptadeSelection.equals("2")){//Remove participant
                            operations.displayTeams(teams);
                            System.out.println("Please select an team to remove participant");
                            int selectTeam=input.intInput();
                            if(operations.isOwner(teams.get(selectTeam-1),user)){
                                if(teams.get(selectTeam-1).getChannels().size()>1){
                                    operations.displayChannelsInformation(teams.get(selectTeam-1));
                                    System.out.print("Please select channel that you want to remove participant in:");
                                    int channel=input.intInput();
                                    ArrayList<User> members=operations.getMembers(teams.get(selectTeam-1),users);
                                    operations.displayMembers(members);
                                    System.out.print("Please select participant from channel information above:");
                                    int select=input.intInput();
                                    operations.removeParticipant(teams.get(selectTeam-1),members.get(select-1),user,(PrivateChannel) teams.get(selectTeam-1).getChannels().get(channel));
                                }else {
                                    System.out.println("There is no private channel in this team");
                                }
                                }
                            else{
                                try {
                                    throw new UnauthorizedUserOperationException("You are not owner for this team so you can not remove participant from team");
                                }catch (Exception ex){
                                    ex.getMessage();
                                }
                            }
                        }
                        else if(uptadeSelection.equals("3")){//Update meeting day and time
                            operations.displayTeams(teams);
                            System.out.print("Please select a team to update meeting day and time: ");
                            String selected=input.strInput();
                            int selectedTeam=Integer.valueOf(selected);
                            operations.displayChannelsInformation(teams.get(selectedTeam-1));
                            System.out.print("Please select a channel to update meeting day and time:");
                            selected=input.strInput();
                            int selectedChannel=Integer.valueOf(selected);
                            System.out.println("Please enter meeting day:");
                            String meetingDay=input.strInput();
                            System.out.println("Please enter meeting time(HH:MM):");
                            String meetingTime=input.strInput();
                            System.out.println("Please enter time period(AM/PM):");
                            String meetingTimePeriod=input.strInput();
                            String meetingDayAndTime=meetingDay.concat(" "+meetingTime).concat(" "+meetingTimePeriod);
                            operations.updateMeetingTime(teams,teams.get(selectedTeam-1).getChannels().get(selectedChannel).getChannelName(),meetingDayAndTime);
                            System.out.println(meetingDayAndTime);

                        }
                        else if(uptadeSelection.equals("4")){//Add member to a team
                            if (operations.isAuthorizedForAddingATeam(user)) {
                                operations.displayTeams(teams);
                                System.out.println("Please select a team to add user in ");
                                int selectedTeam=input.intInput();
                                if(operations.isOwner(teams.get(selectedTeam-1),user)){
                                    operations.displayUsers(users);
                                    System.out.print("Please select an user to add team as a member:");
                                    int selectedUser=input.intInput();
                                    operations.addMemberToATeam(teams.get(selectedTeam-1),user,users.get(selectedUser-1),users);
                                }else {
                                    System.out.println("You are not a owner so you can not add a member to team");
                                }
                            }else {
                                try {
                                    throw new UnauthorizedUserOperationException("You are not authorize for adding member to a team");
                                }catch (Exception ex){
                                    ex.getMessage();
                                }
                            }
                        }
                        else if (uptadeSelection.equals("5")){//Remove member from team
                            if (operations.isAuthorizedForAddingATeam(user)) {
                                operations.displayTeams(teams);
                                System.out.println("Please select a team to remove user from in");
                                int selectedTeam=input.intInput();
                                if(operations.isOwner(teams.get(selectedTeam-1),user)){
                                    operations.displayUsers(users);
                                    System.out.print("Please select an user to remove from team :");
                                    int selectedUser=input.intInput();
                                    operations.removeMemberFromATeam(teams.get(selectedTeam-1),user,users.get(selectedUser-1),users);
                                }else {
                                    System.out.println("You are not a owner so you can not remove a member from a team");
                                }
                            }else {
                                try {
                                    throw new UnauthorizedUserOperationException("You are not authorize for removing member from a team");
                                }catch (Exception ex){
                                    ex.getMessage();
                                }
                            }
                        }
                        else if (uptadeSelection.equals("6")) {//Assign owner to a team
                            operations.displayTeams(teams);
                            System.out.println("Please select a team that you want to assign a user");
                            int selectTeam = input.intInput();
                            if (operations.isOwner(teams.get(selectTeam - 1), user)) {
                                operations.displayUsers(users);
                                System.out.println("Please select a user that you want to assign as an owner");
                                int selectedUser = input.intInput();
                                operations.giveOwnerToAcademician(user, teams.get(selectTeam - 1), users.get(selectedUser - 1));
                            }else{
                                try {
                                    throw new UnauthorizedUserOperationException("You are not authorized to assign a user as an owner");
                                }catch (Exception ex){
                                    ex.getMessage();
                                }
                            }
                        }
                        else if(uptadeSelection.equals("7")){//Adding channel to team
                            operations.displayTeams(teams);
                            System.out.println("Please select a team to add channel in ");
                            String selected=input.strInput();
                            int selectedTeam=Integer.valueOf(selected);
                            System.out.println("Please enter channel name you want to add");
                            String newChannelName=input.strInput();
                            System.out.println("Please enter meeting day:");
                            String meetingDay=input.strInput();
                            System.out.println("Please enter meeting time(HH:MM):");
                            String meetingTime=input.strInput();
                            System.out.println("Please enter time period(AM/PM):");
                            String meetingTimePeriod=input.strInput();
                            String meetingDayAndTime=meetingDay.concat(" "+meetingTime).concat(" "+meetingTimePeriod);
                            operations.createChannel(teams,teams.get(selectedTeam-1),newChannelName,meetingDayAndTime,user,users);
                        }
                       else if(uptadeSelection.equals("8")){//Remove channel from team
                            if (operations.isAuthorizedForAddOrRemoveAMember(user)) {
                                operations.displayTeams(teams);
                                System.out.println("Please select a team to remove channel in ");
                                String selected=input.strInput();
                                int selectedTeam=Integer.valueOf(selected);
                                if (teams.get(selectedTeam-1).getChannels().size()>1) {
                                    if (operations.isOwner(teams.get(selectedTeam - 1), user)) {
                                        operations.displayChannelsInformation(teams.get(selectedTeam - 1));
                                        System.out.println("Please select channel you want to remove");
                                        String select = input.strInput();
                                        int selectedChannel = Integer.valueOf(select);
                                        operations.removeChannel(teams.get(selectedTeam - 1), teams.get(selectedTeam - 1).getChannels().get(selectedChannel));
                                    } else {
                                        System.out.println("You are not a owner for this team so you can not remove channel from team");
                                    }
                                }else {
                                    System.out.println("There is no private channel to remove");
                                }
                            }else {
                                try {
                                    throw new UnauthorizedUserOperationException("You are not authorize for removing channel from a team");
                                }catch (Exception ex){
                                    ex.getMessage();
                                }
                            }
                        }
                    }
                    else if(selectOp.equals("4")){
                        operations.displayTeams(teams);
                        System.out.println("Please select a team to monitor");
                        int selected = input.intInput();
                        operations.monitoringTeamsInformation(teams.get(selected - 1));
                    }
                    else if(selectOp.equals("5")){
                        operations.displayTeams(teams);
                        System.out.println("Please select team");
                        int selectedTeam=input.intInput();
                        operations.countUserTypes(teams.get(selectedTeam-1),users);
                    }

                }

            }


        }


    }


}
