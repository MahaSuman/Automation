package automation;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Microsoft {
	public static void main(String[] args) throws InterruptedException
	{
	System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
	
		ChromeOptions options=new ChromeOptions();
	options.addArguments("--disable-Notifications");

	ChromeDriver driver=new ChromeDriver(options);
	WebDriverWait wait=new WebDriverWait(driver,20);

	//1)https://azure.microsoft.com/en-in/
	driver.get("https://azure.microsoft.com/en-in/");
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	//2) Click on Pricing

	driver.findElementById("navigation-pricing").click();
	//3) Click on Pricing Calculator
	driver.findElementByXPath("//a[contains(text(),'Pricing calculator')]").click();
	//4) Click on Containers
	driver.findElementByXPath("//button[text()='Containers']").click();
	//5) Select Container Instances
	driver.findElementByXPath("(//button[@title='Container Instances'])[2]").click();
	
	//6) Click on Container Instance Added View
	WebElement view=driver.findElementByXPath("//a[@id='new-module-loc']");
	wait.until(ExpectedConditions.visibilityOf(view)).click();
	//7) Select Region as "South India"
	WebElement region=driver.findElementByName("region");
	Select region1=new Select(region);
	region1.selectByVisibleText("South India");
	//8) Set the Duration as 180000 seconds
	WebElement duration=driver.findElementByXPath("(//input[@class='text-input numeric'])[2]");
	duration.click();
	duration.sendKeys(Keys.DELETE);
	duration.sendKeys(Keys.DELETE);
	duration.sendKeys("180000");
	
	//9) Select the Memory as 4GB
WebElement memory=driver.findElementByName("memory");
Select memory1=new Select(memory);
memory1.selectByValue("4");

Thread.sleep(5000);
//10) Enable SHOW DEV/TEST PRICING
driver.findElementByXPath("//button[@id='devtest-toggler']").click();

//11) Select Indian Rupee as currency

WebElement currency=driver.findElementByXPath("//select[@class='select currency-dropdown']");
Select selcur=new Select(currency);
selcur.selectByVisibleText("Indian Rupee (₹)");


//12) Print the Estimated monthly price
String cost=driver.findElementByXPath("(//span[@class='numeric']/span)[4]").getText();
System.out.println("Estimated monthly price" +cost);
	
//13) Click on Export to download the estimate as excel
driver.findElementByXPath("//button[@class='calculator-button button-transparent export-button']").click();


//14) Verify the downloded file in the local folder
		File file = new File("C:\\Users\\suman\\Downloads\\ExportedEstimate.xlsx");
		if(file.exists())
		{
			System.out.println("Estimate file Downloaded successfully");
		}
		else
		{
			System.out.println("Estimate file does not exists in the folder");
		}

//15) Navigate to Example Scenarios and Select CI/CD for Containers

JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("window.scrollTo(0,0)");
//WebElement example=driver.findElementByXPath("//a[text()='Example Scenarios']");
WebElement example=driver.findElementByXPath("//*[@id=\"solution-architectures-picker\"]");

wait.until(ExpectedConditions.elementToBeClickable(example));
example.click();


js.executeScript("document.querySelector(\"button[title='CI/CD for Containers']\").scrollIntoViewIfNeeded()");
Thread.sleep(2000);
WebElement cicdContainer = driver.findElementByXPath("//button[@title='CI/CD for Containers']");
wait.until(ExpectedConditions.elementToBeClickable(cicdContainer));
cicdContainer.click();

//16) Click Add to Estimate
;
//WebElement ab=driver.findElementByXPath("//button[text()='Add to estimate']");
//wait.until(ExpectedConditions.elementToBeClickable(ab));
//ab.click();

Thread.sleep(3000);
WebElement scroll = driver.findElementByXPath("//button[text()='Add to estimate']");
JavascriptExecutor js1 = (JavascriptExecutor)driver;
js1.executeScript("arguments[0].click();", scroll);

//17) Change the Currency as Indian Rupee


WebElement currency1 = driver.findElementByXPath("//select[@class='select currency-dropdown']");
Select selcur1 = new Select(currency);
selcur.selectByVisibleText("Indian Rupee (₹)");

//18) Enable SHOW DEV/TEST PRICING

Thread.sleep(3000);
WebElement toggle1 = driver.findElementByXPath("(//label[@for='devtest-toggler']//div)[1]");
JavascriptExecutor executor2 = (JavascriptExecutor)driver;
executor2.executeScript("arguments[0].click();", toggle1);

//19) Export the Estimate
driver.findElementByXPath("//button[text()='Export']").click();
Thread.sleep(5000);
System.out.println("Estimation file has been downloaded");

File file1 = new File("C:\\Users\\suman\\Downloads\\ExportedEstimate (1).xlsx");
if(file1.exists())
{
	System.out.println("Estimate export successful");
}
else
{
	System.out.println("Estimate download is not successful");
}

driver.quit();
	}

}










//14) Verify the downloded file in the local folder




//20) Verify the downloded file in the local folder
