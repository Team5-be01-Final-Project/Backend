    package com.sales.BPS.msales.service;

    import com.sales.BPS.msales.entity.Client;
    import com.sales.BPS.msales.repository.ClientRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    public class ClientService {

        private final ClientRepository clientrepository;

        @Autowired
        public ClientService(ClientRepository clientrepository) {
            this.clientrepository = clientrepository;
        }

        public Client saveClient(Client client){
            return clientrepository.save(client);
        }

        public List<ClientRepository.ClientProjection> getClientsWithSpecificFields() {
            return clientrepository.findClientsWithSpecificFields();
        }

        public Client updateClient(String clientCode, Client updatedClient){
            Optional<Client> optionalClient = clientrepository.findById(clientCode);
            if(optionalClient.isPresent()){
                Client client = optionalClient.get();
                client.setClientName(updatedClient.getClientName());
                client.setClientClass(updatedClient.getClientClass());
                client.setClientBoss(updatedClient.getClientBoss());
                client.setClientWhere(updatedClient.getClientWhere());
                client.setClientPost(updatedClient.getClientPost());
                client.setClientEmp(updatedClient.getClientEmp());
                client.setClientEmpTel(updatedClient.getClientEmpTel());

                return clientrepository.save(client);


            }else {
                return null;
            }
        }


    }
