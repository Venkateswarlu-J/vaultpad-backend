package com.example.Pad.controller;

import com.example.Pad.service.PadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pad")
@CrossOrigin(origins = "*")
public class PadController {

    @Autowired
    private PadService padService;


    @GetMapping("/")
    public String hello(){
        return "Hello my!";
    }

    // Check if key exists (auto-creates if not)
    @GetMapping("/check/{key}")
    public ResponseEntity<Map<String, Object>> checkKey(@PathVariable String key) {
        if(key.length()>30) {
            Map<String,Object> response=new HashMap<>();
            response.put("success",false);
            response.put("message","Key should not be more than 30 chars");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        return ResponseEntity.ok(padService.checkKey(key));
    }

    // Access pad content
    @PostMapping("/access")
    public ResponseEntity<Map<String, Object>> accessPad(@RequestBody Map<String, String> body) {
        String key = body.get("key");
        String password = body.get("password");
        return ResponseEntity.ok(padService.accessPad(key, password));
    }

    // Save content
    @PutMapping("/save/{key}")
    public ResponseEntity<Map<String, Object>> saveContent(
            @PathVariable String key,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(padService.saveContent(key, body.get("content")));
    }

    // Set lock
    @PutMapping("/lock/{key}")
    public ResponseEntity<Map<String, Object>> setLock(
            @PathVariable String key,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(padService.setLock(key, body.get("password")));
    }

    // Remove lock
    @PutMapping("/unlock/{key}")
    public ResponseEntity<Map<String, Object>> removeLock(
            @PathVariable String key,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(padService.removeLock(key, body.get("password")));
    }

    // Delete pad
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<Map<String, Object>> deletePad(
            @PathVariable String key,
            @RequestBody(required = false) Map<String, String> body) {
        String password = (body != null) ? body.get("password") : null;
        return ResponseEntity.ok(padService.deletePad(key, password));
    }
}