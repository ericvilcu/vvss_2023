package org.example.pages;


import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.WebElementFacade;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.webdriver.jquery.ByJQuerySelector;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

@DefaultUrl("https://the-internet.herokuapp.com/")
public class InternetPage extends PageObject {

    @FindBy(jquery = "ul :nth-child(1) a")
    private WebElementFacade ABButton;
    @FindBy(jquery = "p")
    private WebElementFacade ABText;
    public void goto_AB() {
        ABButton.click();
    }
    public String  AB_TEXT() {
        return ABText.getText();
    }

    @FindBy(jquery = "ul :nth-child(2) a")
    private WebElementFacade adrButton;
    public void goto_add_remove() {
        adrButton.click();
    }

    public int adr_count() {
        List<WebElementFacade> buttons = findAll(By.tagName("button"));
        return buttons.size()-1;
    }

    public void adr_remove() {
        List<WebElementFacade> buttons = findAll(By.tagName("button"));
        if(buttons.size()>1)buttons.get(1).click();
    }

    public void adr_add() {
        List<WebElementFacade> buttons = findAll(By.tagName("button"));
        buttons.get(0).click();
    }
    @FindBy(jquery = "ul :nth-child(11) a")
    private WebElementFacade dropdownPageButton;
    @FindBy(jquery = "select")
    private WebElementFacade dropdown;
    public void gotoDropdown(){
        dropdownPageButton.click();
    }
    public String dropdownState(){
        return dropdown.getValue();
    }
    public void dropdownSelect(int i){
        dropdown.select().byIndex(i);
    }
    @FindBy(jquery = "ul :nth-child(15) a")
    private WebElementFacade AdButton;
    @FindBy(jquery = ".modal-footer > *")
    private WebElementFacade closeAd;
    public void gotoAd(){
        AdButton.click();
    }
    public void closeAd(){
        closeAd.click();
        try {
            new WebDriverWait(getDriver(), 1).until(ExpectedConditions.invisibilityOf(find(new ByJQuerySelector(".modal-footer > *"))));
        }
        catch (Exception ignored){
        }
    }
    public boolean adExists(){
        return find(new ByJQuerySelector(".modal-footer")).isVisible();
    }

    public void re_enable_ad() {
        find(new ByJQuerySelector("p > a")).click();
    }

    public void maybeWaitAd() {
        try {
            new WebDriverWait(getDriver(), 1).until(ExpectedConditions.elementToBeClickable(new ByJQuerySelector(".modal-footer > *")));
        }
        catch (Exception ignored){
        }
    }

    public void exec(String s) {
        getJavascriptExecutorFacade().executeScript(s);
    }
}
