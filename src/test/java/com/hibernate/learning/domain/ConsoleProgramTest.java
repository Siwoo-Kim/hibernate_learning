package com.hibernate.learning.domain;

import com.hibernate.learning.service.HoteServiceImpl;
import com.hibernate.learning.service.UserService;
import com.hibernate.learning.service.UserServiceImpl;
import com.hibernate.learning.utils.DBUtils;
import org.springframework.util.ObjectUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ConsoleProgramTest {

    public enum Entity{
        USER , HOTEL, EXIT , WRONG_VALUE
    }

    public static UserService userService = new UserServiceImpl();
    public static HoteServiceImpl hoteService = new HoteServiceImpl();

    //Bootstrap for console program
    public static void main(String[] args) throws IOException {

        DBUtils.init();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));


        while(true){
            System.out.println("Choose the menu :> ");

            List<Entity> menus = Arrays.asList(Entity.values());

            menus.stream()
                    .filter(entity -> !entity.equals(Entity.WRONG_VALUE))
                    .forEach(menu -> {
                int order = menu.ordinal();
                System.out.println(order+1 + " : "+menu.toString());
            });


            String choosedMenu = bufferedReader.readLine();

            Entity choosedEntity = null;

            try {

                choosedEntity = Entity.valueOf(choosedMenu.toUpperCase());

            }catch (IllegalArgumentException e){

                choosedEntity = Entity.WRONG_VALUE;
            }


            if(choosedEntity == Entity.EXIT) { break; }

            switch (choosedEntity) {

                case HOTEL: while (true){
                    System.out.println("Enter a command:>");
                    String line = bufferedReader.readLine();
                    String[] commands = line.split(" ");

                    if(commands[0].equalsIgnoreCase("exit")){

                        System.out.println("Terminate Program");
                        break;

                    }

                    if(commands[0].equalsIgnoreCase("save")){

                        handleHotelCreateCommand(commands);

                    }

                    if(commands[0].equalsIgnoreCase("list")){

                        handleHotelListCommand(commands);

                    }
                } break;

                case USER: while (true) {
                    System.out.println("Enter a command:>");
                    String line = bufferedReader.readLine();
                    String[] commands = line.split(" ");

                    if (commands[0].equalsIgnoreCase("exit")) {

                        System.out.println("Terminate Program");
                        break;

                    } else if (commands[0].equalsIgnoreCase("join")) {

                        handleJoinCommand(commands);

                    } else if (commands[0].equalsIgnoreCase("view")) {

                        handleViewCommand(commands);

                    } else if (commands[0].equalsIgnoreCase("list")) {

                        handleListCommand(commands);

                    } else if (commands[0].equalsIgnoreCase("changename")) {

                        handleChangeName(commands);

                    } else if (commands[0].equalsIgnoreCase("remove")) {

                        handleRemoveCommand(commands);

                    } else {

                        System.out.println("Wrong command. try again.");

                    }

                    System.out.println("-------");

                } break;

                default: System.out.println("TRY AGAIN :>");
            }

        }

        DBUtils.close();
    }

    private static void handleHotelListCommand(String[] commands) {

        ConsoleProgramTest.hoteService.getHotels().stream()
                .forEach(System.out::println);

    }

    private static void handleHotelCreateCommand(String... commands) {


        if(commands.length < 5){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save [id] [name] [grade] [description]");
            return;

        }


        String id = commands[1];
        String name = commands[2];
        String gradeStr = commands[3];

        String description = new String();

        for(int i=4; i < commands.length; i++){

            description += commands[i]+" ";

        }

        Hotel.Grade grade = null;

        try{

        grade = Hotel.Grade.valueOf(gradeStr.toUpperCase());

        }catch (IllegalArgumentException e){

                System.out.println("Hotel grade is not valid");
                System.out.println("Choose one of these below");
                System.out.println("STAR1 STAR2 STAR3 STAR4 STAR5");
                return;

        }

        Hotel hotel = Hotel.builder().id(id).name(name).grade(grade).description(description).build();

        try{

            Hotel savedHotel = ConsoleProgramTest.hoteService.save(hotel);

            System.out.println("Hotel is created");
            System.out.println("Hotel ID : "+savedHotel.getId());
            System.out.println("Hotel Name : "+savedHotel.getName());
            System.out.println("Hotel Grade : "+savedHotel.getGrade());



        }catch (Exception e){

        }

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