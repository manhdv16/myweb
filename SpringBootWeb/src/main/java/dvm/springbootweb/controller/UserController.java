package dvm.springbootweb.controller;

import dvm.springbootweb.entity.ERole;
import dvm.springbootweb.entity.Role;
import dvm.springbootweb.entity.User;
import dvm.springbootweb.jwt.JwtTokenProvider;
import dvm.springbootweb.payload.request.LoginRequest;
import dvm.springbootweb.payload.request.SignupRequest;
import dvm.springbootweb.payload.response.JwtResponse;
import dvm.springbootweb.payload.response.MessageResponse;
import dvm.springbootweb.security.CustomUserDetail;
import dvm.springbootweb.service.RoleService;
import dvm.springbootweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

    @Value("$(spring.mail.username)")
    private String fromMail;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {
        if(userService.existsByUserName(request.getUserName())) return new ResponseEntity<>(new MessageResponse("Username already exists"), HttpStatus.BAD_REQUEST);
        if(userService.existsByEmail(request.getEmail())) return new ResponseEntity<>(new MessageResponse("Email already exists"), HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getPassword());
        user.setCreated(new Date());
        Set<String> strRole = request.getListRoles();
        Set<Role> listRoles = new HashSet<>();
        if (strRole == null) {
            Role userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: role is not found"));
            listRoles.add(userRole);
        } else {
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        listRoles.add(adminRole);
                    case "manager":
                        Role managerRole = roleService.findByRoleName(ERole.ROLE_MANAGER).orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        listRoles.add(managerRole);
                    case "user":
                        Role userRole = roleService.findByRoleName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: role is not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }
    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customUserDetail);
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetail.getUsername(), customUserDetail.getEmail(), listRoles));
    }
    @GetMapping("/viewInfor")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> getInfor(@RequestHeader("Authorization") String token) {
        String userName = jwtTokenProvider.getUserNameFromJwt(token.substring(7));
        User user = userService.findByUserName(userName);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/updateInfor")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> update(@RequestHeader("Authorization") String token, @RequestParam(required = false) String email, @RequestParam(required = false) String phoneNumber,
                                    @RequestParam(required = false) String address){
        String userName = jwtTokenProvider.getUserNameFromJwt(token.substring(7));
        User user = userService.findByUserName(userName);
        if(email != null) user.setEmail(email);
        if(phoneNumber != null) user.setPhoneNumber(phoneNumber);
        if(address != null) user.setAddress(address);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("Update infor successfully"));
    }
    @PostMapping("/changepassword")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestParam String oldPass, @RequestParam String newPass){
        String userName = jwtTokenProvider.getUserNameFromJwt(token.substring(7));
        User user = userService.findByUserName(userName);
        if(passwordEncoder.matches(oldPass, user.getPassword())) {
            String bcreptNewPass = passwordEncoder.encode(newPass);
            user.setPassword(bcreptNewPass);
            userService.saveOrUpdate(user);
            return ResponseEntity.ok(new MessageResponse("change password successfully"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("oldPassword incorrect"));

    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> fogotPass(@RequestParam String username, @RequestParam String email) {
        User user = userService.findByUserName(username);
        if(user == null) return new ResponseEntity<>(new MessageResponse("Username not exists"),HttpStatus.NOT_FOUND);
        if(!email.equals(user.getEmail())) return new ResponseEntity<>(new MessageResponse("Email not exists"), HttpStatus.NOT_FOUND);
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject("OTP");
        simpleMailMessage.setText(String.valueOf(otp));
        simpleMailMessage.setTo(email);
        mailSender.send(simpleMailMessage);
        user.setOtp(String.valueOf(otp));
        userService.saveOrUpdate(user);
        return new ResponseEntity<>(new MessageResponse("OTP sent to " + email), HttpStatus.OK);
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verifyOTP(@RequestParam String otp,@RequestParam String username){
        User user = userService.findByUserName(username);
        if(otp.equals(user.getOtp())){
            return ResponseEntity.ok(new MessageResponse("verified"));
        }
        return new ResponseEntity<>(new MessageResponse("Verification failed, please re-enter"), HttpStatus.NOT_FOUND);
    }
    @PutMapping("/setpassword")
    public ResponseEntity<?> setPassword(@RequestParam String newPass, @RequestParam String username){
        User user = userService.findByUserName(username);
        user.setPassword(passwordEncoder.encode(newPass));
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageResponse("Update password successfully"));
    }
}
