package com.sales.BPS.msales.service;

// PpcService.java

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.entity.PpcPK;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.repository.PpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PpcService {

    private final PpcRepository ppcRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public PpcService(PpcRepository ppcRepository, ClientRepository clientRepository) {
        this.ppcRepository = ppcRepository;
        this.clientRepository = clientRepository;
    }

    public List<Ppc> getPpcs(String clientCode) {
        return ppcRepository.findByClientCode(clientCode);
    }

    public Ppc addPpc(String clientCode, Integer proCode, Integer ppcSale) {
        PpcPK ppcPK = new PpcPK();
        ppcPK.setClientCode(clientCode);
        ppcPK.setProCode(proCode);

        Ppc ppc = new Ppc();
        ppc.setPpcSale(ppcSale);

        return ppcRepository.save(ppc);
    }

    public Ppc updatePpc(String clientCode, Integer proCode, Integer ppcSale) {
        Optional<Ppc> optionalPpc = ppcRepository.findById(new PpcPK(clientCode, proCode));
        if (optionalPpc.isPresent()) {
            Ppc ppc = optionalPpc.get();
            ppc.setProCode(proCode);
            ppc.setPpcSale(ppcSale);
            return ppcRepository.save(ppc);
        }
        return null; // or throw an exception
    }

    public void deletePpc(String clientCode, Integer proCode) {
        ppcRepository.deleteById(new PpcPK(clientCode, proCode));
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

}
