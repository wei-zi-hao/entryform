package com.ek.project.system.mailConfig.service.impl;

import com.ek.common.utils.Convert;
import com.ek.project.system.mailConfig.domain.MailConfig;
import com.ek.project.system.mailConfig.mapper.MailConfigMapper;
import com.ek.project.system.mailConfig.service.IMailConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮箱配置Service业务层处理
 * 
 * @author eric
 * @date 2020-09-08
 */
@Service
public class MailConfigServiceImpl implements IMailConfigService 
{
    @Autowired
    private MailConfigMapper mailConfigMapper;

    /**
     * 查询邮箱配置
     * 
     * @param smtpAdress 邮箱配置ID
     * @return 邮箱配置
     */
    @Override
    public MailConfig selectMailConfigById(String smtpAdress)
    {
        return mailConfigMapper.selectMailConfigById();
    }

    /**
     * 查询邮箱配置列表
     * 
     * @param mailConfig 邮箱配置
     * @return 邮箱配置
     */
    @Override
    public List<MailConfig> selectMailConfigList(MailConfig mailConfig)
    {
        return mailConfigMapper.selectMailConfigList(mailConfig);
    }

    /**
     * 新增邮箱配置
     * 
     * @param mailConfig 邮箱配置
     * @return 结果
     */
    @Override
    public int insertMailConfig(MailConfig mailConfig)
    {
        return mailConfigMapper.insertMailConfig(mailConfig);
    }

    /**
     * 修改邮箱配置
     * 
     * @param mailConfig 邮箱配置
     * @return 结果
     */
    @Override
    public int updateMailConfig(MailConfig mailConfig)
    {
        return mailConfigMapper.updateMailConfig(mailConfig);
    }

    /**
     * 删除邮箱配置对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteMailConfigByIds(String ids)
    {
        return mailConfigMapper.deleteMailConfigByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除邮箱配置信息
     * 
     * @param smtpAdress 邮箱配置ID
     * @return 结果
     */
    @Override
    public int deleteMailConfigById(String smtpAdress)
    {
        return mailConfigMapper.deleteMailConfigById(smtpAdress);
    }
}
