package com.ek.project.system.form.service.impl;

import com.ek.common.constant.FormConstants;
import com.ek.common.utils.Convert;
import com.ek.common.utils.DateUtils;
import com.ek.common.utils.ShiroUtils;
import com.ek.common.utils.StringUtils;
import com.ek.framework.web.damain.AjaxResult;
import com.ek.project.system.form.domain.FormInfo;
import com.ek.project.system.form.mapper.FormInfoMapper;
import com.ek.project.system.form.mapper.FormUserMapper;
import com.ek.project.system.form.service.IFormInfoService;
import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.mail.domain.MailForm;
import com.ek.project.system.mail.mapper.FormMailMapper;
import com.ek.project.system.mail.mapper.MailMapper;
import com.ek.project.system.user.domain.User;
import com.ek.project.system.user.domain.UserForm;
import com.ek.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 报名表Service业务层处理
 * 
 * @author eric
 * @date 2020-08-28
 */
@Service
public class FormInfoServiceImpl implements IFormInfoService 
{
    @Autowired
    private FormInfoMapper formInfoMapper;

    @Autowired
    private FormUserMapper formUserMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailMapper mailMapper;

    @Autowired
    private FormMailMapper formMailMapper;

    /**
     * 新增报名表
     *
     * @param formInfo 报名表
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult insertFormInfo(FormInfo formInfo, String[] fieldNameList, String[] fieldTypeList)
    {
        // 生成一个form+当前时间戳 的数据表名
        String tableName = "form" + (System.currentTimeMillis());

        Map<String, String> map = new LinkedHashMap<>();

        for (int i = 0; i < fieldNameList.length; i++) {
            map.put(fieldNameList[i], fieldTypeList[i]);
        }
        formInfo.setFormName(tableName);
        formInfo.setCreateTime(DateUtils.getNowDate());
        formInfo.setCreateBy(ShiroUtils.getLoginName());
        //插入表信息
        formInfoMapper.insertFormInfo(formInfo);
        //授权用户

        formUserMapper.saveUserFormByFormId(formInfo.getId(), ShiroUtils.getUserId());

        //创建数据表
         formInfoMapper.addTable(tableName, map);

         return AjaxResult.success("新增成功");
    }

    /**
     * 查询报名表
     *
     * @param id 报名表ID
     * @return 报名表
     */
    @Override
    public FormInfo selectFormInfoById(Long id)
    {
        return formInfoMapper.selectFormInfoById(id);
    }

    /**
     * 查询报名表列表
     *
     * @param formInfo 报名表
     * @return 报名表
     */
    @Override
    public List<FormInfo> selectFormInfoList(FormInfo formInfo)
    {
        return formInfoMapper.selectFormInfoList(formInfo,ShiroUtils.getUserId());
    }

    /**
     * 修改报名表
     * 
     * @param formInfo 报名表
     * @return 结果
     */
    @Override
    public int updateFormInfo(FormInfo formInfo)
    {
        formInfo.setUpdateTime(DateUtils.getNowDate());
        formInfo.setUpdateBy(ShiroUtils.getLoginName());
        return formInfoMapper.updateFormInfo(formInfo);
    }

    /**
     * 删除报名表对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteFormInfoByIds(String ids)
    {
        formUserMapper.deleteFormUserById(ids);
        return formInfoMapper.deleteFormInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除报名表信息
     * 
     * @param id 报名表ID
     * @return 结果
     */
    @Override
    public int deleteFormInfoById(Long id)
    {
        return formInfoMapper.deleteFormInfoById(id);
    }

    /**
     * 校验表单标题
     */
    @Override
    public String checkFormTitleUnique(FormInfo formInfo) {
        Long formId = StringUtils.isNull(formInfo.getId()) ? -1L : formInfo.getId();
        FormInfo info = formInfoMapper.checkFormTitleUnique(formInfo.getFormTitle());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != formId.longValue()) {
            return FormConstants.FORM_TITLE_NOT_UNIQUE;
        }
        return FormConstants.FORM_TITLE_UNIQUE;
    }



    /**
     * 增加表单的一个字段
     * @return
     */
    @Override
    public AjaxResult addColumn(FormInfo formInfo, String oldElement, String newElement) {
        if (StringUtils.isEmpty(formInfo.getFormName())) {
            throw new RuntimeException("数据表名称为空。");
        }
        if (StringUtils.isEmpty(oldElement)) {
            throw new RuntimeException("上一个字段名为空。");
        }
        if (StringUtils.isEmpty(newElement)) {
            throw new RuntimeException("新字段名为空。");
        }
        if (StringUtils.isEmpty(formInfo.getFormField())) {
            throw new RuntimeException("字段文本为空。");
        }

        try {
            formInfoMapper.addColumn(formInfo.getFormName(), oldElement, newElement);
            formInfoMapper.updateFormInfo(formInfo);
            return AjaxResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加失败");
        }

    }

    /**
     * 删除表单的一个字段
     * @return
     */
    @Override
    public AjaxResult removeColumn(FormInfo formInfo, String columnName) {
        System.out.println(formInfo);
        if (StringUtils.isEmpty(formInfo.getFormName())) {
            throw new RuntimeException("数据表名称为空。");
        }
        if (StringUtils.isEmpty(columnName)) {
            throw new RuntimeException("字段名为空。");
        }
        if (StringUtils.isEmpty(formInfo.getFormField())) {
            throw new RuntimeException("字段文本为空。");
        }
        try {
            formInfoMapper.removeColumn(formInfo.getFormName(), columnName);
            formInfo.setUpdateBy(ShiroUtils.getLoginName());
            formInfo.setUpdateTime(DateUtils.getNowDate());
            formInfoMapper.updateFormInfo(formInfo);
            return AjaxResult.success("删除成功");
        } catch (Exception e) {
            throw new RuntimeException("删除失败");
        }
    }

    /**
     * 根据条件分页查询已分配用户表单列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<User> selectAllocatedList(User user) {
        return userMapper.selectAllocatedListByForm(user);
    }

    /**
     * 取消授权
     */
    @Override
    public int deleteAuthUser(String formId, String userId) {
        return formUserMapper.deleteUserFormInfo(formId,userId);
    }
    /**
     * 批量取消授权
     */
    @Override
    public int deleteAuthUsers(Long formId, String userIds) {
        return formUserMapper.deleteUserFormInfos(formId, Convert.toLongArray(userIds));
    }

    /**
     * 查询未分配用户
     */
    @Override
    public List<User> selectUnallocatedList(User user) {
        return userMapper.selectUnallocatedListByForm(user);
    }

    /**
     * 批量授权用户
     */
    @Override
    public int insertAuthUsers(Long formId, String userIds) {
        Long[] users = Convert.toLongArray(userIds);
        // 新增用户与角色管理
        List<UserForm> list = new ArrayList<UserForm>();
        for (Long userId : users)
        {
            UserForm uf = new UserForm();
            uf.setUserId(userId);
            uf.setFormId(formId);
            list.add(uf);
        }
        return formUserMapper.batchUserForm(list);
    }

    /**查询授权邮箱列表*/
    @Override
    public List<Mail> selectAllocatedMailList(Mail mail) {
        return mailMapper.selectAllocatedListByForm(mail);
    }
    /**权限邮箱授权*/
    @Override
    public int deleteAuthMail(String formId, String mailId) {
        return formMailMapper.deleteAuthMail(formId,mailId);
    }

    /**批量取消邮箱授权*/
    @Override
    public int deleteAuthMails(Long formId, String mailIds) {
        return formMailMapper.deleteMailFormInfos(formId, Convert.toLongArray(mailIds));
    }

    /**查询未分配邮箱*/
    @Override
    public List<Mail> selectUnallocatedMailList(Mail mail) {
        return mailMapper.selectUnallocatedListByForm(mail);
    }

    /**批量增加邮箱授权*/
    @Override
    public int insertAuthMails(Long formId, String mailIds) {
        Long[] mails = Convert.toLongArray(mailIds);

        List<MailForm> list = new ArrayList<MailForm>();
        for (Long mailId : mails)
        {
            MailForm mf = new MailForm();
            mf.setMailId(mailId);
            mf.setFormId(formId);
            list.add(mf);
        }
        return formMailMapper.batchMailForm(list);
    }

    @Override
    public String[] selectFormColumn(String formName) {
        return formInfoMapper.selectFormColumn(formName);
    }

    @Override
    public List<Map<String, Object>> selectformDataList(String formName) {
        return formInfoMapper.selectformDataList(formName);
    }

    @Override
    public int deleteFormDataByIds(String formName, String ids) {
        return formInfoMapper.deleteFormDataByIds(formName,Convert.toStrArray(ids));
    }

}
