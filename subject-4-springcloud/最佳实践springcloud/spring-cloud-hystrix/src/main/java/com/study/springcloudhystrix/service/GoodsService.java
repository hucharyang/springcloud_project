package com.study.springcloudhystrix.service;

import com.study.springcloudhystrix.command.GoodsPriceCollapseCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsService {

	@Autowired
	DatabaseService databaseService;

	public Object queryPrice(String goodsId) {
		String value = databaseService.queryPrice(goodsId);
		System.out.println(Thread.currentThread().getName() + "从数据库中取得数据==============>" + value);

		return value;
	}
	
	public Object queryPriceHystrix(String goodsId) throws Exception {
		GoodsPriceCollapseCommand command = new GoodsPriceCollapseCommand(databaseService, goodsId);
		String value = command.queue().get();
		System.out.println(Thread.currentThread().getName() + "从数据库中取得数据==============>" + value);
		return value;
	}
}
