package pageObject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.testng.TestSession;

public class HomePage {

  @FindBy(id="_nkw")
  private WebElement searchField;
  @FindBy(id="ghSearch")
  private WebElement searchButton;
  
  
  public HomePage(){
    PageFactory.initElements(TestSession.webdriver(), this);
  }
  
  public SearchResultPage search(String keyword){
    searchField.sendKeys(keyword);
    searchButton.click();
    return new SearchResultPage(keyword);
  }
}
