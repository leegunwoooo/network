package appjem.network.service;

import appjem.network.domain.UpdateAlarmRequest;
import appjem.network.domain.entity.Alarm;
import appjem.network.domain.repository.AlarmRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final RestTemplate restTemplate;

    public List<Alarm> findAll() {
        return alarmRepository.findAll();
    }

    public Alarm findById(Long id) {
        return alarmRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("알람을 찾을 수 없습니다."));
    }

    public Alarm save(Alarm alarm) {
        return alarmRepository.save(alarm);
    }

    public void delete(Long id) {
        Alarm alarm = findById(id);
        alarmRepository.delete(alarm);
    }

    public void changeActive(Long id) {
        Alarm alarm = findById(id);
        alarm.setActive(!alarm.getActive());
        alarmRepository.save(alarm);
    }

    public Alarm update(UpdateAlarmRequest request) {
        Alarm alarm = findById(request.getId());
        alarm.setName(request.getName());
        alarm.setDescription(request.getDescription());
        alarm.setActive(request.getActive());
        return alarmRepository.save(alarm);
    }

}
