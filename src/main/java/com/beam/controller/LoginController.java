package com.beam.controller;

import com.beam.controller.dto.TokenDTO;
import com.beam.controller.dto.UserDTO;
import com.beam.controller.dto.UserListDTO;
import com.beam.model.JSONIdModel;
import com.beam.model.Login;
import com.beam.model.UserBody;
import com.beam.service.LoginService;
import com.beam.util.InitializerRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(value = "/api/user",produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signin")
    public TokenDTO login (@RequestBody UserBody userBody){
        return loginService.signIn(userBody.getUsername(), userBody.getPassword());
    }

    @PostMapping("/signup")
    public HttpStatus signUpAsUser(@RequestBody Login login){
        UserDTO userDTO = LoginService.mapToDTO(login);
        loginService.saveAsUser(userDTO);
        return HttpStatus.OK;
    }

    @PostMapping("/adminsignup")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpStatus signUpAsAdmin(@RequestBody Login login){
        UserDTO userDTO = LoginService.mapToDTO(login);
        loginService.saveAsAdmin(userDTO);
        return HttpStatus.OK;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserListDTO> listUsers(){
        return loginService.listUsers();
    }

    @PostMapping("/deleteuser")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public HttpStatus deleteUser(@RequestBody JSONIdModel jsonId){

        if (loginService.delete(jsonId)){
            return HttpStatus.OK;
        }else {
           return HttpStatus.UNPROCESSABLE_ENTITY;
        }
    }

}
