## <img src="logo.png" height="30" /> Демонстрация на Microservices с jHipster

Това е примерна реализация на [Microservice](https://en.wikipedia.org/wiki/Microservices) архитектура създадена с [jHipster](https://en.wikipedia.org/wiki/JHipster) (популярен Java генератор на приложения). Използва [Netflix OSS](https://netflix.github.io/), [Spring Boot](https://spring.io/projects/spring-boot), [React](https://reactjs.org/), [Docker](https://www.docker.com/) и множество други библиотеки. За повече информация моля разгледайте [Doing microservices with JHipster](https://www.jhipster.tech/microservices-architecture/).

## Основни компоненти:
* Гейтуей - рутира заявки и хоства React потребителския интерфейс, 
* Две микроуслуги - една използваща SQL база данни, а другата - Mongo, 
* Регистър на услугите - използва Netflix Eureka и Spring Cloud Config,
* ELK стека - за логване.

<img src="microservices_architecture.png" width="800" />

## Технологии
1. Java 1.8+
1. Docker 17+
1. Docker Compose 1.19+
1. Минимум 6 GB свободна оперативна памет
1. Минимум 5 GB свободно дисково пространство

## Билд
```
mvnw -Pprod clean verify com.google.cloud.tools:jib-maven-plugin:1.3.0:dockerBuild
```

**Забележка:** Отделните 3 приложения (`sample-gateway/`, `cars-service/`, `dealers-service/`) могат да бъдат пакетирани отделно.

---

## Стартиране
```bash
cd docker-compose/
docker-compose up -d
```
**Забележка:** Може да се наложи да пуснете командата два пъти понеже регистъра може да не стартира навреме при първото стартиране и да причини спирането на някоя от микроуслугите. Повторното стартиране решава този проблем.

## Разгледайте решението:
* jHispter Registry: http://localhost:8761
* Sample Gateway: http://localhost:8080/
* Kibana: http://localhost:5601/app/kibana/
* Zipkin: http://localhost:9411/zipkin/

**Забележка:** Креденциите са `admin/admin`.

### Приключване
След приключване на демонстрацията спрете контейнерите чрез:
```
cd docker-compose/
docker-compose down
```

## Ръчни инструкции за генериране на това приложение:

Следвайки тези инструксии може да създадете проекта от начало. За целта трябва да инсталирате jHipster.

```bash
# create Car service
mkdir cars-service
cd cars-service
jhipster            # see cars-service/.yo-rc.json for chosen config options
jhipster entity car # see cars-service/.jhipster/Car.json for model
cd ..

# create Dealer service
mkdir dealers-service
cd dealers-service
jhipster               # see dealers-service/.yo-rc.json for chosen config options
jhipster entity dealer # see dealers-service/.jhipster/Dealer.json for model

# create Gateway
mkdir sample-gateway
cd sample-gateway 
jhipster               # see sample-gateway/.yo-rc.json for chosen config options
jhipster entity car    # import config from MS to create UI in GW
jhipster entity dealer # import config from MS to create UI in GW

# setup deployment
mkdir docker-compose
cd docker-compose
jhipster docker-compose # see docker-compose/.yo-rc.json for chosen config options
```
