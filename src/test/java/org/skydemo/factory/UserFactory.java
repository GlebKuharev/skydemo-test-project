package org.skydemo.factory;

import lombok.extern.slf4j.Slf4j;
import org.skydemo.bo.User;
import org.skydemo.utils.StringUtils;

@Slf4j
public class UserFactory {

    public static User createUser() {
        String postfix = StringUtils.randomString();
        String firstName = "First_".concat(postfix);
        String lastName = "Last_".concat(postfix);
        String email = "autotest_".concat(postfix).concat("@test.com");

        return new User(firstName, lastName, email);
    }
}
