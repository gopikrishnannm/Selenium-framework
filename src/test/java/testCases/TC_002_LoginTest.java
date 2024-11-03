package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass{
	
	@Test(groups={"Sanity", "Master"})
	public void verify_login() {
		
		logger.info("**** Starting TC_002_LoginTest *****");
		
		try {
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("username"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			
			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExits();
			Assert.assertEquals(targetPage, true,"Login Failed");
		}
		catch(Exception e) {
			Assert.fail();
		}
		
		
		logger.info("**** Finished TC_002_LoginTest *****");
	}

}
