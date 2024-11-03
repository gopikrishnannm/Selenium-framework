package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;




public class TC_003_LoginDDT extends BaseClass{
	
	@Test(dataProvider = "LoginData", dataProviderClass=DataProviders.class, groups="DataDrivern")
	public void verify_loginDDT(String email, String pwd, String exp) {
		
		
		logger.info("**** Starting TC_003_LoginDDT *****");

		try {
			
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			lp.clickLogin();
			
			MyAccountPage map = new MyAccountPage(driver);
			boolean targetPage = map.isMyAccountPageExits();
			
			/*Data is valid  - login success - test pass  - logout
			Data is valid -- login failed - test fail

			Data is invalid - login success - test fail  - logout
			Data is invalid -- login failed - test pass
			*/
			
			if(exp.equalsIgnoreCase("valid")) {
				if(targetPage == true) {
					Assert.assertTrue(true);
					map.clickLogout();
				}
				else {
					Assert.assertTrue(false);
				}
			}
			if(exp.equalsIgnoreCase("invalid")) {
				if(targetPage == true) {
					map.clickLogout();
					Assert.assertFalse(false);
				}
				else {
					Assert.assertTrue(true);
					
				}
			}
			
		}
		catch(Exception e) {
			Assert.fail();
		}
		
		
		logger.info("**** Finished TC_003_LoginDDT *****");

	}

}
