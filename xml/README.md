# Демонстрация на възможностите за работа с XML посредством Java

Приложението позволява да се експериментира с [XML](https://en.wikipedia.org/wiki/XML) посредством Java. Поддържат се популярни XML парсъри, валидация чрез [XSD](https://en.wikipedia.org/wiki/XML_Schema_(W3C)), извличане на данни чрез [XPath](https://en.wikipedia.org/wiki/XPath), трансформации чрез [XSLT](https://en.wikipedia.org/wiki/XSLT) и работа с XML посредством [JAX-B](https://en.wikipedia.org/wiki/Java_Architecture_for_XML_Binding).

## Билд
```
mvnw clean package
```

---

## Стартиране

### Ръчно
Заредете проекта в IDE по ваш избор и стартирайте класовете с main методи в пакети bg.tusofia.cst.ds.xml.parsers, bg.tusofia.cst.ds.xml.validators и bg.tusofia.cst.ds.xml.other. Разгледайте имплементациите и експериментирайте.

**Забележка:**  Пример XMLValidationExample1 функционира само когато файловете се зареджат от файловата система, а не от клас пъта в джар файла. Затова в сегашния си формат може да се стартира само ръчно. 

### Автоматично
```
java -jar target/xml-1.0-SNAPSHOT.jar <example>
```

Ако програмата се стартира без параметри, показва поддържаните примери. Ако се стартира с името на конкретен пример (например "dom_read"), се изпълнява само този пример. Ако се стартира с параметър "all", се изпълняват всички примери.
