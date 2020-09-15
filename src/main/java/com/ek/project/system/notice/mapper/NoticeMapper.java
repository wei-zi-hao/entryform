package com.ek.project.system.notice.mapper;

import com.ek.project.system.notice.domain.Notice;
import com.ek.project.system.user.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公告 数据层
 *
 * @author ruoyi
 */
public interface NoticeMapper
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
     * 批量删除公告
     *
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String[] noticeIds);
    /**
     * 发布公告
     */
    public int sendNoticeToAll(@Param("noticeId") String noticeId, @Param("users") List<User> users, @Param("userName") String userName);
    /**
     * 获取公告
     */
    public List<Notice> getNoticeByPush(Long userId);
    /**
     * 获取未读公告数量
     */
    public int getUnReadNoticeCount(Long userId);
    /**
     * 修改公告状态
     */
    public int updateNoticeState(@Param("userId") Long userId, @Param("noticeId") String noticeId);
}