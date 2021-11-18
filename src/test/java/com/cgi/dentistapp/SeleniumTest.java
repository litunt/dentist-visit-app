package com.cgi.dentistapp;

import com.cgi.dentistapp.repository.DentistRepository;
import com.dentistapp.selenium.EnvManager;
import com.dentistapp.selenium.RunEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.MouseAction;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SeleniumTest {

    @Autowired
    private DentistRepository dentistRepository;

    private WebDriver driver;

    @Before
    public void startBrowser() {
        EnvManager.initWebDriver();
        driver = RunEnvironment.getWebDriver();
    }

    @Test
    public void FT1() {
        driver.get("http://localhost:8081/");
        Select dentistDropDown = new Select(driver.findElement(By.name("dentistId")));
        dentistDropDown.selectByVisibleText("Toomas Tamm");
        Long chosenDentistId = Long.valueOf(dentistDropDown.getFirstSelectedOption().getAttribute("value"));
        assertNotNull(dentistRepository.findOne(chosenDentistId));
    }

    @Test
    public void FT2() {
        driver.get("http://localhost:8081/");
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        LocalDate today = LocalDate.now();
        String dateAsString = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        dateElement.sendKeys(dateAsString);
        String dateAsStringFormatted = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateAsTextFromInput = dateElement.getAttribute("value");
        assertEquals(dateAsStringFormatted, dateAsTextFromInput);
    }

    @Test
    public void FT3() {
        final String minDateValidationMsg = "Value must be %s or later.";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final DateTimeFormatter msgFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        driver.get("http://localhost:8081/");
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        LocalDate today = LocalDate.now().minusDays(2L);
        String dateAsString = today.format(formatter);
        dateElement.sendKeys(dateAsString);
        assertEquals(String.format(minDateValidationMsg, LocalDate.now().format(msgFormatter)),
                dateElement.getAttribute("validationMessage"));
    }

    @Test
    public void FT4() {
        driver.get("http://localhost:8081/");
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        LocalDate today = LocalDate.now().plusDays(5);
        String dateAsString = today.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        dateElement.sendKeys(dateAsString);
        String dateAsStringFormatted = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateAsTextFromInput = dateElement.getAttribute("value");
        assertEquals(dateAsStringFormatted, dateAsTextFromInput);
    }

    @Test
    public void FT5() {
        driver.get("http://localhost:8081/");
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        WebElement timeElement = driver.findElement(By.name("visitTime"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        LocalDate existingVisitDt = LocalDate.of(2021, 11, 24);
        String dateAsStr = existingVisitDt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalTime existingVisitTime = LocalTime.of(8, 0);
        String timeAsStr = existingVisitTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        dateElement.sendKeys(dateAsStr);
        timeElement.sendKeys(timeAsStr);

        submitBtn.click();

        WebElement errorMsgElement = driver.findElement(By.className("error"));

        String errorMsg = "Chosen visit time overlaps with dentist's other appointments!";
        assertEquals(errorMsg, getInnerText(driver, errorMsgElement));
    }

    @Test
    public void FT6() {
        driver.get("http://localhost:8081/");
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        WebElement timeElement = driver.findElement(By.name("visitTime"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        LocalDate visitDt = LocalDate.of(2021, 11, 25);
        String dateAsStr = visitDt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalTime existingVisitTime = LocalTime.of(8, 0);
        String timeAsStr = existingVisitTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        dateElement.sendKeys(dateAsStr);
        timeElement.sendKeys(timeAsStr);

        submitBtn.click();

        WebElement successMsgElement = driver.findElement(By.tagName("p"));

        String successMsg = "The registration was successful!";
        assertEquals(successMsg, getInnerText(driver, successMsgElement));
    }

    @Test
    public void FT7() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        driver.get("http://localhost:8081/");
        WebElement timeElement = driver.findElement(By.name("visitTime"));
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        LocalTime lastPossibleTime = LocalTime.of(17, 30);
        String timeAsStr = lastPossibleTime.format(formatter);
        LocalDate visitDt = LocalDate.of(2021, 11, 25);
        String dateAsStr = visitDt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        timeElement.sendKeys(timeAsStr);
        dateElement.sendKeys(dateAsStr);

        submitBtn.click();

        WebElement successMsgElement = driver.findElement(By.tagName("p"));

        String successMsg = "The registration was successful!";
        assertEquals(successMsg, getInnerText(driver, successMsgElement));
    }

    @Test
    public void FT8() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        driver.get("http://localhost:8081/");
        WebElement timeElement = driver.findElement(By.name("visitTime"));
        WebElement dateElement = driver.findElement(By.name("visitDate"));
        WebElement submitBtn = driver.findElement(By.cssSelector("button[type='submit']"));

        LocalTime lastPossibleTime = LocalTime.of(17, 30);
        String timeAsStr = lastPossibleTime.format(formatter);
        LocalDate visitDt = LocalDate.of(2021, 11, 25);
        String dateAsStr = visitDt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        timeElement.sendKeys(timeAsStr);
        dateElement.sendKeys(dateAsStr);

        submitBtn.click();

        WebElement errorMsgElement = driver.findElement(By.className("error"));

        String errorMsg = "Chosen visit time overlaps with dentist's other appointments!";
        assertEquals(errorMsg, getInnerText(driver, errorMsgElement));
    }

    @Test
    public void FT9() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final int baseNumOfRows = 3;
        driver.get("http://localhost:8081/appointments");
        WebElement dateElement = driver.findElement(By.name("startDate"));

        LocalDate visitStartDt = LocalDate.of(2021, 11, 10);
        String dateAsStr = visitStartDt.format(formatter);

        dateElement.sendKeys(dateAsStr);

        WebElement searchBtn = driver.findElement(By.id("submit-form"));
        searchBtn.click();

        String searchResultTitle = "Appointments search result";
        WebElement titleElement = driver.findElement(By.tagName("h3"));
        assertEquals(searchResultTitle, getInnerText(driver, titleElement));

        WebElement resultTable = driver.findElement(By.className("table-bordered"));
        List<WebElement> tableRows = resultTable.findElements(By.tagName("tr"));
        assertEquals(6, tableRows.size() - baseNumOfRows);
    }

    @Test
    public void FT10() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final int baseNumOfRows = 3;
        driver.get("http://localhost:8081/appointments");
        WebElement startDtElement = driver.findElement(By.name("startDate"));
        WebElement endDtElement = driver.findElement(By.name("endDate"));

        LocalDate visitStartDt = LocalDate.of(2021, 11, 10);
        String dateAsStr = visitStartDt.format(formatter);

        startDtElement.sendKeys(dateAsStr);
        endDtElement.sendKeys(dateAsStr);

        WebElement searchBtn = driver.findElement(By.id("submit-form"));
        searchBtn.click();

        String searchResultTitle = "Appointments search result";
        WebElement titleElement = driver.findElement(By.tagName("h3"));
        assertEquals(searchResultTitle, getInnerText(driver, titleElement));

        WebElement resultTable = driver.findElement(By.className("table-bordered"));
        List<WebElement> tableRows = resultTable.findElements(By.tagName("tr"));
        assertEquals(3, tableRows.size() - baseNumOfRows);
    }

    @Test
    public void FT11() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        final int baseNumOfRows = 3;
        driver.get("http://localhost:8081/appointments");
        WebElement startDtElement = driver.findElement(By.name("startDate"));
        WebElement endDtElement = driver.findElement(By.name("endDate"));

        LocalDate visitStartDt = LocalDate.of(2021, 11, 15);
        LocalDate visitEndDt = LocalDate.of(2021, 11, 10);

        String dateAsStr = visitStartDt.format(formatter);

        startDtElement.sendKeys(dateAsStr);
        endDtElement.sendKeys(dateAsStr);

        WebElement searchBtn = driver.findElement(By.id("submit-form"));
        searchBtn.click();

        String searchResultTitle = "Appointments search result";
        WebElement titleElement = driver.findElement(By.tagName("h3"));
        assertEquals(searchResultTitle, getInnerText(driver, titleElement));

        WebElement resultTable = driver.findElement(By.className("table-bordered"));
        List<WebElement> tableRows = resultTable.findElements(By.tagName("tr"));
        assertEquals(3, tableRows.size() - baseNumOfRows);
    }

    private String getInnerText(WebDriver driver, WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return (String) executor.executeScript("return arguments[0].innerText", element);
    }

//    @After
//    public void tearDown() {
//        EnvManager.shutDownDriver();
//    }
}
