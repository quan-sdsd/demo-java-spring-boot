package fpt.project.datn.service;

import fpt.project.datn.exception.custom.AccountAuthenticationException;
import fpt.project.datn.exception.custom.EmailConfirmationException;
import fpt.project.datn.object.dto.req.LoginReq;
import fpt.project.datn.object.entity.Token;
import fpt.project.datn.object.entity.User;
import fpt.project.datn.repository.TokenRepository;
import fpt.project.datn.repository.UserCodeRepository;
import fpt.project.datn.repository.UserRepository;
import fpt.project.datn.security.service.SecurityService;
import fpt.project.datn.utility.Utility;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserCodeRepository userCodeRepository;

    public void login(LoginReq userInfo, HttpServletRequest req, HttpServletResponse res) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userInfo.getUsername(),
                        userInfo.getPassword()
                )
        );
        //null checking is not necessary
        User user = userRepository.findUserByUsername(userInfo.getUsername()).get();
        sendTokenCookies(user ,req, res);
        accountVerificationChecking(user);
    }

    private void sendTokenCookies(User user, HttpServletRequest req, HttpServletResponse res) {
        String url = req.getRequestURL().toString();
        String domain = "";
        if(url.contains("http")) {
            url = url.substring(url.indexOf("://") + 3);
        }
        if(url.contains(":")) {
            domain = url.substring(0, url.indexOf(":"));
        } else if(url.contains(".")) {
            domain = url.substring(0, url.indexOf("."));
        } else if(url.contains("/")) {
            domain = url.substring(0, url.indexOf("/"));
        }
        Cookie accessCookie = new Cookie("access-cookie", securityService.generateToken(user));
        accessCookie.setDomain(domain);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60*60);
        Cookie refreshCookie = new Cookie("refresh-cookie", securityService.generateRefreshToken(user));
        refreshCookie.setDomain(domain);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60*60*10);
        res.addCookie(accessCookie);
        res.addCookie(refreshCookie);
        saveToken(user.getUsername(), accessCookie.getValue(), refreshCookie.getValue());
    }

    @Async
    protected void saveToken(String username, String accessToken, String refreshToken) {
        User user = userRepository.findUserByUsername(username).orElseThrow(RuntimeException::new);
        Token token = new Token(username, user, accessToken, refreshToken);
        tokenRepository.save(token);
    }

    private void accountVerificationChecking(User user) {
        if(!user.isEmailConfirmed()) {
            throw new AccountAuthenticationException("email is not confirmed");
        }
    }

    public String emailConfirmation(String code) {
        String username = Utility.getCurrentUserName();
        if(userCodeRepository.emailConfirmation(username, code) == 0) {
            throw new EmailConfirmationException();
        }
        userRepository.confirmEmail(username);
        return "email confirmed";
    }

}
