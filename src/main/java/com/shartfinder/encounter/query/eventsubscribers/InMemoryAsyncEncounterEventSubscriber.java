package com.shartfinder.encounter.query.eventsubscribers;

import javax.inject.Inject;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.shartfinder.encounter.command.events.EncounterCreatedEvent;
import com.shartfinder.encounter.command.events.InitiativeOrderCreatedEvent;
import com.shartfinder.encounter.command.framework.EncounterEventType;
import com.shartfinder.framework.event.Event;
import com.shartfinder.framework.event.EventHandler;
import com.shartfinder.framework.event.EventSubscriber;

@Profile("default")
@Component
public class InMemoryAsyncEncounterEventSubscriber implements
        EventSubscriber<EncounterEventType> {

    private final EventHandler<EncounterCreatedEvent> tableCreatedEventHandler;

    private final EventHandler<InitiativeOrderCreatedEvent> playerCheckedEventHandler;

    @Inject
    public InMemoryAsyncEncounterEventSubscriber(
            EventHandler<EncounterCreatedEvent> tableCreatedEventHandler,
            EventHandler<InitiativeOrderCreatedEvent> playerCheckedEventHandler) {
        this.tableCreatedEventHandler = tableCreatedEventHandler;
        this.playerCheckedEventHandler = playerCheckedEventHandler;
    }

    @Async
    @Override
    public void receive(Event<EncounterEventType> event) {
        switch (event.getType()) {
        case EncounterCreated:
            tableCreatedEventHandler.handle((EncounterCreatedEvent) event);
            break;
        case InitiativeOrderCreated:
            playerCheckedEventHandler.handle((InitiativeOrderCreatedEvent) event);
            break;
        default:
            throw new IllegalArgumentException("Event Type cannot be handled: "
                    + event.getType());
        }
    }

}
