package com.ek.project.system.mailConfig.controller;

import com.ek.common.utils.ExcelUtil;
import com.ek.framework.aspectj.lang.annotation.Log;
import com.ek.framework.aspectj.lang.enums.BusinessType;
import com.ek.framework.web.controller.BaseController;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.framework.web.page.TableDataInfo;
import com.ek.project.system.mailConfig.domain.MailConfig;
import com.ek.project.system.mailConfig.service.IMailConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 邮箱配置Controller
 * 
 * @author eric
 * @date 2020-09-08
 */
@Controller
@RequestMapping("/system/mailConfig")
public class MailConfigController extends BaseController
{
    private String prefix = "system/mailConfig";

    @Autowired
    private IMailConfigService mailConfigService;

    @RequiresPermissions("system:mailConfig:view")
    @GetMapping()
    public String mailConfig()
    {
        return prefix + "/mailConfig";
    }

    /**
     * 查询邮箱配置列表
     */
    @RequiresPermissions("system:mailConfig:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MailConfig mailConfig)
    {
        startPage();
        List<MailConfig> list = mailConfigService.selectMailConfigList(mailConfig);
        return getDataTable(list);
    }

    /**
     * 导出邮箱配置列表
     */
    @RequiresPermissions("system:mailConfig:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MailConfig mailConfig)
    {
        List<MailConfig> list = mailConfigService.selectMailConfigList(mailConfig);
        ExcelUtil<MailConfig> util = new ExcelUtil<MailConfig>(MailConfig.class);
        return util.exportExcel(list, "mailConfig");
    }

    /**
     * 新增邮箱配置
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存邮箱配置
     */
    @RequiresPermissions("system:mailConfig:add")
    @Log(title = "邮箱配置", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MailConfig mailConfig)
    {
        return toAjax(mailConfigService.insertMailConfig(mailConfig));
    }

    /**
     * 修改邮箱配置
     */
    @GetMapping("/edit/{smtpAdress}")
    public String edit(@PathVariable("smtpAdress") String smtpAdress, ModelMap mmap)
    {
        MailConfig mailConfig = mailConfigService.selectMailConfigById(smtpAdress);
        mmap.put("mailConfig", mailConfig);
        return prefix + "/edit";
    }

    /**
     * 修改保存邮箱配置
     */
    @RequiresPermissions("system:mailConfig:edit")
    @Log(title = "邮箱配置", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MailConfig mailConfig)
    {
        return toAjax(mailConfigService.updateMailConfig(mailConfig));
    }

    /**
     * 删除邮箱配置
     */
    @RequiresPermissions("system:mailConfig:remove")
    @Log(title = "邮箱配置", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(mailConfigService.deleteMailConfigByIds(ids));
    }
}
