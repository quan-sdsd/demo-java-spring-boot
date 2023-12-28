package fpt.project.datn.event.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsApplicationEvent extends ApplicationEvent {

    private final Map<String, Object> params;
    public AbsApplicationEvent() {
        super(new HashMap<>());
        params = (HashMap<String, Object>) super.source;
    }

    public void trigger(ApplicationEventPublisher publisher) {
        publisher.publishEvent(this);
    }

    public AbsApplicationEvent addParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public Object getParam(String key) {
        return this.params.get(key);
    }

    public boolean hasParams() {
        return !this.params.isEmpty();
    }

    public abstract void doProcess();

}
