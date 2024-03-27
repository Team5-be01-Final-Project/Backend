package com.sales.BPS.mproduct.repository;

import com.sales.BPS.mproduct.entity.Voucher;
import com.sales.BPS.mproduct.entity.VoucherPK;
import com.sales.BPS.msales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, VoucherPK> {

    List<Voucher> findAllByProductProCode(Integer proCode);

    List<Voucher> findByClient(Client client);

}
