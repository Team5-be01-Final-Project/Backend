package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.TempLogDTO;
import com.sales.BPS.msystem.entity.Alarm;
import com.sales.BPS.msystem.entity.Storage;
import com.sales.BPS.msystem.entity.TempLog;
import com.sales.BPS.msystem.repository.AlarmRepository;
import com.sales.BPS.msystem.repository.StorageRepository;
import com.sales.BPS.msystem.repository.TempLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ColdchainService {

    private Map<String, Integer> latestTemperatures = new ConcurrentHashMap<>();

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private TempLogRepository tempLogRepository;

    @Autowired
    private AlarmRepository alarmRepository;

    private final EmailService emailService;

    @Autowired
    public ColdchainService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(fixedRate = 500000) // 5초마다 실행
    public void checkAndLogTemperature() {
        List<Storage> storages = storageRepository.findAll();
        Random random = new Random();

        storages.forEach(storage -> {
            int temperature = random.nextInt(11); // 0에서 10 사이의 랜덤 값 생성
            System.out.println("Generated random temperature for " + storage.getStorageCode() + ": " + temperature);
            latestTemperatures.put(storage.getStorageCode(), temperature);

            if (temperature < 2 || temperature > 8) { // 온도 범위를 벗어 난 경우
                TempLog tempLog = new TempLog();
                tempLog.setTempCode(storage.getStorageCode() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
                tempLog.setStorageCode(storage.getStorageCode());
                tempLog.setTempTemp(temperature);
                tempLog.setTempDate(LocalDateTime.now());
                tempLogRepository.save(tempLog);

                if(storage.getStorageSeg().equals("차량")){ //차량인경우
                    // 차량번호에 매칭된 유저가 있으면 해당 유저에게 이메일 전송
                    if (storage.getEmployee() != null) {
                        //해당 유저의 "AL01"의 알람의 옵션이 어떤지 검사함
                        Optional<Alarm> alarmOptional = alarmRepository.findByEmpCodeAndAlarmCode(storage.getEmployee().getEmpCode(), "AL01");
                        // 알람 설정이 존재하고, true인 경우, 이메일 전송
                        if (alarmOptional.isPresent() && alarmOptional.get().isAlarmSettings()){
                            String to = storage.getEmployee().getEmpEmail();
                            String subject = "차량 온도 이상 알림: " + storage.getStorageCode();
                            String text = String.format("The temperature of '%s' has been recorded at %d°C, Please check immediately.", storage.getStorageCar(), temperature);

                            //emailService.sendEmail(to, subject, text);

                            System.out.println("메일 전송 완료(차량): "+ storage.getStorageCode());
                        }
                        else {
                            System.out.println("담당자에게 알람 설정이 되어 있지 않아서 메일 전송을 실패했습니다. : "+ storage.getStorageCode());
                        }
                    }
                }
                else{//창고인경우
                    if (storage.getEmployee() != null) {
                        //해당 유저의 "AL01"의 알람의 옵션이 어떤지 검사함
                        Optional<Alarm> alarmOptional = alarmRepository.findByEmpCodeAndAlarmCode(storage.getEmployee().getEmpCode(), "AL01");
                        // 알람 설정이 존재하고, true인 경우, 이메일 전송
                        if (alarmOptional.isPresent() && alarmOptional.get().isAlarmSettings()){
                            String to = storage.getEmployee().getEmpEmail();
                            String subject = "창고 온도 이상 알림: " + storage.getStorageCode();
                            String text = String.format("The temperature of '%s' has been recorded at %d°C, Please check immediately.", storage.getStorageWare(), temperature);

                            //emailService.sendEmail(to, subject, text);

                            System.out.println("메일 전송 완료(창고): "+ storage.getStorageCode());
                        }
                        else {
                            System.out.println("매칭된 담당자에게 알람설정이 없기 때문에 메일 전송을 실패했습니다.: "+ storage.getStorageCode());
                        }
                    }
                }
            }
        });
    }

    /* // 모든 온도 로그를 조회하는 메소드
     public List<TempLogDTO> getAllTempLogsForFrontend() {
         List<TempLog> allLogs = tempLogRepository.findAll();

         return allLogs.stream()
                 .map(this::convertToDTO)
                 .collect(Collectors.toList());
     }

     private TempLogDTO convertToDTO(TempLog log) {
         TempLogDTO dto = new TempLogDTO();
         // TempLog 엔티티에서 필요한 정보를 추출하여 DTO에 설정
         dto.setStorageCode(log.getStorageCode()); // storageCode 설정 추가
         dto.setTempTemp(log.getTempTemp());
         dto.setTempDate(log.getTempDate().toLocalDate());
         if (log.getStorage() != null) {
             dto.setStorageSeg(log.getStorage().getStorageSeg());
             dto.setStorageCar(log.getStorage().getStorageCar());
             if (log.getStorage().getEmployee() != null) {
                 dto.setEmpName(log.getStorage().getEmployee().getEmpName());
             }
         }
         return dto;
     }*/
    public Map<String, Integer> getLatestTemperatures() {
        return new ConcurrentHashMap<>(latestTemperatures);
    }


}