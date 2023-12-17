package org.skydemo.pageobjects.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.skydemo.pageobjects.pages.EditUserPage;
import org.skydemo.pageobjects.pages.UserPage;

@Slf4j
@Getter
public class RolesComponent extends BaseComponent {

    private final Locator roles = page.locator("//*[./*[contains(@class, 'MuiSwitch-root')]]");
    private final Locator enabledRoles = roles.filter(new Locator.FilterOptions().setHas(
            page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setChecked(true))));
    private final String roleCheckboxLocatorTemplate = "//*[./*[text()='%s']]//input[contains(@class, 'MuiSwitch-input')]";
    private final Locator editRolesBtn = page.locator("//*[./p[text()='Roles']]").getByTestId("EditIcon");
    private final Locator undoRolesButton = page.locator("//*[./p[text()='Roles']]")
            .locator("//button[./*[local-name()='svg' and @data-testid='UndoIcon']]");
    private final Locator saveRolesButton = page.locator("//*[./p[text()='Roles']]")
            .locator("//button[./*[local-name()='svg' and @data-testid='SaveIcon']]");

    public RolesComponent(Page page) {
        super(page);
    }

    public void setRole(String role) {
        page.locator(String.format(roleCheckboxLocatorTemplate, role)).check();
    }

    public UserPage clickSaveRolesButton() {
        log.info("Clicking 'Save' roles button");
        saveRolesButton.click();

        return new UserPage(page);
    }

    public UserPage clickUndoRolesButton() {
        log.info("Clicking 'Undo' roles button");
        undoRolesButton.click();

        return new UserPage(page);
    }

    public void unsetRole(String role) {
        log.info("Unchecking role: {}", role);
        page.locator(String.format(roleCheckboxLocatorTemplate, role)).uncheck();
    }

    public EditUserPage clickEditRolesBtn() {
        log.info("Clicking 'Edit' roles button");
        editRolesBtn.click();

        return new EditUserPage(page);
    }
}