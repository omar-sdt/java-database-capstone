package com.project.back_end;
import com.project.back_end.services.*;
import com.project.back_end.models.*;
import com.project.back_end.repo.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class BackEndApplicationTests {
 @Test void passwordsAreHashed(){String h=Passwords.hash("ClinicDemo2026!");assertNotEquals("ClinicDemo2026!",h);assertTrue(Passwords.matches("ClinicDemo2026!",h));assertFalse(Passwords.matches("wrong",h));}
 @Test void tokensRejectTamperingAndUnknownIdentity(){var a=mock(AdminRepository.class);var d=mock(DoctorRepository.class);var p=mock(PatientRepository.class);var t=new TokenService(a,d,p);ReflectionTestUtils.setField(t,"secret","unit-test-signing-secret-at-least-32-bytes");var patient=new Patient();when(p.findByEmail("demo@example.com")).thenReturn(patient);String jwt=t.generateToken("demo@example.com");assertEquals("demo@example.com",t.extractEmail(jwt));assertTrue(t.validateToken(jwt,"patient"));assertFalse(t.validateToken(jwt,"doctor"));assertFalse(t.validateToken(jwt.substring(0,jwt.length()-8)+"tampered","patient"));}
 @Test void availabilityExcludesBookedSlotOnlyOnRequestedDay(){var dr=mock(DoctorRepository.class);var ar=mock(AppointmentRepository.class);var t=mock(TokenService.class);var d=new Doctor();d.setAvailableTimes(List.of("09:00-10:00","10:00-11:00"));when(dr.findById(1L)).thenReturn(Optional.of(d));LocalDate date=LocalDate.now().plusDays(1);var a=new Appointment();a.setAppointmentTime(date.atTime(9,0));when(ar.findByDoctorIdAndAppointmentTimeBetween(eq(1L),eq(date.atStartOfDay()),any())).thenReturn(List.of(a));assertEquals(List.of("10:00-11:00"),new DoctorService(dr,ar,t).getDoctorAvailability(1L,date));}
}
