package tests;

import config.AppiumConfig;
import models.Contact;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.AddNewContactScreen;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class AddNewContactTests extends AppiumConfig {
int i;
    @BeforeMethod
    public void precondition(){
        i = new Random().nextInt(1000) + 1000;
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .fillEmail("anna@mail.com")
                .fillPassword("Qq12345$")
                .submitLogin();
    }

    @Test
    public void addNewContactPositive(){
        Contact contact = Contact.builder()
                .name("Mira")
                .lastName("Silver")
                .phone("12345678" + i)
                .email("mira" + i + "@mail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen( driver)
                .oppenContactForm()
                .fillContactForm(contact)
                .submitContactForm();


    }
    @Test
    public void addNewContactNegativeWrongEmail(){
        Contact contact = Contact.builder()
                .name("Mira")
                .lastName("Silver")
                .phone("12345678" + i)
                .email("miramail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen( driver)
                .oppenContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorMessageContainsTextAddContact("email");


    }
    @Test
    public void addNewContactNegativeWrongPhone(){
        Contact contact = Contact.builder()
                .name("Mira")
                .lastName("Silver")
                .phone("123456789")
                .email("mira" +i + "mail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen( driver)
                .oppenContactForm()
//                .fillContactForm(contact)
//                .submitContactFormNegative()
//                .isErrorMessageContainsTextAddContact("email");
                .addContactNegative(contact)
                .isErrorMessageContainsTextInAlertAddContact("phone");


    }
    @Test
    public void addNewContactNegativeWrongNameSpace(){
        Contact contact = Contact.builder()
                .name(" ")
                .lastName("Silver")
                .phone("12345678" + i)
                .email("mira" +i + "mail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen(driver)
                .oppenContactForm()
                .addContactNegative(contact)
                .isErrorMessageContainsTextInAlertAddContact("name");


    }
    @Test
    public void addNewContactNegativeWrongLastNameSpace(){
        Contact contact = Contact.builder()
                .name("Mora")
                .lastName(" ")
                .phone("12345678" + i)
                .email("mira" +i + "mail.com")
                .address("Rehovot")
                .description("NewPrivate")
                .build();

        new ContactListScreen(driver)
                .oppenContactForm()
                .addContactNegative(contact)
                .isErrorMessageContainsTextInAlertAddContact("lastName");


    }

    @AfterMethod
    public void postCondition(){
        new ContactListScreen(driver).logout();
        new SplashScreen(driver);
    }
}
