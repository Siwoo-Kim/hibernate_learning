package com.hibernate.learning.domain;

import com.hibernate.learning.domain.embeddable.Address;
import com.hibernate.learning.service.*;
import com.hibernate.learning.utils.DBUtils;
import lombok.extern.java.Log;
import org.hibernate.LazyInitializationException;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.hibernate.learning.utils.DBUtils.entityManager;


@Log
public class ConsoleProgramTest {

    public enum Entity{
        USER , HOTEL, SIGHT , MEMBER ,EXIT , WRONG_VALUE
    }

    public static UserService userService = new UserServiceImpl();
    public static HoteServiceImpl hoteService = new HoteServiceImpl();
    public static SightService sightService = new SightServiceImpl();
    public static MemberService memberService = new MemberServiceImpl();
    public static CardService cardService = new CardServiceImpl();

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

                case MEMBER : while (true){
                    System.out.println("Enter a command:>");
                    String line = bufferedReader.readLine();
                    List<String> commands = Arrays.asList(line.split(" "));

                    if(commands.get(0).equalsIgnoreCase("exit")){

                        System.out.println("Terminate Program");
                        break;

                    }else if(commands.contains("--card")){

                        if(commands.get(0).equalsIgnoreCase("save")){

                            handleCardSaveCommand(commands);

                        }else if(commands.get(0).equalsIgnoreCase("assign")){

                            handleCardAssignCommand(commands);

                        }

                    }else if(commands.contains("--detail")){

                        if(commands.get(0).equalsIgnoreCase("save")){

                            handleMemberDetailSaveCommand(commands);
                        }

                    }else if(commands.get(0).equalsIgnoreCase("save")){

                        handleMemberSaveCommand(commands);

                    }else if(commands.get(0).equalsIgnoreCase("list")){

                        handleMemberListCommand(commands);

                    }else {

                        System.out.println("Command not found. Try again :<");

                    }

                } break;

                case SIGHT : while(true){
                        System.out.println("Enter a command:>");
                        String line = bufferedReader.readLine();
                        String[] commands = line.split(" ");

                    if(commands[0].equalsIgnoreCase("exit")){

                        System.out.println("Terminate Program");
                        break;

                    }else if(commands[0].equalsIgnoreCase("save")) {

                        handleSightSaveCommand(commands);

                    }else{

                        System.out.println("Command not found. Try again :<");

                    }

                    } break;

                case HOTEL: while (true){
                    System.out.println("Enter a command:>");
                    String line = bufferedReader.readLine();
                    String[] commands = line.split(" ");
                    List<String> commandsList = Arrays.asList(commands);

                    if(commands[0].equalsIgnoreCase("exit")){

                        System.out.println("Terminate Program");
                        break;

                    }else if(commands[0].equalsIgnoreCase("proxy")){

                        handleHotelProxyCommand(commands);

                    }else if(commands[0].equalsIgnoreCase("save") && !commandsList.stream().anyMatch(str -> str.contains("--"))){

                        handleHotelCreateCommand(commands);

                    }else if(commands[0].equalsIgnoreCase("list")){

                        handleHotelListCommand(commands);

                    }else if(commands[0].equalsIgnoreCase("addAddress")){

                        if(Arrays.asList(commands).contains("--optional")){

                            handleAddAddressOptionalCommand(commands);
                            continue;

                        }else {

                            handleHotelAddAddressCommand(commands);
                            continue;
                        }

                    }else if(commandsList.contains("--review")){

                        if(commands[0].equalsIgnoreCase("save")){

                            handleHotelReviewSaveCommand(commandsList);

                        }

                    }else{

                        System.out.println("Command not found. Try again :<");
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

    private static void handleHotelReviewSaveCommand(List<String> commandsList) {


        if(commandsList.size() < 4){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save --review [hotelName] [rate] [comment]");
            return;

        }

        Hotel hotel = null;
        int rate = 0;

        try {

            hotel = hoteService.getHotelByName(commandsList.get(2));

        }catch (NoResultException e){

            System.out.println("Hotel does not exists");
            System.out.println("Try again :<");

        }


        try {

            rate = Integer.parseInt(commandsList.get(3));

        }catch (NumberFormatException e){

            System.out.println("Please enter a number(0-5) for rate");

        }

        String comment = commandsList.subList(4,commandsList.size()-1)
                .stream()
                .reduce("",(str1,str2)-> str1.concat(" "+str2) );


        HotelReview hotelReview = HotelReview.builder()
                .hotel(hotel)
                .rate(rate)
                .comment(comment)
                .build();

        log.warning(hotelReview.toString());
        System.out.println(hoteService.addReview(hotelReview));

    }

    private static void handleMemberDetailSaveCommand(List<String> commands) {

        if(commands.size() != 6){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save --detail [memberEmail] [city] [street] [zipcode]");
            return;

        }

        try {

            Member foundMember = memberService.find(commands.get(2));

            MemberDetail memberDetail = memberService.addDetail(MemberDetail.builder()
                    .id(foundMember.getId())
                    .member(foundMember)
                    .address(new Address(commands.get(5),commands.get(3),commands.get(4)))
                    .build());

            System.out.println(memberDetail.toString());

        }catch(Exception e){

            e.printStackTrace();

        }
    }

    private static void handleMemberListCommand(List<String> commands) {

        memberService
                .findAll()
                .stream().map(Member::toString)
                .forEach(System.out::println);

    }

    private static void handleCardAssignCommand(List<String> commands) {

        if(commands.size() != 4){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:assign --card [cardNumber] [memberEmail]");
            return;

        }

        Member foundMember = memberService.find(commands.get(3));

        Card card = cardService.find(commands.get(2));

        cardService.assign(foundMember,card);

        System.out.println(card.toString());

    }

    public static DateTimeFormatter cardExpiredDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static void handleCardSaveCommand(List<String> commands) {

        if(commands.size() != 5){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save --card [cardNumber] [yyyy-MM-dd,cardExpireDate] [enabled:true|false]");
            return;

        }


        cardService.save(
                Card.builder()
                        .cardNumber(commands.get(2))
                .expireDate(LocalDate.parse(commands.get(3),cardExpiredDateFormatter))
                .enabled(Boolean.parseBoolean(commands.get(4)))
                .build()
        );

    }

    private static void handleMemberSaveCommand(List<String> commands) {

        if(commands.size() != 4){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save [email] [username] [age]");
            return;

        }

        Member savedMember = memberService.save(
                Member.builder()
                .email(commands.get(1))
                .username(commands.get(2))
                .age(Integer.parseInt(commands.get(3)))
                .build()
        );

        System.out.println(savedMember.toString());

    }

    private static void handleSightSaveCommand(String[] commands) {




        if(commands.length < 2){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save [name] [hoursOfOperation:option] [holiday:option] [facilities:option]");
            return;

        }

        ArrayList<String> commandsList = new ArrayList();

        for(String string : commands){
            commandsList.add(string);
        }

        if(commands.length < 5){

            int index = 5 - commands.length;

            for(int i = index - 1 ; i < 5; i++){
                commandsList.add(i,null);
            }

            System.out.println(commandsList.toString());
        }

        Integer hoursOfOperation = null;

        if(StringUtils.hasText(commandsList.get(2))) {

            try {

                hoursOfOperation = Integer.parseInt(commands[2]);

            } catch (NumberFormatException e) {

                e.printStackTrace();
                System.out.println("hoursOfOperation is not the number");
                System.out.println("Try again :<");
                return;

            }
        }


        Sight savedSight = ConsoleProgramTest.sightService.save(Sight.builder()
            .name(commands[1]).sightDetail(new SightDetail(hoursOfOperation,commandsList.get(3),commandsList.get(4))).build());


        System.out.println(savedSight.toString());

    }

    private static void handleAddAddressOptionalCommand(String[] commands) {

        if(commands.length != 6){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:addAddress --optional [name] [city] [street] [postalCode]");
            return;

        }

        try {

            Hotel updatedHotel = ConsoleProgramTest.hoteService.addOptionalAddress(commands[2],
                    new Address(commands[5], commands[4], commands[3]));

            System.out.println(updatedHotel.toString());

        }catch (IllegalStateException e){

            System.out.println("Primary Address need to be insert fisrt");
            System.out.println("Try again :<");

        }catch (NoResultException e){

            System.out.println("Hotel does not exits");
            System.out.println("Try again :<");

        }
    }

    private static void handleHotelAddAddressCommand(String[] commands) {

        if(commands.length != 5){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:addAddress [name] [city] [street] [postalCode]");
            return;

        }

        try {

            Hotel updatedHotel = ConsoleProgramTest.hoteService.addAddress(commands[1],
                    new Address(commands[4], commands[3], commands[2]));
            System.out.println(updatedHotel.toString());

        }catch (NoResultException e){

            System.out.println("Hotel does not exits");
            System.out.println("Try again :<");

        }

    }

    private static void handleHotelProxyCommand(String[] commands) {

        if(commands.length != 2){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:proxy [id]");
            return;

        }

        Long id =  Long.parseLong(commands[1]);

        Hotel hotel = ConsoleProgramTest.hoteService.getHotelProxy(id);

        System.out.println("Hotel class : "+hotel.getClass());

        try{

            hotel.getName();

        }catch (LazyInitializationException e){

            System.out.println("LazyInitializationException must me occurred, because it is not in transaction");
            System.out.println("Also notice that Proxy's method must be called in transaction otherwise you will see the exception again!");

        }


    }

    private static void handleHotelListCommand(String[] commands) {

        ConsoleProgramTest.hoteService.getHotels().stream()
                .forEach(System.out::println);

    }

    private static void handleHotelCreateCommand(String... commands) {


        if(commands.length < 3){

            System.out.println("Command usage is wrong");
            System.out.println("Usage:save [name] [grade] [description]");
            return;

        }

        String name = commands[1];
        String gradeStr = commands[2];

        String description = new String();

        for(int i=3; i < commands.length; i++){

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

        Hotel hotel = Hotel.builder().name(name).grade(grade).description(description).build();

        try{

            Hotel savedHotel = ConsoleProgramTest.hoteService.save(hotel);

            System.out.println("Hotel is created");
            System.out.println(savedHotel.toString());


        }catch (Exception e){

            System.out.println("Hotel name already exists ");
            System.out.println("Try another name");

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
