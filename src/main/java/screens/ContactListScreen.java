package screens;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import models.Contact;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

public class ContactListScreen extends BaseScreen {
    public ContactListScreen(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    String phoneNumber;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/action_bar']/android.widget.TextView")
    MobileElement activityTextView;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/title']")
    MobileElement logoutButton;
    @FindBy(xpath = "//*[@content-desc='More options']")
    MobileElement moreOptions;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/add_contact_btn']")
    MobileElement addButton;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowName']")
    List<MobileElement> nameList;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowPhone']")
    List<MobileElement> phoneList;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowContainer']")
    List<MobileElement> contacts;

    @FindBy(xpath = "//*[@resource-id='android:id/button1']")
    MobileElement yesButton;
    @FindBy(xpath = "//*[@resource-id='android:id/button2']")
    MobileElement cancelButton;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/rowPhone']")
    MobileElement contactPhone;
    @FindBy(xpath = "//*[@resource-id='com.sheygam.contactapp:id/emptyTxt']")
    MobileElement emptyTxtView;



    public boolean isContactListActivityPresent() {
        return shouldHave(activityTextView, "Contact list", 5);

    }

    public AuthenticationScreen logout() {
        if (isDisplayedWithException(moreOptions)) {
            moreOptions.click();
            logoutButton.click();
        }
        return new AuthenticationScreen(driver);


    }

    public ContactListScreen assertContactListActivityPresent() {
        Assert.assertTrue(isContactListActivityPresent());
        return this;
    }

    public AddNewContactScreen openContactForm() {
//        waitElement(addButton, 5);
        if (isDisplayedWithException(addButton)) {
            addButton.click();
        }

        return new AddNewContactScreen(driver);
    }

    public ContactListScreen isContactAdded(Contact contact) {
        boolean res = false;
        while (!res) {
            boolean checkName = isContainsText(nameList, contact.getName()
                    + " " + contact.getLastName());
            boolean checkPhone = isContainsText(phoneList, contact.getPhone());
            res = checkName && checkPhone;
            if (res == false) isEndOfListClassVersion();

        }
        Assert.assertTrue(res);
        return this;
    }

    public boolean isContainsText(List<MobileElement> list, String text) {
        for (MobileElement element : list) {
            if (element.getText().contains(text))
                return true;
        }
        return false;
    }

    public ContactListScreen removeOneContact() {
        waitElement(addButton, 5);
        MobileElement contact = contacts.get(0);
        phoneNumber = contactPhone.getText();
        System.out.println(phoneNumber);
        Rectangle rect = contact.getRect();

        int xStart = rect.getX() + rect.getWidth() / 8;
        int xEnd = xStart + rect.getWidth() * 6 / 8;
        int y = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction
                .longPress(PointOption.point(xStart, y))
                .moveTo(PointOption.point(xEnd, y))
                .release()
                .perform();

        waitElement(yesButton, 5);
        yesButton.click();
        return this;
    }
    public EditContactScreen updateOneContact() {
        waitElement(addButton, 5);
        MobileElement contact = contacts.get(0);
        phoneNumber = contactPhone.getText();
        System.out.println(phoneNumber);
        Rectangle rect = contact.getRect();

        int xEnd = rect.getX() + rect.getWidth() / 8;
        int xStart = xEnd + rect.getWidth() * 6 / 8;
        int y = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> touchAction = new TouchAction<>(driver);
        touchAction
                .longPress(PointOption.point(xStart, y))
                .moveTo(PointOption.point(xEnd, y))
                .release()
                .perform();

        return new EditContactScreen(driver);
    }

    public  boolean isContactContains(String text){
        pause(3000);
        contacts.get(0).click();
        Contact contact = new ViewContactScreen(driver)
                .viewContactObject();

        driver.navigate().back();
        return contact.toString().contains(text);
    }
    public ContactListScreen isOneContactRemoved() {
        Assert.assertFalse(phoneList.contains(phoneNumber));
        return this;
    }

    public ContactListScreen removeAllContacts() {
        waitElement(addButton, 5);
        while (contacts.size() > 0) {
            removeOneContact();
        }
        return this;
    }

    public ContactListScreen isNoContacts() {
        Assert.assertTrue(shouldHave(emptyTxtView, "No Contacts", 5));
        return this;
    }

    public ContactListScreen provideContacts() {
        if (contacts.size() < 3) {
            for (int i = 0; i < 3; i++) {
                addContact();
            }
        }
        return this;
    }

    public void addContact() {
        int i = new Random().nextInt(1000) + 1000;
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
                .submitContactForm();
    }


    public ContactListScreen scrollingList() {
        waitElement(addButton, 5);

        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();

        int xStart = screenWidth / 2;
        int yStart = (int) (screenHeight * 0.8);
        int yEnd = (int) (screenHeight * 0.2);

        TouchAction touchAction = new TouchAction(driver);
        touchAction.press(PointOption.point(xStart, yStart))
                .waitAction()
                .moveTo(PointOption.point(xStart, yEnd))
                .release()
                .perform();


        return this;
    }

    public String lastPhoneAtScreen() {
        return phoneList.get(phoneList.size() - 1).getText();
    }

    public boolean isEndOfList() {
        String lastBeforeScroll;
        String lastAfterScroll;
        do {
            lastBeforeScroll = lastPhoneAtScreen();
            scrollingList();
            lastAfterScroll = lastPhoneAtScreen();
        } while (!lastBeforeScroll.equals(lastAfterScroll));
        return true;
    }

    public ContactListScreen scrollingListClassVersion() {
        waitElement(addButton, 5);

        MobileElement contact = contacts.get(contacts.size() - 1);

        Rectangle rect = contact.getRect();
        int xRow = rect.getX() + rect.getWidth() / 2;
        int yRow = rect.getY() + rect.getHeight() / 2;

        TouchAction<?> action = new TouchAction<>(driver);
        action.longPress(PointOption.point(xRow, yRow))
                .moveTo(PointOption.point(xRow, 0))
                .release()
                .perform();

        return this;
    }

    public boolean isEndOfListClassVersion() {

        String beforeScroll = nameList.get(nameList.size() - 1)
                .getText() + " " +
                phoneList
                        .get(nameList.size() - 1)
                        .getText();
        scrollingListClassVersion();


        String afterScroll = nameList.get(nameList.size() - 1)
                .getText() + " " +
                phoneList
                        .get(nameList.size() - 1)
                        .getText();
        if (beforeScroll.equals(afterScroll)) return true;
        return false;

    }

}
