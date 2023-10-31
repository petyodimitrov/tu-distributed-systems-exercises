# Демонстрация на Messaging с Apache Kafka

[Apache Kafka](https://en.wikipedia.org/wiki/Apache_Kafka) е Java/Scala платформа за предаване на потоци от данни посредством топици (topics).

## Технологии
- Препочъва се използването на OpenJDK 8+.
- [Apache Kafka](https://kafka.apache.org/downloads.html) - разпределена платформа за потоци от данни.

## Билд
```
mvnw clean package
```

---

## Стартиране

### Изисквания
За да работи примера трябва да инсталирате и стартирате Kafka брокер. 

#### Инструкции за инсталация
Ако инсталирате Apache Kafka ще имате възможност да разгледате административните опции, които предлага.
1. Изтеглете и инсталирайте Java и конфигурирайте JAVA_HOME environment променливата. 
1. Изтеглете и разархивирайте Apache Kafka от https://kafka.apache.org/downloads.html (например версия  kafka_2.13-3.3.1 е версия 3.3.1 използваща Scala 2.13). 
   Клиентската библиотека за вързка с Kafka е за версия 3.3.1 (вижте pom.xml-а за референция), така че при избор на друга версия на Kafka може да се наложи да смените версията на библиотеката в проекта.
1. Разархивирайте Apache Kafka.

#### Инструкции за стартиране
За да стартирате Apache Kafka е необходимо да се изпълнят следните команди от директория `bin\windows`. 
За Линукс операционни системи могат да се използват скриптовете от директория `bin/`.
1. Стартирайте Zookeeper `zookeeper-server-start.bat ..\..\config\zookeeper.properties`
1. Стартирайте Kafka `kafka-server-start.bat ..\..\config\server.properties`

**Забележка**: При необходимост може да промените настройките на двете приложения посредством `properties` файловете.

#### Тестване от командния ред
За да потвърдите, че Apache Kafka работи, може да изпълните следните команди.
1. Създайте топик `kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test`
1. Стартирайте консуматор на съобщения `kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning`
1. Стартирайте конзолен изпращач на съобщения `kafka-console-producer.bat --broker-list localhost:9092 --topic test`

### Консуматор на съобщения
```
java -cp target/messaging-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.kafka.Consumer <topic> <consumer-group>
```
При стартиране приложението принтира `Waiting for message on topic '<topic-name>'...` и започва да чака за съобщения. Ако не въведете топик се използва топик "topic". Ако не въведете консуматорска група се използва група "group-a". 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.kafka.Consumer` директно от средата си за разработка.

#### Множество consumer groups
Стартирайте консуматора два пъти подавайки същото име на топик и различни групи консуматори. В този случай трябва и двата консуматора паралелно да прочетат всички съобщения.

#### Множество partitions
Създайте нов топик с 4 partition-a използвайки команда `kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 4 --topic partitioned-topic`.
Стартирайте консуматора два пъти подавайки името на новия топик (`partitioned-topic`) и еднаква група консуматори (или пропуснете втория параметър). 
В този случай трябва двата консуматора да си разпределят по 2 partition-а и всеки паралелно да прочете половината от съобщенията. Тоест двата консуматора взети заедно консумират всички съобщения.

### Производител на съобщения
```
java -cp target/messaging-kafka-1.0-SNAPSHOT-jar-with-dependencies.jar bg.tusofia.cst.ds.kafka.Producer <topic> <message>
```
При стартиране приложението принтира `Sending message to topic '<topic-name>'...` и изпраща 100 съобщения. Ако не въведете топик се използва топик "topic". Ако не въведете съобщение се изпраща текст "opa opa". 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.kafka.Producer` директно от средата си за разработка.

### Приключване
След приключване на демонстрацията спрете процеса на консуматора (и брокера).
