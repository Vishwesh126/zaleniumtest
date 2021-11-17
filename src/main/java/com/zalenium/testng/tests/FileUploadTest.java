package com.zalenium.testng.tests;

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
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FileUploadTest {

	//	static String executionArea = "Local";
	static String executionArea = "Zalenium";
	static String browserName = "Chrome";
	static WebDriver wd = null;
	static String localFileToUpload = "C:/Users/mjvis/ZalVids/img/linux.png";
	static String remoteFileToUpload = "/home/seluser/Downloads/chromedriver_mac64.zip";
	static String zaleniumTestName = "FileUploadTest";

	@Test
	public void FileDownloadTest() throws Exception {
		System.out.println("######## Test Started ########");
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

		try {
			UploadFileToZalenium();
		} finally {
			wd.quit();
		}

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
		caps.setCapability("zal:name", zaleniumTestName);
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


	public static void UploadFileToZalenium() throws Exception {

		wd.get("http://demo.guru99.com/");
		System.out.println("URL launched for uploading the file");

		//Selenium Link click
		try {
			wd.findElement(By.xpath("//li/a[@class='dropdown-toggle' and contains(text(),'Selenium')]")).click();
			System.out.println("Clicked Selenium Link");
			Thread.sleep(2000);
			wd.findElement(By.xpath("//li/a[text()='File Upload']")).click();
			Thread.sleep(2000);
			System.out.println("Clicked File Upload Link");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("File Upload Link not available");
		}

		//Send the file name
		try {
			if(executionArea.equalsIgnoreCase("local")) {
				wd.findElement(By.name("uploadfile_0")).sendKeys(localFileToUpload);
			} else if(executionArea.equalsIgnoreCase("zalenium")) {
				wd.findElement(By.name("uploadfile_0")).sendKeys(remoteFileToUpload);
			}
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Upload File Choose button not available");
		}

		//Submit and Validate upload text
		try {
			wd.findElement(By.id("submitbutton")).click();
			System.out.println("Submit button Clicked");
			Thread.sleep(2000);
			if(wd.findElement(By.id("res")).getText().contains("uploaded")) {
				System.out.println("Upload Text visible and is " + wd.findElement(By.id("res")).getText());
			} else {
				throw new Exception("Upload Successful msg not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error Occured while validating the successful upload");
		}

	}




}
