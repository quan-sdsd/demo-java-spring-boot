package fpt.project.datn.event;

import fpt.project.datn.event.event.AbsApplicationEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventHandle {

    @Async
    @EventListener
    public void triggerEvent(AbsApplicationEvent event) {
        event.doProcess();
    }
}
