package com.sales.BPS.msales.service;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientrepository;

    @Autowired
    public ClientService(ClientRepository clientrepository) {
        this.clientrepository = clientrepository;
    }

    public Client saveClient(Client client) throws Exception{

        //중복검사로직
        if(clientrepository.existsByClientCode(client.getClientCode())){
            throw new Exception("이미 등록된 사업자 번호입니다.");
        }else
        {
            return clientrepository.save(client);
        }
    }

    public List<ClientRepository.ClientProjection> getClientsWithSpecificFields() {
        return clientrepository.findClientsWithSpecificFields();
    }
}
