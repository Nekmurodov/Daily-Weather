package com.example.dailyWeather.valid;

import com.example.dailyWeather.entity.user.User;
import com.example.dailyWeather.exception.ForbiddenException;
import com.example.dailyWeather.util.MessageKey;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CheckRoleExecutor {
    @Before(value = "@annotation(checkRole)")
    public void checkRole(CheckRole checkRole) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String[] split = checkRole.value().split(",");
        boolean exist = false;
        outerLoop:
        for (GrantedAuthority grantedAuthority : principal.getAuthorities()) {
            for (String role : split) {
                if (role.equals(grantedAuthority.getAuthority())) {
                    exist = true;
                    break outerLoop;
                }
            }
        }
        if (!exist) throw new ForbiddenException(checkRole.value(), MessageKey.FORBIDDEN);
    }
}
