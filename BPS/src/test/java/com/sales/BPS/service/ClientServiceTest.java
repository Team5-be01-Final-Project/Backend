package com.sales.BPS.service;

import com.sales.BPS.msales.dto.ClientDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.service.ClientService;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private Employee employee;
    private ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEmpCode(1);

        client = new Client();
        client.setClientCode("C123");
        client.setClientName("Test Client");
        client.setEmployee(employee);

        clientDTO = new ClientDTO();
        clientDTO.setClientCode("C123");
        clientDTO.setClientName("Test Client");
        clientDTO.setEmpCode(1);
    }

    @Test
    @DisplayName("클라이언트 등록 - empCode가 null인 경우")
    void testSaveClient_WithNullEmpCode() throws Exception {
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        //clientRepository.save 메서드가 호출될 때 어떤 Client 객체를 인자로 받아도 client 객체를 반환하도록 설정

        Client savedClient = clientService.saveClient(client, null);
        //empCode가 null인 경우의 동작을 검증

        assertNotNull(savedClient);
        assertEquals(client.getClientCode(), savedClient.getClientCode());
        assertEquals(client.getClientName(), savedClient.getClientName());
        assertNull(savedClient.getEmployee());

        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("클라이언트 등록 - empCode가 null이 아닌 경우")
    void testSaveClient_WithNonNullEmpCode() throws Exception {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.saveClient(client, 1);

        assertNotNull(savedClient);
        assertEquals(client.getClientCode(), savedClient.getClientCode());
        assertEquals(client.getClientName(), savedClient.getClientName());
        assertEquals(employee, savedClient.getEmployee());

        verify(employeeRepository).findById(anyInt());
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("클라이언트 등록 - 클라이언트 코드가 이미 존재하는 경우")
    void testSaveClient_WithExistingClientCode() {
        when(clientRepository.existsByClientCode(client.getClientCode())).thenReturn(true);

        Exception exception = assertThrows(Exception.class, () -> clientService.saveClient(client, null));
        assertEquals("해당 클라이언트 코드는 이미 등록되었습니다.", exception.getMessage());

        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("getClientsWithSpecificFields 메서드 테스트")
    void testGetClientsWithSpecificFields() {
        List<ClientRepository.ClientProjection> projections = Arrays.asList(mock(ClientRepository.ClientProjection.class));
        when(clientRepository.findClientsWithSpecificFields()).thenReturn(projections);

        List<ClientRepository.ClientProjection> result = clientService.getClientsWithSpecificFields();

        assertEquals(projections, result);
        verify(clientRepository).findClientsWithSpecificFields();
    }

    @Test
    @DisplayName("findClientByClientCode 메서드 테스트")
    void testFindClientByClientCode() {
        when(clientRepository.findById("C123")).thenReturn(Optional.of(client));

        Client result = clientService.findClientByClientCode("C123");

        assertEquals(client, result);
        verify(clientRepository).findById("C123");
    }

    @Test
    @DisplayName("deleteClientByClientCode 메서드 테스트 - 존재하는 클라이언트 코드")
    void testDeleteClientByClientCode_ExistingClientCode() throws Exception {
        when(clientRepository.existsById("C123")).thenReturn(true);

        clientService.deleteClientByClientCode("C123");

        verify(clientRepository).existsById("C123");
        verify(clientRepository).deleteById("C123");
    }

    @Test
    @DisplayName("deleteClientByClientCode 메서드 테스트 - 존재하지 않는 클라이언트 코드")
    void testDeleteClientByClientCode_NonExistingClientCode() {
        when(clientRepository.existsById("C123")).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> clientService.deleteClientByClientCode("C123"));
        assertEquals("Client with code C123 does not exist.", exception.getMessage());

        verify(clientRepository).existsById("C123");
        verify(clientRepository, never()).deleteById(anyString());
    }

    @Test
    @DisplayName("saveOrUpdateClient 메서드 테스트")
    void testSaveOrUpdateClient() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.saveOrUpdateClient(clientDTO);

        assertEquals(client, result);
        verify(employeeRepository).findById(1);
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("convertToDTO 메서드 테스트")
    void testConvertToDTO() {
        ClientDTO result = clientService.convertToDTO(client);

        assertEquals(clientDTO.getClientCode(), result.getClientCode());
        assertEquals(clientDTO.getClientName(), result.getClientName());
        assertEquals(clientDTO.getEmpCode(), result.getEmpCode());
    }

    @Test
    @DisplayName("findByClientCode 메서드 테스트- 존재하는 클라이언트 코드")
    void testFindByClientCode_ExistingClientCode() {
        when(clientRepository.findById("C123")).thenReturn(Optional.of(client));

        ClientDTO result = clientService.findByClientCode("C123");

        assertEquals(clientDTO.getClientCode(), result.getClientCode());
        assertEquals(clientDTO.getClientName(), result.getClientName());
        assertEquals(clientDTO.getEmpCode(), result.getEmpCode());
        verify(clientRepository).findById("C123");
    }

    @Test
    @DisplayName("findByClientCode 메서드 테스트 - 존재하지 않는 클라이언트 코드")
    void testFindByClientCode_NonExistingClientCode() {
        when(clientRepository.findById("C123")).thenReturn(Optional.empty());

        ClientDTO result = clientService.findByClientCode("C123");

        assertNull(result);
        verify(clientRepository).findById("C123");
    }
}