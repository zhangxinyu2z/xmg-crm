package com._520it.crm.service.impl;

import com._520it.crm.domain.CheckIn;
import com._520it.crm.mapper.CheckInMapper;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;
import com._520it.crm.service.ICheckInService;
import com._520it.crm.utils.CheckInUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxinyu
 */
@Service
public class CheckInServiceImpl implements ICheckInService {

    @Autowired
    private CheckInMapper dao;

    @Override
    public int save(CheckIn checkIn) {

        return dao.insert(checkIn);
    }

    @Override
    public int update(CheckIn checkIn) {

        return dao.updateByPrimaryKey(checkIn);
    }

    @Override
    public CheckIn get(Long id) {
        return null;
    }

    @Override
    public List<CheckIn> listAll() {

        return dao.selectAll();
    }

    @Override
    public PageResult queryByCondition(PageReq qo) {
        // 根据查询条件查询出总条数
        Long count = dao.queryByConditionCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CheckIn> listData = dao.queryByCondition(qo);
            return new PageResult(count, listData);
        }
    }

    @Override
    public PageResult queryCheckInByEid(Long id) {
        // 根据查询条件查询出总条数
        Long count = dao.queryCheckInByEidCount(id);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<CheckIn> listData = dao.queryCheckInByEid(id);
            return new PageResult(count, listData);
        }
    }

    @Override
    public int signIn(CheckIn checkIn, Long id) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 当前签到时间
        String dateNowStr = sdf.format(date);
        // 获取历史的所有签到记录，判断当天是否有签到记录
        List<CheckIn> listCheckIn = dao.queryCheckInByEid(id);
        /*
            如果已经签到过，则不能再签到
            如果已经签退过，则不能再签到
         */
        for (CheckIn c : listCheckIn) {
            // 获取历史签到记录
            Date signInDate = c.getSignintime();
            if (signInDate != null) {
                String dateStr = sdf.format(signInDate);
                // 相等说明已经签到了
                if (dateStr.equals(dateNowStr)) {
                    return 0;
                }
            } else {
                Date signOutDate = c.getSignouttime();
                if (signOutDate != null) {
                    String dateStr = sdf.format(signOutDate);
                    if (dateStr.equals(dateNowStr)) {
                        return 0;
                    }
                }
            }
        }
        checkIn.setSignintime(date);
        // 判断是是否晚签或早退
        int result = CheckInUtils.checkSignInTime(date);
        // 当前时间和比较的时间早
        if (result == -1) {
            checkIn.setState(CheckIn.SIGNSTATE_NORMAL);
        } else {
            checkIn.setState(CheckIn.SIGNSTATE_LATE);
        }
        return dao.insert(checkIn);
    }

    /**
     * {@inheritDoc}
     * <p></p>
     * 获取当前用户的所有签到记录
     * 1、判断每条签到记录的签退日期，如果签退日期匹配，说明当前已经签退过，更新签退日期，获取签到的状态，更新签到记录的状态；
     * 2、如果此条签到记录的签退日期不存在，判断是否已经签到过，如果匹配说明是当天的签到记录，更新签退日期，判断签退状态以及签到状态；
     * 3、用户的签退记录中不存在当天的签到记录，说明没有签到和签退过，判断签退状态，新增一条签到记录到数据中。
     *
     * @param checkIn
     * @param id
     * @return
     */
    @Override
    public int signOut(CheckIn checkIn, Long id) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(date);
        List<CheckIn> listCheckIn = dao.queryCheckInByEid(id);
        // 如果有匹配的记录，说明已经签到过了，所以更新数据即可
        for (CheckIn c : listCheckIn) {
            Date signoutdate = c.getSignouttime();
            // 当前签到记录已经有签退了
            if (signoutdate != null) {
                String signoutdateStr = sdf.format(signoutdate);
                // 正是当天的签到记录
                if (dateNowStr.equals(signoutdateStr)) {
                    // 更新下签退时间
                    c.setSignouttime(date);
                    // 签退状态
                    int resultOut = CheckInUtils.checkSignOutTime(date);
                    Date signIndate = c.getSignintime();
                    // 已经签到过
                    if (signIndate != null) {
                        // 签到状态
                        int resultIn = CheckInUtils.checkSignInTime(signIndate);
                        // 早退+迟到
                        if (resultOut == -1) {
                            if (resultIn == 1) {
                                c.setState(CheckIn.SIGNSTATE_LATEANDEARLY);
                            }
                        } else {
                            // 签退正常
                            if (resultIn == -1) {
                                c.setState(CheckIn.SIGNSTATE_NORMAL);
                            } else {
                                // 迟到
                                c.setState(CheckIn.SIGNSTATE_LATE);
                            }
                        }
                    } else { // 尚未签到，处理签退状态
                        if (resultOut == -1) {
                            c.setState(CheckIn.SIGNSTATE_EARLY);
                        } else {
                            c.setState(CheckIn.SIGNSTATE_NORMAL);
                        }
                    }
                    return dao.updateByPrimaryKey(c);
                }
            } else { // 当前签到记录无签退
                Date signInDate = c.getSignintime();
                // 如果当前记录已经签到过
                if (signInDate != null) {
                    String dateStr = sdf.format(signInDate);
                    // 是当天的签到记录
                    if (dateStr.equals(dateNowStr)) {
                        // 更新签退时间，获取签退状态
                        c.setSignouttime(date);
                        int resultOut = CheckInUtils.checkSignOutTime(date);
                        // 获取到当前的签到时间判断签到状态
                        Date signintime = c.getSignintime();
                        int resultIn = CheckInUtils.checkSignInTime(signintime);
                        if (resultOut == -1) {
                            if (resultIn == -1) {
                                // 早退
                                c.setState(CheckIn.SIGNSTATE_EARLY);
                            } else {
                                // 早退+迟到
                                c.setState(CheckIn.SIGNSTATE_LATEANDEARLY);
                            }
                        } else {
                            if (resultIn == -1) {
                                // 正常
                                c.setState(CheckIn.SIGNSTATE_NORMAL);
                            } else {
                                // 迟到
                                c.setState(CheckIn.SIGNSTATE_LATE);
                            }
                        }
                        return dao.updateByPrimaryKey(c);
                    }
                }
            }
        }
        // 没有匹配的记录，说明当天没有签到，也没有签退
        int result = CheckInUtils.checkSignOutTime(date);
        if (result == -1) {
            // 早退
            checkIn.setState(CheckIn.SIGNSTATE_EARLY);
        } else {
            // 签退正常
            checkIn.setState(CheckIn.SIGNSTATE_NORMAL);
        }
        checkIn.setSignouttime(date);
        return dao.insert(checkIn);
    }

    @Override
    public int AddCheckIn(CheckIn checkIn, Long id) {
        return 0;
    }

}
