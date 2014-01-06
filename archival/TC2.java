package formalmethods.archival;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import junit.framework.TestCase;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TC2 extends TestCase {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.eha.ee";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testTC2() throws Exception {
    driver.get(baseUrl + "/labs/digiaines_dev/index.php/site/login");
    driver.findElement(By.id("LoginForm_username")).clear();
    driver.findElement(By.id("LoginForm_username")).sendKeys("admin");
    driver.findElement(By.id("LoginForm_password")).clear();
    driver.findElement(By.id("LoginForm_password")).sendKeys("root");
    driver.findElement(By.name("yt0")).click();
    driver.findElement(By.linkText("In English")).click();
    driver.findElement(By.linkText("Describe")).click();
    driver.findElement(By.id("FileSearchForm_relation_id_1")).click();
    driver.findElement(By.name("yt0")).click();
    
    Thread.sleep(3000);//force the browser to wait up on searching action, a dirty hack to be honest
    
    driver.findElement(By.id("file_keys_0")).click();
    driver.findElement(By.linkText("Describe Archival")).click();
    
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | name=_new | ]]
    String handle = driver.getWindowHandle();//remember current handle
    driver.switchTo().window("_new");//a little fix to switch to newly created tab
    
    driver.findElement(By.id("DescUnit_title")).clear();
    driver.findElement(By.id("DescUnit_title")).sendKeys("Random Title");
    driver.findElement(By.id("btnSubmit")).click();
    
    // ERROR: Caught exception [ERROR: Unsupported command [selectWindow | null | ]]
    driver.switchTo().window(handle);//switch back to main window
    
    driver.findElement(By.linkText("Describe Archival")).click();
    String alertText = closeAlertAndGetItsText();
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
