package org.example.functions;

import com.microsoft.azure.eventgrid.EventGridClient;
import com.microsoft.azure.eventgrid.TopicCredentials;
import com.microsoft.azure.eventgrid.implementation.EventGridClientImpl;
import com.microsoft.azure.eventgrid.models.EventGridEvent;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import org.joda.time.DateTime;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Azure Functions with Time Trigger.
 *  - Publish custom topic events which will be captured as EventGrid events
 */
public class CreateEvent {

    /**
     * This captures the "Data" portion of an EventGridEvent on a custom topic
     */
    static class ContosoItemReceivedEventData
    {
        public String itemSku;

        public ContosoItemReceivedEventData(String itemSku) {
            this.itemSku = itemSku;
        }
    }

    @FunctionName("EventGrid-TimeTriggered-Custom-Publisher")
    public void EventGridWithCustomPublisher(
            @TimerTrigger(name = "timerInfo", schedule = "*/20 * * * * *") String timerInfo,
            final ExecutionContext executionContext) {

        try {
            if (System.getenv("EVENTGRID_TOPIC_KEY").isEmpty() || System.getenv("EVENTGRID_TOPIC_ENDPOINT").isEmpty()) {
                executionContext.getLogger().info("UNEXPECTED EVENTGRID_TOPIC_KEY or EVENTGRID_TOPIC_ENDPOINT are empty so aborting publish");
                return;
            }

            int maxMsg = 5;
            if (null != System.getenv("EVENTGRID_GEN_MESSAGE") ) {
                maxMsg = Integer.parseInt(System.getenv("EVENTGRID_GEN_MESSAGE"));
            }

            // Create an event grid client.
            TopicCredentials topicCredentials = new TopicCredentials(System.getenv("EVENTGRID_TOPIC_KEY"));
            EventGridClient client = new EventGridClientImpl(topicCredentials);

            // Publish custom events to the EventGrid.
            System.out.println("Publish custom events to the EventGrid");
            List<EventGridEvent> eventsList = new ArrayList<>();
            for (int i = 0; i < maxMsg; i++) {
                eventsList.add(new EventGridEvent(
                        UUID.randomUUID().toString(),
                        String.format("Door%d", i),
                        new ContosoItemReceivedEventData("Garrard Item SKU #1"),
                        "Garrard.Items.ItemReceived",
                        DateTime.now(),
                        "2.0"
                ));
            }

            String eventGridEndpoint = String.format("https://%s/", new URI(System.getenv("EVENTGRID_TOPIC_ENDPOINT")).getHost());

            client.publishEvents(eventGridEndpoint, eventsList);
        } catch (Exception e) {
            executionContext.getLogger().info("UNEXPECTED Exception caught: " + e.toString());
        }
    }
}