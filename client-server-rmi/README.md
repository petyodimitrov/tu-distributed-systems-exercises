# Демонстрация на клиент-сървър комуникация с Java RMI

[Java RMI](https://en.wikipedia.org/wiki/Java_remote_method_invocation) (или Java Remote Method Invocation) e Java технология позволяваща комуникацията между отделни Java приложения.

## Технологии
- Препочъва се използването на OpenJDK 8.

## Билд
```
mvnw clean package
```

---

## Стартиране

### Изисквания
За да работи примера трябва да стартирате RMI регистър с Java команда [rmiregistry](https://docs.oracle.com/javase/7/docs/technotes/tools/solaris/rmiregistry.html) (част от OpenJDK; виж поддиректория `bin/`).
```
set CLASSPATH=target/client-server-rmi-1.0-SNAPSHOT.jar
rmiregistry
```
Регистърът има нужда от Java интерфейса играеш ролята на договор между клиента и сървъра. Затова в горната команда целия jar файл на приложението е сложен на Java classpath-а.

**Забележка**: За Windows може да използвате bat файл `run-rmiregistry.bat`.

### RMI сървър
```
java -cp target/client-server-rmi-1.0-SNAPSHOT.jar bg.tusofia.cst.ds.rmi.server.WarehouseServer
```
При стартиране приложението принтира `Running RMI server bean...` и започва да чака за клиентски заявки. 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.rmi.server.WarehouseServer` директно от средата си за разработка.

### RMI клиент
```
java -cp target/client-server-rmi-1.0-SNAPSHOT.jar bg.tusofia.cst.ds.rmi.client.WarehouseClient
```
При стартиране приложението принтира няколко съобщения, след което връща резултат получен от сървъра `price is 69.0` и приключва изпълнението си.  

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.rmi.client.WarehouseClient` директно от средата си за разработка.

### Приключване
След приключване на демонстрацията спрете процесите на RMI сървъра и `rmiregistry`.