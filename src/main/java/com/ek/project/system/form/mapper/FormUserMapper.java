package com.ek.project.system.form.mapper;

import com.ek.project.system.user.domain.UserForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 报名表Mapper接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface FormUserMapper
{


    /**
     * 授权用户
     *
     * @param formId 报名表ID
     * @param userId 当前用户ID
     * @return 报名表
     */
    public void saveUserFormByFormId(@Param("formId") Long formId, @Param("userId")Long userId);

    /**
     * 删除表单对应的用户
     */
    public void deleteFormUserById(String id);

    /**
     * 取消授权
     */
    public int deleteUserFormInfo(@Param("formId")String formId, @Param("userId")String userId);

    /**
     * 批量取消授权
     */
    public int deleteUserFormInfos(@Param("formId") Long formId, @Param("userIds") Long[] userIds);

    /**
     * 批量授权
     */
    public int batchUserForm(List<UserForm> list);
}
