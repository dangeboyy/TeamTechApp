package users;

import java.util.ArrayList;

public abstract class User {
    private String userType;
    private String userName;
    private String userID;
    private String eMail;
    private String password;
    private ArrayList<String> teamID;

    public User(String userType, String userName, String userID, String eMail, String password, ArrayList<String> teamID) {
        this.userType = userType;
        this.userName = userName.trim();//To remove the space after the user type in userList.csv file in student types
        this.userID = userID;
        this.eMail = generateEmail();
        this.password = password;
        this.teamID=teamID;

    }
    public abstract String generateEmail();




    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getTeamID() {
        return teamID;
    }

    public void setTeamID(ArrayList<String> teamID) {
        this.teamID = teamID;
    }
}
