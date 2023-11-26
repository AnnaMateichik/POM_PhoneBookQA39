package tests;

import config.AppiumConfig;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class UpdateContactTests extends AppiumConfig {
int i;
    @BeforeSuite
    public void precondition() {
        i = new Random().nextInt(1000) + 1000;
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .fillEmail("anna@mail.com")
                .fillPassword("Qq12345$")
                .submitLogin();
    }
    @Test
    public void updateOneContact(){
        new ContactListScreen(driver)
                .updateOneContact()
                .updateName("Updaetd name_" + i)
                .submitEditContactForm()
                .isContactContains("Updaetd name_" + i);

    }

}
