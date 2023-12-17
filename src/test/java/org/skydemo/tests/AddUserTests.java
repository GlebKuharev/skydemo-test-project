package org.skydemo.tests;

import org.skydemo.base.TestBase;
import org.skydemo.bo.User;
import org.skydemo.factory.UserFactory;
import org.skydemo.pageobjects.popups.Alert;
import org.skydemo.pageobjects.popups.Tooltip;
import org.skydemo.pageobjects.pages.*;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddUserTests extends TestBase {

    @Test
    public void addUserTest() {
        UsersPage usersPage = new UsersPage(page);
        AddUserPage addUserPage = usersPage.clickAddUserBtn();
        User user = UserFactory.createUser();
        addUserPage.createUser(user);

        Alert alert = new Alert(page);
        assertThat(alert.getAlert()).hasText("New user has been added successfully.");
        alert.close();

        UserPage userPage = usersPage.openUserPage(user.getEmail());

        assertThat(userPage.userInfoComponent.getFirstNameTextbox()).hasText(user.getFirstName());
        assertThat(userPage.userInfoComponent.getLastNameTextbox()).hasText(user.getLastName());
        assertThat(userPage.userInfoComponent.getEmailTextbox()).hasText(user.getEmail());

        // checking that the user has 'Basic Access' enabled by default
        assertThat(userPage.rolesComponent.getEnabledRoles()).hasText(new String[]{"Basic Access"});
    }

    @Test
    public void addUserNegativeTest() {
        UsersPage usersPage = new UsersPage(page);
        AddUserPage addUserPage = usersPage.clickAddUserBtn();
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        User user = UserFactory.createUser();
        addUserPage.userInfoComponent.fillFirstName(user.getFirstName());
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.clearFirstNameInput();
        addUserPage.userInfoComponent.fillLastName(user.getLastName());
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.clearLastNameInput();
        addUserPage.userInfoComponent.fillEmail(user.getEmail());
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.clearEmailInput();
        addUserPage.userInfoComponent.fillFirstName(user.getFirstName());
        addUserPage.userInfoComponent.fillLastName(user.getLastName());
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.clearFirstNameInput();
        addUserPage.userInfoComponent.fillEmail(user.getEmail());
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.clearLastNameInput();
        addUserPage.userInfoComponent.fillFirstName(user.getFirstName());
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.fillLastName(user.getLastName());
        addUserPage.userInfoComponent.clearEmailInput().fillEmail("incorrectEmail@.com");
        Tooltip tooltip = new Tooltip(page);
        assertThat(tooltip.getTooltip()).hasText("This e-mail address does not have a valid format.");
        assertThat(addUserPage.getCreateBtn()).isDisabled();

        addUserPage.userInfoComponent.clearEmailInput().fillEmail(user.getEmail());
        usersPage = addUserPage.clickCreateBtn();
        addUserPage = usersPage.clickAddUserBtn();
        addUserPage.userInfoComponent.fillFirstName(user.getFirstName().concat("A"));
        addUserPage.userInfoComponent.fillLastName(user.getLastName().concat("A"));
        // setting the same email for a new user to check email uniqueness requirement
        addUserPage.userInfoComponent.fillEmail(user.getEmail());
        assertThat(tooltip.getTooltip()).hasText("User with this e-mail address already exists.");
        assertThat(addUserPage.getCreateBtn()).isDisabled();
    }
}
