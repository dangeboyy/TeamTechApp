package channels;

import users.User;

import java.util.ArrayList;

public class DefaultChannel extends Channel {

    public DefaultChannel(String channelName, String meetingDayAndTime) {
        super(channelName, meetingDayAndTime);
    }

    public DefaultChannel(String meetingDayAndTime) {
        super(meetingDayAndTime);
        this.setChannelName("General");
    }

    public DefaultChannel() {
        super();
        this.setChannelName("General");
    }

}
