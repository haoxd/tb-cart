package com.tb.cart.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.tb.cart.dao.CartDao;
import com.tb.cart.pojo.Cart;
import com.tb.cart.pojo.Item;
import com.tb.cart.system.constant.ServiceCode.cart;
import com.tb.cart.thread.UserThreadLocal;
import com.tb.common.org.gtmd.frame.tools.paramData;
import com.tb.sso.query.api.bean.TbUser;

@Service("cartService")
public class CartService {

	@Autowired
	private CartDao dao;

	@Resource(name = "itemService")
	private ItemService itemService;

	/**
	 * 添加商品到购物车
	 * 
	 * @param itemId
	 */
	public void addMyCart(Long itemId, Integer buyNum) {
		TbUser user = UserThreadLocal.get();
		Cart inParam = new Cart();
		inParam.setItemId(itemId);
		inParam.setUserId(user.getUserId());
		Cart cart = this.dao.selectOne(inParam);
		if (null == cart) {
			// 不存在新增
			cart = new Cart();
			cart.setUserId(user.getUserId());
			cart.setCreateTime(new Date());
			cart.setUpdateTime(cart.getCreateTime());

			Item item = this.itemService.queryItemById(itemId);
			cart.setItemId(itemId);
			cart.setItemPrice(item.getPrice());
			cart.setItemTitle(item.getTitle());
			if (StringUtils.isNotEmpty(item.getImage())) {
				cart.setItemImage(StringUtils.split(item.getImage(), ",")[0]);
			} else {
				cart.setItemImage("");
			}
			cart.setNum(buyNum);
			this.dao.insert(cart);
		} else {
			// 存在数量相加
			cart.setNum(cart.getNum() + buyNum);
			cart.setUpdateTime(new Date());
			this.dao.updateByPrimaryKey(cart);
		}
	}

	/**
	 * 查询我的购物车列表
	 * @return
	 */
	public List<Cart> queryMyCatList() {
		paramData<String, Object> inParam = new paramData<String ,Object>();
		inParam.put("USERID", UserThreadLocal.get().getUserId());
		List<Cart> list = this.dao.queryMyCartList(inParam);
		if(list.size()>0 && !list.isEmpty()){
			return list;
		}
		return new ArrayList<Cart>();
	}

	/**
	 * 更新购物车商品数量
	 * @param itemId
	 * @param num
	 * @return
	 */
	public boolean updateCartNum(Long itemId, Integer num) {
		Cart inCart = new Cart();
		inCart.setNum(num);
		inCart.setUpdateTime(new Date());
		//更新条件
		Example ex = new Example(Cart.class);
		ex.createCriteria().andEqualTo("itemId", itemId).andEqualTo("userId", UserThreadLocal.get().getUserId());
		int i=	this.dao.updateByExampleSelective(inCart, ex);
		return i>0?true:false;
	}

	/**
	 * 删除购物车数据
	 * 物理删除
	 * @param itemId
	 */
	public void delMyCart(Long itemId) {
		Cart inCart = new Cart();
		inCart.setItemId(itemId);
		inCart.setUserId(UserThreadLocal.get().getUserId());
		this.dao.delete(inCart);
		
	}

	public List<Cart> queryMyCatList(Long userId, String[] itemIds) {
		paramData<String, Object> inParam = new paramData<String ,Object>();
		inParam.put("USERID",userId);
		inParam.put("ITEMIDS",itemIds);
		List<Cart> list = this.dao.queryMyCartListToWeb(inParam);
		if(list.size()>0 && !list.isEmpty()){
			return list;
		}
		return new ArrayList<Cart>();
		
	}

}
