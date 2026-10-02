# Демонстрация на MCP сървър

[MCP](https://modelcontextprotocol.io/) (Model Context Protocol) е отворен протокол, чрез който приложение с
голям езиков модел (*host*, например [Claude Desktop](https://claude.ai/download) или
[Claude Code](https://claude.com/claude-code)) открива и извиква възможности, предоставени от външни процеси
(*MCP сървъри*). Протоколът е изграден върху [JSON-RPC 2.0](https://www.jsonrpc.org/specification) и от гледна
точка на курса представлява поредният стил за отдалечено извикване — сравним с RMI, SOAP, REST и gRPC, 
но проектиран така, че извикващата страна да е AI езиков модел.

Примерът използва официалното [MCP Java SDK](https://github.com/modelcontextprotocol/java-sdk) и реализира
**и двата транспорта**, дефинирани в спецификацията:

| Транспорт | Стартиране | Приложение |
| --- | --- | --- |
| **STDIO** | `java -jar target/services-mcp.jar` | host процесът стартира сървъра като подпроцес и обменя с него JSON-RPC съобщения, разделени с нов ред, през `stdin`/`stdout` |
| **Streamable HTTP** | `java -jar target/services-mcp.jar --http` | сървърът слуша на TCP порт и може да бъде извикван **отдалечено**, по мрежата |

Самите възможности (инструментът и ресурсът) са дефинирани на едно място — `ExerciseCapabilities.java` — и са
напълно независими от транспорта. Това е и основната поука от примера: транспортът само пренася JSON-RPC
съобщенията и не влияе върху това какво предлага сървърът.

> **Важно (само за STDIO)**: при този транспорт `stdout` е зает от протокола. Нищо не трябва да се принтира на
> стандартния изход — всички съобщения от журнала се пишат на `stderr`
> (виж `src/main/resources/simplelogger.properties`).

## Технологии
- **Задължително OpenJDK 17+** (MCP Java SDK и неговият Jackson 3 слой изискват Java 17).
- [Apache Maven 3.6+](https://maven.apache.org/)
- [MCP Java SDK](https://github.com/modelcontextprotocol/java-sdk) 2.0.1
- [Eclipse Jetty](https://jetty.org/) 12 (ee11) — вграден сервлет контейнер за HTTP транспорта
- По избор: [MCP Inspector](https://github.com/modelcontextprotocol/inspector) — визуален клиент за тестване.

## Билд
```
mvn clean package
```
Билдът произвежда изпълним jar с всички зависимости: `target/services-mcp.jar`.

---

## Какво предоставя сървърът

Сървърът се казва `ds-exercises` и публикува двата основни вида възможности на MCP:

### Инструмент (tool) `exercise_info`
Инструментът е **действие**, което моделът може да извика. Аргументите му се описват с
[JSON Schema](https://json-schema.org/) и се валидират от SDK-то преди извикването на обработчика.

| Аргумент | Тип | Описание |
| --- | --- | --- |
| `id` | `string` (задължителен) | Идентификатор на упражнението, равен на името на директорията, например `services-grpc` |

Връща технологията и кратко описание на съответния демонстрационен проект. При непознат идентификатор връща
резултат с `isError: true` и списък на валидните стойности, така че моделът да може да коригира заявката си.

### Ресурс (resource) `exercises://catalog`
Ресурсът е **контекст за четене**, адресиран с URI и без странични ефекти. Този ресурс връща Markdown таблица
с всички демонстрационни проекти в хранилището и технологията, която всеки от тях илюстрира.

### Изходен код
`src/main/java/bg/tusofia/cst/ds/mcp/`:

| Файл | Предназначение |
| --- | --- |
| `ExerciseCatalog.java` | данните, които се публикуват |
| `ExerciseCapabilities.java` | дефиниция на инструмента и ресурса — обща за двата транспорта |
| `ExercisesMcpServer.java` | входна точка; STDIO транспорт и избор на транспорт от командния ред |
| `ExercisesMcpHttpServer.java` | Streamable HTTP транспорт върху вграден Jetty |

---

## HTTP API (Streamable HTTP транспорт)

```
java -jar target/services-mcp.jar --http          # 0.0.0.0:8080
java -jar target/services-mcp.jar --http 9090     # 0.0.0.0:9090
```

Това е същият JSON-RPC 2.0 протокол, но пренесен през HTTP:

| Заявка | Предназначение |
| --- | --- |
| `POST /mcp` | изпраща JSON-RPC заявка или нотификация. Отговорът е или единичен `application/json`, или `text/event-stream` (SSE), когато сървърът изпраща и междинни съобщения |
| `GET /mcp` | отваря SSE поток, по който сървърът изпраща нотификации и свои заявки към клиента |
| `DELETE /mcp` | приключва сесията |
| `GET /health` | проверка за достъпност (не е част от MCP) |

Сесията, създадена от `initialize`, се идентифицира с хедъра `Mcp-Session-Id`, който клиентът трябва да
връща при всяка следваща заявка.

### Настройки (променливи на средата)

| Променлива | По подразбиране | Описание |
| --- | --- | --- |
| `MCP_HTTP_HOST` | `0.0.0.0` | адрес, на който се слуша |
| `MCP_HTTP_PORT` | `8080` | порт (аргументът от командния ред има предимство) |
| `MCP_ALLOWED_HOSTS` | *(празно)* | списък, разделен със запетая, от разрешени стойности на хедъра `Host`, например `mcp.example.com:8080` или `mcp.example.com:*` |
| `MCP_ALLOWED_ORIGINS` | *(празно)* | аналогично, за хедъра `Origin` |

> **Сигурност**: спецификацията препоръчва проверка на хедърите `Host` и `Origin` като защита срещу
> [DNS rebinding](https://en.wikipedia.org/wiki/DNS_rebinding). В този пример проверката е **изключена по
> подразбиране**, за да работи демонстрацията без допълнителна настройка — при стартиране сървърът извежда
> предупреждение. Преди излагане извън доверена мрежа задайте поне `MCP_ALLOWED_HOSTS` и сложете сървъра зад
> обратен прокси с TLS и автентикация (примерът няма вградена автентикация).

### Тестване с cURL

```
# 1. Ръкостискане - връща хедър Mcp-Session-Id
curl -i -X POST http://localhost:8080/mcp \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json, text/event-stream' \
  -d '{"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2025-06-18","capabilities":{},"clientInfo":{"name":"curl","version":"1.0.0"}}}'

# 2. Запишете идентификатора на сесията от отговора
SID=<стойността на Mcp-Session-Id>

# 3. Потвърждаване на инициализацията (връща 202 Accepted)
curl -X POST http://localhost:8080/mcp \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json, text/event-stream' \
  -H "Mcp-Session-Id: $SID" \
  -d '{"jsonrpc":"2.0","method":"notifications/initialized"}'

# 4. Извикване на инструмента
curl -X POST http://localhost:8080/mcp \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json, text/event-stream' \
  -H "Mcp-Session-Id: $SID" \
  -d '{"jsonrpc":"2.0","id":2,"method":"tools/call","params":{"name":"exercise_info","arguments":{"id":"messaging-kafka"}}}'

# 5. Четене на ресурса
curl -X POST http://localhost:8080/mcp \
  -H 'Content-Type: application/json' \
  -H 'Accept: application/json, text/event-stream' \
  -H "Mcp-Session-Id: $SID" \
  -d '{"jsonrpc":"2.0","id":3,"method":"resources/read","params":{"uri":"exercises://catalog"}}'

# 6. Приключване на сесията
curl -X DELETE http://localhost:8080/mcp -H "Mcp-Session-Id: $SID"
```

---

## Регистриране на сървъра

### Claude Code — отдалечен сървър (HTTP)

```
claude mcp add --transport http ds-exercises http://localhost:8080/mcp
```
За сървър на друга машина заменете `localhost:8080` със съответния адрес. Ако е необходим хедър за
автентикация на прокси пред сървъра, той се добавя с `--header`:
```
claude mcp add --transport http ds-exercises https://mcp.example.com/mcp \
  --header "Authorization: Bearer <token>"
```

### Claude Code — локален сървър (STDIO)

Необходим е **абсолютен** път до изпълнимия jar. Опцията `-s user` прави сървъра достъпен във всички проекти;
по подразбиране обхватът е само текущата директория.

```
claude mcp add ds-exercises -- java -jar /път/до/services-mcp/target/services-mcp.jar
```

Проверка на регистрацията и на връзката:
```
claude mcp list
```
Вътре в сесия на Claude Code състоянието се вижда с командата `/mcp`. Премахване:
```
claude mcp remove ds-exercises
```

Алтернативно, за да се сподели конфигурацията с целия екип, сървърът може да се опише във файл `.mcp.json` в
основната директория на проекта:

```json
{
  "mcpServers": {
    "ds-exercises": {
      "command": "java",
      "args": ["-jar", "/път/до/services-mcp/target/services-mcp.jar"]
    },
    "ds-exercises-http": {
      "type": "http",
      "url": "http://localhost:8080/mcp"
    }
  }
}
```

### Claude Desktop

Конфигурационният файл `claude_desktop_config.json` се отваря най-лесно през
*Settings → Developer → Edit Config*. Намира се на:

| ОС | Път |
| --- | --- |
| macOS | `~/Library/Application Support/Claude/claude_desktop_config.json` |
| Windows | `%APPDATA%\Claude\claude_desktop_config.json` |
| Linux | `~/.config/Claude/claude_desktop_config.json` |

Добавете секция `mcpServers`:

```json
{
  "mcpServers": {
    "ds-exercises": {
      "command": "java",
      "args": ["-jar", "/път/до/services-mcp/target/services-mcp.jar"]
    }
  }
}
```

След записване **рестартирайте Claude Desktop**. Сървърът се появява в менюто за инструменти (иконата на
приставките) под името `ds-exercises`.

> **Забележка**: ако `java` не е в `PATH` на графичната среда, задайте пълен път, например
> `"command": "/usr/lib/jvm/java-17-openjdk/bin/java"`.

За **отдалечен** сървър Claude Desktop се свързва през *Settings → Connectors → Add custom connector*, където
се въвежда адресът `http://<хост>:8080/mcp`. Ако тази възможност не е налична, отдалеченият сървър може да се
добави и през конфигурационния файл с помощта на моста [`mcp-remote`](https://www.npmjs.com/package/mcp-remote):

```json
{
  "mcpServers": {
    "ds-exercises": {
      "command": "npx",
      "args": ["-y", "mcp-remote", "http://<хост>:8080/mcp"]
    }
  }
}
```

### Проба

След регистрацията опитайте в Claude следното:

```
Какво демонстрира упражнението services-grpc?
```
```
Покажи ми каталога с упражненията от курса.
```

---

## Тестване без Claude

### С MCP Inspector
```
npx @modelcontextprotocol/inspector java -jar target/services-mcp.jar          # STDIO
npx @modelcontextprotocol/inspector                                            # HTTP: въведете адреса в UI
```
Инспекторът отваря уеб интерфейс, в който могат да се разгледат и извикат публикуваните инструменти и ресурси.

### Ръчно през stdin
Тъй като STDIO транспортът е обикновен текст, сървърът може да се тества и директно:

```
(cat <<'EOF'
{"jsonrpc":"2.0","id":1,"method":"initialize","params":{"protocolVersion":"2025-06-18","capabilities":{},"clientInfo":{"name":"test","version":"1.0.0"}}}
{"jsonrpc":"2.0","method":"notifications/initialized"}
{"jsonrpc":"2.0","id":2,"method":"tools/list","params":{}}
{"jsonrpc":"2.0","id":3,"method":"tools/call","params":{"name":"exercise_info","arguments":{"id":"services-grpc"}}}
{"jsonrpc":"2.0","id":4,"method":"resources/read","params":{"uri":"exercises://catalog"}}
EOF
sleep 3) | java -jar target/services-mcp.jar
```

`sleep 3` е необходим, защото сървърът приключва веднага щом `stdin` бъде затворен — без него последните
отговори може да не бъдат изпратени.

### Приключване
При STDIO сървърът няма собствен жизнен цикъл — процесът приключва, когато host приложението затвори `stdin`;
при ръчно стартиране го спрете с `Ctrl+D`. HTTP вариантът се спира с `Ctrl+C`.

---

## Идеи за разширяване
- Добавяне на **prompt** — третата възможност на MCP: шаблон за заявка, който потребителят избира явно.
- Връщане на **структуриран резултат** (`outputSchema` + `structuredContent`) от инструмента.
- Добавяне на **автентикация** пред HTTP транспорта (MCP дефинира профил върху OAuth 2.1).
