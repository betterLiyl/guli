package com.guli.ucenter.service;

import com.guli.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author lyl
 * @since 2020-02-19
 */
public interface MemberService extends IService<Member> {
    //统计每天的注册人数
    Integer countRegisterByDay(String day);

    Member getOpenUserInfo(String openid);
}
