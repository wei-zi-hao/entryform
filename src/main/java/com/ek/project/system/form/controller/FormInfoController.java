package com.ek.project.system.form.controller;

import com.ek.common.utils.ExcelUtil;
import com.ek.framework.aspectj.lang.annotation.Log;
import com.ek.framework.aspectj.lang.enums.BusinessType;
import com.ek.framework.web.controller.BaseController;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.framework.web.page.TableDataInfo;
import com.ek.project.system.form.domain.FormInfo;
import com.ek.project.system.form.service.IFormInfoService;
import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.user.domain.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 报名表Controller
 * 
 * @author eric
 * @date 2020-08-28
 */
@Controller
@RequestMapping("/system/form")
public class FormInfoController extends BaseController
{
    private String prefix = "system/form";

    @Autowired
    private IFormInfoService formInfoService;

    @RequiresPermissions("system:form:view")
    @GetMapping()
    public String form()
    {
        return prefix + "/form";
    }

    /**
     * 报名表预览页面
     */
    @RequiresPermissions("system:form:preview")
    @GetMapping("/preview/{id}")
    public String preview(@PathVariable("id") Long id, ModelMap mmap)
    {
        FormInfo formInfo = formInfoService.selectFormInfoById(id);
        mmap.put("formInfo", formInfo);
        return prefix + "/preview";
    }
    /**
     * 报名表数据页面
     */

    @GetMapping("/formData/{id}")
    public String formData(@PathVariable("id") Long id, ModelMap mmap)
    {
        FormInfo formInfo = formInfoService.selectFormInfoById(id);
        String[] formColumn = formInfoService.selectFormColumn(formInfo.getFormName());
        mmap.put("formInfo", formInfo);
        mmap.put("formColumn", formColumn);
        return prefix + "/formData";
    }


    /**
     * 获取表数据
     */
    @RequiresPermissions("system:formdata:list")
    @PostMapping("/formData/list/{formName}")
    @ResponseBody
    public TableDataInfo formDatalist(@PathVariable("formName") String formName)
    {
        startPage();
        List<Map<String, Object>> list = formInfoService.selectformDataList(formName);
        return getDataTable(list);
    }
    /**
     * 获取导出表数据
     */
    @RequiresPermissions("system:formdata:export")
    @RequestMapping("/formData/export")
    @ResponseBody
    public TableDataInfo formDatalistExport(String formName)
    {
        List<Map<String, Object>> list = formInfoService.selectformDataList(formName);
        return getDataTable(list);
    }

    /**
     * 删除表数据
     */
    @RequiresPermissions("system:formdata:remove")
    @Log(title = "报名表数据", businessType = BusinessType.DELETE)
    @PostMapping("/formData/remove")
    @ResponseBody
    public AjaxResult formDataRemove(String formName,String ids)
    {
        return  toAjax(formInfoService.deleteFormDataByIds(formName,ids));
    }
    /**
     * 查询报名表列表
     */
    @RequiresPermissions("system:form:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FormInfo formInfo)
    {
        startPage();
        List<FormInfo> list = formInfoService.selectFormInfoList(formInfo);
        return getDataTable(list);
    }

    /**
     * 导出报名表列表
     */
    @RequiresPermissions("system:form:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FormInfo formInfo)
    {
        List<FormInfo> list = formInfoService.selectFormInfoList(formInfo);
        ExcelUtil<FormInfo> util = new ExcelUtil<FormInfo>(FormInfo.class);
        return util.exportExcel(list, "form");
    }



    /**
     * 新增报名表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存报名表
     */
    @RequiresPermissions("system:form:add")
    @Log(title = "报名表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated FormInfo formInfo, String[] fieldNameList, String[] fieldTypeList)
    {
        return formInfoService.insertFormInfo(formInfo,fieldNameList,fieldTypeList);
    }

    /**
     * 修改报名表结构页面
     */
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, ModelMap mmap)
    {
        FormInfo formInfo = formInfoService.selectFormInfoById(id);
        mmap.put("formInfo", formInfo);
        return prefix + "/update";
    }
    /**
     * 修改报名表基础信息页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        FormInfo formInfo = formInfoService.selectFormInfoById(id);
        mmap.put("formInfo", formInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存报名表基础信息
     */
    @RequiresPermissions("system:form:edit")
    @Log(title = "报名表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FormInfo formInfo)
    {
        return toAjax(formInfoService.updateFormInfo(formInfo));
    }

    /**
     * 修改保存报名表基础信息
     */
    @RequiresPermissions("system:form:edit")
    @PostMapping("/editFormHtml")
    @ResponseBody
    public AjaxResult editFormHtml(FormInfo formInfo)
    {
        return toAjax(formInfoService.updateFormInfo(formInfo));
    }


    /**
     * 增加表单的一个字段
     * @return
     */
    @Log(title = "报名表", businessType = BusinessType.UPDATE)
    @PostMapping("/addColumn")
    @ResponseBody
    public AjaxResult addColumn(FormInfo forminfo,String oldElement,String newElement) {
        return formInfoService.addColumn(forminfo,oldElement,newElement);
    }

    /**
     * 删除表单的一个字段
     * @return
     */
    @Log(title = "报名表", businessType = BusinessType.UPDATE)
    @PostMapping("/removeColumn")
    @ResponseBody
    public AjaxResult removeColumn(FormInfo forminfo,String columnName) {
        return formInfoService.removeColumn(forminfo,columnName);
    }
    /**
     * 删除报名表
     */
    @RequiresPermissions("system:form:remove")
    @Log(title = "报名表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(formInfoService.deleteFormInfoByIds(ids));
    }



    /**
     * 校验表单标题
     */
    @PostMapping("/checkFormTitleUnique")
    @ResponseBody
    public String checkFormTitleUnique(FormInfo  formInfo) {
        return formInfoService.checkFormTitleUnique(formInfo);
    }





    /**
     * 选择用户
     */
    @GetMapping("/authUser/selectUser/{formId}")
    public String selectUser(@PathVariable("formId") Long formId, ModelMap mmap) {
        mmap.put("formInfo", formInfoService.selectFormInfoById(formId));
        return prefix + "/selectUser";
    }
    /**
     * 分配用户
     */
    @RequiresPermissions("system:form:authuser")
    @GetMapping("/authUser/{formId}")
    public String authUser(@PathVariable("formId") Long formId, ModelMap mmap) {
        mmap.put("formInfo", formInfoService.selectFormInfoById(formId));
        return prefix + "/authUser";
    }

    /**
     * 查询已分配用户表单列表
     */

    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(User user) {
        startPage();
        List<User> list = formInfoService.selectAllocatedList(user);
        return getDataTable(list);
    }


    /**
     * 取消用户授权
     */
    @Log(title = "报名表取消用户授权", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public AjaxResult cancelAuthUser(String formId,String userId) {
        return toAjax(formInfoService.deleteAuthUser(formId,userId));
    }

    /**
     * 批量取消用户授权
     */
    @Log(title = "报名表取消用户授权", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public AjaxResult cancelAuthUserAll(Long formId, String userIds) {
        return toAjax(formInfoService.deleteAuthUsers(formId, userIds));
    }



    /**
     * 查询未分配用户角色列表
     */

    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(User user) {
        startPage();
        List<User> list = formInfoService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 批量选择用户授权
     */

    @Log(title = "报名表用户授权", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public AjaxResult selectAuthUserAll(Long formId, String userIds) {
        return toAjax(formInfoService.insertAuthUsers(formId, userIds));
    }



    /**
     * 选择邮箱
     */
    @GetMapping("/authMail/selectMail/{formId}")
    public String selectMail(@PathVariable("formId") Long formId, ModelMap mmap) {
        mmap.put("formInfo", formInfoService.selectFormInfoById(formId));
        return prefix + "/selectMail";
    }

    /**
     * 分配邮箱
     */
    @RequiresPermissions("system:form:authmail")
    @GetMapping("/authMail/{formId}")
    public String authMail(@PathVariable("formId") Long formId, ModelMap mmap) {
        mmap.put("formInfo", formInfoService.selectFormInfoById(formId));
        return prefix + "/authMail";
    }

    /**
     * 查询已分配邮箱表单列表
     */
    @PostMapping("/authMail/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(Mail mail) {
        startPage();
        List<Mail> list = formInfoService.selectAllocatedMailList(mail);
        return getDataTable(list);
    }

    /**
     * 查询未分配邮箱列表
     */
    @PostMapping("/authMail/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(Mail mail) {
        startPage();
        List<Mail> list = formInfoService.selectUnallocatedMailList(mail);
        return getDataTable(list);
    }

    /**
     * 取消邮箱授权
     */
    @Log(title = "报名表取消邮箱授权", businessType = BusinessType.GRANT)
    @PostMapping("/authMail/cancel")
    @ResponseBody
    public AjaxResult cancelAuthMail(String formId,String mailId) {
        return toAjax(formInfoService.deleteAuthMail(formId,mailId));
    }

    /**
     * 批量取消邮箱授权
     */
    @Log(title = "报名表取消邮箱授权", businessType = BusinessType.GRANT)
    @PostMapping("/authMail/cancelAll")
    @ResponseBody
    public AjaxResult cancelAuthMailAll(Long formId, String mailIds) {
        return toAjax(formInfoService.deleteAuthMails(formId, mailIds));
    }

    /**
     * 批量选择邮箱授权
     */
    @Log(title = "报名表邮箱授权", businessType = BusinessType.GRANT)
    @PostMapping("/authMail/selectAll")
    @ResponseBody
    public AjaxResult selectAuthMailAll(Long formId, String mailIds) {
        return toAjax(formInfoService.insertAuthMails(formId, mailIds));
    }
}
