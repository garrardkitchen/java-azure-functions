TBC...

# Getting started

- install windows dependencies:
  - choco install gradle -version 7.1
  - choco install javaruntime
  - choco install openjdk11jre
  
# Functions

- [CreateEvent.java](src/main/java/org/example/functions/CreateEvent.java) creates messages and publishes them to topic using a timer trigger
- [TopicTrigger.java](src/main/java/org/example/functions/TopicTrigger.java) consumes the messages as produced by the CreateEvent function

# References

- https://docs.microsoft.com/en-us/azure/azure-functions/functions-create-maven-intellij
- https://docs.microsoft.com/en-us/azure/azure-functions/functions-triggers-bindings?tabs=java
- https://docs.microsoft.com/en-us/azure/azure-functions/functions-bindings-event-grid-trigger?tabs=java%2Cbash
- https://docs.microsoft.com/en-us/java/api/overview/azure/eventgrid?view=azure-java-stable
- https://github.com/Azure-Samples/event-grid-java-publish-consume-events/blob/master/eventgrid-function-apps-producer-consumer/src/main/java/com/microsoft/azure/eventgrid/samples/EventGridTimeTriggeredCustomPublisher.java