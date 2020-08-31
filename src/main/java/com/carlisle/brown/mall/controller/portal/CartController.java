package com.carlisle.brown.mall.controller.portal;

import com.carlisle.brown.mall.common.Constrant;
import com.carlisle.brown.mall.common.ResponseCode;
import com.carlisle.brown.mall.common.ServerResponse;
import com.carlisle.brown.mall.pojo.User;
import com.carlisle.brown.mall.service.ICartService;
import com.carlisle.brown.mall.vo.CartVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {

    private ICartService iCartService;

    public CartController(ICartService iCartService) {
        this.iCartService = iCartService;
    }

    @GetMapping("list")
    public ServerResponse<CartVo> list(HttpSession session) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @PostMapping("add")
    public ServerResponse<CartVo> add(HttpSession session, Integer count, Integer productId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(),productId,count);
    }

    @PostMapping("update")
    public ServerResponse<CartVo> update(HttpSession session, Integer count, Integer productId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(),productId,count);
    }

    @PostMapping("delete_product")
    public ServerResponse<CartVo> deleteProduct(HttpSession session,String productIds) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(),productIds);
    }

    @PostMapping("select_all")
    public ServerResponse<CartVo> selectAll(HttpSession session) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),null,Constrant.Cart.CHECKED);
    }

    @PostMapping("un_select_all")
    public ServerResponse<CartVo> unSelectAll(HttpSession session) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),null,Constrant.Cart.UN_CHECKED);
    }

    @PostMapping("select")
    public ServerResponse<CartVo> select(HttpSession session,Integer productId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),productId,Constrant.Cart.CHECKED);
    }

    @PostMapping("un_select")
    public ServerResponse<CartVo> unSelect(HttpSession session,Integer productId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelect(user.getId(),productId,Constrant.Cart.UN_CHECKED);
    }

    @GetMapping("get_cart_product_count")
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createBySuccess(0);
        }
        return iCartService.getCartProductCount(user.getId());
    }
}
