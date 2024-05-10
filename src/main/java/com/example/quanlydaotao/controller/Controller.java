package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.respository.JwtTokenRepository;
import com.example.quanlydaotao.security.jwt.JwtAuthenticationFilter;
import com.example.quanlydaotao.service.RoleService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.JwtService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@CrossOrigin("*")
public class Controller {
    @Autowired
    private JwtTokenRepository tokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Iterable<User> users = userService.findAll();
        for (User currentUser : users) {
            if (currentUser.getName().equals(user.getName())) {
                return new ResponseEntity<>("Username existed", HttpStatus.OK);
            }
        }

        if (user.getRoles() == null) {
            Role role1 = roleService.findByName("ROLE_USER");
            List<Role> roles1 = new ArrayList<>();
            roles1.add(role1);
            user.setRoles(roles1);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getName());
            return ResponseEntity.ok(new Response("200", "Login success", new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities())));
        }catch (Exception e){
            return ResponseEntity.ok(new Response("401", "Username or password incorrect", null));
        }
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<?> logout(@RequestHeader HttpHeaders headers) {
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String token = authorization.substring(7);
        JwtToken jwtToken = tokenRepository.findByTokenEquals(token);
        jwtToken.setValid(false);
        tokenRepository.save(jwtToken);
        return ResponseEntity.ok("Logout success");
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin/users")
    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
