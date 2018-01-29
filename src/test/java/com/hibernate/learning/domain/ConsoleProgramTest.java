package com.hibernate.learning.domain;

import com.hibernate.learning.service.UserService;
import com.hibernate.learning.service.UserServiceImpl;
import com.hibernate.learning.utils.DBUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ConsoleProgramTest {

    public static UserService userService = new UserServiceImpl();

    //Bootstrap for console program
    public static void main(String[] args) throws IOException {

        DBUtils.init();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("Enter a command:>");
            String line = bufferedReader.readLine();
            String[] commands = line.split(" ");

            if(commands[0].equalsIgnoreCase("exit")){

                System.out.println("Terminate Program");
                break;

            }else if(commands[0].equalsIgnoreCase("join")){

                handleJoinCommand(commands);

            }else if(commands[0].equalsIgnoreCase("view")){

                handleViewCommand(commands);

            }else if(commands[0].equalsIgnoreCase("list")){
                
                handleListCommand(commands);

            }else if(commands[0].equalsIgnoreCase("changename")){

                handleChangeName(commands);

            }else if(commands[0].equalsIgnoreCase("remove")){

                handleRemoveCommand(commands);

            }else{

                System.out.println("Wrong command. try again.");

            }

            System.out.println("-------");

        }

        DBUtils.close();
    }

    private static void handleRemoveCommand(String[] commands) {

        if(commands.length != 2){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:remove [email]");
            return;

        }

        try{

            ConsoleProgramTest.userService.remove(commands[1]);
            System.out.println("Remove command is successful");

        }catch (UserServiceImpl.UserNotFoundException e){

            System.out.println("Remove command is rejected");
            System.out.println("Message : "+e.getMessage());
            System.out.println("Code : "+e.getCode());

        }
    }

    private static void handleListCommand(String[] commands) {

        List<User> userList = ConsoleProgramTest.userService.getUsers();

        if(userList.isEmpty()){ System.out.println("No user is in database"); }
        else{

            userList.stream().forEach( user ->
                    System.out.printf("| %s | %s | %s \n",
                            user.getEmail(),
                            user.getName(),
                            user.getJoinDate().format(ConsoleProgramTest.dateTimeFormatter)));
        }

    }


    private static void handleChangeName(String[] commands) {

        if(commands.length != 3){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:changename [email] [newName]");
            return;

        }

        try{

            ConsoleProgramTest.userService.changeName(commands[1],commands[2]);
            System.out.println("User name is changed");

        }catch (UserServiceImpl.UserNotFoundException e){

            System.out.println("Changename command is rejected");
            System.out.println("Message : "+e.getMessage());
            System.out.println("Code : "+e.getCode());

        }

    }

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:MM:ss");

    private static void handleViewCommand(String[] commands) {

        if(commands.length != 2){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:view [email]");
            return;

        }

        Optional<User> userOptional = ConsoleProgramTest.userService.user(commands[1]);

        userOptional.ifPresent(user -> {

            System.out.println("Name: "+user.getName());
            System.out.println("Created: "+user.getJoinDate().format(ConsoleProgramTest.dateTimeFormatter));

        });

        if(!userOptional.isPresent()){
            System.out.println("No present");
        }
    }

    private static void handleJoinCommand(String[] commands) {
        if(commands.length != 3){
            System.out.println("Command usage is wrong");
            System.out.println("Usage:join [email] [name]");
            return;
        }

        try{

            userService.join(
                    User.builder()
                            .email(commands[1])
                            .name(commands[2])
                            .joinDate(LocalDateTime.now())
                            .build());
            System.out.println("Join command is successful");
        }catch (UserServiceImpl.DuplicateEmailException e){

            System.out.println("Join command is rejected");
            System.out.println("Message : "+e.getMessage());
            System.out.println("Code : "+e.getCode());

        }
    }
}
