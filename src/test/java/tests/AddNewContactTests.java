package tests;

import config.AppiumConfig;
import models.Contact;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class AddNewContactTests extends AppiumConfig {
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

    @Test(invocationCount = 3)
    public void addNewContactPositive() {
        Contact contact = Contact.builder()
                .name("Mira")
                .lastName("Silver")
                .phone("12345678" + i)
                .email("mira" + i + "@mail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAdded(contact);


    }

    @Test
    public void addNewContactNegativeWrongEmail() {
        Contact contact = Contact.builder()
                .name("Mira")
                .lastName("Silver")
                .phone("12345678" + i)
                .email("miramail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen(driver)
//                .openContactForm()
//                .fillContactForm(contact)
//                .submitContactFormNegative()
//                .isErrorMessageContainsTextAddContact("email");
                .openContactForm()
                .addContactNegative(contact)
                .isErrorMessageContainsTextInAlertAddContact("email");

    }

    @Test
    public void addNewContactNegativeWrongPhone() {
        Contact contact = Contact.builder()
                .name("Mira")
                .lastName("Silver")
                .phone("123456789")
                .email("mira" + i + "@mail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen(driver)
                .openContactForm()
//                .fillContactForm(contact)
//                .submitContactFormNegative()
//                .isErrorMessageContainsTextAddContact("email");
                .addContactNegative(contact)
                .isErrorMessageContainsTextInAlertAddContact("phone");


    }


}
