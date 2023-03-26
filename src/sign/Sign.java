package sign;

import input.Input;
import users.User;

import java.util.ArrayList;

public class Sign {
    Input input = new Input();

    public boolean signIn(ArrayList<User> user, String email, String password) {
        boolean signInControl = false;
        for (User value : user) {
            if (email.equals(value.geteMail())) {
                if (password.equals(value.getPassword())) {
                    System.out.println("Welcome " + value.getUserName());
                    signInControl = true;
                    return signInControl;
                }
            }
        }
        System.out.println("Email or password is wrong!!");

        return signInControl;
    }

    public boolean signOut() {
        return false;
    }
}