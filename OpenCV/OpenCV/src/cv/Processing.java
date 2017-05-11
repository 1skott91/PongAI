package cv;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class Processing extends JFrame
{
	private final int WIDTH = 250;
	private final int HEIGHT = 200;

	static SizedStack<Vector> ballPos =  new SizedStack<Vector>(2);
	static SizedStack<Vector> predictionPos =  new SizedStack<Vector>(2);
	boolean isProcessing = false;
	static Vector prediction;
	List<MatOfPoint> blue_contours;
	
	public Processing()
	{		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setVisible(true);
        setAlwaysOnTop(true);
        pack();
        
        processingButtons();
        
        Vector test = new Vector (0, 0, 0);
   		Vector test1 = new Vector (0, 0, 0);
   				
   		ballPos.add(test);
   		ballPos.add(test1);
   		System.out.println(ballPos.size());
   		System.out.println(ballPos.toString());


	}
	
   public Mat detectColor(Mat src) throws AWTException 
   {   	   
	   Mat orig_img = src.clone();
	   
	   //reduce the noise in image to reduce false circles
	   Imgproc.medianBlur(src, src, 3);
	   
	   //Convert src image to hsv
	   Mat hsv = new Mat();
	   Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
	   
	   //Threshold hsv image, keep red pixels only ---- red
	   Mat lower_red_hue = new Mat();
	   Mat upper_red_hue = new Mat();
	   Core.inRange(src, new Scalar(0, 0, 95), new Scalar(0, 0, 250), lower_red_hue);
	   Core.inRange(src, new Scalar(0, 0, 100), new Scalar(0, 0, 255), upper_red_hue);
	   
	   Mat red_hue = new Mat();
	   Core.addWeighted(lower_red_hue, 1.0, upper_red_hue,  1.0, 0.0, red_hue);
	   Imgproc.GaussianBlur(red_hue, red_hue, new Size(3,3), 1,1);
	   
	   //Threshold hsv image, keep blue pixels only ---- blue
	   Mat lower_blue_hue = new Mat();
	   Mat upper_blue_hue = new Mat();
	   Core.inRange(src, new Scalar(80, 15, 0), new Scalar(255, 150, 0), lower_blue_hue);
	   Core.inRange(src, new Scalar(90, 30, 10), new Scalar(240, 90, 10), upper_blue_hue);
	   
	   Mat blue_hue = new Mat();
	   Core.addWeighted(lower_blue_hue, 1.0, upper_blue_hue,  1.0, 0.0, blue_hue);
	   Imgproc.GaussianBlur(blue_hue, blue_hue, new Size(3,3), 1,1);
	   
	   //Threshold hsv image, keep white pixels only ---- white
	   Mat lower_white_hue = new Mat();
	   Mat upper_white_hue = new Mat();
	   Core.inRange(src, new Scalar(250, 250, 250), new Scalar(252, 252, 252), lower_white_hue);
	   Core.inRange(src, new Scalar(253, 253, 253), new Scalar(255, 255, 255), upper_white_hue);
	   
	   Mat white_hue = new Mat();
	   Core.addWeighted(lower_white_hue, 1.0, upper_white_hue,  1.0, 0.0, white_hue);
	   Imgproc.GaussianBlur(white_hue, white_hue, new Size(3,3), 3,3);
	 
	   //creating mats with contours while keeping the hue mats
	   Mat red_contours_mat = red_hue.clone();
	   Mat blue_contours_mat = blue_hue.clone();
	   //Mat white_contours_mat = white_hue.clone();
	   
	   //Arrays of points for each contour in mat
	   List<MatOfPoint> red_contours = new ArrayList<MatOfPoint>(); 
	   blue_contours = new ArrayList<MatOfPoint>();
	   //List<MatOfPoint> white_contours = new ArrayList<MatOfPoint>();
	   
	   //finding contours and keeping them in arrays above
	   Imgproc.findContours(red_contours_mat, red_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	   Imgproc.findContours(blue_contours_mat, blue_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	   //Imgproc.findContours(white_contours_mat, white_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	   
	   //grayscale to bgr
	   //Imgproc.cvtColor(red_hue, red_hue, Imgproc.COLOR_GRAY2BGR);
	   
	   loopContoursPaddle(red_hue, red_contours_mat,  red_contours, src);
	   loopContoursPaddle(blue_hue, blue_contours_mat, blue_contours, src);
	   houghCircle(white_hue, src);
	   prediction();
	  
	   
	   return src;
   }
   
   public void loopContoursPaddle(Mat hueMat,Mat contour_mat, List<MatOfPoint> contourArray, Mat src)
   {
	   for (int i = 0; i < contourArray.size(); i++)
	   {
		  MatOfPoint currentContour = contourArray.get(i);

		  Rect rect = Imgproc.boundingRect(currentContour);

		  if (hueMat != null)
		  {
			  if (rect.height >= 70 && rect.height <= 75 && rect.width >= 10 && rect.width <= 15)
		      {
				System.out.println("Paddle: " + rect.x +","+rect.y+","+rect.height+","+rect.width);
				Imgproc.rectangle(contour_mat, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(255,255,255));
				drawBoundingBox(currentContour, src);
		      }
		  }
	   }
	   
   }
   
   public void houghCircle(Mat hueMat, Mat src)
   {
	   Mat circles = new Mat();
	   Imgproc.cvtColor(src, circles, Imgproc.COLOR_BGR2GRAY);
	   
	   Imgproc.HoughCircles(hueMat, circles, Imgproc.HOUGH_GRADIENT, 1, hueMat.rows()/8, 200, 25, 9, 11);  
	   
	   for (int i = 0; i < circles.cols(); i++)
	   {
		   double vCircle[] = circles.get(0,i);
		   Point center = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
		   int radius = (int)Math.round(vCircle[2]);
		   
		   Vector centerVector = new Vector((int) center.x, (int) center.y, 0);
		   
		   Imgproc.circle(src, center, radius, new Scalar(0,255,0), 2);
		   
		   System.out.println("Radius: " + radius + " Center: " + center);
//		   System.out.println("Center: " + centerVector);
		   ballPos.push(centerVector);
		   System.out.println("Ball position" + ballPos.toString());
	   }
	   
   }
   
   	private void drawBoundingBox(MatOfPoint currentContour, Mat src) 
   	{
	   Rect rectangle = Imgproc.boundingRect(currentContour);
	   Imgproc.rectangle(src, rectangle.tl(), rectangle.br(), new Scalar(0,255,0),1);
	}
   	
   	private void processingButtons()
   	{
        JPanel panel = new JPanel();
        add(panel);
    
        GridLayout grid = new GridLayout(3,1);
        panel.setLayout(grid);
        
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");
        JLabel status = new JLabel("Press start to enable processing", SwingConstants.CENTER);
        
        panel.add(start);
        panel.add(stop);
        panel.add(status);
        
        Thread thread = new Thread();
        thread.start();
        
        start.addActionListener(new ActionListener()
        {
			public void actionPerformed(ActionEvent e) 
			{
				isProcessing = true;
				Thread thread = new Thread()
				{
					public void run()
					{
						Mat img;
						if (isProcessing)
						{
							status.setText("Processing...");
							while (isProcessing)
							{
								File f = new File("res/saved.png");
								if (f.isFile() && f.canRead())
								{
									System.out.println(f + ( f.exists() +  " is found "));
									img = Imgcodecs.imread("res/saved.png");
									if (!img.empty()) 
									{
										try {
											Imgcodecs.imwrite("test2.png", detectColor(img));
											paddleMovement();
										} catch (AWTException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										status.setText("Processing failed, try again...");
									}	
								}
							}
						}
					}
				}; 
				thread.start();
			}
        });
        
        stop.addActionListener(new ActionListener()
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				isProcessing = false;
				if (!isProcessing)
				{
					status.setText("Currently not processing.");
					thread.interrupt();
				}
			}
        });
   	}
   	
   	public static void prediction()
   	{	

   		//int e1 = ballPos.size() - 1;
   		Vector le = ballPos.elementAt(1);
   		Vector fe = ballPos.elementAt(0);

			double px1 = fe.x;
			double py1 = fe.y;
			
			double px2 = le.x;
			double py2 = le.y;
			
			double distance = Math.sqrt(Math.pow((px1-px2), 2) + Math.pow((py1-py2), 2));
			
			double dx = (px2 - px1) / distance;
	  		double dy = (py2 - py1) / distance;
	  		
	  		double px3 = px2 + (distance * 30) * dx;
	  		double py3 = py2 + (distance * 30) * dy; //x: 352.0 y: 301.0
	  		
	  		prediction = new Vector ((int)px3, (int)py3, 0);
			
			//System.out.println(prediction.toString());
			System.out.println(prediction);
			if (prediction.x != 0 && prediction.y !=0)
			{
				predictionPos.push(prediction);
			}
   	}
			
   	
   	public void paddleMovement() throws AWTException
   	{

   		Robot r = new Robot();
   		
   	  Thread t2=new Thread(){  
   	    public void run(){  
   	    	
   	   		Rect rect = null;
   	   		for (int i = 0; i < blue_contours.size(); i++)
   	   		{
   	   			MatOfPoint currentContour = blue_contours.get(i);
   	   			rect = Imgproc.boundingRect(currentContour);
   	   			
   	   		}
   	   		System.out.println("x: " + rect.x + " y: " + rect.y);
   	   		
   	   			if (prediction.y < rect.y)
   	   			{
   	   				r.keyRelease(KeyEvent.VK_DOWN);
   		   			r.keyPress(KeyEvent.VK_UP);
   	   			}
   	   			else
   	   			{
   	   				r.keyRelease(KeyEvent.VK_UP);
   	   	   			r.keyPress(KeyEvent.VK_DOWN);
   	   	   		}
   	   		}   
   	  };  
   	  t2.start();
   	}
   	
   	public static Vector reversedAnglePoint(double angleIn, int fpX, int fpY, int spX, int spY, Vector result)
   	{
 	   double angle = angleIn;

 	   int firstX = fpX;
 	   int firstY = fpY;
 	   
 	   int startX = spX;
 	   int startY = spY;
 	   
 	   double length = Math.sqrt(Math.pow((firstX-startX), 2) + Math.pow((firstY-startY), 2));

 	   double endX = startX + Math.cos(angle) * length;
 	   double endY = startY + Math.sin(angle) * length;
 	   int endX1 = (int) Math.round(endX);
 	   int endY1 = (int) Math.round(endY);
 	   
 	   result = new Vector (endX1, endY1, 0);
 	   
 	   return result;
   	}
   	
   	public static Vector nextPoint(int Ax, int Ay, int Bx, int By, Vector nextpoint)
   	{
   		double px1 = Ax;
		double py1 = Ay;
		
		double px2 = Bx;
		double py2 = By;
		
		double distance = Math.sqrt(Math.pow((px1-px2), 2) + Math.pow((py1-py2), 2));
		
		double dx = (px2 - px1) / distance;
  		double dy = (py2 - py1) / distance;
  		
  		double px3 = px2 + 	distance * dx;
  		double py3 = py2 + distance * dy; //x: 352.0 y: 301.0
  		
  		nextpoint = new Vector ((int)px3, (int)py3, 0);
  		
  		return nextpoint;
   	}
   	
  //Attempt to fix prediction issue
//	public static void predictionv3()
//	{
// 	  Thread t3=new Thread()
//   	  {  
//   	    public void run()
//   	    {  
//
////   		   		Vector le = ballPos.elementAt(1);
////   		   		Vector fe = ballPos.elementAt(0);
//   	   			
//   	   			
//   		   		Vector fe = ballPos.get(0);
//   		        Vector le = ballPos.get(1);
//   		        
//		   			if (fe.x !=0 && fe.y !=0)
//		   			{
//       		        predictionArray.add(le);
//        			predictionArray.add(fe);
//		   			}
//       		   		
//   		   		while(isPredicting)
//   		   		{
//   		   			
//       					Vector fe1 = predictionArray.get(predictionArray.size() - 1);
//       		   			Vector le1 = predictionArray.get(predictionArray.size() - 2);
//       					
//       					double px1 = le1.x;
//       					double py1 = le1.y;
//       					
//       					double px2 = fe1.x;
//       					double py2 = fe1.y;
//       					
//       					double xVel = px2 - px1;
//       			   		double yVel = py2 - py1;
//       			   		
//       			   		double m = yVel/xVel;
//       			   		double yintersect = py1 - m * px1;
//       			   		double slopeFormula = m*px1 + yintersect;
//       					
//       					double distance = Math.sqrt(Math.pow((px1-px2), 2) + Math.pow((py1-py2), 2));
//       					
//       					double dx = (px2 - px1) / distance;
//       			  		double dy = (py2 - py1) / distance;
//       			  		
//       			  		double px3 = px2 + distance * dx;
//       			  		double py3 = py2 + distance * dy; //x: 352.0 y: 301.0
//       			  		
//       			  		prediction = new Vector ((int)px3, (int)py3, 0);
//       			  		
//       		    		if (prediction.x != 0 && prediction.y != 0)
//       		       		{
//       		    			if (prediction.y < 410 && prediction.y > -10)
//       		    			{
//       		    				predictionArray.add(prediction);
//       		    				if (prediction.y > 400 || prediction.y < 0)
//       		    				{
//       	           					Vector fp = predictionArray.get(predictionArray.size() - 1);
//       	           		   			Vector lp = predictionArray.get(predictionArray.size() - 2);
//       		    					
//								  int xDiff1 = le1.x - fe1.x;
//								  int yDiff1 = le1.y - fe1.y;
//								  result =  Math.atan2(yDiff1, xDiff1) * 180.0 / Math.PI;
//								  
//								  double reversedAngle = result * -1;
//								  
//								  firstP = new Vector(lp.x,lp.y,0);
//								  secondP = new Vector(fp.x,fp.y,0);
//								  
//								  System.out.println("angle: " + result);
//								  System.out.println("angle 2 : " + result * -1);
//								  System.out.println("fe1: " + fp + " le1: " + lp);
//								  
//								  //add firstP and secondP to array later to draw line
//								  
//								  Vector reversedAnglePoint = new Vector(0,0,0);
//								  
//								  reversedAnglePoint(reversedAngle, firstP.x, firstP.y, secondP.x, secondP.y, reversedAnglePoint);
//								  
//								  Vector predictionAfterAngle = new Vector(0,0,0);
//								  
//								  do
//								  {
//									  nextPoint(secondP.x, secondP.y, reversedAnglePoint.x, reversedAnglePoint.y, predictionAfterAngle);
//									  
//									  predictionArray.add(predictionAfterAngle);
//									  
//									  if (predictionAfterAngle.x > 630 && predictionAfterAngle.x < 10)
//									  {
//										  predictionPaddle = new Vector(fp.x, fp.y, 0);
//										  
//										  System.out.println("Prediction paddle: " + predictionPaddle);
//										  System.out.println(predictionArray.toString());
//									  }
//								  }
//								  while(predictionAfterAngle.y < 400);
//
//       		    				}
//       		    			}
//       		       			
//       		       		}
//       		    		//System.out.println(prediction.toString());
//       		    		//System.out.println(predictionArray.toString());
//   		   		}
//   	   		}
//   	    
//   	  };  
//   	  t3.start();
//	}
   	
    
public static void main( String[] args ) throws IOException 
   {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      new Processing();
      new predictionPanel();
      
      
   }


}