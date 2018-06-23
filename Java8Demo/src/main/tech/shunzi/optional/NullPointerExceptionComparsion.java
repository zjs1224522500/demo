package tech.shunzi.optional;

import tech.shunzi.optional.model.Address;
import tech.shunzi.optional.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NullPointerExceptionComparsion {

    private static List<User> userList = new ArrayList<>(3);


    public static void main(String[] args) {
        initData();
        testOrdinaryNPE();
        testUseOptional();
    }

    private static void testOrdinaryNPE() {
        userList.stream().forEach(user -> {
            if(null != user.getAddress()) {
                System.out.println(user.getAddress().getCity());
            }
        });
    }

    private static void testUseOptional() {
        userList.stream().forEach(user -> {
            Optional.ofNullable(user.getAddress()).ifPresent(address -> {
                System.out.println(address.getCity());
            });
        });
    }

    private static void initData() {

        Address address = new Address();
        address.setCity("Chengdu");

        for (int i = 1; i < 4; i++) {
            User user = new User();
            user.setName("elvis" + i);
            if (i % 2 == 1) {
                user.setAddress(address);
            }
            userList.add(user);
        }
        System.out.println(userList);
    }
}
