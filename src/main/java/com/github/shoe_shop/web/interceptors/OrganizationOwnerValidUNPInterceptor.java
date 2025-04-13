package com.github.shoe_shop.web.interceptors;

import com.github.shoe_shop.organization.organization.Organization;
import com.github.shoe_shop.organization.organization.OrganizationService;
import com.github.shoe_shop.user.user.UserRole;
import com.github.shoe_shop.user.user_info.UserInfo;
import com.github.shoe_shop.user.user_info.UserInfoService;
import com.github.shoe_shop.web.annotations.OrganizationOwnerValidUNP;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class OrganizationOwnerValidUNPInterceptor implements HandlerInterceptor {
    private final OrganizationService organizationService;

    private final UserInfoService userInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            final Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(OrganizationOwnerValidUNP.class)) {
                return true;
            }

            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (!authentication.getAuthorities().stream().toList().get(0).toString().equals(UserRole.ORGANIZATION_OWNER.toString())) {
                return true;
            }

            final String unp = getUNPFromRequest(request);
            if (unp == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "UNP is required");
                return false;
            }

            final UserInfo currentUserInfo = userInfoService.findByUserUsername(authentication.getName());
            final Organization organization = organizationService.findByUnp(unp);
            if (organization == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "UNP is not found");
                return false;
            }
            if (!organization.getOrganizationHead().equals(currentUserInfo)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Current user has no access to this organization");
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    private String getUNPFromRequest(final HttpServletRequest request) {
        final Pattern unpPatter = Pattern.compile("\\d{14}");
        final Matcher matcher = unpPatter.matcher(request.getRequestURI());
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
