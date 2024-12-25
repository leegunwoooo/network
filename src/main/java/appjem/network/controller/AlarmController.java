package appjem.network.controller;

import appjem.network.domain.UpdateAlarmRequest;
import appjem.network.domain.entity.Alarm;
import appjem.network.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Alarm")
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/alarms")
    public String alarmPage(Model model) {
        List<Alarm> alarms = alarmService.findAll();
        model.addAttribute("alarms", alarms);
        return "alarm";
    }

    @GetMapping("/add")
    public String addAlarmForm(Model model) {
        return "addAlarm";
    }

    @PostMapping("/save")
    public String save(@RequestParam String name,
                       @RequestParam String description,
                       @RequestParam(required = false) Boolean active,
                       @RequestParam(required = false) String alarmTime) {
        Alarm alarm = new Alarm();
        alarm.setName(name);
        alarm.setDescription(description);
        alarm.setActive(active != null && active);

        if (alarmTime != null && !alarmTime.isEmpty()) {
            alarm.setAlarmTime(LocalTime.parse(alarmTime));
        }

        alarmService.save(alarm);

        return "redirect:/Alarm/alarms";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        Alarm alarm = alarmService.findById(id);
        model.addAttribute("alarm", alarm);
        return "alarmDetail";
    }

    @PostMapping("/{id}/activate")
    public String changeActive(@PathVariable Long id) {
        log.info("Change active for Alarm ID: {}", id);
        alarmService.changeActive(id);
        return "redirect:/Alarm/alarms";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        try {
            alarmService.delete(id);
        } catch (IllegalArgumentException e) {
            return "redirect:/Alarm/alarms?error=알람을+찾을+수+없습니다.";
        }
        return "redirect:/Alarm/alarms";
    }

    @PutMapping("/{id}")
    public String update(@RequestBody UpdateAlarmRequest request) {
        alarmService.update(request);
        return "redirect:/Alarm/alarms";
    }
}
