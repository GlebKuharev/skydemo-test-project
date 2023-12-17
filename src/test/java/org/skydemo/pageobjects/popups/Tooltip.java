package org.skydemo.pageobjects.popups;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import org.skydemo.pageobjects.pages.BasePage;

@Getter
public class Tooltip extends BasePage {

    private final Locator tooltip = page.getByRole(AriaRole.TOOLTIP);

    public Tooltip(Page page) {
        super(page);
    }
}
