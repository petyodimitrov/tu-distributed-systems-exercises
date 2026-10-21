# Демонстрация на структурирани промптове с XML тагове при работа с Claude посредством Java

Приложението показва как се структурира промпт към голям езиков модел ([LLM](https://en.wikipedia.org/wiki/Large_language_model)) чрез [XML](https://en.wikipedia.org/wiki/XML) тагове,
както препоръчва [ръководството на Anthropic за промпт инженеринг](https://docs.claude.com/en/docs/build-with-claude/prompt-engineering/overview).
Като пример се използва автоматичен преглед на код (code review): моделът получава стандартите на екипа и промени по код във формат [diff](https://en.wikipedia.org/wiki/Diff),
открива нарушенията и връща резултата в XML тагове, които програмата извлича чрез [регулярни изрази](https://en.wikipedia.org/wiki/Regular_expression).
Заявките се изпращат чрез официалния [Anthropic Java SDK](https://github.com/anthropics/anthropic-sdk-java).

## Изисквания

- Java 8 или по-нова версия
- [Maven](https://maven.apache.org/)
- API ключ за Claude, създаден в [Claude Console](https://platform.claude.com/)

## Билд
```
mvn clean compile
```

---

## Стартиране

Преди стартиране задайте API ключа като променлива на средата:
```
export ANTHROPIC_API_KEY=sk-ant-...
```
В Windows (PowerShell):
```
$env:ANTHROPIC_API_KEY="sk-ant-..."
```

### Ръчно
Заредете проекта в IDE по ваш избор и стартирайте класа `Main`.

### Автоматично
```
java -jar target/xml-prompting-1.0-SNAPSHOT-jar-with-dependencies.jar
```

---

## Отстраняване на проблеми

- Задайте `ANTHROPIC_LOG=debug`, за да видите HTTP заявките и отговорите.
- Ако оценката е `UNKNOWN`, проверете дали отговорът не е прекъснат (`stop_reason=max_tokens`) и при нужда увеличете `maxTokens`.
