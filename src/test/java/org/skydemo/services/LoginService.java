package org.skydemo.services;

import com.microsoft.playwright.Page;
import lombok.extern.slf4j.Slf4j;
import org.skydemo.pageobjects.pages.LoginPage;

@Slf4j
public class LoginService {

    private final Page page;

    public LoginService (Page page) {
        this.page = page;
    }

    public void doLogin(String email, String password) {
        log.info("Logging in under user: {}", email);
        new LoginPage(page).fillEmail(email)
                .fillPassword(password)
                .submitLoginBtn();
    }
}
