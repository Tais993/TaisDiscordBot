package utilities;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventWaiter extends ListenerAdapter {
    private static Map<Class<?>, Set<WaitingEvent>> waitingEvents = new HashMap<>();
    private static Map<Class<?>, Set<WaitingEvent>> buffer = new HashMap<>();

    public static <T extends Event> void waitForEvent(Class<T> classType, Predicate<T> condition, Consumer<T> action, long validUntil) {
        WaitingEvent<T> we = new WaitingEvent<>(condition, action, validUntil);
        Set<WaitingEvent> setOfEvents = waitingEvents.getOrDefault(classType, new HashSet<>());
        setOfEvents.add(we);
        buffer.put(classType, setOfEvents);
    }

    public static void checkValidationWaiters() {
        try {
            waitingEvents = waitingEvents.entrySet().stream().filter(entry -> {
                entry.setValue(entry.getValue().stream().filter(WaitingEvent::isValid).collect(Collectors.toSet()));
                return !entry.getValue().isEmpty();
            }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onGenericEvent(GenericEvent event) {
        Class c = event.getClass();
        System.out.println("EVENT" + event.getResponseNumber());

        while(c != null) {
            if(waitingEvents.containsKey(c)) {
                Set<WaitingEvent> events = waitingEvents.get(c);
                WaitingEvent[] toRemove = events.toArray(new WaitingEvent[events.size()]);

                events.removeAll(Stream.of(toRemove).filter(i -> i.attempt(event)).collect(Collectors.toSet()));
            }
            c = c.getSuperclass();
        }

        buffer.forEach((key, value) -> {
            value.addAll(waitingEvents.getOrDefault(key, new HashSet<>()));
            waitingEvents.put(key, value);
        });
        buffer.clear();
    }

    private static class WaitingEvent<T extends GenericEvent> {
        final Predicate<T> condition;
        final Consumer<T> action;
        long validUntil;

        WaitingEvent(Predicate<T> condition, Consumer<T> action, long validUntil) {
            this.condition = condition;
            this.action = action;
            this.validUntil = validUntil + System.currentTimeMillis();
        }

        boolean attempt(T event) {
            if(condition.test(event)) {
                action.accept(event);
                return true;
            }
            return false;
        }

        boolean isValid() {
            System.out.println(validUntil + "-" + System.currentTimeMillis());
            return validUntil >= System.currentTimeMillis();
        }
    }
}
