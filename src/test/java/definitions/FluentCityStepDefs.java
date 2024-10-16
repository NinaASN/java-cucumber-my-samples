package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static support.DriverFactory.getDriver;

public class FluentCityStepDefs {
    @Given("I navigate to page {string}")
    public void iNavigateToPage(String site) {
        if (site.equals("fluentcity")){
            getDriver().get("https://www.cricketelearning.com/");
        }else{
            //System.err.println("Unknown page: "+page);
            throw new Error("Unknown page: "+ site);
        }

    }

    @When("I click {string}")
    public void iClick(String menuItem) {
        WebElement popup = getDriver().findElement(By.xpath("//div[@class ='ub-emb-iframe-wrapper ub-emb-visible']"));
        if (popup.isDisplayed()) {
            getDriver()
                    .findElement(By.xpath("//div[@class ='ub-emb-iframe-wrapper ub-emb-visible']/button[text() ='×']"))
                    .click();
        } else {
            getDriver().findElement(By.xpath("//a[contains(text(),'" + menuItem + "')]")).click();
        }
    }


    @Then("I see {string} package on the page")
    public void iSeePackageOnThePage(String packageType) {
        WebElement pack = getDriver().findElement(By.xpath("//*[text()='" + packageType + "']"));
        WebElement inactiveToggle = getDriver().findElement(By.id("subscriptions-list"));
        assertTrue(pack.isDisplayed());
        assertThat(inactiveToggle.getAttribute("style")).isEqualTo("display: none;");
    }

    @When("I select {string} language on {string} package")
    public void iSelectLanguageOnPackage(String language, String packageType) {
        Select select = new Select(getDriver().findElement(By.xpath("//p[text()='" + packageType + "']/../div/select")));
        select.selectByValue(language);
    }

    @And("I click button on {string} card")
    public void iClickButtonOnCard(String packageType) {
        getDriver().findElement(By.xpath(findPackageButtonXpath(packageType))).click();
    }

    @Then("I am directed to {string}")
    public void iAmDirectedTo(String pageName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement newPageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[@class='checkout-title']")));
        assertThat(getDriver().getCurrentUrl()).isEqualTo("https://www.cricketelearning.com/order/checkout");
        assertThat(pageName).isEqualTo(newPageTitle.getText());
    }

    @And("cart contains {string} pack with {string} price")
    public void cartContainsPackWithPrice(String packageType, String price) {
        String packageName = getDriver().findElement(By.xpath("//div[@class='LineItem__body__left']//h2")).getText();
        String actualPrice = getDriver().findElement(By.xpath("//p[@class='LineItem__price']")).getText();
        actualPrice = actualPrice.replace("$", "");
        assertThat(packageName).isEqualTo(packageType);
        assertThat(actualPrice).isEqualTo(price);
    }

    private String findPackageButtonXpath(String packageType){
        String buttonXpath;
        switch(packageType) {
            case "STARTER":
                buttonXpath = "//button[@data-id='304']";
                break;
            case "BASIC":
                buttonXpath = "//button[@data-id='305']";
                break;
            case "PLUS":
                buttonXpath = "//button[@data-id='306']";
                break;
            case "PREMIUM":
                buttonXpath = "//button[@data-id='307']";// code block
                break;
            default:
                throw new Error("Unknown package: "+ packageType);
        }return buttonXpath;
    }

}
