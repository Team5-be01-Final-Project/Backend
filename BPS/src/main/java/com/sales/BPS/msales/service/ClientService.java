package com.sales.BPS.msales.service;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientrepository;
    private final EmployeeRepository employeeRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository, EmployeeRepository employeeRepository) {
        this.clientrepository = clientRepository;
        this.employeeRepository = employeeRepository;
    }

    public Client saveClient(Client client, Integer empCode) throws Exception {
        if (clientrepository.existsByClientCode(client.getClientCode())) {
            throw new Exception("해당 클라이언트 코드는 이미 등록되었습니다.");
        }

        // empCode를 사용하여 Employee 엔티티 연결
        if (empCode != null) {
            Employee employee = employeeRepository.findById(empCode)
                    .orElseThrow(() -> new RuntimeException("해당 empCode를 가진 직원을 찾을 수 없습니다: " + empCode));
            client.setEmployee(employee);
        }

        return clientrepository.save(client);
    }


    public List<ClientRepository.ClientProjection> getClientsWithSpecificFields() {
        return clientrepository.findClientsWithSpecificFields();
    }

}
