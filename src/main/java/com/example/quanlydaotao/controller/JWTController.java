package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tokens")
@CrossOrigin("*")
public class JWTController {
    @Autowired
    private JwtService jwtService;
    @GetMapping
    public ResponseEntity<?> testCode (){
        return new ResponseEntity<>(HttpStatus.OK);
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
