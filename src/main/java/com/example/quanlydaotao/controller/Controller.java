package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.ProcessDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.JwtTokenRepository;
import com.example.quanlydaotao.service.RoleService;
import com.example.quanlydaotao.service.UserService;
import com.example.quanlydaotao.service.impl.InternService;
import com.example.quanlydaotao.service.impl.JwtService;
import com.example.quanlydaotao.service.impl.RecruitmentPlanService;
import com.example.quanlydaotao.service.impl.RecruitmentRequestService;
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
    private RecruitmentRequestService requestService;

    @Autowired
    private RecruitmentPlanService planService;

    @Autowired
    private InternService internService;

//    @PostMapping("/register")
//    public ResponseEntity<String> createUser(@RequestBody User user, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//
//        if (user.getRoles() == null) {
//            Role roleUser = roleService.findByName("ROLE_HR");
//            user.setRoles(Collections.singletonList(roleUser));
//            user.setStatus(true);
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setStatus(true);
//        userService.save(user);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
    @GetMapping("/admin/users/findState")
    public ResponseEntity<Iterable<User>> findByState(@RequestParam(name = "state") String state){
        return new ResponseEntity<>(userService.findUsersByStateAndStatus(state, userService.findAll()), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Response> createUser(@RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new Response("400", "Dữ liệu người dùng không hợp lệ", bindingResult.getFieldErrors()));
        }

        List<User> users = (List<User>) userService.findAll();

        if (user.getPhone() != null) {
            for (int i = 0; i < users.size(); i++) {
                User existingUser = users.get(i);
                if (existingUser.getName().equals(user.getName())) {
                    return ResponseEntity.ok(new Response("409", "Tên người dùng đã tồn tại", null));
                }
                if (existingUser.getPhone().equals(user.getPhone())) {
                    return ResponseEntity.ok(new Response("409", "Số điện thoại đã tồn tại", null));
                }
                if (existingUser.getEmail().equals(user.getEmail())) {
                    return ResponseEntity.ok(new Response("409", "Địa chỉ email đã tồn tại", null));
                }
            }
        } else {
            for (int i = 0; i < users.size(); i++) {
                User existingUser = users.get(i);
                if (existingUser.getEmail().equals(user.getEmail())) {
                    return ResponseEntity.ok(new Response("409", "Địa chỉ email đã tồn tại", null));
                }
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String msg = "Tài khoản đã được tạo. Chờ xác nhận";
        if (users.isEmpty()) {
            Role roleUser = roleService.findByName("ROLE_ADMIN");
            user.setRoles(Collections.singletonList(roleUser));
            user.setStatus(true);
            user.setState(true);
            msg = "Tài khoản quản trị viên đã được tạo.";
        }

        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response("201", msg, null));
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            if (jwt.equals("Tài khoản của bạn đã bị chặn")) {
                return ResponseEntity.ok(new Response("202", "Tài khoản của bạn đã bị chặn", null));
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getEmail());
            if (jwt.equals("Tài khoản của bạn chưa được xác nhận")) {
                return ResponseEntity.ok(new Response("202", "Tài khoản của bạn chưa được xác nhận",
                        new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities())));
            }
            return ResponseEntity.ok(new Response("200", "Đăng nhập thành công",
                    new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), userDetails.getAuthorities())));
        } catch (Exception e) {
            return ResponseEntity.ok(new Response("401", "Tài khoản hoặc mật khẩu không đúng", null));
        }
    }

    @PostMapping("/logoutUser")
    public ResponseEntity<String> logout(@RequestHeader HttpHeaders headers) {
        String authorization = headers.getFirst(HttpHeaders.AUTHORIZATION);

        // Kiểm tra xem authorization có giá trị null không trước khi sử dụng
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            JwtToken jwtToken = tokenRepository.findByTokenEquals(token);

            if (jwtToken != null) {
                jwtToken.setValid(false);
                tokenRepository.save(jwtToken);
                return ResponseEntity.ok("Logout success");
            } else {
                return ResponseEntity.badRequest().body("Invalid token");
            }
        } else {
            return ResponseEntity.badRequest().body("Authorization header missing or invalid");
        }
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
            @RequestParam(name = "role_id") String roleId,
            @RequestParam(name = "state") String state) {

        Pageable pageable = PageRequest.of(page, size);
        Iterable<User> users = userService.filterWithFields(keyword, (roleId.isEmpty() ? 0 : Long.valueOf(roleId)) , state);
        return new ResponseEntity<>(userService.convertToPage(users, pageable), HttpStatus.OK);
    }

    @GetMapping("/admin/users/view/{id}")
    public ResponseEntity<User> view(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/roles/users/view/{id}")
    public ResponseEntity<User> viewRoles(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/admin/users/update/{id}")
    public ResponseEntity<String> updateUserAccount(@PathVariable Long id, @RequestBody User user) {
        return userService.findById(id)
                .map(existingUser -> {
                    boolean statusRole = user.getRoles().isEmpty() ? false : true;
                    user.setId(existingUser.getId());
                    user.setStatus(statusRole);
                    user.setState(statusRole);
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

    @GetMapping("/admin/users/check-phone-add/{phone}")
    public ResponseEntity<Map<String, Boolean>> checkAddPhoneExists(@PathVariable String phone) {
        boolean exists = userService.checkAddPhoneExists(phone);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    @PostMapping("/admin/block/{id}")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {
        //true - Người dùng được cấp quyền
        //false - Người dùng bị từ chối quyền
        String message;
        User user;
        try {
            user = userService.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
        } catch (RuntimeException e) {
            message = "User not found!";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            message = "Not confirmation!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }

        boolean toggleStatus = !user.isStatus();
        user.setStatus(toggleStatus);
        userService.save(user);

        message = toggleStatus ?
                "Blocked user with id = " + id + " access!" :
                "Unblock user with id = " + id + " access!";

        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/process/request/{idRequest}")
    public ResponseEntity showProcessRequest(@PathVariable long idRequest) {
        ProcessDTO processDTO = requestService.showProcessRequest(idRequest);
        processDTO = planService.showProcessPlan(processDTO);
        processDTO = internService.showProcessIntern(processDTO);
        return new ResponseEntity(processDTO, HttpStatus.OK);
    }

    @GetMapping("/process/plan/{idPlan}")
    public ResponseEntity showProcessPlan(@PathVariable long idPlan) {
        RecruitmentPlan plan = planService.findById(idPlan).get();
        long idRequest = plan.getRecruitmentRequest().getId();
        ProcessDTO processDTO = requestService.showProcessRequest(idRequest);
        processDTO = planService.showProcessPlan(processDTO);
        processDTO = internService.showProcessIntern(processDTO);
        return new ResponseEntity(processDTO, HttpStatus.OK);
    }
    @PostMapping("/admin/users/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        // Save the new user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
        return new ResponseEntity<>("User added!", HttpStatus.CREATED);
    }

    @GetMapping("/admin/users/check-email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@PathVariable String email) {
        boolean exists = userService.checkEmailExists(email);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }

    @GetMapping("/checkToken")
    public ResponseEntity<?> checkToken(@RequestParam(name = "token") String token) {
        try {
            if (jwtService.validateJwtToken(token)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}