package com.study.springcloudhystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从数据库中查询
 * 
 * @author tony
 *
 */
@Component
public class DatabaseService {

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * 查询一个商品的价格
	 * 
	 * @param goodsId
	 * @return 商品价格
	 */
	public String queryPrice(String goodsId) {
		Map<String, Object> params = new HashMap<>();
		params.put("goodsId", goodsId);

		String sql = "SELECT goods_price FROM `tb_goods_info` where goods_id=:goodsId";

		Map<String, Object> result = jdbcTemplate.queryForMap(sql, params);

		return result.get("goods_price").toString();
	}

	/**
	 * 查询多个商品的价格
	 * 
	 * @param goodsIds
	 *            商品编号集合
	 * @return 商品价格集合
	 */
	public List<Map<String, Object>> queryPriceList(List<String> goodsIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("goodsIds", goodsIds);

		String sql = "SELECT * FROM `tb_goods_info` where goods_id in (:goodsIds)";

		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, params);

		return result;
	}

}
