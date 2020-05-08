package automation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AjioClass { 
	
	public static RemoteWebDriver driver; 
	
	public String Window() { 
		Set<String> Set = driver.getWindowHandles(); 
		List<String> List = new ArrayList<String>(Set); 
		int size = List.size(); 
		return List.get(size-1); 
	}

	public static void main(String[] args) throws InterruptedException { 
		
		AjioClass obj = new AjioClass(); 
		
		// Launching the application
		System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.silentOutput", "true");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.DISMISS);
		options.merge(cap);
		// go to url

		driver = new ChromeDriver(options);
		driver.get("https://www.ajio.com/shop/sale");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		Actions act = new Actions(driver);
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		
		// This block is consuming time initially 
		try {
			driver.findElementByXPath("//div[@class='ic-close-quickview']").click();
		} catch (Exception e) {
			
			e.printStackTrace();
		} 
		
		// Enter Bags in the Search field and Select Bags in Women Handbags

		driver.findElementByName("searchVal").sendKeys("Bags"); 
		Thread.sleep(3000); 
		act.click(driver.findElementByXPath("//span[text()='Bags in ']//following-sibling::span[text()='Women Handbags']/parent::a")).build().perform(); 
		Thread.sleep(5000); 
		
		// Click on five grid and Select SORT BY as "What's New"

		driver.findElementByXPath("//div[@class='five-grid']").click(); 
		
		
		WebElement sortElement = driver.findElementByXPath("//div[@class='filter-dropdown']//select"); 
		Select sortOptions = new Select(sortElement); 
		sortOptions.selectByValue("newn"); 

		// Enter Price Range Min as 2000 and Max as 5000

		driver.findElementByXPath("//span[text()='price']").click(); 
		driver.findElementById("minPrice").sendKeys("2000"); 
		driver.findElementById("maxPrice").sendKeys("5000"); 
		driver.findElementByXPath("//div[@class='input-price-filter']//button[@type='submit']").click(); 
		Thread.sleep(3000); 
		
		// Click on the product "Puma Ferrari LS Shoulder Bag"
		driver.findElementByXPath("//div[text()='Ferrari LS Shoulder Bag']").click(); 
		Thread.sleep(2000); 
		
		// Switching to the new window 
		driver.switchTo().window(obj.Window()); 
		Thread.sleep(3000); 
		// Verify the Coupon code for the price above 2690 is applicable for your product, if applicable the get the Coupon Code and Calculate the discount and price for the coupon

		String productPriceStr = driver.findElementByXPath("//div[@class='prod-sp']").getText(); 
		int productPrice = Integer.parseInt(productPriceStr.replaceAll("\\D", "")); 
		
		String couponStr = driver.findElementByXPath("//div[@class='promo-desc']").getText(); 
		
		int discountedPrice = 0; 
		int savings = 0; 
		
		if (couponStr.contains("2690")) { 
			String coupon = driver.findElementByXPath("//div[@class='promo-title']").getText(); 
			System.out.println("\nCoupon code: " + coupon);
			String discountedPriceStr = driver.findElementByXPath("//div[@class='promo-discounted-price']").getText(); 
			discountedPrice = Integer.parseInt(discountedPriceStr.replaceAll("\\D", "")); 
			System.out.println("Discounted Price details: " + discountedPrice); 
		}
		
		savings = productPrice - discountedPrice; 
		System.out.println("Savings amount: " + savings); 
		
		// Check the availability of the product for pincode 560043, print the expected delivery date if it is available

		driver.findElementByXPath("//span[@class='ic-address edd-pincode-msg-address-icon']//following-sibling::span").click(); 
		Thread.sleep(3000); 
		driver.findElementByName("pincode").sendKeys("603209"); 
		driver.findElementByClassName("edd-pincode-modal-submit-btn").click(); 
		Thread.sleep(3000); 
		
		String expDelivery = driver.findElementByXPath("//li[contains(text(),'Expected Delivery')]//span").getText(); 
		System.out.println("Expected Delivery Date: " + expDelivery); 
		

		// Click on Other Informations under Product Details and Print the Customer Care,address, phone and email

		
		driver.findElementByClassName("other-info-toggle").click();
		

		List<WebElement> prodList = driver.findElementsByXPath("//ul[@class='prod-list']//li"); 
		
		System.out.println("\nProduct Details:");
		for (int i = 0; i < prodList.size(); i++) { 
			String productDetails = prodList.get(i).getText(); 
			System.out.println(productDetails);
		} 
		
		// Click on ADD TO BAG and then GO TO BAG
		driver.findElementByClassName("ic-pdp-add-cart").click(); 
		Thread.sleep(5000); 
		
		act.moveToElement(driver.findElementByXPath("//div[@class='ic-cart ']")).build().perform(); 
		act.click(driver.findElementByClassName("mini-cart-btn")).build().perform(); 
		Thread.sleep(3000);
		// Check the Order Total before apply coupon
 
		String orderTotalStr = driver.findElementByXPath("//span[text()='Order Total']//following-sibling::span").getText(); 
		double orderTotalDouble = Double.parseDouble(orderTotalStr.replaceAll("\\D", "")); 
		System.out.println("\nOrder Total from the checkout page: " + orderTotalDouble);
		
		// Converting the previous int to double 
		double productPriceDouble = (double)productPrice; 
		
		
		if (orderTotalDouble == productPriceDouble) { 
			System.out.println("Order Total is correct.");
		} else { 
			System.out.println("Incorrect Order Total.");
		}
		
		// Enter Coupon Code and Click Apply

		driver.findElementById("EPIC").click(); 
		driver.findElementByXPath("//button[text()='Apply']").click(); 
		Thread.sleep(3000); 
		
		// Verifying the savings in the checkout page 
		String savingsAmountStr = driver.findElementByXPath("//span[@class='cart-total-saving-text']").getText();
		savingsAmountStr = savingsAmountStr.replaceAll("[^0-9.]", ""); 
		
		String[] amountSplit = savingsAmountStr.split(".", 2); 
		
		double savingsDouble = Double.parseDouble(amountSplit[1]); 
		
		// Verify the Coupon Savings amount(round off if it in decimal) under Order Summary and the matches the amount calculated in Product details
 
		int roundOffSavings = (int)Math.round(savingsDouble); 
		System.out.println(roundOffSavings);
		
		System.out.println("Savings from the checkout page: " + savingsDouble);
		 
		// Comparing the two Integer savings 
		if (roundOffSavings == savings) { 
			System.out.println("Savings Amount matches.");
		} else { 
			System.out.println("Incorrect Savings."); 
		}
		// Click on Delete and Delete the item from Bag
		
		driver.findElementByClassName("delete-btn").click(); 
		driver.findElementByXPath("//div[text()='DELETE']").click(); 
		Thread.sleep(3000); 
		

		// Close all the browsers

		driver.quit(); 
		
		

	}

}