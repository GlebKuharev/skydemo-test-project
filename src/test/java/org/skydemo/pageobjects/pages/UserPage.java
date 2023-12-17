package org.skydemo.pageobjects.pages;

import com.microsoft.playwright.Page;
import org.skydemo.pageobjects.components.RolesComponent;
import org.skydemo.pageobjects.components.UserInfoComponent;

public class UserPage extends BasePage {

    public final RolesComponent rolesComponent;
    public final UserInfoComponent userInfoComponent;

    public UserPage(Page page) {
        super(page);
        rolesComponent = new RolesComponent(page);
        userInfoComponent = new UserInfoComponent(page);
    }
}
