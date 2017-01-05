/**
 * Copyright(c) Beijing Kungeek Science & Technology Ltd. 
 */
package cn.gb40;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;

/**
 * <pre>
 * 图像处理 - 中值滤波（网上方法）。
 * </pre>
 * @author 王文辉  946374340@qq.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class MedianImage3  extends Frame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7685922770912973693L;
	private  BufferedImage dstImage =null;	
	private  BufferedImage inputImage =null;
	private int Image_Width, Image_Height;  
	private int[] pixels;  
	
	
	
	
	/**
	 * @param inputImage
	 * @param image_Width
	 * @param image_Height
	 * @param flag_load
	 */
	public MedianImage3(BufferedImage inputImage
		) {
		this.inputImage=inputImage;
		dstImage = inputImage;
		Image_Width = inputImage.getWidth();
		Image_Height = inputImage.getHeight();
		pixels = new int[Image_Width*Image_Height];  
	}


	public BufferedImage  medianImage(){
		   try{  
               PixelGrabber pg = new PixelGrabber(inputImage,0,0,Image_Width,Image_Height,pixels,0,Image_Width);  
               pg.grabPixels();  
           }catch(InterruptedException e3){  
             e3.printStackTrace();  
           }  
          
           BufferedImage grayImage = new BufferedImage(Image_Width, Image_Height,   
                     BufferedImage.TYPE_INT_RGB);  
             
           ColorModel cm = ColorModel.getRGBdefault();  
             
           int[] tpRed = new int[9];  
           int[] tpGreen = new int[9];  
           int[] tpBlue = new int[9];  
           int media_Red,media_Green,media_Blue;
           for(int i=1;i<Image_Height-1;i++){  
               for(int j=1;j<Image_Width-1;j++){  
               	int m = 0;
               	for(int k=-1;k<2;k++){
               		for(int l=-1;l<2;l++){
               			tpRed[m] = cm.getRed(pixels[(i+k)*Image_Width+j+l]);
               			tpGreen[m] = cm.getGreen(pixels[(i+k)*Image_Width+j+l]); 
               			tpBlue[m] = cm.getBlue(pixels[(i+k)*Image_Width+j+l]); 
               			m++;
               		}
               	}
               	// 对像素点（i*Image_Width+j）八邻域中的所有像素点的redValue排序并求出中值；		
                   for(int rj=0; rj<8; rj++){  
                       for(int ri=0; ri<8-rj; ri++){  
                           if(tpRed[ri]>tpRed[ri+1]){  
                               int Red_Temp = tpRed[ri];  
                               tpRed[ri] = tpRed[ri+1];  
                               tpRed[ri+1] = Red_Temp;  
                           }  
                       }  
                   }  
                   media_Red = tpRed[4];  
                   // 对像素点（i*Image_Width+j）八邻域中的所有像素点的GreenValue 排序并求出中值；
                   for(int rj=0; rj<8; rj++){  
                       for(int ri=0; ri<8-rj; ri++){  
                           if(tpGreen[ri]>tpGreen[ri+1]){  
                               int Green_Temp = tpGreen[ri];  
                               tpGreen[ri] = tpGreen[ri+1];  
                               tpGreen[ri+1] = Green_Temp;  
                           }  
                       }  
                   }  
                   media_Green= tpGreen[4];  
                   // 对像素点（i*Image_Width+j）八邻域中的所有像素点的BlueValue排序并求出中值；
                   for(int rj=0; rj<8; rj++){  
                       for(int ri=0; ri<8-rj; ri++){  
                           if(tpBlue[ri]>tpBlue[ri+1]){  
                               int Blue_Temp = tpBlue[ri];  
                               tpBlue[ri] = tpBlue[ri+1];  
                               tpBlue[ri+1] = Blue_Temp;  
                           }  
                       }  
                   }  
                   media_Blue = tpBlue[4];  
                     
                   int rgb = 255<<24|media_Red<<16|media_Green<<8|media_Blue;   
                   grayImage.setRGB(j, i, rgb);  
               }     
           }  
           dstImage = grayImage;  
           repaint(); 
           return dstImage;
         
       }


//绘图函数  
public void paint(Graphics g){  
        g.drawImage(dstImage,50,50,this);  
}  
}
