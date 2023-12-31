package org.skydemo.tests;

import com.microsoft.playwright.Locator;
import org.skydemo.base.TestBase;
import org.skydemo.bo.User;
import org.skydemo.factory.UserFactory;
import org.skydemo.pageobjects.popups.Alert;
import org.skydemo.pageobjects.pages.*;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class UpdateUserTests extends TestBase {

    /**
     * Test case description: <br>
     * 1. Create a user <br>
     * 2. Check that user's First name and Last name can be edited (changes persist after saving and page refresh) <br>
     * 3. Check that user's email cannot be edited <br>
     * 4. Check that Undo button doesn't save the changes, including after a page refresh <br>
     * 5. Check that user cannot be updated when trying to update First name or Last name to an empty value <br>
     */
    @Test
    public void updateUserInfoTest() {
        User user = UserFactory.createUser();
        String postfix = "_upd";
        String newFirstName = user.getFirstName().concat(postfix);
        String newLastName = user.getLastName().concat(postfix);

        UsersPage usersPage = new UsersPage(page);
        AddUserPage addUserPage = usersPage.clickAddUserBtn();
        addUserPage.createUser(user);
        Alert alert = new Alert(page);
        assertThat(alert.getAlert()).hasText("New user has been added successfully.");
        alert.close();

        UserPage userPage = usersPage.openUserPage(user.getEmail());
        EditUserPage editUserPage = userPage.userInfoComponent.clickEditUserInfoBtn();
        editUserPage.userInfoComponent.clearFirstNameInput().fillFirstName(newFirstName);
        editUserPage.userInfoComponent.clearLastNameInput().fillLastName(newLastName);
        assertThat(editUserPage.userInfoComponent.getEmailInput()).hasCount(0);

        userPage = editUserPage.userInfoComponent.clickSaveUserInfoButton();

        assertThat(alert.getAlert()).hasText("Success request for users");
        alert.close();
        assertThat(userPage.userInfoComponent.getFirstNameTextbox()).hasText(newFirstName);
        assertThat(userPage.userInfoComponent.getLastNameTextbox()).hasText(newLastName);
        assertThat(userPage.userInfoComponent.getEmailTextbox()).hasText(user.getEmail());

        page.reload();
        assertThat(userPage.userInfoComponent.getFirstNameTextbox()).hasText(newFirstName);
        assertThat(userPage.userInfoComponent.getLastNameTextbox()).hasText(newLastName);
        assertThat(userPage.userInfoComponent.getEmailTextbox()).hasText(user.getEmail());

        editUserPage = userPage.userInfoComponent.clickEditUserInfoBtn();
        editUserPage.userInfoComponent.clearFirstNameInput().fillFirstName(user.getFirstName());
        editUserPage.userInfoComponent.clearLastNameInput().fillLastName(user.getLastName());
        editUserPage.userInfoComponent.clickUndoUserInfoButton();
        assertThat(userPage.userInfoComponent.getFirstNameTextbox()).hasText(newFirstName);
        assertThat(userPage.userInfoComponent.getLastNameTextbox()).hasText(newLastName);

        page.reload();
        assertThat(userPage.userInfoComponent.getFirstNameTextbox()).hasText(newFirstName);
        assertThat(userPage.userInfoComponent.getLastNameTextbox()).hasText(newLastName);

        editUserPage = userPage.userInfoComponent.clickEditUserInfoBtn();
        editUserPage.userInfoComponent.clearFirstNameInput();
        assertThat(editUserPage.userInfoComponent.getSaveUserInfoButton()).isDisabled();

        editUserPage.userInfoComponent.fillFirstName(user.getFirstName());
        editUserPage.userInfoComponent.clearLastNameInput();
        assertThat(editUserPage.userInfoComponent.getSaveUserInfoButton()).isDisabled();
    }

    /**
     * Test case description: <br>
     * 1. 1. Create a user with default roles <br>
     * 2. Check that new roles can be added to a user <br>
     * 3. Check that changes persist after a page refresh <br>
     * 4. Check that roles can be changed (removed and added) <br>
     * 5. Check that Undo button doesn't save changes, including after a page refresh <br>
     */
    @Test
    public void updateRolesTest() {
        List<String> rolesToSet = List.of("Insights", "Product Release", "Site Management");
        User user = UserFactory.createUser();

        UsersPage usersPage = new UsersPage(page);
        AddUserPage addUserPage = usersPage.clickAddUserBtn();
        addUserPage.createUser(user);

        UserPage userPage = usersPage.openUserPage(user.getEmail());
        EditUserPage editUserPage = userPage.rolesComponent.clickEditRolesBtn();
        rolesToSet.forEach(editUserPage.rolesComponent::setRole);
        userPage = editUserPage.rolesComponent.clickSaveRolesButton();

        List<String> expectedRoles = new LinkedList<>(rolesToSet);
        expectedRoles.add("Basic Access");

        checkThatExpectedRolesAreEnabled(userPage.rolesComponent.getEnabledRoles(), expectedRoles);

        page.reload();
        checkThatExpectedRolesAreEnabled(userPage.rolesComponent.getEnabledRoles(), expectedRoles);

        editUserPage = userPage.rolesComponent.clickEditRolesBtn();
        String roleToUnset = "Insights";
        editUserPage.rolesComponent.unsetRole(roleToUnset);
        expectedRoles.remove(roleToUnset);

        String roleToSet = "Shipment Management";
        editUserPage.rolesComponent.setRole(roleToSet);
        expectedRoles.add(roleToSet);

        userPage = editUserPage.rolesComponent.clickSaveRolesButton();
        checkThatExpectedRolesAreEnabled(userPage.rolesComponent.getEnabledRoles(), expectedRoles);

        editUserPage = userPage.rolesComponent.clickEditRolesBtn();
        editUserPage.rolesComponent.setRole("Insights");
        editUserPage.rolesComponent.unsetRole("Site Management");
        editUserPage.rolesComponent.clickUndoRolesButton();
        checkThatExpectedRolesAreEnabled(userPage.rolesComponent.getEnabledRoles(), expectedRoles);

        page.reload();
        checkThatExpectedRolesAreEnabled(userPage.rolesComponent.getEnabledRoles(), expectedRoles);
    }

    private void checkThatExpectedRolesAreEnabled(Locator locator, List<String> expectedRoles) {
        assertThat(locator).hasText(expectedRoles.stream().sorted().toArray(String[]::new));
    }
}
