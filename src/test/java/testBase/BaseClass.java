package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;


public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups = {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String browser) throws IOException {
		
		
		p = new Properties();
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p.load(file);
		
		
		logger = LogManager.getLogger(this.getClass());
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			
			String hubUrl = "http://localhost:4444/wd/hub";
			
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setPlatform(Platform.WIN11);
			
			if(os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				cap.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("no matching os");
				return;
			}
			
			switch(browser.toLowerCase()) {
			
			case "chrome" :  cap.setBrowserName("chrome");break;
			case "edge" : cap.setBrowserName("MicrosoftEdge");break;
			default : System.out.println("Invalid browser name");return;
			}
			
			driver = new RemoteWebDriver(new URL(hubUrl), cap);
		}
		
		else {
			switch(browser.toLowerCase()) {
			
			case "chrome" :  driver = new ChromeDriver();break;
			case "firefox" : driver = new FirefoxDriver();break;
			case "edge" : driver = new EdgeDriver();break;
			default : System.out.println("Invalid browser name");return;
			}
		}
		
		
		
		
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Sanity", "Regression", "Master"})
	public void tearDown() {
		driver.quit();
	}
	
	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}
	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}
	public String randomAphaNumeric() {
		return RandomStringUtils.randomAlphabetic(5)+
				RandomStringUtils.randomNumeric(5);
	}
	public String captureScreen(String tname) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
				
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
			
		return targetFilePath;

	}


}
