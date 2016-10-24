/**
 * Copyright(c)  SunTek  Ltd. 
 */
package cn.gb40;

import java.io.IOException;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author wangwenhui  wangwenhui@jiaxincloud.com
 * @version 1.00.00
 * @date  2016年9月22日
 * <pre>
 * 修改记录
 * 修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class OpenProgram {
	 public static void main(String[] args)  {  
	        try {
				Runtime.getRuntime().exec("C:\\Program Files (x86)\\Git\\bin\\sh.exe");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    }  
}
