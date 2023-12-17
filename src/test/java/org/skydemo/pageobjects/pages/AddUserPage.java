package org.skydemo.pageobjects.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skydemo.bo.User;
import org.skydemo.pageobjects.components.UserInfoComponent;

@Slf4j
@Getter
public class AddUserPage extends BasePage {

    public final UserInfoComponent userInfoComponent;

    private final Locator createBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CREATE"));
    private final Locator cancelBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("CANCEL"));

    public AddUserPage(Page page) {
        super(page);
        userInfoComponent = new UserInfoComponent(page);
    }

    public UsersPage clickCreateBtn() {
        log.info("Clicking 'Create' button");
        createBtn.click();

        return new UsersPage(page);
    }

    public UsersPage clickCancelBtn() {
        log.info("Clicking 'Cancel' button");
        cancelBtn.click();

        return new UsersPage(page);
    }

    public UsersPage createUser(User user) {
        log.info("Creating a new user: {}", user);
        userInfoComponent
                .fillFirstName(user.getFirstName())
                .fillLastName(user.getLastName())
                .fillEmail(user.getEmail());
        return clickCreateBtn();
    }
}
