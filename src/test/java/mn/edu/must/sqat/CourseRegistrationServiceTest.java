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
                List.of("CS101"));

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
                contains("CS102"));
    }

    @Test
    void nonExistingStudent_register_returnsErrorNoStudent() {

        // Arrange
        when(repository.findStudent("S999"))
                .thenReturn(null);

        // Act
        String result = service.register("S999", "CS102");

        // Assert
        assertEquals("ERROR_NO_STUDENT", result);

        // Verify
        verify(repository).findStudent("S999");

        verify(repository, never())
                .saveRegistration(anyString(), anyString());

        verify(emailSender, never())
                .send(anyString(), anyString(), anyString());
    }

    @Test
    void inactiveStudent_register_returnsErrorInactiveStudent() {

        // Arrange
        Student student = new Student(
                "S002",
                "inactive@must.edu.mn",
                false,
                List.of());

        when(repository.findStudent("S002"))
                .thenReturn(student);

        // Act
        String result = service.register("S002", "CS102");

        // Assert
        assertEquals("ERROR_INACTIVE_STUDENT", result);

        // Verify
        verify(repository).findStudent("S002");

        verifyNoInteractions(emailSender);
    }

    @Test
    void missingPrerequisites_register_returnsErrorPrerequisites() {

        // Arrange
        Student student = new Student(
                "S003",
                "student3@must.edu.mn",
                true,
                List.of());

        when(repository.findStudent("S003"))
                .thenReturn(student);

        when(repository.courseExists("CS102"))
                .thenReturn(true);

        when(repository.prerequisites("CS102"))
                .thenReturn(List.of("CS101"));

        // Act
        String result = service.register("S003", "CS102");

        // Assert
        assertEquals("ERROR_PREREQUISITES", result);

        // Verify
        verify(repository).findStudent("S003");
        verify(repository).courseExists("CS102");
        verify(repository).prerequisites("CS102");

        verify(repository, never())
                .saveRegistration(anyString(), anyString());

        verify(emailSender, never())
                .send(anyString(), anyString(), anyString());
    }
}