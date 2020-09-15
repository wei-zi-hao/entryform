package com.ek.project.system.form.controller;

import com.ek.framework.web.controller.BaseController;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.project.system.form.domain.FormInfo;
import com.ek.project.system.form.service.IFormDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 报名表Controller
 * 
 * @author eric
 * @date 2020-08-28
 */
@Controller
@RequestMapping("/system/form")
public class FormDataController extends BaseController
{
    private String prefix = "system/form";

    @Autowired
    private IFormDataService formDataService;




    /**
     * 获取表单的数据
     *
     * @return
     */

    @RequestMapping("/findFormInfoByFormName")
    @ResponseBody
    public AjaxResult findFormInfoByFormName(String formName, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        FormInfo formInfo = formDataService.findFormInfoByFormName(formName);
        return  AjaxResult.success(formInfo);

    }


    /**
     * 获取提交的数据来 插入到数据库中
     *
     * @param formName
     *            表名
     * @param map
     *            提交的k【字段名】v【值】
     * @param response
     *            用来跨域
     * @return
     */

    @RequestMapping("/insertData/{formName}")
    @ResponseBody
    public AjaxResult insertDataByFormName(@PathVariable("formName") String formName,
                                           @RequestParam Map<String, Object> map, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        String message = formDataService.insertDataByFormName(formName, map);
        return AjaxResult.success(message);
    }

}
