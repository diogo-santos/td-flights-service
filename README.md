# Flights service
Returns requested flights availability according to url parameters

Using web client for REST client calls

Business logic: Get availability flights from HTTP service that returns data in XML and then converts in a different layout in JSON format

## Test flight availability service
```
mvn spring-boot:run
```

Now that the app is running, visit http://localhost:8080/flights/IST/DUB/2014-01-01/2014-01-01/1 in order to see the flight availability
