# What this is

This little project came about as I wanted to explore REST API's using Spring Boot. The choice fell on making a simple URL shortener application.
NOTE! There are some things still missing from this project; such as proper error handling and code cleanup.
It was created over 3 hot summer days in July -25. Feel free to fork and make your own improvements.

## What this does
The application lets you enter a long URL and recieve a shortened version that redirects to the original. It also stores number of clicks on the shortened URL.

## Project Setup Instructions

### 1. Prerequisites
* Java 23
* Maven
* IntelliJ IDEA or any Java IDE (recommended)
* Git (optional, for version control)

### 2. Clone the Repository
Bash:
```
git clone https://github.com/your-username/your-repo.git
cd your-repo
```

### 3. Build the Project
With Maven:
```
./mvnw clean install
```

### 4. Run the Application
With Maven:
```
./mvnw spring-boot:run
```
Or you can just run it directly from your IDE by starting the main class (the one with the ```public static void main``` method).

### 5. Access the Application
The application will start on ```http://localhost:8080``` by default.

## API Endpoints
**Shorten URL:**
```
POST /api/shorten
```
Body: Raw JSON string of the original URL (e.g. ```"https://www.example.com"```)

**Redirect:**
```
GET /{shortCode}
```
Redirects to the original URL.

**Get click stats:**
```
GET /api/stats/{shortCode}
```
Returns stats (original URL, click count).

## Customization
Edit ```src/main/resources/application.properties``` to change server port, H2 settings, etc.

## Troubleshooting
* Ensure correct Java version.
* Make sure all dependencies are downloaded (run a build).
* Check for port conflicts on ```8080```.
