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
        //clientRepository.save 메서드가 호출될 때 어떤 Client 객체를 인자로 받아도 client 객체를 반환하도록 설정
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        //empCode를 null로 설정하여 empCode가 null인 경우의 동작을 검증
        Client savedClient = clientService.saveClient(client, null);

        // 반환된 savedClient 객체가 null이 아님을 확인
        assertNotNull(savedClient);

        // 입력한 client 객체와 반환된 savedClient 객체의 clientCode가 같은지 확인
        assertEquals(client.getClientCode(), savedClient.getClientCode());

        // 입력한 client 객체와 반환된 savedClient 객체의 clientName이 같은지 확인
        assertEquals(client.getClientName(), savedClient.getClientName());

        // 반환된 savedClient 객체의 employee가 null임을 확인
        assertNull(savedClient.getEmployee());

        // clientRepository.save 메서드가 인자로 어떤 Client 객체를 받아서 호출되었는지 확인
        verify(clientRepository).save(any(Client.class)); //any(Client.class)는 어떤 Client 객체가 인자로 전달되어도 상관 없음
    }

    @Test
    @DisplayName("클라이언트 등록 - empCode가 null이 아닌 경우")
    void testSaveClient_WithNonNullEmpCode() throws Exception {
        //employeeRepository.findById() 메서드가 호출될 때 어떤 int 값이든 받으면 employee 객체를 포함한 Optional을 반환하도록 설정
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        //clientRepository.save() 메서드가 호출될 때 어떤 Client 객체든 받으면 client 객체를 반환하도록 설정
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // clientService.saveClient() 메서드를 호출하여 client 객체와 1 값을 인자로 전달하고 반환되는 값을 savedClient 변수에 저장
        Client savedClient = clientService.saveClient(client, 1);

        //savedClient가 null이 아님을 검증
        assertNotNull(savedClient);

        //client 객체와 savedClient 객체의 clientCode 값이 같은지를 검증
        assertEquals(client.getClientCode(), savedClient.getClientCode());

        //client 객체와 savedClient 객체의 clientName 값이 같은지를 검증
        assertEquals(client.getClientName(), savedClient.getClientName());

        // employee 객체와 savedClient 객체의 employee 값이 같은지를 검증합니다.
        // empCode가 null이 아닌 경우에 employeeRepository.findById()로부터 반환된 값을 확인하는 것
        assertEquals(employee, savedClient.getEmployee());

        //employeeRepository.findById() 메서드가 anyInt() 값을 받아 호출되었는지를 검증
        verify(employeeRepository).findById(anyInt());

        //clientRepository.save() 메서드가 any(Client.class) 값을 받아 호출되었는지를 검증
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("클라이언트 등록 - 클라이언트 코드가 이미 존재하는 경우")
    void testSaveClient_WithExistingClientCode() {
        // 클라이언트 코드가 이미 존재하는지를 확인하기 위해 clientRepository.existsByClientCode() 메서드의 반환 값을 조작
        // 여기서는 해당 클라이언트 코드가 이미 존재한다고 설정
        when(clientRepository.existsByClientCode(client.getClientCode())).thenReturn(true);

        // 예외를 검증 (예상되는 예외는 Exception.class)
        // clientService.saveClient()를 호출할 때 클라이언트 코드가 이미 존재하는 경우 예외가 발생하는지 확인
        Exception exception = assertThrows(Exception.class, () -> clientService.saveClient(client, null));

        // 예외 메시지를 확인 (예외 메시지가 기대한 메시지와 일치하는지를 검증)
        assertEquals("해당 클라이언트 코드는 이미 등록되었습니다.", exception.getMessage());

        // 특정 메서드가 특정 조건에서 호출되지 않았음을 검증
        // clientRepository.save() 메서드가 호출되지 않아야 함을 확인
        verify(clientRepository, never()).save(any(Client.class));
    }

    @Test
    @DisplayName("getClientsWithSpecificFields 메서드 테스트")
    void testGetClientsWithSpecificFields() {
        // ClientRepository.ClientProjection 인터페이스를 구현한 가짜(mock) 객체를 생성하여 리스트에 담기
        List<ClientRepository.ClientProjection> projections = Arrays.asList(mock(ClientRepository.ClientProjection.class));

        // findClientsWithSpecificFields() 메서드가 호출될 때, 앞서 생성한 가짜 프로젝션 리스트를 반환하도록 설정
        when(clientRepository.findClientsWithSpecificFields()).thenReturn(projections);

        // getClientsWithSpecificFields() 메서드를 호출하여 실제 결과 가져오기
        // 이때 목(mock)으로 설정한 결과가 반환
        List<ClientRepository.ClientProjection> result = clientService.getClientsWithSpecificFields();

        // rojections 리스트와 result 리스트가 동일한지를 확인하여 테스트를 수행
        assertEquals(projections, result);

        // clientRepository.findClientsWithSpecificFields() 메서드가 한 번 호출되었는지를 검증
        verify(clientRepository).findClientsWithSpecificFields();
    }

    @Test
    @DisplayName("findClientByClientCode 메서드 테스트")
    void testFindClientByClientCode() {
        //clientRepository에서 "C123" 클라이언트 코드를 가진 클라이언트를 찾는 findById() 메서드가 호출될 때,
        // 클라이언트 객체 client를 포함한 Optional 객체를 반환하도록 설정
        when(clientRepository.findById("C123")).thenReturn(Optional.of(client));

        // findClientByClientCode() 메서드를 호출하여 실제 결과 가져오기
        Client result = clientService.findClientByClientCode("C123");

        //client 객체와 result 객체가 동일한지를 확인하여 테스트를 수행
        assertEquals(client, result);

        //해당 메서드가 예상대로 호출되었는지를 확인하는 것, 목(mock) 객체의 메서드 호출 상태를 검증
        verify(clientRepository).findById("C123");
    }

    @Test
    @DisplayName("deleteClientByClientCode 메서드 테스트 - 존재하는 클라이언트 코드")
    void testDeleteClientByClientCode_ExistingClientCode() throws Exception {
        //C123" 클라이언트 코드가 존재하는지 확인하는 메서드 호출에 대한 목(mock) 객체의 동작을 설정
        //DB에 C123이 있다고 설정
        when(clientRepository.existsById("C123")).thenReturn(true);

        //C123 클라이언트 코드를 가진 클라이언트를 삭제하는 deleteClientByClientCode() 메서드를 호출
        clientService.deleteClientByClientCode("C123");

        //clientRepository.existsById("C123") 메서드가 한 번 호출되었는지를 확인
        verify(clientRepository).existsById("C123");

        //C123" 클라이언트 코드를 가진 클라이언트가 데이터베이스에 존재하므로, 해당 클라이언트가 삭제되었음을 검증
        verify(clientRepository).deleteById("C123");
    }

    @Test
    @DisplayName("deleteClientByClientCode 메서드 테스트 - 존재하지 않는 클라이언트 코드")
    void testDeleteClientByClientCode_NonExistingClientCode() {
        //  "C123" 클라이언트 코드가 데이터베이스에 존재하지 않는다고 설정
        when(clientRepository.existsById("C123")).thenReturn(false);

        // C123" 클라이언트 코드를 가진 클라이언트를 삭제하는 deleteClientByClientCode() 메서드 호출 시
        // 예외가 발생하는지 확인
        Exception exception = assertThrows(Exception.class, () -> clientService.deleteClientByClientCode("C123"));

        // 발생한 예외 메시지가 "Client with code C123 does not exist."인지 확인
        assertEquals("Client with code C123 does not exist.", exception.getMessage());

        // 메서드가 예상대로 호출되었는지를 검증
        verify(clientRepository).existsById("C123");

        //클라이언트 코드가 존재하지 않으므로 해당 클라이언트를 삭제하는 메서드가 호출되지 않아야 함을 검증
        verify(clientRepository, never()).deleteById(anyString());
    }

    @Test
    @DisplayName("saveOrUpdateClient 메서드 테스트")
    void testSaveOrUpdateClient() {
        // employeeRepository.findById(1) 메서드가 호출되었을 때, Optional.of(employee)를 반환하도록 목 설정
        //  clientDTO 객체에서 얻은 empCode를 기반으로 직원을 검색하는 과정을 모의
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        //어떤 Client 객체를 인자로 받아도 client 객체를 반환하도록 목 설정
        // 이는 클라이언트가 저장되었을 때 해당 클라이언트를 반환하는 과정을 모의
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        //clientDTO를 사용하여 클라이언트를 저장 또는 업데이트하고, 결과로 반환된 클라이언트를 result 변수에 할당
        Client result = clientService.saveOrUpdateClient(clientDTO);

        // 예상한 클라이언트 객체 client와 실제로 반환된 클라이언트 객체 result를 비교하여 같은지 확인
        assertEquals(client, result);

        // 특정 empCode에 해당하는 직원을 찾기 위해 findById 메서드가 호출되는지를 확인
        verify(employeeRepository).findById(1);

        // 클라이언트가 저장되었는지를 확인하기 위해 save 메서드가 호출되는지를 확인
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("convertToDTO 메서드 테스트")
    void testConvertToDTO() {

        //clientService의 convertToDTO 메서드를 호출하여 주어진 클라이언트 객체 client를 DTO 객체로 변환하고,
        // 그 결과를 result 변수에 할당
        ClientDTO result = clientService.convertToDTO(client);

        // 예상한 클라이언트 DTO의 클라이언트 코드와 실제로 반환된 DTO 객체의 클라이언트 코드를 비교하여 같은지 확인
        assertEquals(clientDTO.getClientCode(), result.getClientCode());

        // 예상한 클라이언트 DTO의 클라이언트 이름과 실제로 반환된 DTO 객체의 클라이언트 이름을 비교하여 같은지 확인
        assertEquals(clientDTO.getClientName(), result.getClientName());

        // 예상한 클라이언트 DTO의 직원 코드와 실제로 반환된 DTO 객체의 직원 코드를 비교하여 같은지 확인
        assertEquals(clientDTO.getEmpCode(), result.getEmpCode());
    }

    @Test
    @DisplayName("findByClientCode 메서드 테스트- 존재하는 클라이언트 코드")
    void testFindByClientCode_ExistingClientCode() {
        //클라이언트 코드 "C123"에 해당하는 클라이언트가 존재하는 경우를 가정.
        //findById 메서드가 호출될 때 "C123" 클라이언트 코드에 해당하는 클라이언트를 반환하도록 설정
        when(clientRepository.findById("C123")).thenReturn(Optional.of(client));

        //clientService의 findByClientCode 메서드를 호출하여 클라이언트 코드 "C123"에 해당하는 클라이언트를 찾고,
        // 해당 결과를 result 변수에 할당
        ClientDTO result = clientService.findByClientCode("C123");

        //예상한 클라이언트 DTO의 클라이언트 코드와 result 객체의 클라이언트 코드를 비교하여 같은지 확인
        assertEquals(clientDTO.getClientCode(), result.getClientCode());

        // 예상한 클라이언트 DTO의 클라이언트 이름과 result 객체의 클라이언트 이름을 비교하여 같은지 확인
        assertEquals(clientDTO.getClientName(), result.getClientName());

        // 예상한 클라이언트 DTO의 직원 코드와 result 객체의 직원 코드를 비교하여 같은지 확인
        assertEquals(clientDTO.getEmpCode(), result.getEmpCode());

        // findById 메서드가 주어진 클라이언트 코드 "C123"로 한 번 호출되었는지를 검증
        verify(clientRepository).findById("C123");
    }

    @Test
    @DisplayName("findByClientCode 메서드 테스트 - 존재하지 않는 클라이언트 코드")
    void testFindByClientCode_NonExistingClientCode() {
        //클라이언트 코드 "C123"에 해당하는 클라이언트가 존재하지 않는 경우를 가정
        //findById 메서드가 호출될 때 "C123" 클라이언트 코드에 해당하는 클라이언트가 없음을 나타내는
        // 빈 Optional을 반환하도록 설정
        when(clientRepository.findById("C123")).thenReturn(Optional.empty());

        //clientService의 findByClientCode 메서드를 호출하여
        // 클라이언트 코드 "C123"에 해당하는 클라이언트를 찾고, 해당 결과를 result 변수에 할당
        ClientDTO result = clientService.findByClientCode("C123");

        //result 객체가 null인지 확인하여 클라이언트 코드 "C123"에 해당하는 클라이언트가 존재하지 않음을 검증
        assertNull(result);

        // findById 메서드가 주어진 클라이언트 코드 "C123"로 한 번 호출되었는지를 검증
        // 클라이언트가 존재하지 않는 경우에도 해당 클라이언트 코드로 조회가 시도되었는지를 확인
        verify(clientRepository).findById("C123");
    }
}