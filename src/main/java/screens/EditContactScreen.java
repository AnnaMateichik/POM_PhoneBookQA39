package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;

public class EditContactScreen extends BaseScreen{
    public EditContactScreen(AppiumDriver<MobileElement> driver) {
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
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/updateBtn']")
    MobileElement updateButton;


    public ContactListScreen submitEditContactForm(){
        updateButton.click();
        return  new ContactListScreen(driver);
    }

    public  EditContactScreen updateName(String text){
        waitElement(nameEditText , 5 );
        type(nameEditText, text);
        return this;

    }

}
