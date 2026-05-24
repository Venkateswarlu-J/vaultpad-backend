package com.example.Pad.service;

import com.example.Pad.entity.Pad;
import com.example.Pad.repository.PadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PadService {

    @Autowired
    private PadRepository padRepository;

//    @Value("${DB_URL}")
//    private String url;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Check if key exists

    public Map<String, Object> checkKey(String key) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pad> padOpt = padRepository.findByPadKey(key);
//        System.out.println(url);

        if (padOpt.isPresent()) {
            Pad pad = padOpt.get();
            response.put("exists", true);
            response.put("isLocked", pad.getLocked());
        } else {
            // Auto-create a new empty pad
            Pad newPad = new Pad();
            newPad.setPadKey(key);
            newPad.setContent("");
            newPad.setLocked(false);
            padRepository.save(newPad);

            response.put("exists", false);
            response.put("isLocked", false);
        }

        return response;
    }

    // Access pad (with or without password) both

    public Map<String, Object> accessPad(String key, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pad> padOpt = padRepository.findByPadKey(key);

        if (padOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Pad not found");
            return response;
        }

        Pad pad = padOpt.get();

        if (pad.getLocked()) {
            if (password == null || !encoder.matches(password, pad.getPassword())) {
                response.put("success", false);
                response.put("message", "Wrong password");
                return response;
            }
        }

        response.put("success", true);
        response.put("content", pad.getContent());
        return response;
    }

    // Save entered data
    public Map<String, Object> saveContent(String key, String content) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pad> padOpt = padRepository.findByPadKey(key);

        if (padOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Pad not found");
            return response;
        }

        Pad pad = padOpt.get();
        pad.setContent(content);
        padRepository.save(pad);

        response.put("success", true);
        response.put("message", "Saved");
        return response;
    }

    // Set lock creating pass
    public Map<String, Object> setLock(String key, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pad> padOpt = padRepository.findByPadKey(key);

        if (padOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Pad not found");
            return response;
        }

        Pad pad = padOpt.get();
        pad.setPassword(encoder.encode(password));
        pad.setLocked(true);
        padRepository.save(pad);

        response.put("success", true);
        response.put("message", "Lock set");
        return response;
    }

    // Remove pass
    public Map<String, Object> removeLock(String key, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pad> padOpt = padRepository.findByPadKey(key);

        if (padOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Pad not found");
            return response;
        }

        Pad pad = padOpt.get();

        if (!encoder.matches(password, pad.getPassword())) {
            response.put("success", false);
            response.put("message", "Wrong password");
            return response;
        }

        pad.setPassword(null);
        pad.setLocked(false);
        padRepository.save(pad);

        response.put("success", true);
        response.put("message", "Lock removed");
        return response;
    }

    // Delete pad
    public Map<String, Object> deletePad(String key, String password) {
        Map<String, Object> response = new HashMap<>();
        Optional<Pad> padOpt = padRepository.findByPadKey(key);

        if (padOpt.isEmpty()) {
            response.put("success", false);
            response.put("message", "Pad not found");
            return response;
        }

        Pad pad = padOpt.get();

        if (pad.getLocked()) {
            if (password == null || !encoder.matches(password, pad.getPassword())) {
                response.put("success", false);
                response.put("message", "Wrong password");
                return response;
            }
        }

        padRepository.delete(pad);
        response.put("success", true);
        response.put("message", "Deleted");
        return response;
    }
}