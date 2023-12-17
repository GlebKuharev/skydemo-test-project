package org.skydemo.pageobjects.popups;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skydemo.pageobjects.pages.BasePage;

@Slf4j
@Getter
public class Alert extends BasePage {

    private final Locator alert = page.getByRole(AriaRole.ALERT);
    private final Locator closeButton = alert.locator("button[title=Close]");

    public Alert(Page page) {
        super(page);
    }

    public void close() {
        log.info("Closing alert with text: {}", alert.textContent());
        closeButton.click();
    }
}
