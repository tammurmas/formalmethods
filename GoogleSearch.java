package formalmethods;

import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleSearch {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "https://www.google.ee/";
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
  }

  public static void main(String[] args) throws Exception
  {
      GoogleSearch search = new GoogleSearch();
      search.setUp();
      search.testGoogleSearch();
      //search.tearDown();
  }
  
  @Test
  public void testGoogleSearch() throws Exception {
    driver.get(baseUrl + "/wiki/Formal_methods");
    driver.get(baseUrl + "/?gws_rd=cr&ei=Mg24UuioPOXu0gXr34HYDg");
    driver.findElement(By.id("gbqfq")).clear();
    driver.findElement(By.id("gbqfq")).sendKeys("formal methods");
    driver.findElement(By.id("gbqfb")).click();
    driver.findElement(By.linkText("Formal methods - Wikipedia, the free encyclopedia")).click();
    assertEquals("Formal methods - Wikipedia, the free encyclopedia", driver.getTitle());
    // ERROR: Caught exception [ERROR: Unsupported command [runScript | alert("Heureka"); | ]]
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
