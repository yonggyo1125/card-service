package org.koreait.train.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.koreait.train.entities.QTrainData;
import org.koreait.train.entities.TrainData;
import org.koreait.train.repositories.TrainDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Lazy
@Service
@RequiredArgsConstructor
public class TrainService {

    @Value("${python.path}")
    private String runPath;

    @Value("${python.script}")
    private String scriptPath;

    private final TrainDataRepository repository;

    /**
     * 훈련 데이터 조회
     *
     * @param isAll
     * @return
     */
    public List<TrainData> getList(boolean isAll) {

        if (isAll) {
            return repository.findAll();
        } else {
            QTrainData trainData = QTrainData.trainData;
            return (List<TrainData>) repository.findAll(trainData.done.eq(false));
        }
    }

    // 매일 자정에 훈련 진행
    @Scheduled(cron="0 0 0 * * *")
    public void train() {
        try {
            log.info("훈련 시작");
            ProcessBuilder builder = new ProcessBuilder(runPath, scriptPath + "partial.py");
            Process process = builder.start();
            int code = process.waitFor();
            log.info("훈련 완료: {}", code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
