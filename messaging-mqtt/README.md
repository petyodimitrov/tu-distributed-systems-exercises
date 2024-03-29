# Демонстрация на Messaging с MQTT

[MQTT](https://en.wikipedia.org/wiki/MQTT)(или MQ Telemetry Transport) е pub-sub мрежови протокол за транспортиране на съобщения между устройства посредством topic-и.

## Технологии
- Препочъва се използването на OpenJDK 8+.
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
java -cp target/messaging-jms-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.mqtt.Broker
```
**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.mqtt.Broker` директно от средата си за разработка.

#### Използване на самостоятелна ActiveMQ инсталация (по желание)
Ако инсталирате ActiveMQ ще имате възможност да разгледате административните опции, които предлага.
1. Изтеглете и разархивирайте ActiveMQ
1. Верифицирайте, че протокол MQTT е активиран (https://activemq.apache.org/mqtt)
1. Изпълнете `bin/activemq start`
1. Отворете административната страница на ActiveMQ http://localhost:8161/ (креденции: admin/admin)

### Консуматор на съобщения
```
java -cp target/messaging-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.mqtt.Consumer
```
При стартиране приложението принтира `Waiting for message on topic 'myhome/livingroom/temperature'...` и започва да чака за съобщение (след пристигането на съобщение приложението го прочита и спира). 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.mqtt.Consumer` директно от средата си за разработка.

### Производител на съобщения
```
java -cp target/messaging-mqtt-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.mqtt.Producer <съобщение>
```
При стартиране приложението принтира `Sending message to topic 'myhome/livingroom/temperature'...` и изпраща съобщение. Ако не въведете съобщение се изпраща текст "opa opa".

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.mqtt.Producer` директно от средата си за разработка.

### Приключване
След приключване на демонстрацията спрете процеса на брокера (и на консуматора, ако все още чака съобщение).
