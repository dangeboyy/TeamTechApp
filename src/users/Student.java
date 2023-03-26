package users;

import generator.PasswordGenerator;

import java.util.ArrayList;
import java.util.Locale;

public class Student extends User{

    public Student(String userType, String userName, String userID, String eMail, String password, ArrayList<String> teamID) {
        super(userType, userName, userID, eMail, password, teamID);

    }
    public String generateEmail() {
        String[] fullName = getUserName().split(" ");
        String firstName = fullName[0].toLowerCase(Locale.ROOT);
        String lastName = fullName[fullName.length-1].toLowerCase(Locale.ROOT);
        String email =firstName + lastName + "@std.iyte.edu.tr";
        return email;

    }





}
