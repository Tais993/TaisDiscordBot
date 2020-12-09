package utilities;

import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventWaiter extends ListenerAdapter {
    private static final HashMap<Class<?>, WaitingEvent> waitingEvents = new HashMap<>();

    public static <T extends Event> void waitForEvent(Class<T> classType, Predicate<T> condition, Consumer<T> action, long validUntil) {
        WaitingEvent<T> we = new WaitingEvent<>(condition, action, validUntil);
        waitingEvents.put(classType, we);
    }

    public static void checkValidationWaiters() {
        waitingEvents.entrySet().forEach((classWaitingEventEntry -> {
            if (!classWaitingEventEntry.getValue().isValid()) {
                waitingEvents.remove(classWaitingEventEntry.getKey());
            }
        }));
    }

    @Override
    public final void onGenericEvent(GenericEvent event) {
        Class c = event.getClass();

        while(c != null) {
            if(waitingEvents.containsKey(c)) {
                WaitingEvent waitingEvent = waitingEvents.get(c);
                if (waitingEvent.attempt(event)) {
                   waitingEvents.remove(c, waitingEvent);
                }
            }
            c = c.getSuperclass();
        }
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
            return validUntil >= System.currentTimeMillis();
        }
    }
}
