# Демонстрация на Messaging с JMS

[JMS](https://en.wikipedia.org/wiki/Java_Message_Service)(или Java Message Service) е Java message-oriented middleware API за изпращане и получаване на съобщения между два или повече клиента. JMS поддържа два вида структури - опашка (queue) и топик (topic).

## Технологии
- Препочъва се използването на OpenJDK 8.
- [Apache Active MQ](https://activemq.apache.org/index.html) - Java message-oriented middleware.

## Билд
```
mvnw clean package
```

---

## Стартиране

### Изисквания
За да работи примера трябва да стартирате ActiveMQ брокер. За улеснение този пример включва вложен брокер, който може да се стартира с команда:
```
java -cp target/messaging-jms-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.jms.Broker
```
**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.jms.Broker` директно от средата си за разработка.

#### Използване на самостоятелна ActiveMQ инсталация (по желание)
Ако инсталирате ActiveMQ ще имате възможност да разгледате административните опции, които предлага.
1. Изтеглете и разархивирайте ActiveMQ
1. Изпълнете `bin/activemq start`
1. Отворете административната страница на ActiveMQ http://localhost:8161/ (креденции: admin/admin)

### Консуматор на съобщения
```
java -cp target/messaging-jms-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.jms.Consumer
```
При стартиране приложението принтира `Waiting for message on queue 'test_queue'...` и започва да чака за съобщение (след пристигането на съобщение приложението го прочита и спира). 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.jms.Consumer` директно от средата си за разработка.

### Производител на съобщения
```
java -cp target/messaging-jms-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.jms.Producer <съобщение>
```
При стартиране приложението принтира `Sending message to queue 'test_queue'...` и изпраща съобщение. Ако не въведете съобщение се изпраща текст "opa opa".

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.jms.Producer` директно от средата си за разработка.

### Приключване
След приключване на демонстрацията спрете процеса на брокера (и на консуматора, ако все още чака съобщение).

## AMQP 1.0 поддръжка
Active MQ 5.8+ поддържа протокол AMQP 1.0. Това може да се демонстрира със същия проект. Ако използвате самостоятелна инстанция на Active MQ вижте инструкциите [тук](https://activemq.apache.org/amqp). Вграденият брокер поддържа AMQP по подразбиране.
Класовете AmqpConsumer и AmqpProducer са аналогични на базовите класове, но използват протокол AMQP. При стартиране те принтират същите съобщения заедно с детайли за AMQP връзката.

### Консуматор на съобщения
```
java -cp target/messaging-jms-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.jms.AmqpConsumer
```

### Производител на съобщения
```
java -cp target/messaging-jms-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.jms.AmqpProducer <съобщение>
```