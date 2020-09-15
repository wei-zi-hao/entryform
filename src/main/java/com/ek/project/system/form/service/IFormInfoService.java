package com.ek.project.system.form.service;

import com.ek.framework.web.damain.AjaxResult;
import com.ek.project.system.form.domain.FormInfo;
import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.user.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 报名表Service接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface IFormInfoService 
{
    /**
     * 查询报名表
     * 
     * @param id 报名表ID
     * @return 报名表
     */
    public FormInfo selectFormInfoById(Long id);

    /**
     * 查询报名表列表
     * 
     * @param formInfo 报名表
     * @return 报名表集合
     */
    public List<FormInfo> selectFormInfoList(FormInfo formInfo);

    /**
     * 新增报名表
     * 
     * @param formInfo 报名表
     * @return 结果
     */
    public AjaxResult insertFormInfo(FormInfo formInfo, String[] fieldNameList, String[] fieldTypeList);

    /**
     * 修改报名表
     * 
     * @param formInfo 报名表
     * @return 结果
     */
    public int updateFormInfo(FormInfo formInfo);

    /**
     * 批量删除报名表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFormInfoByIds(String ids);

    /**
     * 删除报名表信息
     * 
     * @param id 报名表ID
     * @return 结果
     */
    public int deleteFormInfoById(Long id);

    /**
     * 检查报名表名称是否唯一
     */
    public String checkFormTitleUnique(FormInfo formInfo);


    /**
     * 增加表单的一个字段
     * @return
     */
    public AjaxResult addColumn(FormInfo forminfo, String oldElement, String newElement);

    /**
     * 删除表单的一个字段
     * @return
     */
    public AjaxResult removeColumn(FormInfo forminfo, String columnName);

    /**
     * 根据条件分页查询已分配用户表单列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectAllocatedList(User user);

    /**
     * 取消授权用户角色
     * @return 结果
     */
    public int deleteAuthUser(String formId, String userId);

    /**
     * 批量取消授权用户角色
     * @return 结果
     */
    public int deleteAuthUsers(Long roleId, String userIds);

    /**
     * 根据条件分页查询未分配用户表单列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    public List<User> selectUnallocatedList(User user);

    /**
     * 批量授权用户表单列表
     */
    public int insertAuthUsers(Long formId, String userIds);

    /**
     * 查询授权邮箱列表
     */
    public List<Mail> selectAllocatedMailList(Mail mail);

    /**
     * 取消邮箱授权
     */
    public int deleteAuthMail(String formId, String mailId);

    /**
     * 批量取消邮箱授权
     */
    public int deleteAuthMails(Long formId, String mailIds);

    /**
     * 查询未授权的邮箱
     */
    public List<Mail> selectUnallocatedMailList(Mail mail);

    /**
     * 批量授权的邮箱
     */
    public int insertAuthMails(Long formId, String mailIds);

    /**
     * 查询表列名
     */
    public String[] selectFormColumn(String formName);

    /**
     * 获取表数据
     */
    public List<Map<String, Object>> selectformDataList(String formName);

    public int deleteFormDataByIds(String formName, String ids);
}
