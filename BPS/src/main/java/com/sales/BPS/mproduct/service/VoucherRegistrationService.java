package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.VoucherItemDto;
import com.sales.BPS.mproduct.dto.VoucherRegistrationDTO;
import com.sales.BPS.mproduct.entity.Product;
import com.sales.BPS.mproduct.entity.Stock;
import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.repository.ProductRepository;
import com.sales.BPS.mproduct.repository.StockRepository;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msales.repository.ClientRepository;
import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.Employee;
import com.sales.BPS.msystem.repository.AuthorityRepository;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherRegistrationService {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Long generateVoucherId() {
        LocalDateTime now = LocalDateTime.now();
        String datePrefix = now.format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 시퀀스로부터 다음 값을 가져옵니다. 시퀀스가 월별로 초기화되어야 하므로,
        // 시퀀스 값의 최대값(999)에 도달하면, 이 로직은 자동으로 시퀀스 값을 1로 재설정합니다.
        Query query = entityManager.createNativeQuery("SELECT NEXTVAL('voucher_seq')");
        Long sequenceValue = ((Number) query.getSingleResult()).longValue();

        // 시퀀스 값이 최대값을 초과하는 경우 시퀀스를 초기화합니다.
        if (sequenceValue > 999) {
            query = entityManager.createNativeQuery("ALTER SEQUENCE voucher_seq RESTART WITH 1");
            query.executeUpdate();
            sequenceValue = 1L; // 시퀀스를 1로 재설정
        }

        // 생성된 전표 ID를 반환합니다.
        return Long.parseLong(datePrefix + String.format("%03d", sequenceValue));
    }

    @Transactional
    public Long createVoucher(VoucherRegistrationDTO registrationDTO) {
        Employee employee = employeeRepository.findById(registrationDTO.getEmpCode())
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        Client client = clientRepository.findByClientCode(registrationDTO.getClientCode())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        registrationDTO.setStorageCar(employee.getStorage().getStorageCar());
        Voucher voucher = new Voucher();
        voucher.setVoucId(generateVoucherId());
        voucher.setVoucDate(LocalDate.now());
        voucher.setEmployee(employee);
        voucher.setClient(client);

        Long totalSales = 0L;
        List<VoucherItemDto> items = registrationDTO.getItems();
        for (VoucherItemDto itemDto : items) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            Stock stock = stockRepository.findByProduct(product)
                    .orElseThrow(() -> new IllegalArgumentException("Stock not found for product"));
            if (stock.getStoAmo() < itemDto.getVoucAmount()) {
                throw new IllegalArgumentException("Insufficient stock");
            }
            stock.setStoAmo(stock.getStoAmo() - itemDto.getVoucAmount());
            stockRepository.save(stock);

            totalSales += itemDto.getVoucherSales();
        }

        voucher.setVoucSales(totalSales);
        voucher = voucherRepository.save(voucher);
        return voucher.getVoucId();
    }

    public List<Employee> findSignersWithAuthority() {
        return authorityRepository.findEmployeesWithAuthCode("AUTH003")
                .stream()
                .map(Authority::getEmployee)
                .collect(Collectors.toList());
    }
}