package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class TopicTrigger {
    /**
     * This function listens at endpoint "/api/topicTrigger". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/topicTrigger
     * 2. curl {your host}/api/topicTrigger?name=HTTP%20Query
     */
    @FunctionName("eventGridMonitorString")
    public void logEvent(
            @EventGridTrigger(
                    name = "event"
            )
                    String content,
            final ExecutionContext context) {
        context.getLogger().info("Event content: " + content);
    }
}


