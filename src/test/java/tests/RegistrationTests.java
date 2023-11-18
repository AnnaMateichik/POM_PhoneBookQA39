package tests;

import config.AppiumConfig;
import models.Auth;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import screens.ContactListScreen;
import screens.SplashScreen;

import java.util.Random;

public class RegistrationTests extends AppiumConfig {

    int i;
    @BeforeMethod
            public void precondition(){
        i = new Random().nextInt(1000) + 1000;
    }


    @Test
    public void registrationPositive() {
                new SplashScreen(driver)
                        .gotoAuthenticationScreen()
                        .fillEmail("anna" + i + "@mail.com")
                        .fillPassword("Qq12345$")
                        .submitRegistration()
                        .assertContactListActivityPresent();



    }

    @Test
    public void registrationPositiveModel() {
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .registration(
                        Auth.builder()
                                .email("anna" + i +"@mail.com")
                                .password("Qq12345$")
                                .build()
                )
                .assertContactListActivityPresent();

    }
    @Test
    public void registrationNegativeWrongEmail() {
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .fillEmail("anna" + i + "mail.com")
                .fillPassword("Qq12345$")
                .submitRegistrationNegative()
                .isErrorMessageContainsText("email address");

    }

    @Test
    public void registrationNegativeWrongPassword() {
        new SplashScreen(driver)
                .gotoAuthenticationScreen()
                .registrationNegative(
                        Auth.builder()
                                .email("anna" + i +"@mail.com")
                                .password("Qq123456")
                                .build()
                )
//                .isErrorMessageContainsText("password");
                .isErrorMessageContainsTextInAlert("password");
    }

    @AfterMethod
    public void postCondition(){
        new ContactListScreen(driver).logout();
        new SplashScreen(driver);
    }


}
