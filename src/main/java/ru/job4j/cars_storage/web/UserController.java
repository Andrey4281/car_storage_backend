package ru.job4j.cars_storage.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars_storage.config.jwt.JwtTokenUtil;
import ru.job4j.cars_storage.service.model.AuthTokenResponse;
import ru.job4j.cars_storage.service.model.LoginUser;
import ru.job4j.cars_storage.service.UserServiceImpl;
import ru.job4j.cars_storage.service.model.NewUserResponse;

@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/signin")
    public ResponseEntity<AuthTokenResponse> register(@RequestBody LoginUser loginUser) throws AuthenticationException {
        log.debug("SIGN IN REQUEST:");
        log.debug(loginUser.toString());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getLogin(),
                        loginUser.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails user = userService.loadUserByUsername(loginUser.getLogin());
        final String token = jwtTokenUtil.doGenerateToken(user);
        return ResponseEntity.ok(new AuthTokenResponse(token, user.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<NewUserResponse> saveUser(@RequestBody LoginUser user){
        return new ResponseEntity(userService.save(user), HttpStatus.OK);
    }
}

