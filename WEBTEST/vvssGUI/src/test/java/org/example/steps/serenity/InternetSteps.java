package org.example.steps.serenity;

import net.thucydides.core.annotations.Step;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;

import org.example.pages.InternetPage;

public class InternetSteps {

    InternetPage internetPage;
    @Step
    public void go_to_AB(){
        internetPage.goto_AB();
    }
    @Step
    public String  AB_TEXT(){
        return internetPage.AB_TEXT();
    }

    @Step
    public void go_to_adr(){
        internetPage.goto_add_remove();
    }
    @Step
    public void adr_add(){
        internetPage.adr_add();
    }
    @Step
    public void adr_remove(){
        internetPage.adr_remove();
    }
    @Step
    public int adr_count(){
        return internetPage.adr_count();
    }

    @Step
    public void go_to_dropdown(){
        internetPage.gotoDropdown();
    }
    @Step
    public String dropdownState(){
        return internetPage.dropdownState();
    }
    @Step
    public void dropdownSelect(int i){
        internetPage.dropdownSelect(i);
    }

    @Step
    public void reset(){
        internetPage.open();
    }

    @Step
    public void gotoAd(){
        internetPage.gotoAd();
        internetPage.maybeWaitAd();
    }

    @Step
    public void closeAd(){
        internetPage.closeAd();
    }

    @Step
    public boolean adExists(){
        return internetPage.adExists();
    }

    public void re_enable_ad() {
        internetPage.re_enable_ad();
    }

    @Step
    public void back(){
        internetPage.exec("window.history.go(-1)");
    }
}
