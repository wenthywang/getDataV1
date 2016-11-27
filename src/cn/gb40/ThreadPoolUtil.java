package cn.gb40;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * 
 * <pre>
 * 線程池工具類。
 * </pre>
 * @author wangwenhui  946364340@qq.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ThreadPoolUtil   {
	/**
	 * 創建緩存線程池
	 */
	public static ExecutorService executorService=Executors.newCachedThreadPool();
	/**
	 * 創建固定線程池
	 */
	public static ExecutorService fixedService=Executors.newFixedThreadPool(100);
	/**
	 * 創建單一線程池
	 */
	public static ExecutorService singleService=Executors.newSingleThreadExecutor();

	
	
	
	
}