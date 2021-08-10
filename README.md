# ratelimiter
Simple RateLimiting filter for an API

Created this project using Spring-boot
The logic for RateLimiting exists in filter/RequestTimingUtility.java
and gets applied to the API using a java.servlet.filter implementation of filter/RateLimitFilter

Added a PostmanScript as well rateLimiter.postman_collection.json to test
