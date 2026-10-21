# Backend Coding Standards

## Reliability
- **R-01:** Never swallow exceptions. A `catch` block must log the exception or rethrow it.
- **R-02:** Retries must use a bounded number of attempts and a backoff delay between attempts.
- **R-07:** External calls (HTTP, database, message broker) must have an explicit timeout.

## Security
- **S-01:** No secrets, API keys or passwords in source code. Read them from configuration or a secret store.
- **S-02:** Never log sensitive data such as card numbers, passwords or tokens.

## Code quality
- **Q-01:** No magic numbers. Use a named constant for values with business meaning.
- **Q-02:** Use the SLF4J logger, never `System.out.println`, in production code.
- **Q-03:** Public methods must have a Javadoc comment.
