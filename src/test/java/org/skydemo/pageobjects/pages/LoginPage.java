package org.skydemo.pageobjects.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoginPage extends BasePage {

    private final Locator emailInput = page.getByLabel("E-Mail Address");
    private final Locator passwordInput = page.getByLabel("Password");
    private final Locator loginBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("login"));

    public LoginPage(Page page) {
        super(page);
    }

    public LoginPage fillEmail(String email) {
        log.info("Filling email field with: {}", email);
        emailInput.fill(email);

        return this;
    }

    public LoginPage fillPassword(String password) {
        log.info("Filling password field with: {}", password);
        passwordInput.fill(password);

        return this;
    }

    public UsersPage submitLoginBtn() {
        log.info("Clicking 'Login' button");
        loginBtn.click();

        return new UsersPage(page);
    }
}
