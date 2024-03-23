package com.sales.BPS.msales.service;

// PpcService.java

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.entity.PpcPK;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msales.repository.PpcRepository;
import jakarta.transaction.Transactional;
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
    public void deletePpcByProCode(Integer proCode) {
        // 상품 코드로 상품을 찾아서 삭제
        Ppc ppc = ppcRepository.findByProCode(proCode);
        if (ppc != null) {
            ppcRepository.delete(ppc);
        } else {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: " + proCode);
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
    @Transactional
    public Ppc updatePpc(Ppc existingPpc) {
        return ppcRepository.save(existingPpc); // JPA의 save 메소드는 주어진 엔터티가 이미 존재하면 업데이트를 수행합니다.
    }
}
