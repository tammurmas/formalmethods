package formalmethods;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Dell
 */
public class Archival {

    public static void main(String[] args) throws NoSuchElementException, Exception {
        //set the logger to the highest level to avoid all verbose and unneeded warnings
        Logger logger = Logger.getLogger("");
        logger.setLevel(Level.SEVERE);

        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Google
        driver.get("http://www.eha.ee/labs/digiaines_dev/index.php/site/login");

        //try to locate the Wikipedia link
        try {
            // Find the text input element by its name
            WebElement username = driver.findElement(By.id("LoginForm_username"));
            username.sendKeys("admin");

            (driver.findElement(By.id("LoginForm_password"))).sendKeys("root");

            // Now submit the form. WebDriver will find the form for us from the element
            username.submit();

            // create anew WebDriverWait object from the previous "regular" driver
            // wait until the page is loaded and link is clickable
            WebDriverWait wait = new WebDriverWait(driver, 5);

            //swithc to English
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("In English")));
            (driver.findElement(By.linkText("In English"))).click();

            //navigate to view used for creating archival data
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Describe")));
            (driver.findElement(By.linkText("Describe"))).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Describe Archival")));
            (driver.findElement(By.linkText("Describe Archival"))).click();

            //attempt to create an archival without checking any files berfore-hand
            //we should get a JavaScript alert here, otherwise throw an exception
            String alert = handleAlert(driver);
            System.out.println("System response: " + alert);
            
            //start a search to retrieve all unrelated files
            WebElement unrelated = driver.findElement(By.id("FileSearchForm_relation_id_1"));
            unrelated.click();

            unrelated.submit();

            try {
                String handle = driver.getWindowHandle();//get current window handle
            
                Thread.sleep(5000);//wait unitl search has finished (this is really stupid actually, trying to guess how long it'd take)
                
                WebElement fileChkBox = driver.findElement(By.id("file_keys_0"));
                fileChkBox.click();
                (driver.findElement(By.linkText("Describe Archival"))).click();
                
                Object[] handles = driver.getWindowHandles().toArray();
                
                for (int i=0; i<handles.length; i++) 
                {
                    if(!handles[i].toString().equals(handle))
                    {
                        driver.switchTo().window(handles[i].toString());//switch to newly opened window
                        break;
                    }
                }
                
                //insert a title for the new archival and submit form
                wait.until(ExpectedConditions.elementToBeClickable(By.id("DescUnit_title")));
                WebElement title = driver.findElement(By.id("DescUnit_title"));
                title.sendKeys("Random Title");
                title.submit();
                
                driver.switchTo().window(handle);//switch back to previous window
                (driver.findElement(By.linkText("Describe Archival"))).click();
                
                alert = handleAlert(driver);
                System.out.println("System reponse: "+alert);
                        
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());//just in case there are no files
            }


        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        } finally {
            driver.quit();
        }
    }

    /**
     * Helper to handle JavaScript dialogs
     * @param driver
     * @return 
     */
    protected static String handleAlert(WebDriver driver) {
        String alertText;
        driver.switchTo().alert();
        try {
            Alert alert = driver.switchTo().alert();
            alertText = alert.getText();
            alert.dismiss();
        } catch (NoAlertPresentException e) {
            return e.getMessage();
        }

        return alertText;
    }
}
