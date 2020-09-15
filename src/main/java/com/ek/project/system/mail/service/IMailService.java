package com.ek.project.system.mail.service;

import com.ek.project.system.mail.domain.Mail;
import java.util.List;

/**
 * 邮箱Service接口
 * 
 * @author eric
 * @date 2020-09-03
 */
public interface IMailService 
{
    /**
     * 查询邮箱
     * 
     * @param mailId 邮箱ID
     * @return 邮箱
     */
    public Mail selectMailById(Long mailId);

    /**
     * 查询邮箱列表
     * 
     * @param mail 邮箱
     * @return 邮箱集合
     */
    public List<Mail> selectMailList(Mail mail);

    /**
     * 新增邮箱
     * 
     * @param mail 邮箱
     * @return 结果
     */
    public int insertMail(Mail mail);

    /**
     * 修改邮箱
     * 
     * @param mail 邮箱
     * @return 结果
     */
    public int updateMail(Mail mail);

    /**
     * 批量删除邮箱
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMailByIds(String ids);

    /**
     * 删除邮箱信息
     * 
     * @param mailId 邮箱ID
     * @return 结果
     */
    public int deleteMailById(Long mailId);

    /**
     * 检查邮箱账号是否唯一
     */
    public String checkMailAccountUnique(Mail mail);
}
