# URL Shortener API

A simple URL Shortener API built with Java 21 and Spring Boot. This application allows users to convert long URLs into short, unique ones and redirect from the short URL back to the original.

## Features

- Shorten long URLs using a hash-based approach (non-sequential)
- In-memory storage for URL mappings
- Input validation for both original and shortened URLs
- Redirection from short URL to original URL
- Simple, self-contained with no third-party shortening services
- Unit tests with high coverage

## Technologies

- Java 21
- Spring Boot 3.2.5
- JUnit 5
- Mockito
- Lombok
- Gradle

## Project Structure

```
url-shortener-api/
├── build.gradle
├── src/
│   ├── main/
│   │   ├── java/com/marcusmariette/
│   │   │   ├── controller/
│   │   │   ├── error/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── util/
│   │   └── resources/
│   │       └── postman/
│   └── test/
│       └── java/com/marcusmariette/
│           ├── controller/
│           ├── service/
│           └── util/
│       └── resources/
└── README.md
```

## Getting Started

### 1. Clone Repository

```bash
git clone https://github.com/marcusmariette/url-shortener-api.git
cd url-shortener-api
```

### 2. Install Dependencies

Ensure you have Java 21 and Gradle installed. Then run:

```bash
./gradlew build
```

### 3. Run the Application

```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`.

## API Usage

### Shorten URL

**POST** `/api/shorten`

**Request Body:**
```json
{
  "originalUrl": "https://example.com/some/long/path"
}
```

**Response:**
```json
{
  "originalUrl": "https://example.com/some/long/path",
  "shortUrl": "http://short.ly/abc123xy"
}
```

### Redirect to Original URL

**GET** `/api/redirect?shortUrl=http://short.ly/abc123xy`

- Redirects (302) to the original URL if found.
- Returns 404 if the short URL does not exist.
- Returns 400 for invalid short URLs.

## Running Tests

```bash
./gradlew test
```

Test results are shown in the console. If enabled, coverage reports can be found in `build/reports/tests/test/index.html`.

## License

This project is open-source and free to use.
