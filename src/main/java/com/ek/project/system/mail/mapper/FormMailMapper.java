package com.ek.project.system.mail.mapper;

import com.ek.project.system.mail.domain.MailForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报名表Mapper接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface FormMailMapper
{



    /**
     * 取消授权
     */
    public int deleteAuthMail(@Param("formId") String formId, @Param("mailId") String mailId);



    /**
     * 批量授权
     */
    public int batchMailForm(List<MailForm> list);

    /**
     * 批量取消授权
     */
    public int deleteMailFormInfos(@Param("formId") Long formId, @Param("mailIds")Long[] mailIds);
}
