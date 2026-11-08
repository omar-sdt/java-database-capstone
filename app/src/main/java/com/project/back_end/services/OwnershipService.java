package com.project.back_end.services;
import com.project.back_end.repo.*;
import org.springframework.stereotype.Component;
@Component
public class OwnershipService {
 private final TokenService tokens; private final PatientRepository patients; private final DoctorRepository doctors; private final AppointmentRepository appointments;
 public OwnershipService(TokenService t,PatientRepository p,DoctorRepository d,AppointmentRepository a){tokens=t;patients=p;doctors=d;appointments=a;}
 public boolean patient(String token,Long id){return tokens.validateToken(token,"patient") && patients.findByEmail(tokens.extractEmail(token)).getId().equals(id);}
 public boolean appointment(String token,Long id,String role){
  if(!tokens.validateToken(token,role))return false;
  return appointments.findById(id).map(a->role.equals("doctor")?a.getDoctor().getEmail().equals(tokens.extractEmail(token)):a.getPatient().getEmail().equals(tokens.extractEmail(token))).orElse(false);
 }
}
