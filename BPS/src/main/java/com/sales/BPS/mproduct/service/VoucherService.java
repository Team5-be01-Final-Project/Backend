package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.VoucherApprovalDTO;
import com.sales.BPS.mproduct.dto.VoucherDTO;
import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.entity.Stock;
import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import com.sales.BPS.mproduct.repository.ApprovalCodeRepository;
import com.sales.BPS.mproduct.repository.ProductRepository;
import com.sales.BPS.mproduct.repository.StockRepository;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.BPS.msystem.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final ProductRepository productRepository;
    private final VoucherRepository voucherRepository;
    private final StockRepository stockRepository;
    private final ApprovalCodeRepository approvalCodeRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public VoucherService(ProductRepository productRepository, VoucherRepository voucherRepository, StockRepository stockRepository,
                          ApprovalCodeRepository approvalCodeRepository, EmployeeRepository employeeRepository) {
        this.productRepository = productRepository;
        this.voucherRepository = voucherRepository;
        this.stockRepository = stockRepository;
        this.approvalCodeRepository = approvalCodeRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<VoucherDTO> getAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public VoucherDTO mapToDTO(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();

        if (voucher != null) {
            dto.setVoucId(voucher.getVoucId());
            dto.setProName(voucher.getProduct() != null ? voucher.getProduct().getProName() : null); // 제품 엔티티에서 제품명 추출, 널 체크 추가
            dto.setVoucDate(voucher.getVoucDate());
            dto.setVoucSale(Math.toIntExact(voucher.getVoucSale()));
            dto.setVoucAmount(voucher.getVoucAmount());
            dto.setVoucSales(Long.valueOf(voucher.getVoucSales()));
            dto.setVoucApproval(voucher.getVoucApproval());
            dto.setClientName(voucher.getClient() != null ? voucher.getClient().getClientName() : null); // 클라이언트 엔티티에서 거래처명 추출, 널 체크 추가
            dto.setEmpName(voucher.getEmployee() != null ? voucher.getEmployee().getEmpName() : null); // 담당자 엔티티에서 이름 추출, 널 체크 추가
            dto.setSignerName(voucher.getEmployeeSign() != null ? voucher.getEmployeeSign().getEmpName() : null); // 결재자 엔티티에서 이름 추출, 널 체크 추가
            dto.setVoucNote(voucher.getVoucNote());
            dto.setApprovalStatus(voucher.getApprovalCode() != null ? voucher.getApprovalCode().getAppName() : null); // 승인 코드 엔티티에서 승인 상태 이름 추출, 널 체크 추가
            // 버튼 표시 여부 설정
            dto.setShowApproveButton(voucher.getApprovalCode() != null && voucher.getApprovalCode().getAppCode().equals("A00"));
            dto.setShowRejectButton(voucher.getApprovalCode() != null && voucher.getApprovalCode().getAppCode().equals("A00"));
        }

        return dto;
    }

    private VoucherDTO convertToDto(Voucher voucher) {
        VoucherDTO dto = new VoucherDTO();
        dto.setVoucId(voucher.getVoucId());
        dto.setEmpName(voucher.getEmployee() != null ? voucher.getEmployee().getEmpName() : null);
        dto.setClientName(voucher.getClient() != null ? voucher.getClient().getClientName() : null);
        dto.setProName(voucher.getProduct() != null ? voucher.getProduct().getProName() : null);
        dto.setVoucDate(voucher.getVoucDate() != null ? LocalDate.parse(voucher.getVoucDate().toString()) : null); // LocalDate를 String으로 변환
        dto.setVoucSale(Math.toIntExact(voucher.getVoucSale()));
        dto.setVoucAmount(voucher.getVoucAmount());
        dto.setVoucSales(Long.valueOf(voucher.getVoucSales()));
        dto.setVoucApproval(voucher.getVoucApproval() != null ? LocalDate.parse(voucher.getVoucApproval().toString()) : null); // LocalDate를 String으로 변환
        dto.setSignerName(voucher.getEmployeeSign() != null ? voucher.getEmployeeSign().getEmpName() : null);
        dto.setVoucNote(voucher.getVoucNote());
        dto.setApprovalStatus(voucher.getApprovalCode() != null ? voucher.getApprovalCode().getAppName() : null);

        dto.setShowApproveButton(voucher.getApprovalCode() != null && voucher.getApprovalCode().getAppCode().equals("A00"));
        dto.setShowRejectButton(voucher.getApprovalCode() != null && voucher.getApprovalCode().getAppCode().equals("A00"));

        if (voucher.getEmployee() != null && voucher.getEmployee().getStorage() != null) {
            dto.setStorageCar(voucher.getEmployee().getStorage().getStorageCar());
        }
        return dto;
    }


    public List<VoucherDTO> findVouchersByVoucIdAsDto(Long voucId) {
        List<Voucher> vouchers = voucherRepository.findByVoucId(voucId);
        return vouchers.stream().map(this::convertToDto).collect(Collectors.toList());

    }

    // 매출액 계산 로직
    private void calculateVoucSales(Voucher voucher) {
        if (voucher.getVoucSale() != null && voucher.getVoucAmount() != null) {
            Long sales = (long) (voucher.getVoucSale() * voucher.getVoucAmount());
            voucher.setVoucSales(sales);
        }
    }

    public VoucherDTO approveVoucher(Long voucId, VoucherApprovalDTO request) {
        VoucherPK voucherPK = new VoucherPK();
        voucherPK.setVoucId(voucId);

        Product product = productRepository.findByProCode(request.getProCode());
        if (product == null) {
            throw new IllegalArgumentException("Product not found for proCode: " + request.getProCode());
        }
        voucherPK.setProduct(product);

        Voucher voucher = voucherRepository.findById(voucherPK)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found for voucId: " + voucId + " and proCode: " + request.getProCode()));

        voucher.setApprovalCode(approvalCodeRepository.findById("A01").orElse(null));
        voucher.setEmployeeSign(employeeRepository.findById(request.getEmpCodeSign()).orElse(null));
        voucher.setVoucApproval(LocalDate.now());
        voucherRepository.save(voucher);

        return convertToDto(voucher);
    }

    public VoucherDTO rejectVoucher(Long voucId, VoucherApprovalDTO request) {
        VoucherPK voucherPK = new VoucherPK();
        voucherPK.setVoucId(voucId);

        Product product = productRepository.findByProCode(request.getProCode());
        if (product == null) {
            throw new IllegalArgumentException("Product not found for proCode: " + request.getProCode());
        }
        voucherPK.setProduct(product);

        Voucher voucher = voucherRepository.findById(voucherPK)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found for voucId: " + voucId + " and proCode: " + request.getProCode()));

        if (!voucher.getProduct().getProCode().equals(request.getProCode())) {
            throw new IllegalArgumentException("Invalid product code");
        }

        voucher.setApprovalCode(approvalCodeRepository.findById("A02").orElse(null));
        voucher.setEmployeeSign(employeeRepository.findById(request.getEmpCodeSign()).orElse(null));
        voucher.setVoucApproval(LocalDate.now());
        voucherRepository.save(voucher);

        Optional<Stock> stockOptional = stockRepository.findById(request.getProCode());
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            int restoredStock = stock.getStoAmo() + voucher.getVoucAmount();
            stock.setStoAmo(restoredStock);
            stockRepository.save(stock);
        } else {
            throw new IllegalArgumentException("Stock not found for proCode: " + request.getProCode());
        }

        return convertToDto(voucher);
    }

}
