package formalmethods;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FirefxDriver {
    
    public static void main(String[] args) throws NoSuchElementException {    
        
        //set the logger to the highest level to avoid unneeded warnings
        Logger logger = Logger.getLogger ("");
        logger.setLevel (Level.SEVERE);
        
        // Create a new instance of Firefox driver
        WebDriver driver = new FirefoxDriver();

        // And now use this to visit Google
        driver.get("http://www.google.com");

        // Find the text input element by its name
        WebElement element = driver.findElement(By.name("q"));

        // Enter something to search for
        element.sendKeys("Formal methods in software engineering");

        // Now submit the form. WebDriver will find the form for us from the element
        element.submit();

        //try to locate the Wikipedia link
        try
        {
            String linkName = "Formal methods - Wikipedia, the free encyclopedia";
            
            // create anew WebDriverWait object from the previous "regular" driver
            // wait until the page is loaded and link is clickable
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkName)));
            
            WebElement link = driver.findElement(By.linkText(linkName));
            //click the link
            link.click();

            //get the title
            if(driver.getTitle().equals(linkName))
                System.out.println("Heureka!");
            else
                System.out.println("Weird...");
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            driver.quit();
        }
        
    }
}
