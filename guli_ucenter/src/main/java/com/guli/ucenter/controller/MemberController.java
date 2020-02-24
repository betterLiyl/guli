package com.guli.ucenter.controller;


import com.guli.common.result.Result;
import com.guli.ucenter.entity.Member;
import com.guli.ucenter.service.MemberService;
import com.guli.ucenter.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author lyl
 * @since 2020-02-19
 */
@RestController
@RequestMapping("/ucenter")
@CrossOrigin
public class MemberController {
    @Autowired
    private MemberService memberService;
    //根据token获取用户信息
    @PostMapping("getUserInfo/{token}")
    public Result getUserInfo(@PathVariable String token){
        if(StringUtils.isEmpty(token)){
            return Result.error();
        }
        Claims claims = JwtUtils.checkJWT(token);
        Member member = new Member();
        member.setId((String)claims.get("id"));
        member.setNickname((String)claims.get("nickname"));
        member.setAvatar((String)claims.get("avatar"));
        return Result.ok().data("member",member);
    }
    @ApiOperation(value = "今日注册数")
    @GetMapping(value = "countRegister/{day}")
    public Result registerCount(
            @ApiParam(name = "day", value = "统计日期")
            @PathVariable String day){
        Integer count = memberService.countRegisterByDay(day);
        return Result.ok().data("countRegister", count);
    }
}

