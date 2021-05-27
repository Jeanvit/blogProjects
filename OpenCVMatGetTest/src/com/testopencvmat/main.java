package com.testopencvmat;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.*;
import org.opencv.core.*;

public class main {

	public static void main(String[] args) {
		
		//Make sure to add a link to lib on your IDE so it can find the files
		//For Eclipse users: https://docs.opencv.org/master/d1/d0a/tutorial_java_eclipse.html
	    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    
	    final int CONVERTION_CONST = 1000000;
	    final Mat img = Imgcodecs.imread("img/ear.jpg", Imgcodecs.IMREAD_COLOR);
	    double[]  pixelValue ;
	    double    singlePixelValue;
	    final int rowSize  = img.rows();
	    final int colSize  = img.cols();
	    final int channels = img.channels();
	    final int totalPixelsPerChannel = colSize * rowSize;
	    long      totalSum = 0, startTime2 = 0;
	    double    imageAsMatrix[][][] = new double[rowSize][colSize][channels];
	    
		System.out.println("Image dimension: " + colSize + "x" + rowSize + "x" + channels  + ", totalPixels (per channel): " + totalPixelsPerChannel);
		
		
		/* Using get() from OpenCV to take an array
		 * of BGR pixels.
		 * */
		long startTime = System.nanoTime();
		for (int i = 0 ; i < rowSize ; i ++ ) {
			for (int j = 0 ; j < colSize ; j ++ ) {
				startTime2 = System.nanoTime();
				pixelValue = img.get(i,j);
				totalSum += System.nanoTime() - startTime2;
			}
		}
		System.out.println("[MAT.GET] TOTAL TIME: " + ((System.nanoTime() - startTime)/CONVERTION_CONST) + " ms");	
		System.out.println("[MAT.GET] AVG TIME: " + totalSum/totalPixelsPerChannel + " ns" );
		
		
		/* Converting the image to a Matrix 
		 * to use on following test
		 * */
		for (int i = 0 ; i < rowSize ; i ++ ) {
			for (int j = 0 ; j < colSize ; j ++ ) {
				imageAsMatrix[i][j] =  img.get(i,j); 
			}
		}
		
		/* Making a "get" for each pixel individually using the 
		 * matrix created above
		 */
		totalSum = 0;
		startTime = System.nanoTime();
		for (int i = 0 ; i < rowSize ; i ++ ) {
			for (int j = 0 ; j < colSize ; j ++ ) {
				startTime2 = System.nanoTime();
				for (int c = 0 ; c < channels ; c ++ ) {
					singlePixelValue = imageAsMatrix[i][j][c];
				}
				totalSum += System.nanoTime() - startTime2;
			}
		}
		System.out.println("[AS ARRAY] TOTAL TIME: " + ((System.nanoTime() - startTime)/CONVERTION_CONST) + " ms");
		System.out.println("[AS ARRAY] AVG TIME: " + (totalSum/totalPixelsPerChannel) +" ns" );
		
		/* Making a "get" with direct attribution of each channel
		 * This is closest to the original image.get example
		 * */
		totalSum = 0;
		startTime = System.nanoTime();
		for (int i = 0 ; i < rowSize ; i ++ ) {
			for (int j = 0 ; j < colSize ; j ++ ) {
				startTime2 = System.nanoTime();
				pixelValue = imageAsMatrix[i][j];
				totalSum += System.nanoTime() - startTime2;
			}
		}
		System.out.println("[NO CHANNEL/AS ARRAY] TOTAL TIME: " + ((System.nanoTime() - startTime)/CONVERTION_CONST) + " ms");
		System.out.println("[NO CHANNEL/AS ARRAY] AVG TIME: " + totalSum/totalPixelsPerChannel +" ns");		
		
		/* Test time for OpenCV put(). The equivalent of a set() 
		 */
		totalSum = 0;
		startTime = System.nanoTime();
		for (int i = 0 ; i < rowSize ; i ++ ) {
			for (int j = 0 ; j < colSize ; j ++ ) {
				startTime2 = System.nanoTime();
				img.put(i,j,imageAsMatrix[i][j]);
				totalSum += System.nanoTime() - startTime2;
			}
		}
		System.out.println("[PUT] TOTAL TIME: " + ((System.nanoTime() - startTime)/CONVERTION_CONST) + " ms");
		System.out.println("[PUT] AVG TIME: " + totalSum/totalPixelsPerChannel  + " ns");
	}
}
	