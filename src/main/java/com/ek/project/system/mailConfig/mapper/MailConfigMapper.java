package com.ek.project.system.mailConfig.mapper;

import com.ek.project.system.mailConfig.domain.MailConfig;
import java.util.List;

/**
 * 邮箱配置Mapper接口
 * 
 * @author eric
 * @date 2020-09-08
 */
public interface MailConfigMapper 
{
    /**
     * 查询邮箱配置
     * 
     * @param smtpAdress 邮箱配置ID
     * @return 邮箱配置
     */
    public MailConfig selectMailConfigById();

    /**
     * 查询邮箱配置列表
     * 
     * @param mailConfig 邮箱配置
     * @return 邮箱配置集合
     */
    public List<MailConfig> selectMailConfigList(MailConfig mailConfig);

    /**
     * 新增邮箱配置
     * 
     * @param mailConfig 邮箱配置
     * @return 结果
     */
    public int insertMailConfig(MailConfig mailConfig);

    /**
     * 修改邮箱配置
     * 
     * @param mailConfig 邮箱配置
     * @return 结果
     */
    public int updateMailConfig(MailConfig mailConfig);

    /**
     * 删除邮箱配置
     * 
     * @param smtpAdress 邮箱配置ID
     * @return 结果
     */
    public int deleteMailConfigById(String smtpAdress);

    /**
     * 批量删除邮箱配置
     * 
     * @param smtpAdresss 需要删除的数据ID
     * @return 结果
     */
    public int deleteMailConfigByIds(String[] smtpAdresss);
}
