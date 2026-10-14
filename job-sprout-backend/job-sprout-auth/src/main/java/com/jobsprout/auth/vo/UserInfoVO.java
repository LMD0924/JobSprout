package com.jobsprout.auth.vo;

import com.jobsprout.auth.entity.SysUser;
import lombok.Data;

import java.time.LocalDateTime;

/** 返回给前端的安全用户信息，不包含密码。 */
@Data
public class UserInfoVO {
    private Long id;
    private String username;
    private String nickname;
    private String role;
    private String email;
    private String phone;
    private String avatar;
    private Integer status;
    private LocalDateTime createTime;

    public static UserInfoVO from(SysUser user) {
        UserInfoVO vo = new UserInfoVO();
        vo.id = user.getId(); vo.username = user.getUsername(); vo.nickname = user.getNickname();
        vo.role = user.getRole(); vo.email = user.getEmail(); vo.phone = user.getPhone();
        vo.avatar = user.getAvatar(); vo.status = user.getStatus(); vo.createTime = user.getCreateTime();
        return vo;
    }
}
