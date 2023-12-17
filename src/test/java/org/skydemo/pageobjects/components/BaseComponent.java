package org.skydemo.pageobjects.components;

import com.microsoft.playwright.Page;

public class BaseComponent {

    protected Page page;

    protected BaseComponent (Page page) {
        this.page = page;
    }
}
