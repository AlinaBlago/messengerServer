package com.finalproject.server.controller;

import com.finalproject.server.exception.MessengerExceptions;
import com.finalproject.server.payload.request.SignupRequest;
import com.finalproject.server.payload.response.UserResponse;
import com.finalproject.server.service.UserOperations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class AdminController {

    private final UserOperations userOperations;

    public AdminController(UserOperations userOperations) {
        this.userOperations = userOperations;
    }

    @PostMapping("/admins")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerAdmin(@RequestBody @Valid SignupRequest request) {
        return userOperations.createAdmin(request);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable long id) {
        return userOperations.findById(id).orElseThrow(() -> MessengerExceptions.userNotFound(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void lockUser(@AuthenticationPrincipal String email) {
        userOperations.deleteByUsername(email);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unLockCurrentUser(@AuthenticationPrincipal String email) {
        userOperations.deleteByUsername(email);
    }

//    @RequestMapping(value = "/loadUsersForAdmin" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Set<String>> loadUsersForAdmin(String login){
//
//        Set<String> users = Collections.singleton(messageOperations.getUserChats(login).toString());
//
//        userOperations.findAll().forEach(item -> {
//            users.add(item.getUsername());
//        });
//
//        return new ResponseEntity<Set<String>>(users , HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/deleteUser" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity.BodyBuilder deleteUser(Long id, String adminLogin){
//
//        if(userOperations.loadUserByUsername(adminLogin) != null){
//            userOperations.deleteById(id);
//
//            return ResponseEntity.status(HttpStatus.OK);
//        }
//
//        return ResponseEntity.status(HttpStatus.CONFLICT);
//    }
//
//    @RequestMapping(value = "/banUser" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity.BodyBuilder banUser(String adminLogin, Long userToBanId){
//
//        if(userOperations.loadUserByUsername(adminLogin) != null){
//            userOperations.ban(userToBanId);
//
//            return ResponseEntity.status(HttpStatus.OK);
//        }
//
//        return ResponseEntity.status(HttpStatus.CONFLICT);
//    }
//
//    @RequestMapping(value = "/unBanUser" , method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity.BodyBuilder unBanUser(String adminLogin, Long userToUnBanId){
//
//        if(userOperations.loadUserByUsername(adminLogin) != null){
//            userOperations.unBan(userToUnBanId);
//
//            return ResponseEntity.status(HttpStatus.OK);
//        }
//
//        return ResponseEntity.status(HttpStatus.CONFLICT);
//    }
}
