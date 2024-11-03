package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass{
	
	
	
	@Test(groups={"Regression","Master"})
	void testAccountRegistration() {
		
		
		logger.info("testAccountRegistration tc started");
		
		
		try {
			HomePage hm = new HomePage(driver);
			
			
			hm.clickMyAccount();
			logger.info("clicked on my account");
			hm.clickRegister();
			logger.info("clicked on register");
			
			
			logger.info("providing customer details");
			
			AccountRegistrationPage arp = new AccountRegistrationPage(driver);
			arp.setFirstName(randomString().toUpperCase());
			arp.setLastName(randomString().toUpperCase());
			arp.setEmail(randomString() + "@gmail.com");
			
			String password = randomAphaNumeric();
			arp.setPassword(password);
			arp.setConfirmPassword(password);
			arp.setTelephone(randomNumber());
			arp.setPrivacyPolicy();
			
			arp.clickContinue();
			
			
			logger.info("validate message");
			String confirmationMessage = arp.getConfirmationMessage();
			Assert.assertEquals(confirmationMessage, "Your Account Has Been Created!");
			if(confirmationMessage.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			}
			else {
				logger.error("test failed");
				logger.debug("Debug logs");
			}
		}
		catch(Exception e) {
			
			Assert.fail();
		}
		logger.info("Finished testAccountRegistration");

	}
	
	
	
	
	

}
