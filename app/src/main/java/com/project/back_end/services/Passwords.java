package com.project.back_end.services;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
public final class Passwords {
 private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();
 private Passwords() {}
 public static String hash(String password) { return ENCODER.encode(password); }
 public static boolean matches(String raw, String encoded) { return raw != null && encoded != null && ENCODER.matches(raw, encoded); }
}
