package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.JwtTokenRepository;
import com.example.quanlydaotao.service.RoleService;
import com.example.quanlydaotao.service.TrainingStatsService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.JwtService;
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

    @Autowired
    private TrainingStatsService trainingStatsService;

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        if (user.getRoles() == null) {
            Role roleUser = roleService.findByName("ROLE_HR");
            user.setRoles(Collections.singletonList(roleUser));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getName());
            return ResponseEntity.ok(new Response("200", "Login success",
                    new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities())));
        } catch (Exception e) {
            return ResponseEntity.ok(new Response("401", "Username or password incorrect", null));
        }
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<String> logout(@RequestHeader HttpHeaders headers) {
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String token = authorization.substring(7);
        JwtToken jwtToken = tokenRepository.findByTokenEquals(token);
        jwtToken.setValid(false);
        tokenRepository.save(jwtToken);
        return ResponseEntity.ok("Logout success");
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/admin/users/role")
    public ResponseEntity<Iterable<Role>> getListRole() {
        List<Role> roles = (List<Role>) roleService.findAll();
        roles.removeIf(role -> role.getId().equals(roleService.findByName("ROLE_ADMIN").getId()));
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/admin/users/filterWithFields")
    public ResponseEntity<Page<User>> filterWithFields(
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "role_id") String roleId) {

        Pageable pageable = PageRequest.of(page, size);
        Iterable<User> users = roleId.isEmpty() ?
                userService.findAllByNameOrEmail(keyword) :
                userService.filterWithFields(keyword, Long.valueOf(roleId));

        return new ResponseEntity<>(userService.convertToPage(users, pageable), HttpStatus.OK);
    }

    @GetMapping("/admin/users/view/{id}")
    public ResponseEntity<User> view(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/admin/users/update/{id}")
    public ResponseEntity<String> updateUserAccount(@PathVariable Long id, @RequestBody User user) {
        return userService.findById(id)
                .map(existingUser -> {
                    user.setId(existingUser.getId());
                    userService.save(user);
                    return new ResponseEntity<>("Updated!", HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/admin/users/check-phone/{phone}")
    public ResponseEntity<Map<String, Boolean>> checkPhoneExists(@PathVariable String phone) {
        boolean exists = userService.checkPhoneExists(phone);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    @GetMapping("/admin/trainingStats")
    public ResponseEntity<TrainingStatsDTO> getTrainingStats() {
        TrainingStatsDTO trainingStatsDTO = trainingStatsService.getTrainingStats();
        return new ResponseEntity<>(trainingStatsDTO, HttpStatus.OK);
    }
}