package com.ek.project.system.notice.controller;

import com.ek.framework.aspectj.lang.annotation.Log;
import com.ek.framework.aspectj.lang.enums.BusinessType;
import com.ek.framework.web.controller.BaseController;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.framework.web.page.TableDataInfo;
import com.ek.project.system.notice.domain.Notice;
import com.ek.project.system.notice.service.INoticeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 公告 信息操作处理
 *
 * @author ruoyi
 */
@Controller
@RequestMapping("/system/notice")
public class NoticeController extends BaseController
{
    private String prefix = "system/notice";

    @Autowired
    private INoticeService noticeService;

    @RequiresPermissions("system:notice:view")
    @GetMapping()
    public String notice()
    {
        return prefix + "/notice";
    }

    /**
     * 查询公告列表
     */
    @RequiresPermissions("system:notice:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Notice notice)
    {
        startPage();
        List<Notice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 新增公告
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存公告
     */
    @RequiresPermissions("system:notice:add")
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Notice notice)
    {
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改公告
     */
    @GetMapping("/edit/{noticeId}")
    public String edit(@PathVariable("noticeId") Long noticeId, ModelMap mmap)
    {
        mmap.put("notice", noticeService.selectNoticeById(noticeId));
        return prefix + "/edit";
    }
    /**
     * 展示公告
     */
    @GetMapping("/show/{noticeId}")
    public String show(@PathVariable("noticeId") Long noticeId, ModelMap mmap)
    {
        mmap.put("notice", noticeService.selectNoticeById(noticeId));
        return prefix + "/show";
    }

    /**
     * 修改保存公告
     */
    @RequiresPermissions("system:notice:edit")
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Notice notice)
    {
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除公告
     */
    @RequiresPermissions("system:notice:remove")
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(noticeService.deleteNoticeByIds(ids));
    }



    /**
     * 获取未读公告数量
     */
    @PostMapping("/getUnReadNoticeCount")
    @ResponseBody
    public int getUnReadNoticeCount()
    {
        return noticeService.getUnReadNoticeCount();
    }
    /**
     * 获取公告
     */
    @PostMapping("/getNoticeByPush")
    @ResponseBody
    public List<Notice> getNoticeByPush()
    {
        return noticeService.getNoticeByPush();
    }

    /**
     * 修改公司状态
     */
    @PostMapping("/updateNoticeState")
    @ResponseBody
    public AjaxResult updateNoticeState(String noticeId)
    {
        return toAjax(noticeService.updateNoticeState(noticeId));
    }

}
