package com.ek.project.system.form.mapper;

import com.ek.project.system.form.domain.FormInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 报名表Mapper接口
 * 
 * @author eric
 * @date 2020-08-28
 */
public interface FormInfoMapper 
{
    /**
     * 查询报名表
     * 
     * @param id 报名表ID
     * @return 报名表
     */
    public FormInfo selectFormInfoById(Long id);

    /**
     * 查询报名表列表
     * 
     * @param formInfo 报名表
     * @return 报名表集合
     * @param userId 当前用户ID
     */
    public List<FormInfo> selectFormInfoList(@Param("formInfo") FormInfo formInfo, @Param("userId")Long userId);

    /**
     * 新增报名表
     * 
     * @param formInfo 报名表
     * @return 结果
     */
    public int insertFormInfo(FormInfo formInfo);

    /**
     * 修改报名表
     * 
     * @param formInfo 报名表
     * @return 结果
     */
    public int updateFormInfo(FormInfo formInfo);

    /**
     * 删除报名表
     * 
     * @param id 报名表ID
     * @return 结果
     */
    public int deleteFormInfoById(Long id);

    /**
     * 批量删除报名表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFormInfoByIds(String[] ids);

    /**
     * 查询表单名称是否唯一
     * */
    public FormInfo checkFormTitleUnique(String formTitle);

    /**
     * 添加表单
     * */
    public int addTable(@Param("tableName")String tableName, @Param("fieldMap")Map<String, String> map);
    /**
     * 通过名称查询表单
     * */
    public FormInfo findFormInfoByFormName(String formName);
    /**
     * 添加表单字段
     * */
    public void addColumn(@Param("formName") String formName, @Param("oldElement")String oldElement, @Param("newElement")String newElement);

    /**
     * 删除表单字段
     * */
    public void removeColumn(@Param("formName")String formName, @Param("columnName")String columnName);

    /**
     * 获取表列名
     * */
    public String[] selectFormColumn(@Param("formName") String formName);

    public List<Map<String, Object>> selectformDataList(@Param("formName")String formName);

    public int deleteFormDataByIds(@Param("formName") String formName,@Param("ids")String[] ids);
}
