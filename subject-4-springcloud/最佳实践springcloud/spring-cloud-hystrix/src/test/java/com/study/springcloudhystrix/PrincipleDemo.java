package com.study.springcloudhystrix;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

/**
 * 原理示例
 * 
 * @author
 *
 */
public class PrincipleDemo {
	public static void main(String[] args) throws Exception {
		// 1. 初始化一个queue
		LinkedBlockingQueue<Object[]> linkedBlockingQueue = new LinkedBlockingQueue<>();

		// 2. 开始多个请求
		for (int i = 0; i < 3; i++) {
			String requestId = "req-" + i; // 请求ID( 和结果对应的标准)
			new Thread(() -> {
				try {
					// 构建request对象(这里用map代替)
					CompletableFuture<Object> completableFuture = new CompletableFuture<>();
					Object[] objects = new Object[] { requestId, completableFuture };
					// 塞到队列queue
					linkedBlockingQueue.add(objects);
					// 等待结果
					System.out.println("塞进去了");
					Object object = completableFuture.get();
					System.out.println(requestId + " 收到响应：" + object);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}).start();
		}

		// 3. 遍历取出queue里面的请求，合并成一个请求。（忽略请求，直接返回结果）
		Thread.sleep(2000L);
		HashMap<String, Object> result = new HashMap<String, Object>() {
			{
				put("req-0", "第一个请求的结果");
				put("req-1", "第二个请求的结果");
				put("req-2", "第三个请求的结果");
			}
		};
		// 4. 将结果 和 请求 一一 对应,并通知
		linkedBlockingQueue.forEach(new Consumer<Object[]>() {
			@Override
			public void accept(Object[] request) {
				// 取出参数
				String reqId = (String) request[0];
				CompletableFuture future = (CompletableFuture) request[1];
				// 根据请求参数，把结果和请求对应起来
				String value = (String) result.get(reqId);
				future.complete(value);
			}
		});
	}
}
