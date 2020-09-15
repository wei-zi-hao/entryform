package com.ek.project.system.notice.service;

import com.ek.common.utils.Convert;
import com.ek.common.utils.ShiroUtils;
import com.ek.common.utils.StringUtils;
import com.ek.project.system.notice.domain.Notice;
import com.ek.project.system.notice.mapper.NoticeMapper;
import com.ek.project.system.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 公告 服务层实现
 *
 * @author ruoyi
 * @date 2018-06-25
 */
@Service
public class NoticeServiceImpl implements INoticeService
{
    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public Notice selectNoticeById(Long noticeId)
    {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<Notice> selectNoticeList(Notice notice)
    {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(Notice notice)
    {
        notice.setCreateBy(ShiroUtils.getLoginName());
        return noticeMapper.insertNotice(notice);
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(Notice notice)
    {
        notice.setUpdateBy(ShiroUtils.getLoginName());
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids)
    {
        return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
    }



    @Override
    public  List<Notice> getNoticeByPush() {
        Long userId = ShiroUtils.getUserId();
        return  noticeMapper.getNoticeByPush(userId);
    }

    @Override
    public int getUnReadNoticeCount() {
        Long userId = ShiroUtils.getUserId();
        return noticeMapper.getUnReadNoticeCount(userId);
    }

    @Override
    public int updateNoticeState(String noticeId) {
        Long userId = ShiroUtils.getUserId();
        if(StringUtils.isNotNull(noticeId)){
            return noticeMapper.updateNoticeState(userId,noticeId);
        }
        return 0;
    }
}
