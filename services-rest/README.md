# Демонстрация на REST уеб услуга

[REST](https://en.wikipedia.org/wiki/Representational_state_transfer)(или REpresentational State Transfer) е стил софтуерна архитектура за реализация на уеб услуги. Примерът използва [JAX-RS](https://docs.oracle.com/javaee/6/tutorial/doc/giepu.html) за създаване на REST услуга и [cURL](https://en.wikipedia.org/wiki/CURL) за тестването ѝ.

## Технологии
- Препочъва се използването на OpenJDK 8.
- [Postman](https://www.getpostman.com/) - клиент за REST услуги.

## Билд
```
mvnw clean package
```

---

## Стартиране

### REST услуга (използваща JAX-RS)
```
java -jar target/services-rest-1.0-SNAPSHOT-jar-with-dependencies.jar
```
При стартиране приложението принтира `Running REST web service...` и започва да чака за клиентски заявки. 

**Забележка**: Вместо да използвате горната команда, може да стартирате клас `bg.tusofia.cst.ds.rest.Main` директно от средата си за разработка.

### REST клиент (използващ cURL)

Следните команди могат да се използват за тестване на услугата.

**Забележка:** Също така може да използвате визуален REST клиент като [Postman](https://www.getpostman.com/) вместо команди. 

#### Взимане на всички лекции
```
curl -X GET http://localhost:9998/lectures 	
```

#### Създаване на лекция (идентификаторът се връща чрез хедър)
```
curl -X POST http://localhost:9998/lectures \
  -H 'Content-Type: application/json' \
  -d '{
        "title": "REST",
        "slideCount": 90,
        "createdOn": 1575851740931
    }'
```

#### Взимане на новата лекция 
```
curl -X GET http://localhost:9998/lectures/4
```

#### Промяна на лекцията
```
curl -X PUT http://localhost:9998/lectures/4 \
  -H 'Content-Type: application/json' \
  -d '{
        "title": "REST",
        "slideCount": 91,
        "createdOn": 1575851740931
    }'
```

#### Изтриване на лекцията
```
curl -X DELETE http://localhost:9998/lectures/4
```

### Приключване
След приключване на демонстрацията спрете процеса на REST услугата.

---