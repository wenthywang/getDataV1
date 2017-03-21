package cn.gb40;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * <pre>
 * 根据文章标题统计结果。
 * </pre>
 * @author 王文辉  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class Test_1 {

	private static final  File resultTxt=new File(GetJsonData.root+"根据文章标题统计结果.txt");
	private static final String excelName=GetJsonData.root+"根据文章标题统计结果";
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public static void main(JSONArray json,String date) throws FileNotFoundException {
		
        JSONArray array = json;
        JSONObject rows = null;
        ArrayList<entity>resultList=new ArrayList<entity>();
        int Count=array.size();
        System.out.println(array.size());
        for (int i = 0; i < array.size(); i++) {
            rows = array.getJSONObject(i);
            entity e=new entity();
            e.setTime(rows.getString("time").substring(0, 10));
            e.setTitle(rows.getString("title"));
            try{
                e.setPushdate(rows.getString("pushdate").substring(0, 10));
            }catch(Exception e3){
            	continue;
            }
        
            e.setPV(rows.getString("PV"));
            e.setUV(rows.getString("UV"));
            e.setCk_module(rows.getString("ck_module"));
            e.setDiscoveryid(rows.getString("discoveryid"));
            e.setPushdate2(rows.getString("pushdate"));
            resultList.add(e);
        }
        int PVCount=0;
        int UVCount=0;
        for (int i = 0; i < resultList.size(); i++) {
        	if (resultList.get(i).getCk_module().equals("push")||resultList.get(i).getCk_module().equals("null")) {
        		resultList.remove(i);
        		i--;
        	}
        }
        
        for (entity entity : resultList) {
		    PVCount=PVCount+Integer.parseInt(entity.getPV());
		    UVCount=UVCount+Integer.parseInt(entity.getUV());
	   }
        System.out.println("统计记录数："+Count);
        System.out.println("PV总数："+PVCount);
        System.out.println("UV总数："+UVCount);
        for (int i = 0; i < resultList.size(); i++) {
            if (!resultList.get(i).getPushdate().equals(resultList.get(i).getTime())) {
            	resultList.remove(i);
                i--;
            }
        }
        int length=resultList.size();
        for (int i = 0; i < length; i++) {
        	for(int j=i+1;j<length;j++){
        		if(resultList.get(i).getTitle().equals(resultList.get(j).getTitle())){
        			int temp=Integer.parseInt(resultList.get(j).getPV());
        			int temp2=Integer.parseInt(resultList.get(i).getPV());
        			if(resultList.get(i).getPvcount()!=0){
        				temp2=resultList.get(i).getPvcount();
        			}
        			int temp3=Integer.parseInt(resultList.get(j).getUV());
        			int temp4=Integer.parseInt(resultList.get(i).getUV());
        			if(resultList.get(i).getUvcount()!=0){
        				temp4=resultList.get(i).getUvcount();
        			}
        			resultList.get(i).setPvcount(temp+temp2);
        			resultList.get(i).setUvcount(temp3+temp4);
        			resultList.remove(j);
        			length--;
        		}
        	}
           }
        for (entity entity : resultList) {
			if(entity.getPvcount()==0){
				entity.setPvcount(Integer.parseInt(entity.getPV()));
			}
			if(entity.getUvcount()==0){
				entity.setUvcount(Integer.parseInt(entity.getUV()));
			}
		}
        
        Collections.sort(resultList, new Comparator() {   
            public int compare(Object a, Object b) {   
              int one = ((entity)a).getPvcount();   
              int two = ((entity)b).getPvcount ();    
              return two- one ;    
            }   
         });  
        
        
        
   
//        File writename = new File("C:\\Users\\Administrator\\Desktop\\根据文章标题统计结果.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
                    try {
                    	resultTxt.createNewFile();
						   BufferedWriter out = new BufferedWriter(new FileWriter(resultTxt));
						   out.write("统计记录数："+Count+"\r\n"); // \r\n即为换行
							out.write("PV总数："+PVCount+"\r\n"); // \r\n即为换行
							out.write("UV总数："+UVCount+"\r\n"); // \r\n即为换行
						     for(entity entity:resultList){
//						        	System.out.println(entity.getTitle()+"--------PV: "+entity.getPvcount()+"   UV: "+entity.getUvcount());
						        	
						        	out.write(entity.getTitle()+"--------PV: "+entity.getPvcount()+"   UV: "+entity.getUvcount()+"\r\n"); // \r\n即为换行
						     }
							out.write("\r\n"); // \r\n即为换行
						 	out.write("---------文章ID相同的文章有以下---------"+"\r\n"); // \r\n即为换行
						 	out.write("\r\n"); // \r\n即为换行
						 	for (int i = 0; i < resultList.size(); i++) {
					        	for(int j=i+1;j<resultList.size();j++){
							        	if(resultList.get(i).getDiscoveryid().equals(resultList.get(j).getDiscoveryid())){
//							        		System.out.println(resultList.get(j).getTitle()+"--------文章ID："+resultList.get(j).getDiscoveryid());
							        		out.write("********文章ID："+resultList.get(j).getDiscoveryid()+"********"+"\r\n");
							        		out.write(resultList.get(j).getTitle()+"  发布日期："+resultList.get(j).getPushdate()+"\r\n"+resultList.get(i).getTitle()+"  发布日期："+resultList.get(i).getPushdate()+"\r\n"); // \r\n即为换行
							        		out.write("\r\n"); // \r\n即为换行	        	
							        	}
							        	
							     }
						     }
						 	out.flush(); // 把缓存区内容压入文件
		                  out.close(); // 最后记得关闭文件
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // 创建新文件
                    
                    
                    
                    HSSFWorkbook wb = new HSSFWorkbook();  
                    // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
                    HSSFSheet sheet = wb.createSheet("按文章标题筛选");  
                    sheet.setColumnWidth(0, 12000);
                    sheet.setColumnWidth(1, 5000);
                    sheet.setColumnWidth(2, 3000);
                    // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
                    HSSFRow row = sheet.createRow((int) 0);  
                    // 第四步，创建单元格，并设置值表头 设置表头居中  
                    HSSFCellStyle style = wb.createCellStyle();  
                    style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
                    HSSFCell cell = row.createCell(0);
                    cell.setCellValue("文章标题");  
                    cell.setCellStyle(style);  
                    cell = row.createCell(1);
                    cell.setCellValue("PV");  
                    cell.setCellStyle(style);  
                    cell = row.createCell(2);
                    cell.setCellValue("UV");  
                    cell.setCellStyle(style);  
                    cell = row.createCell(3);
                    cell.setCellValue("发布日期");  
                    cell.setCellStyle(style);  
                  
              
                    // 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
                
              
                    for (int i = 0; i < resultList.size(); i++)  
                    {  
                        row = sheet.createRow( i + 1);  
                        entity e = (entity) resultList.get(i);  
                        // 第四步，创建单元格，并设置值  
                        row.createCell(0).setCellValue(e.getTitle());
                        row.createCell(1).setCellValue(e.getPvcount());
                        row.createCell(2).setCellValue(e.getUvcount());
                        row.createCell(3).setCellValue(e.getPushdate2());
                    }  
                    row=sheet.createRow(resultList.size()+2);
                    row.createCell(1).setCellValue("------文章ID相同的文章有以下----");
     
                    row=sheet.createRow(resultList.size()+3);
                    row.createCell(0).setCellValue("文章标题");
                    row.getCell(0).setCellStyle(style);
                    
                   row.createCell(1).setCellValue("文章ID");
                   row.getCell(1).setCellStyle(style);
                    row.createCell(2).setCellValue("发布日期");
                    row.getCell(2).setCellStyle(style);
                    row=sheet.createRow(resultList.size()+4);
                    int k=0;
                	for (int i = 0; i < resultList.size(); i++) {
			        	for(int j=i+1;j<resultList.size();j++){
					        	if(resultList.get(i).getDiscoveryid().equals(resultList.get(j).getDiscoveryid())){
					        		   k++;
					        		  row.createCell(0).setCellValue(resultList.get(j).getTitle());
					        		  row.getCell(0).setCellStyle(style);
					        		  row.createCell(1).setCellValue(resultList.get(j).getDiscoveryid());
					        		  row.createCell(2).setCellValue(resultList.get(j).getPushdate());
					        		  row=sheet.createRow(resultList.size()+4+k);
//					        		System.out.println(resultList.get(j).getTitle()+"--------文章ID："+resultList.get(j).getDiscoveryid());
//					        		out.write("********文章ID："+resultList.get(j).getDiscoveryid()+"********"+"\r\n");
//					        		out.write(resultList.get(j).getTitle()+"  发布日期："+resultList.get(j).getPushdate()+"\r\n"+resultList.get(i).getTitle()+"  发布日期："+resultList.get(i).getPushdate()+"\r\n"); // \r\n即为换行
//					        		out.write("\r\n"); // \r\n即为换行	        	
					        	}
					        	
					     }
				     }
                	
                	  row=sheet.createRow(sheet.getLastRowNum()+2);
                	  row.createCell(0).setCellValue("统计记录数");
                      row.getCell(0).setCellStyle(style);
                      row.createCell(1).setCellValue(Count);
                      row.getCell(1).setCellStyle(style);
                      row=sheet.createRow(sheet.getLastRowNum()+1);
                      row.createCell(0).setCellValue("PV总数");
                      row.getCell(0).setCellStyle(style);
                      row.createCell(1).setCellValue(PVCount);
                      row.getCell(1).setCellStyle(style);
                      row=sheet.createRow(sheet.getLastRowNum()+1);
                      row.createCell(0).setCellValue("UV总数");
                      row.getCell(0).setCellStyle(style);
                      row.createCell(1).setCellValue(UVCount);
                      row.getCell(1).setCellStyle(style);
                    // 第六步，将文件存到指定位置  
                    	  File f=new File(excelName+date+".xls");
                          FileOutputStream fout = new FileOutputStream(f);  
                          try {
							wb.write(fout);
						      fout.close();  
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}  
                  
                       
}
}
