package com.zalenium.dummy;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUpload {
	
//	static String executionArea = "Local";
	static String executionArea = "Zalenium";
	static String browserName = "Chrome";
	static String fileToUpload = "C:\\Users\\mjvis\\ZalVids\\img\\linux.png";
	
	
	public static void main(String[] args) throws Exception {

		System.out.println("######## Test Started ########");
		WebDriver wd = null;

		if(executionArea.equalsIgnoreCase("local")) {
			WebDriverManager.chromedriver().setup();
			wd = new ChromeDriver();
			System.out.println("Local Chrome Driver initiated");
		} else if(executionArea.equalsIgnoreCase("zalenium")) {
			wd = new RemoteWebDriver(new URL("http://localhost:8585/wd/hub"), addChromeOptions());
			System.out.println("Remote Chrome Driver initiated");
		}
		
		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wd.manage().window().maximize();
		wd.get("http://demo.guru99.com/");
		System.out.println("URL launched");

		//Selenium Link click
		try {
			wd.findElement(By.xpath("//li/a[@class='dropdown-toggle' and contains(text(),'Selenium')]")).click();
			System.out.println("Clicked Selenium Link");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Selenium Link not available");
		}

		//File Upload link click
		try {
			wd.findElement(By.xpath("//li/a[text()='File Upload']")).click();
			System.out.println("Clicked File Upload Link");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("File Upload Link not available");
		}

		//Submit button
		try {
			wd.findElement(By.id("submitbutton")).click();
			System.out.println("Submit button Clicked");
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Submit button not available");
		}
		
		//Send the file name
		try {
			wd.findElement(By.name("uploadfile_0")).sendKeys(fileToUpload);
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Upload File Choose button not available");
		}

		//Validate upload text
		try {
			if(wd.findElement(By.id("res")).getText().contains("uploaded")) {
				System.out.println("Upload Text visible and is " + wd.findElement(By.id("res")).getText());
			} else {
				throw new Exception("Upload Successful msg not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error Occured while validating the successful upload");
		}
		
		
		
		wd.quit();
		System.out.println("######## Test Successful ########");

	}
	
	public static ChromeOptions addChromeOptions() {
		DesiredCapabilities caps = new DesiredCapabilities();
		if(browserName.equalsIgnoreCase("chrome")) {
			caps.setCapability(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
		} else if(browserName.equalsIgnoreCase("firefox")) {
			caps.setCapability(CapabilityType.BROWSER_NAME, BrowserType.FIREFOX);
		}
		caps.setCapability(CapabilityType.PLATFORM_NAME, Platform.LINUX);
		caps.setCapability("zal:name", "MyZaleniumTesting");
		caps.setCapability("zal:build", "MyZaleniumBuild");
		caps.setCapability("zal:tz", "Asia/Kolkata");
		caps.setCapability("zal:screenResolution", "1280x720");
		caps.setCapability("zal:idleTimeout", 6000);
		caps.setCapability("zal:recordVideo", true);
		ChromeOptions opts = new ChromeOptions();
		opts.addArguments("--no-sandbox");
		opts.addArguments("--disable-dev-shm-usage");
		opts.addArguments("--disable-gpu");
		opts.merge(caps);
		return opts;
	}

}




//Notes
//https://www.guru99.com/upload-download-file-selenium-webdriver.html
