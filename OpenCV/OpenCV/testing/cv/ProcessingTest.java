package cv;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ProcessingTest {
	
	//all of the testing is done assuming the software has been run at least once
	Processing process = new Processing();

	@Test
	public void detectColorTest() throws InterruptedException, AWTException
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//create path for file
		File f = new File("res/saved.png");
		
		//read image from resources folder
		Mat img = Imgcodecs.imread("res/saved.png");
		
		System.out.println("\nAlgorithm");
		//run the algorithm on the image
		Imgcodecs.imwrite("test2.png", process.detectColor(img));
		
		//check if file exists and can be read
		assertTrue(f.isFile());
		assertTrue(f.canRead());
		
		//check if img is not empty
		assertFalse(img.empty());
	}
	
	@Test
	public void loopContoursPaddleTest()
	{
		Mat img = Imgcodecs.imread("res/saved.png");
		
		Mat lower_red_hue = new Mat();
		Mat upper_red_hue = new Mat();
		Core.inRange(img, new Scalar(0, 0, 95), new Scalar(0, 0, 250), lower_red_hue);
		Core.inRange(img, new Scalar(0, 0, 100), new Scalar(0, 0, 255), upper_red_hue);
		   
		Mat red_hue = new Mat();
		Core.addWeighted(lower_red_hue, 1.0, upper_red_hue,  1.0, 0.0, red_hue);
		Imgproc.GaussianBlur(red_hue, red_hue, new Size(3,3), 1,1);
		
		Mat red_contours_mat = red_hue.clone();
		
		List<MatOfPoint> red_contours = new ArrayList<MatOfPoint>();

		Imgproc.findContours(red_contours_mat, red_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
		System.out.println("\nPaddle");
		process.loopContoursPaddle(red_hue, red_contours_mat,  red_contours, img);
		
		//check if image is not empty
		assertFalse(img.empty());
		
		//check if red colour is in the img
		assertFalse(red_hue.empty());
		assertFalse(red_contours_mat.empty());
		
		//check if contour array is empty
		assertFalse(red_contours.isEmpty());
	}
	
	@Test
	public void houghCircleTest()
	{
		Mat img = Imgcodecs.imread("res/saved.png");
		
		Mat lower_white_hue = new Mat();
		Mat upper_white_hue = new Mat();
		Core.inRange(img, new Scalar(250, 250, 250), new Scalar(252, 252, 252), lower_white_hue);
		Core.inRange(img, new Scalar(253, 253, 253), new Scalar(255, 255, 255), upper_white_hue);
		
		Mat white_hue = new Mat();
		Core.addWeighted(lower_white_hue, 1.0, upper_white_hue,  1.0, 0.0, white_hue);
		Imgproc.GaussianBlur(white_hue, white_hue, new Size(3,3), 3,3);
		
		Mat lower_red_hue = new Mat();
		Mat upper_red_hue = new Mat();
		Core.inRange(img, new Scalar(0, 0, 95), new Scalar(0, 0, 250), lower_red_hue);
		Core.inRange(img, new Scalar(0, 0, 100), new Scalar(0, 0, 255), upper_red_hue);
		   
		Mat red_hue = new Mat();
		Core.addWeighted(lower_red_hue, 1.0, upper_red_hue,  1.0, 0.0, red_hue);
		Imgproc.GaussianBlur(red_hue, red_hue, new Size(3,3), 1,1);
		
		System.out.println("\nHough white");
		process.houghCircle(white_hue, img);
		System.out.println("\nHough red");
		process.houghCircle(red_hue, img);
		
		//check if image is not empty
		assertFalse(img.empty());
		
		//check if white colour is in the img
		assertFalse(white_hue.empty());
		
		//check if red colour is in the img
		assertFalse(red_hue.empty()); //- it does not contain any circles
	}

	@Test 
	public void nextPointTest()
	{
		//create test vectors 
		Vector pointA = new Vector(5,5,0);
		int aX = pointA.x;
		int aY = pointA.y;
		
		Vector pointB = new Vector(10,10,0);
		int bX = pointB.x;
		int bY = pointB.y;
		
		//create vectors that will later be checked if are the same
		Vector predictionPoint = new Vector(0,0,0);
		Vector predictionExpected = new Vector (15,15,0);
		
		//call function
		Vector predictionPoint1 = Processing.nextPoint(aX,  aY, bX, bY, predictionPoint);
		
		//test if they are equal
		assertEquals(predictionExpected.x, predictionPoint1.x);
		assertEquals(predictionExpected.y, predictionPoint1.y);
	}
	
	@Test public void reversedAngletest()
	{
		//create test vectors 
		Vector pointA = new Vector(5,5,0);
		int aX = pointA.x;
		int aY = pointA.y;
		
		Vector pointB = new Vector(10,10,0);
		int bX = pointB.x;
		int bY = pointB.y;
		
		int xDiff1 = bX - aX;
		int yDiff1 = bY - aY;
		double result =  Math.atan2(yDiff1, xDiff1) * 180.0 / Math.PI;
		double reversedAngle = result * -1;
		
		
		//create vectors that will later be checked if are the same
		Vector resultVector = new Vector (0,0,0);
		Vector predictionExpected = new Vector (14,4,0);
		
		//call function
		Vector resultVector1 = Processing.reversedAnglePoint(reversedAngle, aX, aY, bX, bY, resultVector);
		
		//test if they are equal
		assertEquals(predictionExpected.x, resultVector1.x);
		assertEquals(predictionExpected.y, resultVector1.y);
	
	}
}
