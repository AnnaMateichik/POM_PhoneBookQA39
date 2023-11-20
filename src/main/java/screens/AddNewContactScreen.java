package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import models.Auth;
import models.Contact;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class AddNewContactScreen extends BaseScreen{
    public AddNewContactScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/inputName']")
    MobileElement nameEditText;
    @FindBy(id="com.sheygam.contactapp:id/inputLastName") //// +
    MobileElement lastNameEditText;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/inputEmail']")
    MobileElement emailEditText;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/inputPhone']")
    MobileElement phoneEditText;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/inputAddress']")
    MobileElement addressEditText;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/inputDesc']")
    MobileElement descriptionEditText;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/createBtn']")
    MobileElement createButton;
    @FindBy(xpath = "//*[@resource-id='android:id/message']")
    MobileElement errorTextViewAddContact;

    @FindBy(xpath = "//*[@resource-id='android:id/button1']")
    MobileElement okButton;

    public AddNewContactScreen fillContactForm(Contact contact){
        waitElement(nameEditText, 15);
        type(nameEditText, contact.getName());
        type(lastNameEditText, contact.getLastName());
        type(emailEditText, contact.getEmail());
        type(phoneEditText, contact.getPhone());
        type(addressEditText, contact.getAddress());
        type(descriptionEditText, contact.getDescription());
        return this;
    }

    public ContactListScreen submitContactForm(){
        createButton.click();
        return new ContactListScreen(driver);
    }
   public AddNewContactScreen submitContactFormNegative(){
        createButton.click();
        return this;
    }

    public AddNewContactScreen isErrorMessageContainsTextAddContact(String text){
        Assert.assertTrue(errorTextViewAddContact.getText().contains(text));
        return this;
    }

    public AddNewContactScreen addContactNegative(Contact contact){
      fillContactForm(contact);
      submitContactFormNegative();
        return this;
    }
    public AddNewContactScreen isErrorMessageContainsTextInAlertAddContact(String text){
        Alert alert = new WebDriverWait(driver, 3).until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert();
        Assert.assertTrue(alert.getText().contains(text));
        alert.accept();
        return this;
    }




}
