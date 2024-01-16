package fpt.project.datn.controller;

import fpt.project.datn.object.dto.req.LoginReq;
import fpt.project.datn.object.dto.req.UserReq;
import fpt.project.datn.object.entity.User;
import fpt.project.datn.service.AccountService;
import fpt.project.datn.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController extends AbsGeneralCRUDController<User, UserReq, User, Integer>{
    private final AccountService service;

    public UserController(UserService service, AccountService accountService) {
        super(service);
        this.service = accountService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginReq userInfo, HttpServletRequest req, HttpServletResponse res) {
        service.login(userInfo, req, res);
    }

    @PostMapping("/account-confirmation/{code}")
    public String confirmation(@PathVariable String code) {
        return service.emailConfirmation(code);
    }
}
