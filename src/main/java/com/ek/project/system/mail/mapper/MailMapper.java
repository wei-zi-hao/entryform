package com.ek.project.system.mail.mapper;

import com.ek.project.system.mail.domain.Mail;
import java.util.List;

/**
 * 邮箱Mapper接口
 * 
 * @author eric
 * @date 2020-09-03
 */
public interface MailMapper 
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
     * 删除邮箱
     * 
     * @param mailId 邮箱ID
     * @return 结果
     */
    public int deleteMailById(Long mailId);

    /**
     * 批量删除邮箱
     * 
     * @param mailIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteMailByIds(String[] mailIds);

    /**
     * 检查邮箱账号是否唯一
     * */
    public Mail checkMailAccountUnique(String mailAccount);

    /**
     * 查询表单授权的邮箱
     * */
    public List<Mail> selectAllocatedListByForm(Mail mail);

    /**
     * 查询未授权的邮箱
     * */
    List<Mail> selectUnallocatedListByForm(Mail mail);
}
