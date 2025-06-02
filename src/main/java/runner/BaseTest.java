package runner;

import config.ApiConfigUtils;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.*;

public class BaseTest {

    private static final Logger LOG = Logger.getLogger(BaseTest.class);

    static {
        System.setProperty("HTTP.URL.BASE", ApiConfigUtils.CONFIG.apiUrl());
    }

    @BeforeSuite(alwaysRun = true)
    public void setGroupByInstances() {
        LOG.info("=== start test suite ===");

    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass(ITestContext context) {
        LOG.info(String.format("=== start test class %s ===", this.getClass().getSimpleName()));
        context.getCurrentXmlTest().getSuite().setGroupByInstances(true);
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        LOG.info("=== start test ===");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        LOG.info("=== start test method ===");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        LOG.info("=== after suite ===");
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        LOG.info("=== after class ===");
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        LOG.info("=== after test ===");
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        LOG.info("=== after test method ===");
    }
}
