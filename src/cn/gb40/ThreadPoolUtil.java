package cn.gb40;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil   {
	
	public static ExecutorService executorService=Executors.newCachedThreadPool();
}