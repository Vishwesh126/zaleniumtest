package com.zalenium.dummy;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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

public class OtherSiteDownloadTest {

	//	static String executionArea = "Local";
	static String executionArea = "Zalenium";
	static String browserName = "Chrome";
	static WebDriver wd = null;
	static String localFileToUpload = "C:/Users/mjvis/ZalVids/img/linux.png";
	static String remoteFileToUpload = "/home/seluser/Downloads/chromedriver_mac32.zip";
	static String zaleniumTestName = "FileDownloadTest";

	@Test
	public void FileUploadTest() throws Exception {
		System.out.println("######## Test Started ########");
		if(executionArea.equalsIgnoreCase("local")) {
			WebDriverManager.chromedriver().setup();
			wd = new ChromeDriver();
			System.out.println("Local Chrome Driver initiated");
		} else if(executionArea.equalsIgnoreCase("zalenium")) {
			wd = new RemoteWebDriver(new URL("http://localhost:9090/wd/hub"), addChromeOptions());
			System.out.println("Remote Chrome Driver initiated");
		}

		wd.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wd.manage().window().maximize();

		try {
			downloadFileForUpload();
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
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.prompt_for_download", false);
		prefs.put("download.default_directory", "/home/seluser/Downloads/");
		opts.setExperimentalOption("prefs", prefs);
		opts.addArguments("--no-sandbox");
		opts.addArguments("--disable-dev-shm-usage");
		opts.addArguments("--disable-gpu");
		
		opts.merge(caps);
		return opts;
	}


	public static void downloadFileForUpload() throws Exception {
		wd.get("http://www.landxmlproject.org/file-cabinet");
		System.out.println("URL launched for downloading the file");
		int randomInt = (int)(50.0 * Math.random());
		System.out.println("Random Number: "+randomInt);
		try {
			wd.findElement(By.xpath("(//a[text()='Download'])["+randomInt+"]")).click();
			Thread.sleep(10000);
			System.out.println("File Downloaded!!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Issue while downloading the file!!");
		}

	}


}
