package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.IncentiveDTO;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncentiveService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<IncentiveDTO> calculateIncentive(int month){

        //직원별, 월별, 매출 데이터 리스트
        List<Object[]> salesData = voucherRepository.findSalesByEmployeeAndMonth(month);

        //뷰에 보내줄 DTO를 저장하는 리스트
        List<IncentiveDTO> incentiveList = new ArrayList<>();

        int rank = 1;

        for (Object[] data : salesData) { // 매출 데이터 순회
            int empCode = ((Number) data[0]).intValue(); // 직원 코드 추출

            boolean empExits = employeeRepository.findById(empCode).isPresent();

            if(empExits){
                String empName = employeeRepository.findById(empCode).get().getEmpName();
                String deptCode = employeeRepository.findById(empCode).get().getDepartment().getDeptName();

                long voucMonthSales = ((Number) data[1]).longValue(); // 월 매출액
                int salesRank = rank;

                int incentive = (int)Math.round(voucMonthSales*0.01);
                int addincentive = 0;

                if (rank == 1) {
                    addincentive = (int)(voucMonthSales*0.005); // 1위
                } else if (rank == 2) {
                    addincentive = (int)(voucMonthSales*0.003); // 2위
                } else if (rank == 3) {
                    addincentive = (int)(voucMonthSales*0.001); // 3위
                }
                incentive += addincentive;

                IncentiveDTO incentiveDTO = new IncentiveDTO(empName, deptCode, voucMonthSales, salesRank, incentive);
                incentiveList.add(incentiveDTO);

                rank ++;
            }
            else{
                continue;
            }
        }
        return incentiveList;
    }
}
