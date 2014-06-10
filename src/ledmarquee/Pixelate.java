package ledmarquee;
/////////////////////////////////////////////////////////////////////////////////////////
/* Class: Pixelate
Purpose: To convert images or text into arrays
Methods:
NOTE: All returns methods return int return as the Processing representation of "color"
	imageToArray:
		Converts an image directly to an array of ints.
	imageToPixelatedPImage:
		Pixelate a PImage and return a PImage to a given resolution.
	imageToPixelatePGraphic:
		Pixelate a PImage and return a PGraphic to a given resolution.
	textToArray:
	 	Converts text to an array of ints.
	textToBooleanArray:
		Converts text to a boolean array at a given resolution (on/off for pixelated text)
	textToPixelatedImage:
		Converts text to an int array at a given resolution. Set text color and background
	
	Example of use:
	
		Pixelate p = new Pixelate(this); 
		resolution = 5;
		testFont = createFont("Georgia", 60);
		int txtColor = color(245, 12, 19);
		int bgColor  = color(0, 0, 255);
		PImage textImg = p.textToPixelatedImage(testFont, txtColor, bgColor, "Here", resolution);
		image(textImg, 400 , 300);
		...
	
	Known issues:
		At the moment there are not many checks for valid input since I was building this
		primarily for my own usage. I will fix so errors are thrown in resolution is too 
		large or invalid.
*/


import processing.core.*; 


///////////////////////////////////////////////////////////////////////////////////////
class Pixelate {
  
  float resolution; 
  PApplet parent; 
  
  Pixelate(PApplet p) {
    parent = p;
    
  }
  
   //Converts a string into array of RGB color - Not given a resolution so 
  ////////////////////////////////////////////////////////////////////////////////////////  
  int[][] textToArray(PFont font, String str) {    
    
    parent.textFont(font);
    int txtWidth  = (int)parent.textWidth(str); 
    int txtHeight = (int)(parent.textDescent() + parent.textAscent()); 
    
    PGraphics pgBuffer    = parent.createGraphics(txtWidth, txtHeight, parent.JAVA2D);
    int [][] colorArray   = new int[txtWidth][txtHeight];  
   
    pgBuffer.beginDraw();
    pgBuffer.background(255);
    pgBuffer.fill(0);
    pgBuffer.textFont(font);
    pgBuffer.textAlign(parent.LEFT, parent.CENTER);
    pgBuffer.text(str, 0 , (5.0f/12.0f) * txtHeight);
    pgBuffer.endDraw();
    
    for(int x = 0; x < txtWidth; x++) {
      for(int y = 0; y < txtHeight; y++) {
        colorArray[x][y] = parent.color ((int)parent.red(pgBuffer.get(x, y)), 
                                  		 (int)parent.green(pgBuffer.get(x, y)), 
                                  		 (int)parent.blue(pgBuffer.get(x, y)));
      } 
    }
    
    return colorArray;
  }//End of textToArray
  
  
  //Converts a string into a boolean area given a particular resolution
  //This is a simple on/off pixel representation of text at a given 
  //resolution.
  ////////////////////////////////////////////////////////////////////////////////////////
  boolean [][] textToBooleanArray(PFont font, String str, int resolution) {
 
      
    this.resolution = resolution;
    parent.textFont(font);
    int txtWidth  = (int)parent.textWidth(str); 
    int txtHeight = (int)(parent.textDescent() + parent.textAscent()); 
    
    PGraphics pgBuffer = parent.createGraphics(txtWidth, txtHeight, parent.JAVA2D);
    int arrayWidth  = (int)Math.ceil((pgBuffer.width) / resolution);
    int arrayHeight = (int)Math.ceil((pgBuffer.height) / resolution);
    boolean [][] pixelArray = new boolean[arrayWidth][arrayHeight];  
   
    pgBuffer.beginDraw();
    pgBuffer.background(255);
    pgBuffer.fill(0);
    pgBuffer.textFont(font);
    pgBuffer.textAlign(parent.LEFT, parent.CENTER);
    pgBuffer.text(str, 0 , (5.0f/12.0f) * txtHeight);
    pgBuffer.endDraw();
    
    for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
      for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
    
        float sum = 0.0f;
        for(int  imgX = resolution * arrayX; imgX < resolution * arrayX + resolution; imgX++) {
          for(int imgY = resolution * arrayY; imgY < resolution * arrayY + resolution; imgY++) {
            float colorAvg = ((float)parent.red(pgBuffer.get(imgX, imgY)) + 
                              (float)parent.green(pgBuffer.get(imgX, imgY)) + 
                              (float)parent.blue(pgBuffer.get(imgX, imgY))) / 3.0f;
            sum = sum + colorAvg;
            
          }
        }
        sum = sum / ((float)resolution * (float)resolution);
        if(sum < 200) {
          pixelArray[arrayX][arrayY] = true;
          
        } 
        else {
          pixelArray[arrayX][arrayY] = false;
        } 
      } 
    }
    
    return pixelArray;  
    
  }
  
  //Converts a image into array of color.
  ////////////////////////////////////////////////////////////////////////////////////////
  int [][] imageToArray(PImage img, int resolution) {
      
    PGraphics pgBuffer = parent.createGraphics(img.width, img.height, parent.JAVA2D);
    int arrayWidth  = (int)Math.ceil((pgBuffer.width) / resolution);
    int arrayHeight = (int)Math.ceil((pgBuffer.height) / resolution);
    int [][] colorArray = new int[arrayWidth][arrayHeight];  
    float pixelsPer = (float)(resolution * resolution);
   

    pgBuffer.beginDraw();
    pgBuffer.image(img, 0, 0);
    pgBuffer.endDraw();
    
    
    for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
      for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
        
        float redSum   = 0.0f;
        float greenSum = 0.0f;
        float blueSum  = 0.0f;
        
        for(int  imgX = resolution * arrayX; imgX < resolution * arrayX + resolution; imgX++) {
          for(int imgY = resolution * arrayY; imgY < resolution * arrayY + resolution; imgY++) {
            
            redSum   = redSum   + (float)parent.red(pgBuffer.get(imgX, imgY));
            greenSum = greenSum + (float)parent.green(pgBuffer.get(imgX, imgY));
            blueSum  = blueSum  + (float)parent.blue(pgBuffer.get(imgX, imgY));
      
          }
        }
        colorArray[arrayX][arrayY] = parent.color(redSum   / pixelsPer, 
                                                  greenSum / pixelsPer,
                                                  blueSum  / pixelsPer);
        
      } 
    }
    
    return colorArray;
  }
  
  
  //Converts a string into an Image
  ////////////////////////////////////////////////////////////////////////////////////////
  PImage textToPixelatedImage(PFont font, int textColor, int bgColor, String str, int resolution) {
 
    this.resolution = resolution;
    parent.textFont(font);
    int txtWidth  = (int)parent.textWidth(str); 
    int txtHeight = (int)(parent.textDescent() + parent.textAscent()); 
    
    PGraphics pgBuffer      = parent.createGraphics(txtWidth, txtHeight, parent.JAVA2D);
    int arrayWidth  = (int)Math.ceil((pgBuffer.width) / resolution);
    int arrayHeight = (int)Math.ceil((pgBuffer.height) / resolution);
    boolean [][] pixelArray = new boolean[arrayWidth][arrayHeight];  
   
    pgBuffer.beginDraw();
    pgBuffer.background(255);
    pgBuffer.fill(0);
    pgBuffer.textFont(font);
    pgBuffer.textAlign(parent.LEFT, parent.CENTER);
    pgBuffer.text(str, 0 , (5.0f/12.0f) * txtHeight);
    pgBuffer.endDraw();
    
    for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
      for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
    
        float sum = 0.0f;
        for(int  imgX = resolution * arrayX; imgX < resolution * arrayX + resolution; imgX++) {
          for(int imgY = resolution * arrayY; imgY < resolution * arrayY + resolution; imgY++) {
            float colorAvg = ((float)parent.red(pgBuffer.get(imgX, imgY)) + 
                              (float)parent.green(pgBuffer.get(imgX, imgY)) + 
                              (float)parent.blue(pgBuffer.get(imgX, imgY))) / 3.0f;
            sum = sum + colorAvg;
            
          }
        }
        sum = sum / ((float)resolution * (float)resolution);
        if(sum < 200) {
          pixelArray[arrayX][arrayY] = true;
          
        } 
        else {
          pixelArray[arrayX][arrayY] = false;
        } 
      } 
    }
    
    PImage outputImage = new PImage(arrayWidth * resolution, arrayHeight * resolution);
    outputImage.loadPixels();
     
    for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
      for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
        for(int  imgX = resolution * arrayX; imgX < resolution * arrayX + resolution; imgX++) {
          for(int imgY = resolution * arrayY; imgY < resolution * arrayY + resolution; imgY++) {
            if(pixelArray[arrayX][arrayY]) { 
            	outputImage.set(imgX, imgY, parent.color(parent.red(bgColor), 
	  					  								 parent.green(bgColor), 
	  					  								 parent.blue(bgColor)));
            }
            else {
              outputImage.set(imgX, imgY, parent.color( parent.red(textColor), 
            		  					  parent.green(textColor), 
            		  					  parent.blue(textColor)));
            } 
            
          }
        }
      } 
    }
        
    parent.updatePixels();
    return outputImage;
    
  }
  
  
  //Converts a string into a boolean area given a particular resolution
  ////////////////////////////////////////////////////////////////////////////////////////
  PImage imageToPixelatedImage(PImage img, int resolution) {
      
    PGraphics pgBuffer = parent.createGraphics(img.width, img.height, parent.JAVA2D);
    int arrayWidth  = (int)Math.ceil((pgBuffer.width) / resolution);
    int arrayHeight = (int)Math.ceil((pgBuffer.height) / resolution);
    int [][] colorArray = new int[arrayWidth][arrayHeight];  
    float pixelsPer = (float)(resolution * resolution);
   
    pgBuffer.beginDraw();
    pgBuffer.image(img, 0, 0);
    pgBuffer.endDraw();
    
    for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
      for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
        
        float redSum   = 0.0f;
        float greenSum = 0.0f;
        float blueSum  = 0.0f;
        
        for(int  imgX = resolution * arrayX; imgX < resolution * arrayX + resolution; imgX++) {
          for(int imgY = resolution * arrayY; imgY < resolution * arrayY + resolution; imgY++) {
            
            redSum   = redSum   + (float)parent.red(pgBuffer.get(imgX, imgY));
            greenSum = greenSum + (float)parent.green(pgBuffer.get(imgX, imgY));
            blueSum  = blueSum  + (float)parent.blue(pgBuffer.get(imgX, imgY));
      
          }
        }
        colorArray[arrayX][arrayY] = parent.color(redSum   / pixelsPer, 
        									   	  greenSum / pixelsPer,
                                                  blueSum  / pixelsPer);
        
      } 
    }
     
    PImage outputImage = new PImage(arrayWidth * resolution, arrayHeight * resolution);
    outputImage.loadPixels();
     
    for(int arrayX = 0; arrayX < arrayWidth; arrayX++) {
      for(int arrayY = 0; arrayY < arrayHeight; arrayY++) {
        for(int  imgX = resolution * arrayX; imgX < resolution * arrayX + resolution; imgX++) {
          for(int imgY = resolution * arrayY; imgY < resolution * arrayY + resolution; imgY++) {
               outputImage.set(imgX, imgY, parent.color((int)parent.red(colorArray[arrayX][arrayY]),
            		   									(int)parent.green(colorArray[arrayX][arrayY]), 
            		   									(int)parent.blue(colorArray[arrayX][arrayY])));
          }
        }
      } 
    }
        
    parent.updatePixels();
    return outputImage;
  }
 
  
  //Converts a string into a boolean area given a particular resolution
  ////////////////////////////////////////////////////////////////////////////////////////
  PGraphics imageToPixelatedPGraphics(PImage img, int resolution) {
  
    img = imageToPixelatedImage(img, resolution); 
    PGraphics pgBuffer = parent.createGraphics(img.width, img.height, parent.JAVA2D);
    int arrayWidth  = (int)Math.ceil((pgBuffer.width) / resolution);
    int arrayHeight = (int)Math.ceil((pgBuffer.height) / resolution);
    int [][] colorArray = new int[arrayWidth][arrayHeight];  
    float pixelsPer = (float)(resolution * resolution);
   
    pgBuffer.beginDraw();
    pgBuffer.image(img, 0, 0);
    pgBuffer.endDraw();
    
    return pgBuffer;
  }
  
 
  
}//End of Class

