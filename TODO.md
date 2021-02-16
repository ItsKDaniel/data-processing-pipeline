### Requirements

- [x] REST API implementation
    - [x] GET `retrieve all data from redis`
    - [x] POST `save data to redis as well as publish to websocket`
    - [x] Redis Template, Jackson serializer
    - [x] Calculate `longest_palindrome_size`
    - [x] Convert timestamp to GMT
- [x] Redis PubSub implementation
    - [x] Messaging publish
- [x] WebSocket implementation
    - [x] HTML, CSS
    - [x] Core JS
    - [x] Messaging Implementation in Java
- [x] Tests
- [x] dockerize
- [ ] documentation
    - [ ] update readme with flow diagram  

Here is how's it going to be built.

1. Create a POST endpoint to receive the payload and publish to Redis.
2. Create two subscribers that pulls contents from Redis.  
   2.1. Repository subscriber - pull content, calculate longest palindrome size, persist to redis  
   2.2. WebSocket Broadcast subscriber - pull content, publish to socket listener topic
3. Create a GET endpoint to retrieve all the keys from redis. (If this is not possible at the moment, persist content to
   a list type and return that when queried)
