package com.atithi;

//import static org.bytedeco.javacpp.opencv_core.CV_32SC1;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


// org.bytedeco.javacpp.opencv_core.Mat;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class LiveFaceDetector {

	//private static final String MY_TRANSFORM = "src/main/resources/lbpcascade_frontalface.xml";
	private static final String MY_TRANSFORM = "/home/pi/face_image/lbpcascade_frontalface.xml"; 
			//"E:/Relive_Cdac/Workspace_Mars/FaceRecogniser/src/main/resources/lbpcascade_frontalface.xml";
	private static int FIXED_IMAGE_WIDTH=277;
	private static int FIXED_IMAGE_HEIGHT=277;
	
	
	
   private boolean faceDetector(Mat image) {
	   
	    boolean ifDetected = false;
		//final Mat image = Highgui.imread(imageName);
		if(!image.empty()) {
			System.out.println("not empty");
			final CascadeClassifier cascadeClassifier = new CascadeClassifier();
			if(cascadeClassifier.load(MY_TRANSFORM)) {
				System.out.println("Loaded");
				final MatOfRect matOfRect = new MatOfRect();
				cascadeClassifier.detectMultiScale(image, matOfRect);
				int faces = matOfRect.toArray().length;
				if(faces > 0) {
				ifDetected=true;
				System.out.println("Faces detected"+faces);
				
				Rect rect_crop = null;
				
				for (Rect rect : matOfRect.toArray()) {
				      Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
				              new Scalar(0, 255, 0));
				      rect_crop = new Rect(rect.x, rect.y, rect.width, rect.height);
				      
				      
				      final Mat cropped_image = new Mat(image, rect_crop);
					  final Mat resized = new Mat();
						org.opencv.core.Size size = new org.opencv.core.Size(FIXED_IMAGE_HEIGHT, FIXED_IMAGE_WIDTH);
						Imgproc.resize(cropped_image, resized, size);
						
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
						Date date = new Date();
						String fileName ="MyGuest";//dateFormat.format(date);
						
						
						
						Highgui.imwrite("/home/pi/face_image/"+fileName+".png", resized);
						System.out.println("   Height is  "+resized.height()+"   Width is  "+resized.width());
				  }
				 
			     }				
				
			  }
			
		}	
		else{
			System.out.println("empty");
		}
		return ifDetected;
	}
	
	
	public static void main(String[] argv) {
		
		System.out.println("hello");
		System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);	
		System.out.println("hello");
		
	    final LiveFaceDetector detector = new LiveFaceDetector();
		final VideoCapture cap = new VideoCapture(0);
		Mat image= null;
	    long start = System.currentTimeMillis();
	    long timeelapsed = 0L;
	    while(timeelapsed < 1*60*1000) {
	    	image = new Mat();
			cap.read(image);
			System.out.println("   Height is  "+image.height()+"   Width is  "+image.width());
			boolean result = detector.faceDetector(image);
			if(result)
			break;	    	
	    	timeelapsed = (new Date().getTime()-start);
	    }
	    
		
//		while(true){
//		
//		image = new Mat();
//		cap.read(image);
//		boolean result = detector.faceDetector(image);
//		if(result)
//		break;
//		}
		
		
		
	}
}
