package org.skydemo.factory;

import com.microsoft.playwright.*;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class BrowserFactory {
    private Playwright playwright;
    private Browser browser;
    private Properties properties;

    public Page initializeBrowser(String browserName, String headless) throws IllegalArgumentException {
        boolean isHeadless = Boolean.parseBoolean(headless);

        playwright = Playwright.create();

        switch (browserName.toLowerCase()) {
            case "chromium":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            case "chrome":
                browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(isHeadless));
                break;
            case "firefox":
                browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            case "webkit":
                browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                break;
            default:
                throw new IllegalArgumentException("Please provide a valid browser name (chrome, firefox, webkit or chromium).");
        }

        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")));
        Page page = browserContext.newPage();
        page.setViewportSize(1920, 1080);

        return page;
    }

    public Properties initializeConfigProperties() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String configUrl = classLoader.getResource("config/config.properties").getPath();
            FileInputStream fileInputStream = new FileInputStream(configUrl);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return properties;
    }
}
