package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.model.JwtResponse;
import com.example.quanlydaotao.model.Response;
import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.service.RoleService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
public class Controller {
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
//            return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities()));
        }catch (Exception e){
            return ResponseEntity.ok(new Response("401", "Username or password incorrect", ""));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Iterable<User>> showAllUser() {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

//    @GetMapping("/admin/users")
//    public ResponseEntity<Iterable<User>> showAllUserByAdmin() {
//        Iterable<User> users = userService.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        Optional<User> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//    @GetMapping("/admin/users")
//    public ResponseEntity<Page<User>> getListUserWithRole(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int limit) {
//        Pageable pageable = PageRequest.of(page - 1, limit);
//        Page<User> userPage = userService.findAllUserWithRoles(pageable);
//        if (userPage.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(userPage, HttpStatus.OK);
//    }
//    @GetMapping("/admin/users/search")
//    public ResponseEntity<Page<User>> searchUserWithNameOrEmail(@RequestParam("keyword") String keyword) {
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<User> userPage;
//        if (StringUtils.isEmpty(keyword)) {
//            userPage = userService.findAllUserWithRoles(pageable);
//        } else {
//            userPage = userService.findAllByNameOrEmail(pageable, keyword);
//        }
//        if (userPage.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(userPage, HttpStatus.OK);
//    }
//    @GetMapping("/admin/users/filter/{role_id}")
//    public ResponseEntity<Page<User>> filterUserByRole(@PathVariable Long role_id) {
//        Optional<Role> role = roleService.findById(role_id);
//        Pageable pageable = PageRequest.of(0, 10);
//        if (role.isPresent()) {
//            Page<User> userPage = userService.findUsersByRoles(pageable, role.get());
//            if (userPage.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            return new ResponseEntity<>(userPage, HttpStatus.OK);
//        } else {
//            return getListUserWithRole(1, 10);
//        }
//    }

//    @GetMapping("/admin/users/role")
//    public ResponseEntity<Iterable<Role>> getListRole() {
//        List<Role> roles = (List<Role>) roleService.findAll();;
//        roles.removeIf(role -> role.getId().equals(roleService.findByName("ROLE_ADMIN").getId()));
//        return new ResponseEntity<>(roles, HttpStatus.OK);
//    }
//
//    @GetMapping("/admin/users")
//    public ResponseEntity<Iterable<User>> getListUserWithRole() {
//        List<User> userList = (List<User>) userService.findAll();
//        int index = 0;
//        for (User user : userList) {
//            index++;
//            if (user.getRoles().size() > 1) {
//                user.getRoles().removeIf(role ->
//                        (role.equals(role.getName() == "ROLE_USER")
//                                && role.equals(role.getDisplay_name() == "NA")));
//            }
//            userList.set(index, user);
//        }
//        return new ResponseEntity<>(userList, HttpStatus.OK);
//    }
    @GetMapping("/admin/users/search")
    public ResponseEntity<Iterable<User>> searchUserWithNameOrEmail(@RequestParam("keyword") String keyword) {
        System.out.println(keyword);
        Iterable<User> userIterable;
        if (keyword.isEmpty()) {
            userIterable = userService.findAll();
        } else {
            userIterable = userService.findAllByNameOrEmail(keyword);
        }
        return new ResponseEntity<>(userIterable, HttpStatus.OK);
    }
    @GetMapping("/admin/users/filter")
    public ResponseEntity<Iterable<User>> filterUserByRole(@RequestParam("role_id") Long role_id) {
        Optional<Role> role = roleService.findById(role_id);
        if (role.isPresent()) {
            Iterable<User> userIterable = userService.findUsersByRoles(role.get());
            return new ResponseEntity<>(userIterable, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }

    }

}
