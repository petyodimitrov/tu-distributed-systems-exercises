# Демонстрация на клиент-сървър комуникация с CORBA

[CORBA](https://en.wikipedia.org/wiki/Common_Object_Request_Broker_Architecture)(или Common Object Request Broker Architecture) e отворен стандарт проектиран да позволи комуникацията между приложения работещи на различни системи (операционни системи, програмни езици и хардуер).

## Технологии
- Препочъва се използването на OpenJDK 8.

## Билд
```
mvnw clean package
```

---

## Стартиране

### Изисквания
За да работи примера трябва да стартирате CORBA регистър с Java команда [orbd](https://docs.oracle.com/javase/9/tools/orbd.htm#JSWOR714) (част от OpenJDK; виж поддиректория `bin/`).
```
orbd -ORBInitialPort 1050
```
**Забележка**: За Windows може да използвате bat файл `run-orbd.bat`.
 
### CORBA сървър
```
java -cp target/client-server-corba-1.0-SNAPSHOT.jar bg.tusofia.cst.ds.corba.server.HelloServer
```
При стартиране приложението принтира `Running ORB...` и започва да чака за клиентски заявки. 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.corba.server.HelloServer` директно от средата си за разработка.

### CORBA клиент
```
java -cp target/client-server-corba-1.0-SNAPSHOT.jar bg.tusofia.cst.ds.corba.client.HelloClient
```
При стартиране приложението принтира няколко съобщения, след което връща резултат получен от сървъра `result: opa` и приключва изпълнението си.  

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.corba.client.HelloClient` директно от средата си за разработка.

### Приключване
След приключване на демонстрацията спрете процесите на CORBA сървъра и `orbd`.

---

## Генериране на Java код от IDL документ.

Приложението съдържа автоматично генерирани сървърни и клиентски класове в пакет `bg.tusofia.cst.ds.corba.generated`. Те се генерират от IDL файл `src\main\resources\hello.idl` чрез команда [idlj](https://docs.oracle.com/javase/7/docs/technotes/tools/share/idlj.html) (част от OpenJDK; виж поддиректория `bin/`).

При нужда тези класове могат да се генерират отново чрез команда:
```
idlj -fclient -fserver -pkgPrefix hello bg.tusofia.cst.ds.corba.generated -td src\main\java src\main\resources\hello.idl
``` 

**Забележка**: По принцип не е добра практика генериран код да се държи под сорс контрол. Вместо това процесът по генерация може да се автоматизира чрез Maven (или друг инструмент). За целите на тази демонстрация обаче е желателно кода да е видим и не нужно да усложняваме процеса с допълнителни стъпки. При интерес може да разгледате Maven плъгин [CORBA IDL Compiler](https://www.mojohaus.org/idlj-maven-plugin/), който позволява подобна автоматизация.
