package formalmethods;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Creates a number of new users based on the pre-defined parameters
 * @author Dell
 */

public class CreateUser {

    public enum Role
    {
        RESEARCHER, SITEMANAGER
    }
    
    public static class User
    {
        String username;
        String firstname;
        String lastname;
        String password;
        Role role;
        
        public User(String username, String firstname, String lastname, String password, Role role)
        {
            this.username  = username;
            this.lastname  = lastname;
            this.firstname = firstname;
            this.password  = password;
            this.role      = role;
        }
    }
    
    public static void main(String[] args) throws NoSuchElementException {
    
        ArrayList<User> users = new ArrayList<User>();
        
        //instead we can read them from file, console, where-ever
        User test1 = new User("test4","test4","test4","test4",Role.valueOf("researcher".toUpperCase()));
        users.add(test1);
        User test2 = new User("test2","test2","test2","test2",Role.valueOf("sitemanager".toUpperCase()));
        users.add(test2);
        
        
        //set the logger to the highest level to avoid all verbose and unneeded warnings
        Logger logger = Logger.getLogger ("");
        logger.setLevel (Level.SEVERE);
        
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Google
        driver.get("http://www.eha.ee/labs/digiaines_dev/index.php/site/login");

        //try to locate the Wikipedia link
        try
        {
            // Find the text input element by its name
            WebElement username = driver.findElement(By.id("LoginForm_username"));
            username.sendKeys("admin");

            (driver.findElement(By.id("LoginForm_password"))).sendKeys("root");

            // Now submit the form. WebDriver will find the form for us from the element
            username.submit();
            
            // create anew WebDriverWait object from the previous "regular" driver
            // wait until the page is loaded and link is clickable
            WebDriverWait wait = new WebDriverWait(driver, 5);
            
            //navigate to form for creating new users
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Halda")));
            (driver.findElement(By.linkText("Halda"))).click();
            
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Kasutajad")));
            (driver.findElement(By.linkText("Kasutajad"))).click();
            
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Uus kasutaja")));
            (driver.findElement(By.linkText("Uus kasutaja"))).click();
            
            wait.until(ExpectedConditions.elementToBeClickable(By.id("User_username")));
            
            for(int i=0; i<users.size(); i++)
            {
                wait.until(ExpectedConditions.elementToBeClickable(By.id("btnSubmit")));
                
                //output validation errors and break
                if(!(driver.findElements(By.className("errorSummary"))).isEmpty())
                {
                    System.out.println("Error occured!");
                    System.out.println((driver.findElement(By.className("errorSummary"))).getText());
                    break;
                } else {
                    User user = users.get(i);
                    
                    //fill in the form and submit
                    username = driver.findElement(By.id("User_username"));
                    username.sendKeys(user.username);

                    (driver.findElement(By.id("User_password"))).sendKeys(user.password);
                    (driver.findElement(By.id("User_repeatPassword"))).sendKeys(user.password);
                    (driver.findElement(By.id("User_firstname"))).sendKeys(user.firstname);
                    (driver.findElement(By.id("User_lastname"))).sendKeys(user.lastname);
                    (driver.findElement(By.id("User_role"))).sendKeys((user.role).toString());

                    username.submit();
                }
                
                wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Uus kasutaja")));
                (driver.findElement(By.linkText("Uus kasutaja"))).click();
                
            }
            
            
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            System.out.println("Done");
            driver.quit();
        }
    }
}
