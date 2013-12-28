package formalmethods;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class Login {

    public static void main(String[] args) throws NoSuchElementException {
    
        //set the logger to the highest level to avoid all verbose and unneeded warnings
        Logger logger = Logger.getLogger ("");
        logger.setLevel (Level.SEVERE);
        
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new HtmlUnitDriver();

        // And now use this to visit Google
        driver.get("http://www.eha.ee/labs/digiaines_dev/index.php/site/login");

        // Find the text input element by its name
        //WebElement element = driver.findElement(By.name("q"));

        WebElement user = driver.findElement(By.id("LoginForm_username"));
        // Enter something to search for
        user.sendKeys("admin");

        WebElement password = driver.findElement(By.id("LoginForm_password"));
        // Enter something to search for
        password.sendKeys("root");
        
        // Now submit the form. WebDriver will find the form for us from the element
        password.submit();

        //try to locate the Wikipedia link
        try
        {
            WebElement link = driver.findElement(By.linkText("Kirjelda"));
            
            System.out.println("Done");
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
