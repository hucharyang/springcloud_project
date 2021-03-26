package com.study.springcloudhystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.study.springcloudhystrix.service.GoodsService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringCloudHystrixApplication.class)
public class HystrxDemoApplicationTests {

	long timed = 0L;

	@Before
	public void start() {
		System.out.println("开始测试");
		timed = System.currentTimeMillis();
	}

	@After
	public void end() {
		System.out.println("结束测试,执行时长：" + (System.currentTimeMillis() - timed));
	}

	// 模拟的请求数量
	private static final int THREAD_NUM = 1000;

	// 倒计数器 juc包中常用工具类
	private CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUM);

	@Autowired
	GoodsService goodsService;

	@Test
	public void benchmark() throws InterruptedException {
		HystrixRequestContext context = HystrixRequestContext.initializeContext();
		// 创建 并不是马上发起请求
		Thread[] threads = new Thread[THREAD_NUM];
		for (int i = 0; i < THREAD_NUM; i++) {
			final String goodsId = "id-" + (i + 1);
			// 多线程模拟用户查询请求
			Thread thread = new Thread(() -> {
				try {
					// 代码在这里等待，等待countDownLatch为0，代表所有线程都start，再运行后续的代码
					countDownLatch.await();
					// http请求，实际上就是多线程调用这个方法
					// goodsService.queryPrice(goodsId);
					goodsService.queryPriceHystrix(goodsId);

				} catch (Exception e) {
					System.out.println(
							Thread.currentThread().getName() + " 线程执行出现异常:" + e.getMessage());
				}
			});
			threads[i] = thread;
			thread.setName("price-thread-" + goodsId);
			thread.start();
			// 田径。启动后，倒计时器倒计数 减一，代表又有一个线程就绪了
			countDownLatch.countDown();
		}

		// 等待上面所有线程执行完毕之后，结束测试
		for (Thread thread : threads) {
			thread.join();
		}
		context.shutdown();
	}

}
