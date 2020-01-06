# Демонстрация на SOAP уеб услуга

[SOAP](https://en.wikipedia.org/wiki/SOAP) e отворен стандарт проектиран да позволи комуникацията между приложения работещи на различни системи (операционни системи, програмни езици и хардуер). Примерите използват [JAX-WS](https://docs.oracle.com/javaee/6/tutorial/doc/bnayl.html) за създаване на SOAP услуга и [SAAJ](https://docs.oracle.com/javaee/5/tutorial/doc/bnbhg.html) за създаване на SOAP клиент.

## Технологии
- Препочъва се използването на OpenJDK 8.
- [soapUI](https://www.soapui.org/downloads/soapui.html) - клиент за SOAP услуги.

## Билд
```
mvnw clean package
```

---

## Стартиране

### SOAP услуга (използваща JAX-WS)
```
java -cp target/services-soap-1.0-SNAPSHOT.jar bg.tusofia.cst.ds.soap.server.CinemaPublisher
```
При стартиране приложението принтира `Running SOAP web service...` и започва да чака за клиентски заявки. 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.soap.server.CinemaPublisher` директно от средата си за разработка.

### SOAP клиент (използваш SAAJ)
```
java -cp target/services-soap-1.0-SNAPSHOT.jar bg.tusofia.cst.ds.soap.client.CinemaClient
```
При стартиране приложението принтира SOAP заявката и отговора от услугата.

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.soap.client.CinemaClient` директно от средата си за разработка. 

### SOAP UI клиент
Може да използвате [SOAP UI](https://www.soapui.org/downloads/soapui.html), за да експериментирате с услугата (особено с WS-Addressing). За целта инсталирайте и стартирайте приложението. Може да импортирайте услугата като изберете File > New soapUI project, въведете адреса на WSDL-а на услугата (`http://localhost:9999/cinema?wsdl`), и натиснете OK. След това може да разгледате генерираната заявка и да я тествате.

**Забележка:** За да тествате WS-Addressing трябва да отворите [WS-A настройките](https://www.soapui.org/soap-and-wsdl/supported-standards/using-ws-addressing.html) и като минимум да активирате опции:
- Enable WS-A addressing
- Add default wsa:Action
- Generate MessageID

Ако искате да пренасичките отговора или грешката връщана от услугата към друг сървър може да използвате [RequestBin](http://requestbin.net/), друга подобна услуга или локален сървър. 
Ако искате да ползвате локален сървър може да използвате `nc` команда, която да стартира сървър слушащ на финсиран порт:
```
nc -l -p 8080
```
Команда `nc` е на разположение на Linux операционни системи, а за Windows потребители проекта включва порт на приложението (`nc.exe`).

### Приключване
След приключване на демонстрацията спрете процеса на SOAP услугата.

---