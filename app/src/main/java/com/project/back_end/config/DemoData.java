package com.project.back_end.config;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import com.project.back_end.services.Passwords;
import java.time.*;
import java.util.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
@Configuration @Profile("demo")
public class DemoData {
 @Bean CommandLineRunner seed(AdminRepository admins,DoctorRepository doctors,PatientRepository patients,AppointmentRepository appointments){return args->{
 if(admins.count()==0){Admin a=new Admin();a.setUsername("admin");a.setPassword(Passwords.hash("ClinicDemo2026!"));admins.save(a);}
 if(doctors.count()==0){for(int i=1;i<=3;i++){Doctor d=new Doctor();d.setName(List.of("Dr Alice Martin","Dr Karim Benali","Dr Sarah Wilson").get(i-1));d.setSpecialty(i==1?"Cardiology":i==2?"Dermatology":"General Medicine");d.setEmail("doctor"+i+"@example.com");d.setPhone("060000000"+i);d.setPassword(Passwords.hash("ClinicDemo2026!"));d.setAvailableTimes(new ArrayList<>(List.of("09:00-10:00","10:00-11:00","14:00-15:00","15:00-16:00")));doctors.save(d);}}
 if(patients.count()==0){for(int i=1;i<=5;i++){Patient p=new Patient();p.setName("Demo Patient "+i);p.setEmail("patient"+i+"@example.com");p.setPhone("070000000"+i);p.setAddress("Demo Street "+i);p.setPassword(Passwords.hash("ClinicDemo2026!"));patients.save(p);}}
 if(appointments.count()==0){var ds=doctors.findAll();var ps=patients.findAll();for(int i=0;i<5;i++){Appointment a=new Appointment();a.setDoctor(ds.get(i%3));a.setPatient(ps.get(i));a.setAppointmentTime(LocalDate.now().plusDays(1).atTime(i<3?9:10,0));a.setStatus(0);appointments.save(a);}}
 };}
}
