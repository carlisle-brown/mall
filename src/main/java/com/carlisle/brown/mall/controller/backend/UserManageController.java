package com.carlisle.brown.mall.controller.backend;

import com.carlisle.brown.mall.common.Constrant;
import com.carlisle.brown.mall.common.ServerResponse;
import com.carlisle.brown.mall.pojo.User;
import com.carlisle.brown.mall.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user")
public class UserManageController {

    private IUserService iUserService;

    public UserManageController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    public ServerResponse<User> login(String username, String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Constrant.Role.ROLE_ADMIN){
                session.setAttribute(Constrant.CURRENT_USER,user);
                return response;
            }else{
                return ServerResponse.createByErrorMessage("不是管理员,无法登录");
            }
        }
        return response;
    }

}
