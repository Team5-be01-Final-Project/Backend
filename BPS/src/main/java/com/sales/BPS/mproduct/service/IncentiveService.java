package com.sales.BPS.mproduct.service;

import com.sales.BPS.mproduct.dto.IncentiveDTO;
import com.sales.BPS.mproduct.repository.VoucherRepository;
import com.sales.BPS.msystem.entity.Employee;
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

    public List<IncentiveDTO> calculateIncentive(int year, int month) {

        //직원별, 월별, 매출 데이터 리스트
        List<Object[]> salesData = voucherRepository.findSalesByEmployeeAndYearAndMonth(year, month);

        //뷰에 보내줄 DTO를 저장하는 리스트
        List<IncentiveDTO> incentiveList = new ArrayList<>();

        int rank = 1;
        String[] teamlist_code = {"D01", "D02", "D03"};
        long[] teamvoucMonthSales = new long[3];//팀장 인센티브 계산을 위한 배열

        for (Object[] data : salesData) { // 매출 데이터 순회
            int empCode = ((Number) data[0]).intValue(); // 직원 코드 추출

            boolean empExits = employeeRepository.findById(empCode).isPresent();

            if (empExits) {
                String empName = employeeRepository.findById(empCode).get().getEmpName();
                String deptName = employeeRepository.findById(empCode).get().getDepartment().getDeptName();

                long voucMonthSales = ((Number) data[1]).longValue(); // 월 매출액
                int salesRank = rank;

                int incentive = (int) Math.round(voucMonthSales * 0.01);
                int addincentive = 0;

                if (rank == 1) {
                    addincentive = (int) (voucMonthSales * 0.005); // 1위
                } else if (rank == 2) {
                    addincentive = (int) (voucMonthSales * 0.003); // 2위
                } else if (rank == 3) {
                    addincentive = (int) (voucMonthSales * 0.001); // 3위
                }
                incentive += addincentive;

                IncentiveDTO incentiveDTO = new IncentiveDTO(empName, deptName, voucMonthSales, salesRank, incentive);
                System.out.println(incentiveDTO);
                incentiveList.add(incentiveDTO);

                rank++;

                switch (deptName.replace("\r", "")) {
                    case "영업 1팀":
                        teamvoucMonthSales[0] += voucMonthSales;
                        //System.out.println("teamvoucMonthSales[0]: "+teamvoucMonthSales[0]);
                        break;

                    case "영업 2팀":
                        teamvoucMonthSales[1] += voucMonthSales;
                        //System.out.println("teamvoucMonthSales[1]: "+teamvoucMonthSales[1]);
                        break;

                    case "영업 3팀":
                        teamvoucMonthSales[2] += voucMonthSales;
                        //System.out.println("teamvoucMonthSales[2]: "+teamvoucMonthSales[2]);
                        break;

                    default:
                        //System.out.println("일치하는 부서 없음!!!");
                        break;
                }
            } else {
                continue;
            }
        }//end for

        for (int i = 0; i < teamlist_code.length; i++) {
            List<Employee> managers = employeeRepository.findByDeptCodeAndPositionCode(teamlist_code[i], "P02");
            if (!managers.isEmpty()) {
                Employee manager = managers.get(0);
                String empName = manager.getEmpName();
                String deptName = manager.getDepartment().getDeptName();
                long voucMonthSales = teamvoucMonthSales[i];
                int salesRank = 0;
                int incentive = (int) Math.round(voucMonthSales * 0.01);
                IncentiveDTO incentiveDTO = new IncentiveDTO(empName, deptName, voucMonthSales, salesRank, incentive);

                System.out.println(incentiveDTO);

                if(voucMonthSales!=0){ //매출 금액이 0보다 클 때만 추가
                    incentiveList.add(incentiveDTO);
                }
            }
        }
        return incentiveList;
    }
}
