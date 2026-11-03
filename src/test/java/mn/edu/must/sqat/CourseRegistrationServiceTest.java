package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CourseRegistrationServiceTest {

    @Mock
    StudentRepository repository;

    @Mock
    EmailSender emailSender;

    CourseRegistrationService service;

    @BeforeEach
    void setUp() {
        service = new CourseRegistrationService(repository, emailSender);
    }

        @Test
    void activeStudent_registerSuccessfully_returnsOK() {

        // Arrange
        Student student = new Student(
                "S001",
                "student@must.edu.mn",
                true,
                List.of("CS101")
        );

        when(repository.findStudent("S001"))
                .thenReturn(student);

        when(repository.courseExists("CS102"))
                .thenReturn(true);

        when(repository.prerequisites("CS102"))
                .thenReturn(List.of("CS101"));

        // Act
        String result = service.register("S001", "CS102");

        // Assert
        assertEquals("OK", result);

        // Verify
        verify(repository).findStudent("S001");
        verify(repository).courseExists("CS102");
        verify(repository).prerequisites("CS102");
        verify(repository).saveRegistration("S001", "CS102");

        verify(emailSender).send(
                eq("student@must.edu.mn"),
                eq("Бүртгэл баталгаажлаа"),
                contains("CS102")
        );
    }
}