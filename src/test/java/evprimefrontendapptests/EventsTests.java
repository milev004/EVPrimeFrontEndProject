package evprimefrontendapptests;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;
import util.DateBuilder;
import util.DbClient;


import static org.junit.Assert.*;


public class EventsTests {
    private WebDriver driver;
    private SidePanel sidePanel;
    private CreateUserLoginPage createUserLoginPage;
    private EventsPage eventsPage;
    private EventPage eventPage;
    static DateBuilder dateBuilder = new DateBuilder();

    @Before
    public void SetUp() throws InterruptedException {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        sidePanel = new SidePanel(driver);
        createUserLoginPage = new CreateUserLoginPage(driver);
        eventsPage = new EventsPage(driver);
        eventPage = new EventPage(driver);

        sidePanel.navigateTo("http://localhost:3000");
        sidePanel.clickMenuIcon();
        Thread.sleep(2000);
        sidePanel.clickLoginButton();
    }

    @Test
    public void createAnEventTest() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        eventsPage.hoverPlusButton();
        Thread.sleep(2000);
        eventsPage.clickAddEventButton();
        eventsPage.insertEventTitle(RandomStringUtils.randomAlphabetic(10));
        eventsPage.insertEventImage("https://www.globalrentacar.com.mk/images/news/8abdbcfb4b48d1fa051bac1e0b9c40c92f537308.jpg");
        eventsPage.insertEventDate(dateBuilder.currentTime());
        eventsPage.insertEventLocation("Ohrid");
        eventsPage.insertEventDescription(RandomStringUtils.randomAlphabetic(25));
        eventsPage.clickCreateEventButton();
    }

    //precondition create event directly from database
    @Test
    public void eventValidationTest() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        sidePanel.clickEventsButton();
        Thread.sleep(1000);
        eventsPage.clickOnEventByIndex(1);
        assertEquals("Natprevar vo jadenje pizza", eventPage.getTextEventTitle());
        assertEquals("02.01.2024", eventPage.getTextEventDate());
        assertEquals("Pizzeria Leonardo", eventPage.getTextEventLocation());
        assertEquals("Proveri kolku si gladen", eventPage.getTextEventDescription());
        assertEquals("BACK TO EVENTS", eventPage.getTextFromBackToEventsButton());
        assertEquals("EDIT EVENT", eventPage.getEditEventButtonText());
    }

    @Test
    public void validateEventFieldsText() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        sidePanel.clickEventsButton();
        Thread.sleep(2000);
        eventsPage.clickOnEventByIndex(1);
        eventPage.clickEditButton();
        assertEquals("Event Title",eventPage.getEventTitleTextField());
        assertEquals("Event Image",eventPage.getEventImageTextField());
        assertEquals("Event Date",eventPage.getEventDateTextField());
        assertEquals("Event Location",eventPage.getEventLocationField());
        assertEquals("Event Description",eventPage.getEventDescriptionField());
    }

    //precondition create event directly from database
    @Test
    public void validateBackToEventsButtonTest() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        sidePanel.clickEventsButton();
        Thread.sleep(1000);
        eventsPage.clickOnEventByIndex(1);
        Thread.sleep(1000);
        eventPage.clickBackToEventsButton();
        WebElement eventList = eventsPage.findEventList();
        assertTrue(eventList.isDisplayed());
    }

    //precondition create event directly from database
    @Test
    public void eventValidationWhenUserNotLoggedInTest() throws InterruptedException {
        sidePanel.clickEventsButton();
        Thread.sleep(1000);
        eventsPage.clickOnEventByIndex(1);
        Thread.sleep(1000);
        assertEquals("Natprevar vo jadenje pizza", eventPage.getTextEventTitle());
        assertEquals("02.01.2024", eventPage.getTextEventDate());
        assertEquals("Pizzeria Leonardo", eventPage.getTextEventLocation());
        assertEquals("Proveri kolku si gladen", eventPage.getTextEventDescription());
        assertEquals("BACK TO EVENTS", eventPage.getTextFromBackToEventsButton());
        eventPage.clickBackToEventsButton();
        Thread.sleep(1000);
        WebElement eventList = eventsPage.findEventList();
        assertTrue(eventList.isDisplayed());
    }

    //precondition create event directly from database
    @Test
    public void updateEventTest() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        sidePanel.clickEventsButton();
        Thread.sleep(2000);
        eventsPage.clickOnEventByIndex(3);
        assertEquals("EDIT EVENT", eventPage.getEditEventButtonText());
        eventPage.clickEditButton();
        eventPage.eventTitleInsertText("Juventus Champions");
        assertEquals("UPDATE EVENT", eventPage.getUpdateButtonText());
        eventPage.clickUpdateButton();
    }

    @Test
    public void deleteEventTest() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        sidePanel.clickEventsButton();
        Thread.sleep(2000);
        eventsPage.clickOnEventByIndex(3);
        eventPage.clickDeleteButton();
        assertEquals("DELETE EVENT",eventPage.getDeleteButtonText());
        DbClient.deleteEventById(3);
        eventPage.clickConfirmDeleteButton();
    }

    @Test
    public void verifyingEventButtonsCss() throws InterruptedException {
        createUserLoginPage.insertEmail("mail@email.com");
        createUserLoginPage.insertPassword("password");
        createUserLoginPage.clickGoButton();
        Thread.sleep(2000);
        sidePanel.clickEventsButton();
        Thread.sleep(1000);
        eventsPage.clickOnEventByIndex(2);

        assertEquals("#d32f2f",eventPage.getDeleteButtonColor());
        assertEquals("\"Josefin Sans\"",eventPage.getDeleteButtonFontType());
        assertEquals("14px",eventPage.getDeleteButtonFontSize());

        assertEquals("#304ffe",eventPage.getEditButtonColor());
        assertEquals("\"Josefin Sans\"",eventPage.getEditButtonFontType());
        assertEquals("14px",eventPage.getEditButtonFontSize());

        assertEquals("#9c27b0",eventPage.getBackToEventsButtonColor());
        assertEquals("\"Josefin Sans\"",eventPage.getBackToEventsButtonFontType());
        assertEquals("14px",eventPage.getBackToEventsButtonFontSize());

        eventPage.clickEditButton();
        assertEquals("#2e7d32",eventPage.getUpdateButtonColor());
        assertEquals("\"Josefin Sans\"",eventPage.getUpdateButtonFontType());
        assertEquals("14px",eventPage.getUpdateButtonFontSize());
    }

    @After
    public void TearDown(){
        driver.quit();
    }

}