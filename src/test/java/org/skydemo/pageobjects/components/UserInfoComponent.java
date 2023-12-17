package org.skydemo.pageobjects.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skydemo.pageobjects.pages.EditUserPage;
import org.skydemo.pageobjects.pages.UserPage;

@Slf4j
@Getter
public class UserInfoComponent extends BaseComponent {

    private final String inputLocatorTemplate = "//*[./div[normalize-space(text())='%s']]//input";
    private final Locator firstNameInput = page.locator(String.format(inputLocatorTemplate, "First Name"));
    private final Locator lastNameInput = page.locator(String.format(inputLocatorTemplate, "Last Name"));
    private final Locator emailInput = page.locator(String.format(inputLocatorTemplate, "E-Mail Address"));
    private final String textboxLocatorTemplate = "//div[text()='%s']/following-sibling::*";
    private final Locator firstNameTextbox = page.locator(String.format(textboxLocatorTemplate, "First Name"));
    private final Locator lastNameTextbox = page.locator(String.format(textboxLocatorTemplate, "Last Name"));
    private final Locator emailTextbox = page.locator(String.format(textboxLocatorTemplate, "E-Mail Address"));
    private final Locator undoUserInfoButton = page.locator("//*[./p[text()='User Information']]")
            .locator("//button[./*[local-name()='svg' and @data-testid='UndoIcon']]");
    private final Locator saveUserInfoButton = page.locator("//*[./p[text()='User Information']]")
            .locator("//button[./*[local-name()='svg' and @data-testid='SaveIcon']]");
    private final Locator editUserInfoBtn =
            page.locator("//*[./p[text()='User Information']]").getByTestId("EditIcon");



    public UserInfoComponent(Page page) {
        super(page);
    }

    public UserInfoComponent fillFirstName(String firstName) {
        log.info("Filling first name: {}", firstName);
        firstNameInput.fill(firstName);

        return this;
    }

    public UserInfoComponent fillLastName(String lastName) {
        log.info("Filling last name: {}", lastName);
        lastNameInput.fill(lastName);

        return this;
    }

    public UserInfoComponent fillEmail(String email) {
        log.info("Filling email: {}", email);
        emailInput.fill(email);

        return this;
    }

    public UserInfoComponent clearFirstNameInput() {
        log.info("Clearing 'First name' input");
        firstNameInput.clear();

        return this;
    }

    public UserInfoComponent clearLastNameInput() {
        log.info("Clearing 'Last name' input");
        lastNameInput.clear();

        return this;
    }

    public UserInfoComponent clearEmailInput() {
        log.info("Clearing 'Email' input");
        emailInput.clear();

        return this;
    }

    public UserPage clickSaveUserInfoButton() {
        log.info("Clicking 'Save' user info button");
        saveUserInfoButton.click();

        return new UserPage(page);
    }

    public UserPage clickUndoUserInfoButton() {
        log.info("Clicking 'Undo' user info button");
        undoUserInfoButton.click();

        return new UserPage(page);
    }

    public EditUserPage clickEditUserInfoBtn() {
        log.info("Clicking 'Edit' user info button");
        editUserInfoBtn.click();

        return new EditUserPage(page);
    }
}