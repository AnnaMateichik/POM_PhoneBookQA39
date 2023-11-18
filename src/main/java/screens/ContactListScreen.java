package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class ContactListScreen extends BaseScreen {
    public ContactListScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/action_bar']/android.widget.TextView")
    MobileElement activityTextView;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/title']")
    MobileElement logoutButton;
    @FindBy(xpath = "//*[@content-desc='More options']")
    MobileElement moreOptions;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/add_contact_btn']")
    MobileElement addButton;

    public boolean isContactListActivityPresent() {
        return shouldHave(activityTextView, "Contact list", 5);

    }

    public AuthenticationScreen logout(){
        if(isDisplayedWithException(moreOptions)) {
            moreOptions.click();
            logoutButton.click();
        }
        return new AuthenticationScreen(driver);


    }
    public ContactListScreen assertContactListActivityPresent(){
        Assert.assertTrue(isContactListActivityPresent());
        return this;
    }
    public AddNewContactScreen oppenContactForm(){
        waitElement(addButton, 5);
        addButton.click();
        return new AddNewContactScreen(driver);
    }

}
