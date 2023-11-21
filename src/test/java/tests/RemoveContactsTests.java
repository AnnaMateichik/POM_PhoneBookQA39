package tests;

import config.AppiumConfig;
import org.testng.annotations.*;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class RemoveContactsTests extends AppiumConfig {
    @BeforeClass
    public void precondition() {
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .fillEmail("anna@mail.com")
                .fillPassword("Qq12345$")
                .submitLogin();
    }

    @BeforeMethod
    public void providerContacts() {
        new ContactListScreen(driver)
                .provideContacts();
    }

    @Test
    public void removeOneContactPositive() {
        new ContactListScreen(driver)
                .removeOneContact()
                .isOneContactRemoved();

    }

    @Test
    public void removeAllContactsPositive() {
        new ContactListScreen(driver)
                .removeAllContacts()
                .isNoContacts();

    }


    @Test
    public void moveToLastContactInTheList() {
        new ContactListScreen(driver)
                .isEndOfList();
    }


    @AfterClass
    public void postCondition() {
        new ContactListScreen(driver).logout();
    }

}
