package org.skydemo.pageobjects.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UsersPage extends BasePage {

    private final Locator addUserBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add User"));

    public UsersPage(Page page) {
        super(page);
    }

    public AddUserPage clickAddUserBtn() {
        log.info("Clicking add user button");
        addUserBtn.click();

        return new AddUserPage(page);
    }

    public UserPage openUserPage(String email) {
        log.info("Opening user page of user: {}", email);
        page.getByText(email).click();

        return new UserPage(page);
    }

}
