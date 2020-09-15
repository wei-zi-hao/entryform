package com.ek.project.system.mail.controller;

import com.ek.common.utils.ExcelUtil;
import com.ek.framework.aspectj.lang.annotation.Log;
import com.ek.framework.aspectj.lang.enums.BusinessType;
import com.ek.framework.web.controller.BaseController;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.framework.web.page.TableDataInfo;
import com.ek.project.system.form.service.IFormInfoService;
import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.mail.service.IMailService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 邮箱Controller
 * 
 * @author eric
 * @date 2020-09-03
 */
@Controller
@RequestMapping("/system/mail")
public class MailController extends BaseController
{
    private String prefix = "system/mail";

    @Autowired
    private IMailService mailService;

    @Autowired
    private IFormInfoService formInfoService;

    @RequiresPermissions("system:mail:view")
    @GetMapping()
    public String mail()
    {
        return prefix + "/mail";
    }

    /**
     * 查询邮箱列表
     */
    @RequiresPermissions("system:mail:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Mail mail)
    {
        startPage();
        List<Mail> list = mailService.selectMailList(mail);
        return getDataTable(list);
    }

    /**
     * 导出邮箱列表
     */
    @RequiresPermissions("system:mail:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Mail mail)
    {
        List<Mail> list = mailService.selectMailList(mail);
        ExcelUtil<Mail> util = new ExcelUtil<Mail>(Mail.class);
        return util.exportExcel(list, "mail");
    }

    /**
     * 新增邮箱
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存邮箱
     */
    @RequiresPermissions("system:mail:add")
    @Log(title = "邮箱", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Mail mail)
    {
        return toAjax(mailService.insertMail(mail));
    }

    /**
     * 修改邮箱
     */
    @GetMapping("/edit/{mailId}")
    public String edit(@PathVariable("mailId") Long mailId, ModelMap mmap)
    {
        Mail mail = mailService.selectMailById(mailId);
        mmap.put("mail", mail);
        return prefix + "/edit";
    }

    /**
     * 修改保存邮箱
     */
    @RequiresPermissions("system:mail:edit")
    @Log(title = "邮箱", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Mail mail)
    {
        return toAjax(mailService.updateMail(mail));
    }

    /**
     * 删除邮箱
     */
    @RequiresPermissions("system:mail:remove")
    @Log(title = "邮箱", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(mailService.deleteMailByIds(ids));
    }


    /**
     * 校验email邮箱
     */
    @PostMapping("/checkMailAccountUnique")
    @ResponseBody
    public String checkMailAccountUnique(Mail mail) {
        return mailService.checkMailAccountUnique(mail);
    }



}
