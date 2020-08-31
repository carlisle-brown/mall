package com.carlisle.brown.mall.controller.backend;

import com.carlisle.brown.mall.common.Constrant;
import com.carlisle.brown.mall.common.ResponseCode;
import com.carlisle.brown.mall.common.ServerResponse;
import com.carlisle.brown.mall.pojo.User;
import com.carlisle.brown.mall.service.ICategoryService;
import com.carlisle.brown.mall.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {

    private IUserService iUserService;

    private ICategoryService iCategoryService;

    public CategoryManageController(IUserService iUserService, ICategoryService iCategoryService) {
        this.iUserService = iUserService;
        this.iCategoryService = iCategoryService;
    }

    /**
     * 添加分类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @PostMapping("add_category")
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * 修改分类名称
     * @param session
     * @param categoryId
     * @param categoryName
     * @return
     */
    @PostMapping("set_category_name")
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * 获取下级分类
     * @param session
     * @param categoryId
     * @return
     */
    @GetMapping("get_category")
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }

    /**
     * 递归获取分类ID
     * @param session
     * @param categoryId
     * @return
     */
    @GetMapping("get_deep_category")
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId" ,defaultValue = "0") Integer categoryId) {
        User user = (User)session.getAttribute(Constrant.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iCategoryService.selectCategoryAndChildrenById(categoryId);

        }else{
            return ServerResponse.createByErrorMessage("无权限操作,需要管理员权限");
        }
    }
}
