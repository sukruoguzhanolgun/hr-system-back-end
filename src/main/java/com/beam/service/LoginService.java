package com.beam.service;

import com.beam.controller.dto.TokenDTO;
import com.beam.controller.dto.UserDTO;
import com.beam.controller.dto.UserListDTO;
import com.beam.exception.CustomException;
import com.beam.model.JSONIdModel;
import com.beam.model.Login;
import com.beam.model.enums.Role;
import com.beam.repository.LoginRepository;
import com.beam.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {

    private final LoginRepository loginRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(LoginRepository loginRepository,
                        PasswordEncoder passwordEncoder,
                        JwtTokenProvider jwtTokenProvider,
                        AuthenticationManager authenticationManager) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public List<UserListDTO> listUsers(){
        List<Login> logins = loginRepository.findAll();
        return logins.stream()
                .map(LoginService::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public void saveAsUser(UserDTO userDTO){
        userDTO.setId(0);
        userDTO.setRole(Role.ROLE_USER);
        mapToDTO(this.loginRepository.save(new Login(
            userDTO.getId(),
            userDTO.getUsername(),
            passwordEncoder.encode(userDTO.getPassword()),
            userDTO.getRole()
        )));
    }

    public void saveAsAdmin(UserDTO userDTO){
        userDTO.setId(0);
        userDTO.setRole(Role.ROLE_ADMIN);
        mapToDTO(this.loginRepository.save(new Login(
                userDTO.getId(),
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getRole()
        )));
    }

    public TokenDTO signIn(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Login login = loginRepository.findByUsername(username);
            List<Role> roles = new ArrayList<>();
            roles.add(login.getRole());
            return new TokenDTO(jwtTokenProvider.createToken(username, roles)
                    ,login.getRole()
                    ,login.getId()
                    , login.getUsername());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public boolean delete(JSONIdModel id){

        Login login = loginRepository.getOne(id.getId());

        if (login != null){
            loginRepository.delete(login);
            return true;
        }else {
            return false;
        }
    }

    public Login findUser(int id){
        return loginRepository.findById(id).orElseThrow(() -> new CustomException("Invalid User",HttpStatus.UNPROCESSABLE_ENTITY));
    }

    public static UserDTO mapToDTO(Login login){
        if (login != null){
            return new UserDTO(
                    login.getId(),
                    login.getUsername(),
                    login.getPassword(),
                    login.getRole()
            );
        }
        return null;
    }

    public static UserListDTO mapToUserDTO(Login login){
        if (login != null){
            return new UserListDTO(
                    login.getId(),
                    login.getUsername(),
                    login.getRole()
            );
        }
        return null;
    }
}
