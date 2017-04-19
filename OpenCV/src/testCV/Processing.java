package testCV;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
	
	private JButton start, stop;
	private JLabel status;
	
	boolean isProcessing = false;
	//Mat changedImg = Imgcodecs.imwrite("img2.jpg", img);
	
	public Processing()
	{
//		img = Imgcodecs.imread("res/saved.png");
//		Imgcodecs.imwrite("test2.png", detectColor(img));
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setResizable(false);
        setVisible(true);
        pack();
        
        processingButtons();
        
		//img = Imgcodecs.imread("res/circles1.jpg");
		//do a start/stop GUI
		
		//img2 = Imgcodecs.imread("test2.jpg");

	}
	
   private Mat detectColor(Mat src) 
   {   
//	   Mat hsv = new Mat();
//	   Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV);
	   
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
	   List<MatOfPoint> blue_contours = new ArrayList<MatOfPoint>();
	   //List<MatOfPoint> white_contours = new ArrayList<MatOfPoint>();
	   
	   //finding contours and keeping them in arrays above
	   Imgproc.findContours(red_contours_mat, red_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	   Imgproc.findContours(blue_contours_mat, blue_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	   //Imgproc.findContours(white_contours_mat, white_contours, new Mat(),Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
	   
	   //grayscale to bgr
	   //Imgproc.cvtColor(red_hue, red_hue, Imgproc.COLOR_GRAY2BGR);
	   
	   loopContoursPaddle(red_hue, red_contours_mat,  red_contours, src);
	   loopContoursPaddle(blue_hue, blue_contours_mat, blue_contours, src);
	   //loopForBall(white_hue, white_contours_mat, white_contours);
	   houghCircle(white_hue, src);
	   
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
				//drawConvexHull(currentContour);
		      }
		  }
		  else
		  {
			  System.out.println("Mat is not found or does not contain any pixels");
		  }
	   }
	   
   }
   
   public void houghCircle(Mat hueMat, Mat src)
   {
	   Mat circles = new Mat();
	   Imgproc.cvtColor(src, circles, Imgproc.COLOR_BGR2GRAY);
	   
	   Imgproc.HoughCircles(hueMat, circles, Imgproc.HOUGH_GRADIENT, 1, hueMat.rows()/8, 200, 25, 9, 11);  
	   //Imgproc.HoughC
	   //hueMat.rows()/8
	   
	   for (int i = 0; i < circles.cols(); i++)
	   {
		   double vCircle[] = circles.get(0,i);
		   Point center = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
		   int radius = (int)Math.round(vCircle[2]);
		   
		   Imgproc.circle(src, center, radius, new Scalar(0,255,0), 2);
		   System.out.println("Radius: " + radius + " Center: " + center);
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
        
        start = new JButton("Start");
        stop = new JButton("Stop");
        status = new JLabel("Press start to enable processing", SwingConstants.CENTER);
        
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
						Mat img;;
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
										Imgcodecs.imwrite("test2.png", detectColor(img));
									}
									
									try 
									{
										Thread.sleep(650);
									} catch (InterruptedException e) {
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
				}
			}
        });
   	}
    
public static void main( String[] args ) throws IOException 
   {
      System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
      
      new Processing();
     // displayConvertedImage();
      
   }


}