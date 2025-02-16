package com.dev.oms.users;


import com.dev.oms.errors.NotFoundException;
import com.dev.oms.errors.UnauthorizedException;
import com.dev.oms.security.Jwt;
import com.dev.oms.security.JwtAuthentication;
import com.dev.oms.security.JwtAuthenticationToken;
import com.dev.oms.utils.ApiUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.dev.oms.utils.ApiUtils.success;


@RestController
@RequestMapping("api/users")
public class UserRestController {

    private final Jwt jwt;

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    public UserRestController(Jwt jwt, AuthenticationManager authenticationManager, UserService userService) {
        this.jwt = jwt;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(path = "login")
    public ApiUtils.ApiResult<LoginResult> login(
            @Valid @RequestBody LoginRequest request
    ) throws UnauthorizedException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new JwtAuthenticationToken(request.getPrincipal(), request.getCredentials())
            );
            final User user = (User) authentication.getDetails();
            final String token = user.newJwt(
                    jwt,
                    authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .toArray(String[]::new)
            );
            return success(new LoginResult(token, user));
        } catch (AuthenticationException e) {
            throw new UnauthorizedException(e.getMessage(), e);
        }
    }

    @GetMapping(path = "me")
    public ApiUtils.ApiResult<UserDto> me(
            // JwtAuthenticationTokenFilter 에서 JWT 값을 통해 사용자를 인증한다.
            // 사용자 인증이 정상으로 완료됐다면 @AuthenticationPrincipal 어노테이션을 사용하여 인증된 사용자 정보(JwtAuthentication)에 접근할 수 있다.
            @AuthenticationPrincipal JwtAuthentication authentication
    ) {
        return success(
                userService.findById(authentication.id)
                        .map(UserDto::new)
                        .orElseThrow(() -> new NotFoundException("Could nof found user for " + authentication.id))
        );
    }

}