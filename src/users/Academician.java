package users;
import generator.PasswordGenerator;
import generator.idGenerator;

import java.util.ArrayList;
import java.util.Locale;


public class Academician extends User{
    public Academician(String userType, String userName, String userID, String eMail, String password, ArrayList<String> teamID) {
        super(userType, userName, userID, eMail, password, teamID);
    }

    @Override
    public String generateEmail() { //for Academician
        String[] fullName = getUserName().split(" ");
        String firstName = fullName[0].toLowerCase(Locale.ROOT);
        String lastName = fullName[fullName.length-1].toLowerCase(Locale.ROOT);
        String email =firstName + lastName + "@iyte.edu.tr";
        return email;
    }





}