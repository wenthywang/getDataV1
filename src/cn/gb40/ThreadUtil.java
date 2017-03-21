package cn.gb40;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池执行
 * @author xubing
 *
 */
public class ThreadUtil {
	
	private static ExecutorService notifyService = Executors.newFixedThreadPool(10);

	/**
	 * 通知接受到新的工单
	 * @param runnable
	 */
	public static void executeNotifyThread(Runnable runnable) {
		notifyService.execute(runnable);
	}
	
	/**
	 * 执行线程
	 * @param runnable
	 */
	public static void executeThread(Runnable runnable) {
		notifyService.execute(runnable);
	}
	
	public static ExecutorService  getThreadPool(){
		return notifyService;
	}

}
