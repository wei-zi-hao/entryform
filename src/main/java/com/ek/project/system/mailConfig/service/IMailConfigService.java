package com.ek.project.system.mailConfig.service;

import com.ek.project.system.mailConfig.domain.MailConfig;
import java.util.List;

/**
 * 邮箱配置Service接口
 * 
 * @author eric
 * @date 2020-09-08
 */
public interface IMailConfigService 
{
    /**
     * 查询邮箱配置
     * 
     * @param smtpAdress 邮箱配置ID
     * @return 邮箱配置
     */
    public MailConfig selectMailConfigById(String smtpAdress);

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
     * 批量删除邮箱配置
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteMailConfigByIds(String ids);

    /**
     * 删除邮箱配置信息
     * 
     * @param smtpAdress 邮箱配置ID
     * @return 结果
     */
    public int deleteMailConfigById(String smtpAdress);
}
