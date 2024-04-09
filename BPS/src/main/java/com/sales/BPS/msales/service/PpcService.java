package com.sales.BPS.msales.service;// PpcService.java

import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.repository.StockRepository;
import com.sales.BPS.mproduct.service.StockService;
import com.sales.BPS.msales.dto.PpcDTO;
import com.sales.BPS.msales.dto.PpcVoucherDTO;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.repository.PpcRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PpcService {

    private final PpcRepository ppcRepository;
    private final StockService stockService; // 가정
    private final ClientService clientService;

    @Autowired
    public PpcService(PpcRepository ppcRepository, StockService stockService, ClientService clientService) {
        this.ppcRepository = ppcRepository;
        this.stockService = stockService;
        this.clientService = clientService;
    }

    // PpcService 클래스 내에 추가
    public List<PpcDTO> getAllPpcs() {
        List<Ppc> allPpcs = ppcRepository.findAll(); // JpaRepository의 findAll 메서드를 사용
        List<PpcDTO> allPpcDTOs = allPpcs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return allPpcDTOs;
    }



    @Transactional
    public void updatePpc(Integer proCode, PpcDTO ppcDTO) {
        // 상품 코드로 Ppc 엔티티를 조회합니다.
        List<Ppc> ppcs = ppcRepository.findByProCode(proCode);
        for (Ppc existingPpc : ppcs) {
            // 클라이언트 정보 업데이트
            Client client = clientService.findClientByClientCode(ppcDTO.getClientCode());
            if (client == null) {
                // 클라이언트가 존재하지 않으면 새로운 클라이언트를 생성합니다.
                client = new Client();
                client.setClientCode(ppcDTO.getClientCode());
                // 필요에 따라 클라이언트의 다른 속성을 설정할 수 있습니다.
            }
            existingPpc.setClient(client);

            // 상품 정보 업데이트
            // 필요에 따라 다른 상품 정보도 업데이트할 수 있습니다.
            existingPpc.getProduct().setProName(ppcDTO.getProName());
            existingPpc.getProduct().setProSeg(ppcDTO.getProSeg());
            existingPpc.getProduct().setProCat(ppcDTO.getProCat());
            existingPpc.getProduct().setProAtc(ppcDTO.getProAtc());

            // 판매가 업데이트
            existingPpc.setPpcSale(ppcDTO.getPpcSale());

            // 엔티티를 저장하여 업데이트를 완료합니다.
            ppcRepository.save(existingPpc);
        }
    }

    public boolean isExistingSale(String clientCode, Integer proCode) {
        return ppcRepository.existsByClientCodeAndProCode(clientCode, proCode);
    }

    public PpcDTO convertToDTO(Ppc ppc) {
        PpcDTO dto = new PpcDTO();
        dto.setClientCode(ppc.getClientCode());
        dto.setClientName(ppc.getClient().getClientName());
        dto.setProAtc(ppc.getProduct().getProAtc());
        dto.setProCode(ppc.getProduct().getProCode());
        dto.setProName(ppc.getProduct().getProName());
        dto.setProSeg(ppc.getProduct().getProSeg());
        dto.setProCat(ppc.getProduct().getProCat());
        dto.setPpcSale(ppc.getPpcSale());
        dto.setProUnit(ppc.getProduct().getProUnit()); // 상품의 단위 정보를 DTO에 추가
        return dto;
    }

    private Ppc convertToEntity(PpcDTO dto, Client client) {
        Ppc ppc = new Ppc();
        ppc.setClientCode(dto.getClientCode());
        ppc.setClient(client);
        ppc.setProCode(dto.getProCode());
        // Product 엔터티 설정을 위해 Product 정보를 조회하거나 다른 방법을 사용해야 할 수도 있습니다.
        // 예제에서는 Product 엔터티의 proCode만 설정하였습니다.
        ppc.setProduct(new Product(dto.getProCode()));
        ppc.setPpcSale(dto.getPpcSale());
        return ppc;
    }


    @Transactional
    public void deletePpcByProCode(Integer proCode) {
        List<Ppc> ppcs = ppcRepository.findByProCode(proCode);
        ppcs.forEach(ppcRepository::delete);
    }

    public List<PpcDTO> getPpcs(String clientCode) {
        // 거래처 코드에 해당하는 모든 Ppc 엔터티를 데이터베이스에서 조회
        List<Ppc> ppcs = ppcRepository.findByClientCode(clientCode);

        // 조회된 Ppc 엔터티 리스트를 PpcDTO 리스트로 변환
        List<PpcDTO> ppcDTOs = ppcs.stream()
                .map(this::convertToDTO) // 각 Ppc 엔터티를 PpcDTO로 변환하는 메서드 참조
                .collect(Collectors.toList()); // 결과를 리스트로 수집

        return ppcDTOs; // 변환된 PpcDTO 리스트를 반환
    }
    @Transactional
    public void registerPpc(PpcDTO ppcDTO) {
        Client client = clientService.findClientByClientCode(ppcDTO.getClientCode());
        if (client == null) {
            // 클라이언트가 존재하지 않으면 새로운 클라이언트를 생성합니다.
            client = new Client();
            client.setClientCode(ppcDTO.getClientCode());
            // 필요에 따라 클라이언트의 다른 속성을 설정할 수 있습니다.
        }

        Ppc ppc = convertToEntity(ppcDTO, client);
        ppcRepository.save(ppc);
    }


    public List<PpcVoucherDTO> findPpcByClient(String clientCode){
        List<Ppc> ppcs = ppcRepository.findByClientCode(clientCode);
        List<PpcVoucherDTO> ppcVoucherDTOS = new ArrayList<>();


        for(Ppc ppc : ppcs){
            Integer proCode = ppc.getProCode();
            String proName = ppc.getProduct().getProName();
            Integer ppcSale = ppc.getPpcSale();
            Integer ppcStock = stockService.getStockAmountByProductCode(proCode);
            PpcVoucherDTO ppcVoucherDTO = new PpcVoucherDTO(proCode, proName, ppcSale, ppcStock);
            ppcVoucherDTOS.add(ppcVoucherDTO);
        }
        return ppcVoucherDTOS;
    }



}
