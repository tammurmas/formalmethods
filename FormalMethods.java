package formalmethods;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class FormalMethods {

    public static void main(String[] args) throws NoSuchElementException {
    
        File file = new File("E:/selenium-2.39.0/IEDriverServer_x64_2.39.0/IEDriverServer.exe");
        System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
        
        //set the logger to the highest level to avoid unneeded warnings
        Logger logger = Logger.getLogger ("");
        logger.setLevel (Level.SEVERE);
        
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        WebDriver driver = new InternetExplorerDriver();

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
    }
}
