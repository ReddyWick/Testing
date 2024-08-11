package Screenshots;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.Screenshot;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class ScreenshotResolutions {
	WebDriver driver;
	

	  @BeforeClass
	  @Parameters({"browser", "url"})
	  public void link(String br, String url) throws InterruptedException {
		  switch (br.toLowerCase()) {
			case "chrome": driver = new ChromeDriver(); break;
			
			//Hi, My system laptop is not supporting safari browser[With this commented statement test case will pass]
			//case "safari": driver = new ChromeDriver(); break;
			
			//This statement from testing.xml file
//			<test thread-count="5" name="safariTest">
//		  	<parameter name="browser" value="safari"/>
//		  	<parameter name="url" value="https://www.getcalley.com/page-sitemap.xml"/>
//		    <classes>
//		      <class name="Screenshots.ScreenshotResolutions"/>
//		    </classes>
//		  	</test> <!-- Test -->
			
			case "firefox": driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser"); return;
			}
			  
			  
			  driver.get(url);
			  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			  driver.manage().window().maximize();
			  Thread.sleep(3000);
	  }
	  
	  @Test
	  public void screenShot() throws InterruptedException, IOException {
		  
		  List<WebElement> lista = driver.findElements(By.xpath("//tbody/tr/td/a"));
		  
		  String[][] resolutions = {{"Desktop","1920","1080"}, {"Laptop","1366","768"}, {"TabletX","1536","864"},
	  				 			    {"TabletS","360","640"}, {"Iphone","414","896"}, {"Realme","375","667"}};
		  
		  for(int i=0; i<=3; i++) {
			  
			  try {
			  lista.get(i).click();
			  } catch(Exception e) {
				  lista = driver.findElements(By.xpath("//tbody/tr/td/a"));
				  lista.get(i).click();
			  }
			  
			  for(int r1=0; r1<resolutions.length; r1++) {
				  int r2 = 1;
				  	  int num1 = Integer.parseInt(resolutions[r1][r2]);
				  	  int num2 = Integer.parseInt(resolutions[r1][r2+1]);
					  driver.manage().window().setSize(new Dimension(num1,num2));
					  Thread.sleep(1000);
					  String res = "("+resolutions[r1][r2]+","+resolutions[r1][r2+1]+")";
					  
					  File location = new File(System.getProperty("user.dir")+"\\screenshots\\img\\"+resolutions[r1][0]+res+"Screenshot"+timestamp()+".png");
					  
					  Screenshot fullpage = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
					  ImageIO.write(fullpage.getImage(), "png", location);
					  Thread.sleep(2000);
			  }
			  Thread.sleep(2000);
			  driver.navigate().back();
		  }
	  }
	  
	  public String timestamp() {
		  return new SimpleDateFormat("YYYY-MM-DD HH-MM-SS").format(new Date());
	  }
	  
	  @AfterClass
	  public void exit() {
		  driver.quit();
	  }
}
