package org.sikuli.webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.sikuli.api.*;
import webdriver.Logger;
import webdriver.Browser;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SikuliDriver {

	private static Logger logger = Logger.getInstance();
	private ScreenRegion webdriverRegion;
	private ImageTarget target;
	private WebElement foundWebElement;
	private Rectangle r;

	public class WebDriverScreenCustom implements Screen {

		private final TakesScreenshot driver;
		private final Dimension size;

		public WebDriverScreenCustom(TakesScreenshot driver) throws IOException{
			this.driver = driver;		
			File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
			BufferedImage b = crop(ImageIO.read(screenshotFile),0,0,(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
			size = new Dimension(b.getWidth(),b.getHeight());
		}
		
		/**
		 * Crop image
		 * @param src BufferedImage
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 * @return
		 */
		BufferedImage crop(BufferedImage src, int x, int y, int width, int height){
			BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = dest.getGraphics();
			g.drawImage(src, 0, 0, width, height, x, y, x + width, y + height, null);
			g.dispose();
			return dest;
		}


		/* 
		 * Get Screenshot
		 */
		@Override
		public BufferedImage getScreenshot(int x, int y, int width,int height) {
			File screenshotFile = driver.getScreenshotAs(OutputType.FILE);
			try {
				BufferedImage full = ImageIO.read(screenshotFile);
				BufferedImage cropped = crop(full, x, y, width, height);
				return cropped;
			} catch (IOException e) {
				logger.debug(this, e);
			}
			return null;
		}

		@Override
		public Dimension getSize() {
			return size;
		}

		
	}

	public SikuliDriver(URL imageUrl){	
		WebDriverScreenCustom webDriverScreen;
		try {
			webDriverScreen = new WebDriverScreenCustom((TakesScreenshot) Browser.getDriver());
		} catch (IOException e1) {
            logger.debug(this, e1);
			throw new ExceptionInInitializerError("Unable to initialize Sikuli Driver");
		}
		webdriverRegion = new DefaultScreenRegion(webDriverScreen);		
		target = new ImageTarget(imageUrl);
	}

	public SikuliDriver(URL imageUrl,ScreenRegion screen){
		webdriverRegion = screen;
		target = new ImageTarget(imageUrl);
	}

	/**Find element by location
	 * @param x
	 * @param y
	 * @return WebElement
	 */
	public WebElement findElementByLocation(int x, int y){
		return (WebElement) Browser.getDriver().executeScript("return document.elementFromPoint(" + x + "," + y + ")");
	}

	/**
	 * Get region
	 * @return
	 */
	public ScreenRegion getRegion() {
		return webdriverRegion;
	}

	/**
	 * Find element by image and similarity
	 * @param similarity similar (0.7)
	 * @return ImageElement
	 */
	public ImageElement findImageElement(final double similarity, final int timeoutMilisecs) {
		return findImageElement(similarity, timeoutMilisecs, 0, 0, 0, 0);
	}
	
	/**
	 * Find element by image and similarity
	 * @param similarity similar (0.7)
	 * @return ImageElement
	 */
	public ImageElement findImageElement(final double similarity, 
	final int timeoutMilisecs, int x, int y, int width, int heigth) {
		target.setMinScore(similarity);
		
		ScreenRegion imageRegion, region;		
		if(width!=0 && heigth!=0){
			region = webdriverRegion.
			getRelativeScreenRegion(x, y, width, heigth);
			imageRegion = region.wait(target, timeoutMilisecs);
		}
		else
			imageRegion = webdriverRegion.wait(target, timeoutMilisecs);
		if (imageRegion != null){
			ScreenLocation center = imageRegion.getCenter();
			foundWebElement = findElementByLocation(center.getX(), center.getY());
			r = imageRegion.getBounds();
			return new DefaultImageElement(Browser.getInstance().getDriver(), foundWebElement,
					r.x + x,
					r.y + y,
					r.width,
					r.height);
		}
		else{
			logger.debug("image is not found");
			return null;
		}		
	}

	/**
	 * Find element by image and similarity
	 * @param similarity similar (0.7)
	 * @return ImageElement
	 */
	public ImageElement findImageElement(final double similarity) {
		return findImageElement(similarity,Integer.parseInt(Browser.getTimeoutForSikuli()));
	}	
	
	/**
	 * Find element by image pattern
	 * @return ImageElement
	 */
	public ImageElement findImageElement() {
		return findImageElement(Double.parseDouble(Browser.getSimilarityForSikuli()));
	}

	/**
	 * Find element by image pattern
	 * @return ImageElement
	 */
	public ImageElement findImageElement(final int timeoutMiliSecs) {
		return findImageElement(Double.parseDouble(Browser.getSimilarityForSikuli()),timeoutMiliSecs);
	}
	
	/**
	 * Get webElement (webdriver)
	 * @return WebElement
	 */
	public WebElement getElement(){
		return foundWebElement;
	}

	/**
	 * Get point to interact with
	 * @return
	 */
	public Point getPoint(){
		return new Point((int)r.getX(), (int)r.getY());
	}

	/**
	 * Get rectangle to interact with
	 * @return
	 */
	public Rectangle getRectangle(){
		return r;
	}
}
