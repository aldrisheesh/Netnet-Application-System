# Netnet Application System

This repository contains the source code for **Netnet Application System**, a Java based desktop application built with Swing. It features a multi–step sign up process, account management pages and integration with a MySQL backend.

## Project Structure

```
application-system/
├── pom.xml              # Maven build file
├── src
│   ├── main
│   │   ├── java         # Java source code
│   │   └── resources    # Fonts, icons and images used by the UI
│   └── test
│       └── java         # Unit tests (placeholder)
└── target               # Build output (jar and dependencies)
```

The main package is `com.group_9.project` and includes multiple UI screens such as `Homepage`, `LoginPage`, `PlansPage` and step based sign–up frames. Utility classes reside in `com.group_9.project.utils` while database connectivity and services live under `com.group_9.project.database`.

## Building

The project uses **Maven**. Ensure you have JDK 21 installed then run:

```bash
mvn -f application-system/pom.xml package
```

This will create `application-system-1.0-SNAPSHOT.jar` in `application-system/target` along with a Windows batch script `Run_FiberXpress.bat` to launch it.

## Running

Navigate to the `application-system/target` directory and execute:

```bash
java -jar application-system-1.0-SNAPSHOT.jar
```

The application starts at `com.group_9.project.Main` which shows the homepage and guides users through account creation and plan selection.

## Database

The application expects a MySQL server running locally with a database named `fiberxpress` (see `DatabaseConnection` for credentials). The schema includes tables such as `tbl_residence`, `tbl_customer`, `tbl_application`, `tbl_payment` and `tbl_service`. Application data is stored using `ApplicationService` which ensures transactions and validation.

## Testing

A basic test class is provided in `src/test/java` and Maven Surefire/Failsafe plugins are configured for unit and integration tests.

## License

This project is provided as‑is for educational purposes.
