package org.framework.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.framework.controller.MainController;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestUtility  {


	/*
	 * @author Hemanth.Sridhar
	 *
	 */

	WebDriver driver;

	public TestUtility(WebDriver driver)
	{
		this.driver = driver;
	}

	public   void openPageInNewTab() throws AWTException {
		Robot r = new Robot();                          
		r.keyPress(KeyEvent.VK_CONTROL); 
		r.keyPress(KeyEvent.VK_T); 
		r.keyRelease(KeyEvent.VK_CONTROL); 
		r.keyRelease(KeyEvent.VK_T);
	}

	public   void fileUpload(String fileLocation) throws AWTException {
		
		 StringSelection stringSelection = new StringSelection(fileLocation);
		  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		   Robot robot;
		   
		   robot = new Robot();
		   robot.keyPress(KeyEvent.VK_ENTER);
	       robot.keyRelease(KeyEvent.VK_ENTER);
           robot.keyPress(KeyEvent.VK_CONTROL);
           robot.keyPress(KeyEvent.VK_C);
           robot.keyRelease(KeyEvent.VK_C);
           robot.keyRelease(KeyEvent.VK_CONTROL);
           robot.keyPress(KeyEvent.VK_CONTROL);
           robot.keyPress(KeyEvent.VK_V);
           robot.keyRelease(KeyEvent.VK_CONTROL);
           robot.keyRelease(KeyEvent.VK_V);
           robot.keyPress(KeyEvent.VK_ENTER);
           robot.keyRelease(KeyEvent.VK_ENTER);
           
		}
	

	public   void closeCurrentTab() {

		driver.close();
		
	}

	public   void closeAllTabsExceptFirst() {

		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		for(int i=1;i<tabs.size();i++)
		{	
			driver.switchTo().window(tabs.get(i));
			driver.close();
		}
		driver.switchTo().window(tabs.get(0));
	}

	public   void switchToDialogBox(){

		driver.switchTo().window(driver.getWindowHandle());
	    
	}


	public   void printAllTheWindows() {

		ArrayList<String> al = new ArrayList<String>(driver.getWindowHandles());
		for(String window : al)
		{
			System.out.println(window);
		}
		
	}

	public   void hitEnter() throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
	}

	public   void alertAccept() {

		Waiting wait = new Waiting(driver);
		wait.explicitWaitForAlert(5);
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	public   void selectByVisibleText(WebElement element, String text){
		Select select = new Select(element);
		select.selectByVisibleText(text);
	}
	
	public   void selectByIndex(WebElement element, int index){
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	
	public   void mouseHoverActions(WebElement element)
	{

		Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}
	
	public   void clickAndHoldActions(WebElement element)
	{

		Actions action = new Actions(driver);
		action.clickAndHold(element).build().perform();
	}
	
	public   String getAlertText()
	{

		Alert alert = driver.switchTo().alert();
		String alertText = alert.getText().trim();
		return alertText;
	}

	public   void alertDismiss() {
		Waiting wait=new Waiting(driver);

		Alert alert =driver.switchTo().alert();
		wait.explicitWaitForAlert(5);
		alert.dismiss();
		
	}

	public   void verifyToolTip(WebElement element, String expectedToolTip) {
		
		Assert.assertEquals(expectedToolTip,element.getAttribute("title").trim());
		
	}
	
	public   void maximizeScreen(WebDriver driver) {
 	    java.awt.Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
 	    Point position = new Point(0, 0);
 	    driver.manage().window().setPosition(position);
 	    Dimension maximizedScreenSize =
 	        new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
 	    driver.manage().window().setSize(maximizedScreenSize);
 	  }
	

	 public    HashMap<String,Integer> headers(By table, By header){
	HashMap<String,Integer> headerindex=new HashMap<String, Integer>();

		 WebDriverWait wait = new WebDriverWait(driver, 10);
	        wait.until(ExpectedConditions.presenceOfElementLocated(table));
	        WebElement tablename = driver.findElement(table);
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(header));
	        List<WebElement> colummheader = tablename.findElements(header);
	        for (int i = 0; i < colummheader.size(); i++) {
	            if(i==0){
	                continue;
	            }
	            headerindex.put(colummheader.get(i).getText(),i);
	        }
	        return headerindex;
	    }
	 
	    public   void enterDataInHandsOnTable(int rownumber,String colName,String dataToEnter) throws Exception{
	    	HashMap<String,Integer> headerindex=new HashMap<String, Integer>();
	    	Waiting wait = new Waiting(driver);

	    	Actions action = new Actions(driver);
	    	
	    	String colrow="//tr["+rownumber+"]/td["+headerindex.get(colName)+"]";
	    	wait.explicitWaitVisibilityOfElement(driver.findElement(By.xpath(colrow)), 5);
	   
	    	wait.explicitWaitVisibilityOfElement(driver.findElement(By.xpath(colrow)), 5);
	        action.click(driver.findElement(By.xpath(colrow))).sendKeys(dataToEnter).build().perform();
	    	}
	    
	    
	    
	    public   void rightCickOnCell(int rownumber,String colName) throws Exception{
	    	HashMap<String,Integer> headerindex=new HashMap<String, Integer>();

	    	Actions action = new Actions(driver);
	    
	    	String colrow="//tr["+rownumber+"]/td["+headerindex.get(colName)+"]";
	    	  
	    	driver.findElement(By.xpath(colrow)).click();

	        action.contextClick(driver.findElement(By.xpath(colrow))).build().perform();
	      
	       }
	    }

