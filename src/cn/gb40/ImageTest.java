package cn.gb40;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;


/**
 * 这是一个自动识别验证码的程序。要求是简单的验证码，固定大小，固定位置，固定字体；字体纯色最好，如不是需要修改代码。
 * 
 * @author acer
 *
 */
public class ImageTest {
    // 存放所有下载验证码的目录
    private static final String DOWNLOAD_DIR = "C:\\Users\\Administrator\\Desktop\\testOcr\\temp3";

    // 存放已经拆分开的单个数字图片的目录，供比对用
    private static final String REMOVE_DIR = "C:\\Users\\Administrator\\Desktop\\testOcr\\temp3\\remove\\";
    // 存放已经拆分开的单个数字图片的目录，供比对用
    private static final String TRAIN_DIR = "C:\\Users\\Administrator\\Desktop\\testOcr\\temp3\\train\\";

    // 存放比对结果的目录（重新以验证码所含数字命名文件，非常直观）
    private static final String RESULT_DIR = "C:\\Users\\Administrator\\Desktop\\testOcr\\temp3\\result";

    // 存放比对图片与代表数字的Map
    private static Map<BufferedImage, String> trainMap = new HashMap<BufferedImage, String>();
    
    // 图片过滤器，想要什么样的图片，传进名称即可。如：png/gif/.png
    static class ImageFileFilter implements FileFilter {
        private String postfix = ".jpg";
        
        public ImageFileFilter(String postfix) {
            if(!postfix.startsWith("."))
                postfix = "." + postfix;
            
            this.postfix = postfix;
        }
        
        @Override
        public boolean accept(File pathname) {
            return pathname.getName().toLowerCase().endsWith(postfix);
        }
    }

    static {
        try {
            // 将TRAIN_DIR目录的供比对的图片装载进来
            File dir = new File(TRAIN_DIR);
            File[] files = dir.listFiles(new ImageFileFilter("jpg"));
            for (File file : files) {
                trainMap.put(ImageIO.read(file), file.getName().charAt(0) + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static int isBlack(int colorInt) {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {  
            return 1;  
        }  
        return 0;  
    }  
  
    public static int isWhite(int colorInt) {  
        Color color = new Color(colorInt);  
        if (color.getRed() + color.getGreen() + color.getBlue() > 600) {  
            return 1;  
        }  
        return 0;  
    }  
    
    public static BufferedImage removeBlank(BufferedImage img) throws Exception {  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int start = 0;  
        int end = 0;  
        Label1: for (int y = 0; y < height; ++y) {  
            for (int x = 0; x < width; ++x) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    start = y;  
                    break Label1;  
                }  
            }  
        }  
        Label2: for (int y = height - 1; y >= 0; --y) {  
            for (int x = 0; x < width; ++x) {  
                if (isBlack(img.getRGB(x, y)) == 1) {  
                    end = y;  
                    break Label2;  
                }  
            }  
        }  
        return img.getSubimage(0, start, width, end - start + 1);  
    }  
    // 1.下载验证码：将多个验证码图片下载到指定目录，要求各种可能的验证码（单个数字）都应该有，比如：0-9。
//    private void downloadImage() throws Exception {
//        HttpClient httpClient = new DefaultHttpClient();
//        for (int i = 0; i < 10; i++) {
//            String url = "http://www.yoursite.com/yz.php";
//            HttpGet getMethod = new HttpGet(url);
//            try {
//                HttpResponse response = httpClient.execute(getMethod, new BasicHttpContext());
//                HttpEntity entity = response.getEntity();
//                InputStream instream = entity.getContent(); 
//                OutputStream outstream = new FileOutputStream(new File(DOWNLOAD_DIR, i + ".png"));
//                int l = -1;
//                byte[] tmp = new byte[2048]; 
//                while ((l = instream.read(tmp)) != -1) {
//                    outstream.write(tmp);
//                } 
//                outstream.close();
//            } finally {
//                getMethod.releaseConnection();
//            }
//        }
//
//        System.out.println("下载验证码完毕！");
//    }
    
    // 2.去除图像干扰像素（非必须操作，只是可以提高精度而已）。
    public static BufferedImage removeInterference(BufferedImage img)  
            throws Exception {  
//        int width = image.getWidth();  
//        int height = image.getHeight();  
//        for (int x = 0; x < width;++x) {  
//            for (int y = 0; y < height;++y) { 
//          // 下面三行代码将一个数字转换为RGB数字  
//
//            
//                if (isNotFontColor(image.getRGB(x, y))) {
//              
//////                    // 如果当前像素是字体色，则检查周边是否都为白色，如都是则删除本像素。
////                    image.setRGB(x, y, Color.WHITE.getRGB());  
////                	int roundWhiteCount = 0;
////                    if(isWhiteColor(image, x+1, y+1))
////                        roundWhiteCount++;
////                    if(isWhiteColor(image, x+1, y-1))
////                        roundWhiteCount++;
////                    if(isWhiteColor(image, x-1, y+1))
////                        roundWhiteCount++;
////                    if(isWhiteColor(image, x-1, y-1))
////                        roundWhiteCount++;
////                    if(roundWhiteCount >2) {
//                	
//                             image.setRGB(x, y, Color.WHITE.getRGB());  
//                             
//                     
//                    }
////                } 
//            }  
//        }  
//    
//        return image;  
		
		img = img.getSubimage(2, 2, img.getWidth()-7 , img.getHeight()-2);

	int width = img.getWidth();
	int height = img.getHeight();
	double subWidth = width / 4.0;
	for (int i = 0; i < 4; i++) {
//		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
//		for (int x = (int) (1 + i * subWidth); x < (i + 1) * subWidth
//				&& x < width - 1; ++x) {
//			for (int y = 0; y < height; ++y) {
//				if (isWhite(img.getRGB(x, y)) == 1)
//					continue;
////				if (map.containsKey(img.getRGB(x, y))) {
////					map.put(img.getRGB(x, y), map.get(img.getRGB(x, y)) + 1);
////				} else {
////					map.put(img.getRGB(x, y), 1);
////				}
//			}
//		}
//		int max = 0;
//		int colorMax = 0;
//		for (Integer color : map.keySet()) {
//			if (max < map.get(color)) {
//				max = map.get(color);
//				colorMax = color;
//			}
//		}
		for (int x = (int) (1 + i * subWidth); x < (i + 1) * subWidth
				&& x < width - 1; ++x) {
			for (int y = 0; y < height; ++y) {
				 int color=img.getRGB(x, y);
					if (isWhite(color) == 1)
					{
						continue;
					}
					
				 Color temp=new Color(color);
				 if (temp.getRed() <= 110 && temp.getGreen() <= 120
							&& temp.getBlue() <= 120) {
						img.setRGB(x, y, Color.BLACK.getRGB());
					} else {
					
						img.setRGB(x, y,Color.WHITE.getRGB());
					}

//				if (img.getRGB(x, y) != colorMax) {
//					img.setRGB(x, y, Color.WHITE.getRGB());
//				} else {
//					img.setRGB(x, y, Color.BLACK.getRGB());
//				}
			}
		}
	}
	return img;
     }
    
    // 取得指定位置的颜色是否为白色，如果超出边界，返回true
    // 本方法是从removeInterference方法中摘取出来的。单独调用本方法无意义。
    private static boolean isWhiteColor(BufferedImage image, int x, int y) throws Exception {
        if(x < 0 || y < 0) return true;
        if(x >= image.getWidth() || y >= image.getHeight()) return true;

        Color color = new Color(image.getRGB(x, y));
        
        return color.equals(Color.WHITE)?true:false;
    }

    
    public static List<BufferedImage> splitImage1(BufferedImage img)  
            throws Exception {  
        List<BufferedImage> subImgs = new ArrayList<BufferedImage>();  
//        int width = img.getWidth();  
//        int height = img.getHeight();  
//        List<Integer> weightlist = new ArrayList<Integer>();  
//        for (int x = 2; x < width-1; ++x) {  
//            int count = 0;  
//            for (int y =2; y < height-1; ++y) {  
//                if (isBlack(img.getRGB(x, y)) == 1) {  
//                    count++;  
//                }  
//            }  
//            weightlist.add(count);  
//        }  
//        for (int i = 0; i < weightlist.size();i++) {  
//            int length = 0;  
//            while (i < weightlist.size() && weightlist.get(i) > 0) {  
//                i++;  
//                length++;  
//            }  
//            if (length > 2) {  
//                subImgs.add(removeBlank(img.getSubimage(i - length, 0,  
//                        length, height-1)));  
//            }  
//        }  
//        return subImgs;  
		subImgs.add(img.getSubimage(1, 2, 20, 28));
		subImgs.add(img.getSubimage(21, 2, 20, 28));
		subImgs.add(img.getSubimage(41, 2, 19, 28));
		subImgs.add(img.getSubimage(60, 2, 22, 28));
		return subImgs;
    }  
    
//    // 3.判断拆分验证码的标准：就是定义验证码中包含的各数字的x、y坐标值，及它们的宽度（width）、高度（height）。
//    private static List<BufferedImage> splitImage(BufferedImage image) throws Exception {
//        final int DIGIT_WIDTH = 19;
//        final int DIGIT_HEIGHT = 16;
//System.out.println(image.getHeight());
//System.out.println(image.getWidth());
//        List<BufferedImage> digitImageList = new ArrayList<BufferedImage>();
//        digitImageList.add(image.getSubimage(10, 2, 22, 30));
//        digitImageList.add(image.getSubimage(30, 2, 22, 30));
//        digitImageList.add(image.getSubimage(40, 2, 22, 30));
//        digitImageList.add(image.getSubimage(60, 2, 22, 30));
//
//        return digitImageList;
//    }

//    // 4.判断字体的颜色含义：正常可以用rgb三种颜色加起来表示，字与非字应该有显示的区别，找出来。
//    private static boolean isNotFontColor( int colorInt) {
//                 Color color = new Color(colorInt);
//            	
//            	return  color.getRed() + color.getGreen() + color.getBlue()>300;
//    	
//    }


    // 5.将下载的验证码图片全部拆分到另一个目录。
    public void generateStdDigitImgage() throws Exception {
        File dir = new File(DOWNLOAD_DIR);
        File[] files = dir.listFiles(new ImageFileFilter("jpg"));
        
        int counter = 0;
        for (File file : files) {
            BufferedImage image = ImageIO.read(file);
            image=   removeInterference(image);
           MedianImage3 medianImage=new MedianImage3(image);
            image=medianImage.medianImage();
            List<BufferedImage> digitImageList = splitImage1(image);
            for (int i = 0; i < digitImageList.size(); i++) {
                BufferedImage bi = digitImageList.get(i);
                ImageIO.write(bi, "JPG", new File(TRAIN_DIR, "temp_" + counter++ + ".jpg"));
            }
        }
        System.out.println("生成供比对的图片完毕，请到目录中手工识别并重命名图片，并删除其它无关图片！");
    }
    

    // 7.测试判断效果：运行方法，可以调整rgb三值，以达到高的分辨率。
    // 目前此方法提供在输出判断结果的同时，在目标目录生成以判断结果命名的新验证码图片，以批量检查效果。
    public void testDownloadImage() throws Exception {
        File dir = new File(DOWNLOAD_DIR);
        File[] files = dir.listFiles(new ImageFileFilter("jpg"));
        
        for (File file : files) {
            String validateCode = getValidateCode(file);
            System.out.println(file.getName() + "=" + validateCode);
        }
        
        System.out.println("判断完毕，请到相关目录检查效果！");
    }
    
    /**
     * 8.提供给外界接口调用。
     * @param file
     * @return
     * @throws Exception
     */
    public static String getValidateCode(File file) throws Exception {
        // 装载图片
        BufferedImage image = ImageIO.read(file);
        image=removeInterference(image);
        MedianImage3 medianImage=new MedianImage3(image);
        image=medianImage.medianImage();
        // 拆分图片
        List<BufferedImage> digitImageList = splitImage1(image);
        String result = "";
        // 循环每一位数字图进行比对
        StringBuilder sb = new StringBuilder();
        for (BufferedImage digitImage : digitImageList) {
        	
        
        
            result+=    getSingleCharOcr(digitImage,trainMap);
     	   
//            int width = digitImage.getWidth();
//            int height = digitImage.getHeight();
//            
//            // 最小的不同次数（初始值为总像素），值越小就越像。
//            int minDiffCount = width * height;
//            for (BufferedImage bi : trainMap.keySet()) {
//                // 对每一位数字图与字典中的进行按像素比较
//                int currDiffCount = 0; // 按像素比较不同的次数
//                outer : for (int x = 0; x < width; ++x) {
//                    for (int y = 0; y < height; ++y) {
//                        if (isNotFontColor(digitImage.getRGB(x, y)) != isNotFontColor(bi.getRGB(x, y))) {
//                            // 按像素比较如果不同，则加1；
//                            currDiffCount++;
//                            // 如果值大于minDiffCount，则不用再比较了，因为我们要找最小的minDiffCount。
//                            if (currDiffCount >= minDiffCount) 
//                                break outer;
//                        }
//                    }
//                }
//                if (currDiffCount < minDiffCount) {
//                    // 现在谁差别最小，就先暂时把值赋予给它
//                    minDiffCount = currDiffCount;
//                    result = trainMap.get(bi);
//                }
//            }
//            sb.append(result);
        }        
        
        
         result= result.replaceAll("#", "");
        ImageIO.write(image, "JPG", new File(RESULT_DIR, result + ".jpg"));
        
        return result;
    }

    
    
    public static String getSingleCharOcr(BufferedImage img,  
            Map<BufferedImage, String> map) {  
        String result = "#";  
        int width = img.getWidth();  
        int height = img.getHeight();  
        int min = width * height;  
        for (BufferedImage bi : map.keySet()) {  
            int count = 0;  
            if (Math.abs(bi.getWidth()-width) > 2)  
                continue;  
            int widthmin = width < bi.getWidth() ? width : bi.getWidth();  
            int heightmin = height < bi.getHeight() ? height : bi.getHeight();  
            Label1: for (int x = 0; x < widthmin; ++x) {  
                for (int y = 0; y < heightmin; ++y) {  
                    if (isBlack(img.getRGB(x, y)) != isBlack(bi.getRGB(x, y))) {  
                        count++;  
                        if (count >= min)  
                            break Label1;  
                    }  
                }  
            }  
            if (count < min) {  
                min = count;  
                result = map.get(bi);  
            }  
        }  
        return result;  
    }  
    
    public static void main(String[] args) throws Exception {
        ImageTest ins = new ImageTest();
    
        // 第1步，下载验证码到DOWNLOAD_DIR
//        ins.downloadImage();
        
       //  第2步，去除干扰的像素
//        File dir = new File(DOWNLOAD_DIR);
//        File[] files = dir.listFiles(new ImageFileFilter("jpg"));
//        	  for (File file : files) {
//                  BufferedImage image = ImageIO.read(file);
//                 image=   removeInterference(image);
//                 MedianImage3 medianImage=new MedianImage3(image);
//                 image=medianImage.medianImage();
//                  ImageIO.write(image, "jpg", new File(REMOVE_DIR+file.getName()));
//                  System.out.println("成功处理：" + file.getName());
           
//                  ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);  
//                  ColorConvertOp op = new ColorConvertOp(cs, null);  
//                  image2 = op.filter(image2, null);  
//                  ImageIO.write(image2, "JPEG",  new File("temp3\\new"+file.getName()));  
//                  List<BufferedImage> list=   splitImage(image2);
//                  
//                  ImageIO.write(list.get(0), "jpg", new File("temp3\\1"+file.getName()));
//                  ImageIO.write(list.get(1), "jpg", new File("temp3\\2"+file.getName()));
//                  ImageIO.write(list.get(2), "jpg", new File("temp3\\3"+file.getName()));
//                  ImageIO.write(list.get(3), "jpg", new File("temp3\\4"+file.getName()));
			
         
//        }
      
  
        // 第3步，判断拆分验证码的标准
        // 通过PhotoShop打开验证码并放大观察，我这儿的结果参考splitImage()方法中的变量
        
        // 第4步，判断字体的颜色含义
        // 通过PhotoShop打开验证码并放大观察，我这儿字体颜色的rgb总值加起来在340。因为是纯色。
        
        // 第5步，将下载的验证码图片全部拆分到TRAIN_DIR目录。
      //ins.generateStdDigitImgage();
        
        // 第6步，手工命名文件
        // 打开资源管理器，选择TRAIN_DIR，分别找出显示0-9数字的文件，以它的名字重新命名，删除其它所有的。
        
        // 第7步，测试判断效果，运行后打开RESULT_DIR，检查文件名是否与验证码内容一致。
       ins.testDownloadImage();
        
        // 第8步，提供给外界接口调用。
//        String validateCode = ImageProcess.getValidateCode(new File(DOWNLOAD_DIR, "0.png"));
//        System.out.println("验证码为：" + validateCode);
    }
//    public static String getAllOcr(String file) throws Exception {  
//   
//        List<BufferedImage> listImg = splitImage(img);  
//        Map<BufferedImage, String> map =trainMap;
//        String result = "";  
//        for (BufferedImage bi : listImg) {  
//            result += getSingleCharOcr(bi, map);  
//        }  
//        ImageIO.write(img, "JPG", new File("result3//" + result + ".jpg"));  
//        return result;  
//    }  
//    
//    public static void main(String[] args) throws Exception {  
//        //trainData();  
//        // downloadImage();  
//        for (int i = 0; i < 30; ++i) {  
//            String text = getAllOcr("img3//" + i + ".jpg");  
//            System.out.println(i + ".jpg = " + text);  
//        }  
//    }  
}