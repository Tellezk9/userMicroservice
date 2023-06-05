package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.auth.IAuthUser;
import com.pragma.powerup.usermicroservice.domain.geteway.IHttpAdapter;
import com.pragma.powerup.usermicroservice.domain.model.Employee;
import com.pragma.powerup.usermicroservice.domain.spi.IEmployeePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeUseCaseTest {

    @Mock
    private IEmployeePersistencePort employeePersistencePort;
    @Mock
    public IHttpAdapter httpAdapter;
    @Mock
    public IAuthUser authUser;
    @InjectMocks
    private EmployeeUseCase employeeUseCase;

    @Test
    void saveEmployee() {
        Employee employee = new Employee(null,"testName","testLastName",123,"+53322222222","2000/05/01","test@email.com","string",null);
        when(authUser.getId()).thenReturn(1L);
        when(authUser.getToken()).thenReturn("TokenTest");
        when(authUser.getRole()).thenReturn(Constants.OWNER_ROLE_NAME);

        doNothing().when(httpAdapter).checkRestaurant(1,"TokenTest");
        doNothing().when(employeePersistencePort).saveEmployee(employee);

        employeeUseCase.saveEmployee(employee);

        verify(employeePersistencePort, times(1)).saveEmployee(employee);
        verify(httpAdapter, times(1)).checkRestaurant(1,"TokenTest");
    }

    @Test
    void findByDni() {
        Employee employee = new Employee(null,"testName","testLastName",123,"+53322222222","2000/05/01","test@email.com","string",null);

        when(employeePersistencePort.findByDni(1)).thenReturn(employee);

        employeeUseCase.findByDni(1);

        verify(employeePersistencePort,times(1)).findByDni(1);
    }
}