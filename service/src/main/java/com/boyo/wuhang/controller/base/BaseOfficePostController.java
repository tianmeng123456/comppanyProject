package com.boyo.wuhang.controller.base;

import com.boyo.wuhang.entity.base.BaseEmployee;
import com.boyo.wuhang.entity.base.BaseOfficePost;
import com.boyo.wuhang.service.base.BaseOfficePostService;
import com.boyo.wuhang.service.base.EmployeeService;
import com.boyo.wuhang.ultity.JSONTool;
import com.boyo.wuhang.ultity.JsonBuilder;
import com.boyo.wuhang.ultity.Pager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/base/office/")
public class BaseOfficePostController {
    @Autowired
    private BaseOfficePostService officePostService;
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("list")
    public JSONObject getOfficePostList(@RequestBody JSONObject jsonObject){
        Pager page = new Pager();
        page.setPageNumber(Integer.parseInt(jsonObject.getString("pageNumber")));
        page.setPageSize(Integer.parseInt(jsonObject.getString("pageSize")));
        jsonObject.remove("pageNumber");
        jsonObject.remove("pageSize");
        BaseOfficePost officePost = JSONTool.getObject(jsonObject.toString(), BaseOfficePost.class);
        page = officePostService.getOfficePostList(officePost,page);
        return JsonBuilder.build(0,"",page);
    }

    @PostMapping("add")
    public JSONObject addOffice(@RequestBody BaseOfficePost officePost){
        if (!officePostService.checkNo(officePost)){
            return JsonBuilder.build(1,"岗位编号重复",null);
        }
        if (StringUtils.isBlank(officePost.getPostNo())){
            return JsonBuilder.build(1,"岗位编号为空",null);
        }
        if (StringUtils.isBlank(officePost.getPostName())){
            return JsonBuilder.build(1,"岗位名称为空",null);
        }
        if (officePostService.addOffice(officePost)){
            return JsonBuilder.build(0,"添加岗位成功",officePost);
        }else{
            return JsonBuilder.build(1,"添加岗位失败",null);
        }
    }

    @PostMapping("edit")
    public JSONObject upOffice(@RequestBody BaseOfficePost officePost){

        BaseOfficePost selectById = officePostService.selectById(officePost.getId());
        if (selectById == null){
            return JsonBuilder.build(1,"修改的岗位不存在",null);
        }
        if (StringUtils.isNotBlank(officePost.getPostNo())){
            if (!officePostService.checkNo(officePost)){
                return JsonBuilder.build(1,"岗位编号重复",null);
            }
            if (StringUtils.isBlank(officePost.getPostNo())){
                return JsonBuilder.build(1,"岗位编号为空",null);
            }
        }
        if (StringUtils.isNotBlank(officePost.getPostName())){
            if (StringUtils.isBlank(officePost.getPostNo())){
                return JsonBuilder.build(1,"岗位编号为空",null);
            }
        }
        if (officePostService.upOffice(officePost)){
            return JsonBuilder.build(0,"修改岗位成功",officePost);
        }else {
            return JsonBuilder.build(1,"修改岗位失败",null);
        }
    }

    @PostMapping("del")
    public JSONObject delOffice(@RequestBody JSONObject jsonObject){
        long id = Long.parseLong(jsonObject.getString("id"));
        BaseOfficePost baseOfficePost = officePostService.selectById(id);
        if (baseOfficePost == null){
            return JsonBuilder.build(1,"数据不存在",null);
        }
        if (officePostService.delOffice(id)){
            return JsonBuilder.build(0,"删除岗位成功",null);
        }else{
            return JsonBuilder.build(1,"删除岗位失败",null);
        }
    }

    @PostMapping("post")
    public JSONObject getEmployeeByPostId(@RequestBody JSONObject jsonObject){
        long postId = Long.parseLong(jsonObject.getString("postId"));
        BaseEmployee employee = employeeService.getEmployeeByPostId(postId);
        return JsonBuilder.build(0,"",employee);
    }
}
