package com.sales.BPS.msales.service;

// PpcService.java

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.entity.PpcPK;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.repository.PpcRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PpcService {

    private final PpcRepository ppcRepository;
    private final ClientRepository clientRepository;
    private final ClientService clientService;


    @Autowired
    public PpcService(PpcRepository ppcRepository, ClientRepository clientRepository, ClientService clientService) {
        this.ppcRepository = ppcRepository;
        this.clientRepository = clientRepository;
        this.clientService = clientService; // ClientService 주입
    }

    public List<Ppc> getPpcs(String clientCode) {
        return ppcRepository.findByClientCode(clientCode);
    }

    public Ppc addOrUpdatePpc(String clientCode, Integer proCode, Integer ppcSale) {
        // Find client by clientCode
        Client client = clientService.findClientByClientCode(clientCode);
        if (client == null) {
            // Handle case when client does not exist
            throw new IllegalArgumentException("Client with code " + clientCode + " does not exist");
        }

        // Find PPC entry by clientCode and proCode
        Ppc ppc = ppcRepository.findByClientCodeAndProCode(clientCode, proCode).orElse(new Ppc());

        // Update PPC properties
        ppc.setClientCode(clientCode);
        ppc.setProCode(proCode);
        ppc.setPpcSale(ppcSale);

        // Save or update PPC entry
        return ppcRepository.save(ppc);
    }

    public Ppc addPpc(String clientCode, Integer proCode, Integer ppcSale) {
        PpcPK ppcPK = new PpcPK();
        ppcPK.setClientCode(clientCode);
        ppcPK.setProCode(proCode);

        Ppc ppc = new Ppc();
        ppc.setPpcSale(ppcSale);

        return ppcRepository.save(ppc);
    }

    // Ppc 엔터티를 업데이트하는 메소드
    @Transactional
    public Ppc updatePpc(Ppc existingPpc) {
        return ppcRepository.save(existingPpc); // JPA의 save 메소드는 주어진 엔터티가 이미 존재하면 업데이트를 수행합니다.
    }

    // proCode를 사용하여 Ppc 엔터티를 삭제하는 메소드
    public void deletePpcByProCode(Integer proCode) {
        List<Ppc> ppcs = ppcRepository.findByProCode(proCode);
        if (!ppcs.isEmpty()) {
            for (Ppc ppc : ppcs) {
                ppcRepository.delete(ppc);
            }
        } else {
            // 적절한 예외 처리 또는 로깅
            throw new EntityNotFoundException("해당 proCode를 가진 상품이 존재하지 않습니다: " + proCode);
        }
    }




    // 모든 거래처의 정보를 반환하는 메서드
    public List<Ppc> getAllPpcs() {
        List<Ppc> allPpcs = ppcRepository.findAll();
        for (Ppc ppc : allPpcs) {
            String clientCode = ppc.getClientCode();
            Optional<Client> optionalClient = clientRepository.findById(clientCode);
            optionalClient.ifPresent(client -> {
                ppc.setClientName(client.getClientName());
                ppc.setEmpName(client.getEmpName()); // empName 설정 추가
            });
        }
        return allPpcs;
    }

    public boolean isExistingSale(String clientCode, Integer proCode) {
        return ppcRepository.existsByClientCodeAndProCode(clientCode, proCode);
    }

    public Ppc findPpcByClientCodeAndProCode(String clientCode, Integer proCode) {
        Optional<Ppc> ppc = ppcRepository.findByClientCodeAndProCode(clientCode, proCode);
        return ppc.orElse(null); // Ppc 객체가 존재하면 반환하고, 그렇지 않으면 null을 반환
    }
    // Ppc 엔터티를 업데이트하는 메소드

}
