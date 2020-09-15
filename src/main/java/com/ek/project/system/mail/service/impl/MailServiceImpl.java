package com.ek.project.system.mail.service.impl;

import com.ek.common.constant.UserConstants;
import com.ek.common.utils.Convert;
import com.ek.common.utils.DateUtils;
import com.ek.common.utils.ShiroUtils;
import com.ek.common.utils.StringUtils;
import com.ek.project.system.mail.domain.Mail;
import com.ek.project.system.mail.mapper.MailMapper;
import com.ek.project.system.mail.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮箱Service业务层处理
 * 
 * @author eric
 * @date 2020-09-03
 */
@Service
public class MailServiceImpl implements IMailService 
{
    @Autowired
    private MailMapper mailMapper;

    /**
     * 查询邮箱
     * 
     * @param mailId 邮箱ID
     * @return 邮箱
     */
    @Override
    public Mail selectMailById(Long mailId)
    {
        return mailMapper.selectMailById(mailId);
    }

    /**
     * 查询邮箱列表
     * 
     * @param mail 邮箱
     * @return 邮箱
     */
    @Override
    public List<Mail> selectMailList(Mail mail)
    {
        return mailMapper.selectMailList(mail);
    }

    /**
     * 新增邮箱
     * 
     * @param mail 邮箱
     * @return 结果
     */
    @Override
    public int insertMail(Mail mail)
    {
        mail.setCreateBy(ShiroUtils.getLoginName());
        mail.setCreateTime(DateUtils.getNowDate());
        return mailMapper.insertMail(mail);
    }

    /**
     * 修改邮箱
     * 
     * @param mail 邮箱
     * @return 结果
     */
    @Override
    public int updateMail(Mail mail)
    {
        mail.setUpdateBy(ShiroUtils.getLoginName());
        mail.setUpdateTime(DateUtils.getNowDate());
        return mailMapper.updateMail(mail);
    }

    /**
     * 删除邮箱对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMailByIds(String ids)
    {
        return mailMapper.deleteMailByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除邮箱信息
     * 
     * @param mailId 邮箱ID
     * @return 结果
     */
    @Override
    public int deleteMailById(Long mailId)
    {
        return mailMapper.deleteMailById(mailId);
    }

    @Override
    public String checkMailAccountUnique(Mail mail) {
        Long mailId = StringUtils.isNull(mail.getMailId()) ? -1L : mail.getMailId();
        Mail info = mailMapper.checkMailAccountUnique(mail.getMailAccount());
        if (StringUtils.isNotNull(info) && info.getMailId().longValue() != mailId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }
}
