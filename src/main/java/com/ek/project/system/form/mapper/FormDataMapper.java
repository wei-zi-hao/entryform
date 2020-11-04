package com.ek.project.system.form.mapper;

import com.ek.project.system.form.domain.FormInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 报名表Mapper接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface FormDataMapper
{

    /**
     * 通过名称查询表单部分数据
     * */
    public FormInfo findFormInfoByFormName(String formName);

    /**
     * 插入表单数据
     * */
    public void insertDataByFormName(@Param("formName")String formName, @Param("mapData")Map<String, Object> mapData, @Param("ipAddr")String ipAddr);

    /**
     * 通过名称查询表单全部数据
     * */
    public FormInfo findFormInfoAllByFormName(String formName);


    int saveNoteVerify(@Param("phone")String phone, @Param("ip")String ip, @Param("verifyNumber")String verifyNumber);

    String selectVerifyTimeByPhone(@Param("phone")String phone);

    int  selectVerifyTimeByIP(@Param("ip")String ip,@Param("time")String time);

    int saveVerifyNumber(@Param("verifyNumber")String verifyNumber);

    String verifyNum(@Param("phone")String phone);

    int findRepeatPhoneByFormName(@Param("formName")String formName, @Param("phoneField")Object phoneField, @Param("repeatPhone")Object repeatPhone);
}
