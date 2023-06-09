# CENG431 – Building Software Systems

# Midterm 1 Project

In this project you are expected to build a software system called “TeamsTECH” that is
responsible of managing courses. You should fulfill the concepts of:

```
 Object Oriented Analysis and Design
 Object Orientation Fundamentals, Abstract Data Types
 Inheritance, Polymorphism, Abstract Classes, Interfaces, Exceptions
 Collections
 UML Design with Class Diagrams
 Mediator Design Pattern
```
In this software, a team corresponds to one of the courses that is opened in
IZTECH. Each team will have a name, a team id and at least one default meeting channel
which is accessible by every member of the team. Whenever a team is removed its
meeting channels will be removed as well. Also, each team will have a set of members
and at least one team owner.
A user who has a user id, name, email, password and a department i.e., “Computer
Engineering” could be a member of a team (There may be users who are not a member
of any team). Students and academicians are users.
A user id will be randomly generated number between 1 and 1000. The password
will be a randomly generated string of length 4. The email addresses of the users will be
determined by the following convention: “first name + last name + @ + domain”.
The domain of email is “std.iyte.edu.tr” if a user is a student. The domain of email
is “iyte.edu.tr” if a user is an academician. Instructors and teaching assistants are
academicians. Only instructors can create a new team and become a team owner.
Additionally, instructors can add teaching assistants as team owners. Only team owners
can add other users as team owners, otherwise an UnauthorizedUserOperationException
occurs.
A meeting channel in a team is a standard channel by default which is accessible
by each member of a team. Also, private channels exist which are accessible only by a
list of participants. Any user can create private channels. A meeting channel could have
an updatable, organized meeting which occurs at the same day and time every week such
as “Monday 09:45 AM”.
For this project, you are given a CSV file namely “userList.csv” which consists
of (User Type, User Name, User ID, Email, Password, Team ID) columns. The user type
and the user name columns are already filled. Please fill user id (except for lines 4 and 5),
email and password (except for lines 4 and 5) columns for each user according to the
instructions above. In the file, at the end of each line for each user, there is the id of the
team or teams that the users join. Also, you are given the file “teamList.csv” which consist
of (Team Name, Team ID, Default Channel, Default Meeting Day and Time, Meeting
Channel, Meeting Day and Time, Participant ID) columns. Please, create the users and
the teams written in these file and adjust the team members and owners.


Additionally, you are expected to implement a mediator class for this project
which is responsible of:

```
 Adding, finding and removing of teams
 Updating a team by:
o Adding and removing a meeting channel,
o Updating a meeting channel by:
```
- Adding and removing participants, if it is private
- Updating meeting day and time
o Adding and removing a member (only academicians)
o Finding and updating a member as a team owner (only team owners)
o Monitoring each team’s meeting channels, meeting channels’ meeting
time and participants (if the channel is private)
o Finding number of distinct students, instructors and teaching assistants.
For this project, please create a user-friendly menu which always lists the options
to the user and guides the user about inputs. For example:
What would you like to do?
1 - Add a team
2 - Remove a team
3 - Update a team
Please enter a number between 1-3: 3
