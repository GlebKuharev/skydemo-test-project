package org.skydemo.pageobjects.pages;

import com.microsoft.playwright.Page;

public class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }
}
