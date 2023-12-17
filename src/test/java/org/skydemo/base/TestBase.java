package org.skydemo.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import lombok.extern.slf4j.Slf4j;
import org.skydemo.core.logger.LogbackConfigurator;
import org.skydemo.factory.BrowserFactory;
import org.skydemo.services.LoginService;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Slf4j
public class TestBase {
    protected Page page;
    private final BrowserFactory browserFactory = new BrowserFactory();
    private final Properties properties = browserFactory.initializeConfigProperties();
    private String browserName;
    private String testSuiteStartTime;

    @BeforeSuite
    public void beforeSuite(ITestContext testContext) {
        testSuiteStartTime = testContext.getStartDate().toString().replace(":", "-");
        LogbackConfigurator.setLogFilePath(Path.of("./target/", testSuiteStartTime, "logs",
                testContext.getAllTestMethods()[0].getMethodName() + ".log"));
        log.info("Test suite start time: {}", testSuiteStartTime);
    }

    @BeforeMethod
    @Parameters({"browserName", "headless"})
    public void setUp(@Optional("chrome") String browserName, @Optional("false") String headless) throws IllegalArgumentException {
        this.browserName = browserName;
        page = browserFactory.initializeBrowser(browserName, headless);
        startTracing();
        page.navigate(properties.getProperty("BASE_URL").trim());

        LoginService loginService = new LoginService(page);
        loginService.doLogin(properties.getProperty("EMAIL"), properties.getProperty("PASSWORD"));
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws IOException {
        takeScreenshotOnFailure(result);
        stopTracing(result.getName());
        page.context().close();
        saveVideo(result);
        page.context().browser().close();
    }

    private void startTracing() {
        page.context().tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
    }

    private void stopTracing(String fileName) {
        page.context().tracing().stop(new Tracing.StopOptions()
                .setPath(Paths.get("./target", testSuiteStartTime, "Traces", fileName + ".zip")));
    }

    private void takeScreenshotOnFailure(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String resultName = result.getName() + "_" + browserName;
            Path screenshotPath = Paths.get("./target/", testSuiteStartTime, "Screenshots", resultName + ".png");

            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
        }
    }

    private void saveVideo(ITestResult result) {
        //saving the video to a custom folder
        String resultName = result.getName() + "_" + browserName;
        Path videoPath = Path.of("./target/", testSuiteStartTime, "Videos", resultName + ".webm");
        page.video().saveAs(videoPath);

        //deleting the video from the default folder
        page.video().delete();
    }
}
