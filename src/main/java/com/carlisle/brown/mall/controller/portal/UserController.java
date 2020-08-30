package com.carlisle.brown.mall.controller.portal;

import com.carlisle.brown.mall.common.Constrant;
import com.carlisle.brown.mall.common.ResponseCode;
import com.carlisle.brown.mall.common.ServerResponse;
import com.carlisle.brown.mall.pojo.User;
import com.carlisle.brown.mall.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService iUserService;


    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    /**
     * User Login
     *
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @PostMapping("login")
    public ServerResponse<User> login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Constrant.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 退出
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public ServerResponse<String> logout(HttpSession session) {
        session.removeAttribute(Constrant.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * User register
     *
     * @param user 用户对象
     * @return
     */
    @PostMapping("register")
    public ServerResponse<String> register(@RequestBody User user) {
        return iUserService.register(user);
    }

    @PostMapping("check_valid")
    public ServerResponse<String> checkValid(String str, String type) {
        return iUserService.checkValid(str,type);
    }

    /**
     * Get user info
     *
     * @param session
     * @return
     */
    @GetMapping("get_user_info")
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Constrant.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
    }

    /**
     * 忘记密码
     *
     * @param username 用户名
     * @return
     */
    @PostMapping("forget_get_question")
    public ServerResponse<String> forgetGetQuestion(String username) {
        return iUserService.selectQuestion(username);
    }

    /**
     * 提交问题答案
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return
     */
    @PostMapping("forget_check_answer")
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 忘记密码的重设密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken token
     * @return
     */
    @PostMapping("forget_reset_password")
    public ServerResponse<String> forgetRestPassword(String username, String passwordNew, String forgetToken) {
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 登录重置密码
     *
     * @param session
     * @param passwordOld 老密码
     * @param passwordNew 新密码
     * @return
     */
    @PostMapping("reset_password")
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新个人信息
     *
     * @param session
     * @param user    用户对象
     * @return
     */
    @PostMapping("update_information")
    public ServerResponse<User> update_information(HttpSession session, User user) {
        User currentUser = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            response.getData().setUsername(currentUser.getUsername());
            session.setAttribute(Constrant.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 当前登录用户的详细信息
     *
     * @param session
     * @return
     */
    @GetMapping("get_information")
    public ServerResponse<User> get_information(HttpSession session) {
        User currentUser = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }
}
