package com.carlisle.brown.mall.controller.portal;

import com.carlisle.brown.mall.common.Constrant;
import com.carlisle.brown.mall.common.ResponseCode;
import com.carlisle.brown.mall.common.ServerResponse;
import com.carlisle.brown.mall.pojo.Shipping;
import com.carlisle.brown.mall.pojo.User;
import com.carlisle.brown.mall.service.IShippingService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    private IShippingService iShippingService;

    public ShippingController(IShippingService iShippingService) {
        this.iShippingService = iShippingService;
    }

    @PostMapping("add")
    public ServerResponse add(HttpSession session, Shipping shipping) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.add(user.getId(),shipping);
    }

    @PostMapping("del")
    public ServerResponse del(HttpSession session,Integer shippingId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.del(user.getId(),shippingId);
    }

    @PostMapping("update")
    public ServerResponse update(HttpSession session,Shipping shipping) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.update(user.getId(),shipping);
    }

    @GetMapping("select")
    public ServerResponse<Shipping> select(HttpSession session,Integer shippingId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    @GetMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")int pageSize,
                                         HttpSession session) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }

}
