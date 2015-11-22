# Messaging example

1. Setup and start Active MQ (http://activemq.apache.org/activemq-5121-release.html).
	a. Execute bin/activemq start
	b. Open admin page http://localhost:8161/ in a browser (default credentials: admin/admin)
2. Run bg.tusofia.fksu.soa.examples.messaging.Consumer to start listening for a message.
3. Run bg.tusofia.fksu.soa.examples.messaging.Producer to send a message.
4. Verify bg.tusofia.fksu.soa.examples.messaging.Consumer has received the message and stopped.
5. Explore Active MQ admin page (e.g. list and read queues, topics). 
6. Clean up (stop Active MQ).