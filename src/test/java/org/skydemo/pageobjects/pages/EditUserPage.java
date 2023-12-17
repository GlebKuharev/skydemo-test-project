package org.skydemo.pageobjects.pages;

import com.microsoft.playwright.Page;
import org.skydemo.pageobjects.components.RolesComponent;
import org.skydemo.pageobjects.components.UserInfoComponent;

public class EditUserPage extends BasePage {

    public final RolesComponent rolesComponent;
    public final UserInfoComponent userInfoComponent;

    public EditUserPage(Page page) {
        super(page);
        rolesComponent = new RolesComponent(page);
        userInfoComponent = new UserInfoComponent(page);
    }
}
