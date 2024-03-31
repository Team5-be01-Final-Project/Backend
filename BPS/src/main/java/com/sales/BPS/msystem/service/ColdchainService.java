package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.entity.Storage;
import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.StorageRepository;
import com.sales.BPS.msystem.repository.TempLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class ColdchainService {

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private TempLogRepository tempLogRepository;

    private final EmailService emailService;

    @Autowired
    public ColdchainService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 5000) // 5초마다 실행
    public void checkAndLogTemperature() {
        List<Storage> storages = storageRepository.findAll();
        Random random = new Random();

        storages.forEach(storage -> {
            int temperature = random.nextInt(11); // 0에서 10 사이의 랜덤 값 생성
           // System.out.println("Generated random temperature for " + storage.getStorageCode() + ": " + temperature);

            if (temperature < 2 || temperature > 8) { // 온도 범위를 벗어 난 경우
                TempLog tempLog = new TempLog();
                tempLog.setTempCode(storage.getStorageCode() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                tempLog.setStorageCode(storage.getStorageCode());
                tempLog.setTempTemp(temperature);
                tempLog.setTempDate(LocalDateTime.now());
                tempLogRepository.save(tempLog);

                // 차량번호에 매칭된 유저가 있으면 해당 유저에게 이메일 전송
                if (storage.getEmployee() != null && storage.getEmployee().getEmpEmail() != null) {
                    String to = storage.getEmployee().getEmpEmail();
                    String subject = "Temperature Alert for Storage " + storage.getStorageCode();
                    String text = String.format("The temperature of '%s' has been recorded at %d°C, Please check immediately.", storage.getStorageCode(), temperature);

                    emailService.sendEmail(to, subject, text);

                    System.out.println("메일 전송");
                }
            }
        });
    }


}