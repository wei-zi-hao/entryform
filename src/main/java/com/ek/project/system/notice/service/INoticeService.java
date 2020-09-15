package com.ek.project.system.notice.service;

import com.ek.project.system.notice.domain.Notice;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author ruoyi
 */
public interface INoticeService
{
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public Notice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<Notice> selectNoticeList(Notice notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(Notice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(Notice notice);

    /**
     * 删除公告信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String ids);

    /**
     * 获取公告信息
     * */
    public  List<Notice> getNoticeByPush();
    /**
     * 获取未读公告数量
     * */
    int getUnReadNoticeCount();
    /**
     * 获取未读公告信息
     * */
    int updateNoticeState(String noticeId);
}
