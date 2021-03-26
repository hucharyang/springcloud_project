package com.study.springcloudhystrix.command;

import com.netflix.hystrix.*;
import com.study.springcloudhystrix.service.DatabaseService;

import java.util.*;
import java.util.stream.Collectors;

/** 构建一个合并请求的命令 */
public class GoodsPriceCollapseCommand
		extends HystrixCollapser<List<Map<String, Object>>, String, String> {

	private DatabaseService databaseService;
	private String goodsId;

	public GoodsPriceCollapseCommand(DatabaseService databaseService, String goodsId) {
		super(Setter
				.withCollapserKey(HystrixCollapserKey.Factory.asKey("goodsPriceCollapseCommand"))
				.andCollapserPropertiesDefaults(
						// 10毫秒内的请求合并
						HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(10)
								.withMaxRequestsInBatch(200))// 单次最多合并200个命令
				.andScope(Scope.GLOBAL));
		this.databaseService = databaseService;
		this.goodsId = goodsId;
	}

	/** 请求参数 */
	@Override
	public String getRequestArgument() {
		return goodsId;
	}

	/** 创建实际发起请求的命令 */
	@Override
	protected HystrixCommand<List<Map<String, Object>>> createCommand(
			Collection<CollapsedRequest<String, String>> collapsedRequests) {
		// 取出这个时间窗口的所有请求，将
		List<String> goodsIds = new ArrayList<>(collapsedRequests.size());
		goodsIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument)
				.collect(Collectors.toList()));
		System.out.println("查询数据: " + goodsIds);
		return new GoodsPriceBatchCommand(databaseService, goodsIds);
	}

	/** 批量请求结束后，将结果和请求一一映射 */
	@Override
	protected void mapResponseToRequests(List<Map<String, Object>> batchResponse,
			Collection<CollapsedRequest<String, String>> collapsedRequests) {
		// 结果和请求映射起来 - 根据商品编号查找(goodsId编号 -> goodsPrice商品价格)
		Map<String, String> responseMap = new HashMap<>();
		for (Map<String, Object> response : batchResponse) {
			responseMap.put(response.get("goods_id").toString(),
					response.get("goods_price").toString());
		}
		// 根据请求附带的参数(商品编号goodsId)，找到对应的结果，设置每个请求线程的结果
		for (CollapsedRequest<String, String> collapsedRequest : collapsedRequests) {
			String price = responseMap.get(collapsedRequest.getArgument());
			collapsedRequest.setResponse(price);
		}
	}

	// 构建一个真正会执行实际逻辑的命令
	private class GoodsPriceBatchCommand extends HystrixCommand<List<Map<String, Object>>> {

		DatabaseService databaseService;
		List<String> goodsIds;

		public GoodsPriceBatchCommand(DatabaseService databaseService, List<String> goodsIds) {
			super(Setter
					.withGroupKey(HystrixCommandGroupKey.Factory.asKey("goodsPriceBatchCommand"))
					.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
							.withExecutionTimeoutInMilliseconds(10000))); // 10秒超时
			this.goodsIds = goodsIds;
			this.databaseService = databaseService;
		}

		@Override
		protected List<Map<String, Object>> run() throws Exception {
			System.out.println("调用批量查询，数量:" + goodsIds.size());
			return databaseService.queryPriceList(goodsIds);
		}

	}

}