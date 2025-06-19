# Netnet Application System

This repository contains a Java based desktop application packaged with Maven. The application provides multiple GUI screens to sign up for a service, manage accounts and interact with a backend MySQL database.

## Project Structure

```
application-system/
├── pom.xml              # Maven configuration
└── src/
    ├── main/
    │   ├── java/com/group_9/project       # Application classes
    │   │   ├── *.java
    │   │   ├── database/                  # Database helpers
    │   │   ├── session/                   # Session data store
    │   │   └── utils/                     # UI utilities
    │   └── resources/                     # Fonts, icons and images
    └── test/java/com/group_9/project      # Unit tests
```

### Main Screens (com.group_9.project)

| File                     | Purpose |
|--------------------------|---------|
| `AboutUsPage.java`       | Display team information. |
| `AccountAddressPage.java`| Manage the user address information. |
| `AccountDetailsPage.java`| Show account details retrieved from the database. |
| `AccountSubsPage.java`   | Display subscription information. |
| `AddConfirm.java`        | Confirmation page when adding a plan. |
| `AddPlansPage.java`      | Screen to select additional plans. |
| `ErrorPage.java`         | Generic error message window. |
| `HelpSupportPage.java`   | Help and support contact form. |
| `Homepage.java`          | Landing page with navigation. |
| `LoginPage.java`         | Log in form for existing users. |
| `PlansPage.java`         | Lists internet plans. |
| `SignUp1.java`–`SignUp6.java` | Multi-step sign up workflow. |
| `Template.java`          | Base frame used for quick testing of components. |
| `TrackingPage.java`      | Track application status. |

### Database Utilities (com.group_9.project.database)

| File                        | Purpose |
|-----------------------------|---------|
| `AccountService.java`       | Fetch account information from the database. |
| `ApplicationService.java`   | Create new service applications. |
| `DatabaseConnection.java`   | Handles MySQL connection logic. |
| `LoginAuth.java`            | Validate login credentials. |
| `PaymentDao.java`           | Persist payment details. |

### Session Store (com.group_9.project.session)

| File                     | Purpose |
|--------------------------|---------|
| `UserApplicationData.java` | Static map holding data between screens. |

### UI Helpers (com.group_9.project.utils)

Utility classes used throughout the screens.

| File                           | Description |
|--------------------------------|-------------|
| `AccountNavigationUtil.java`   | Opens account pages using session data. |
| `AccountSidebarUtil.java`      | Generates the account sidebar component. |
| `BackgroundPanel.java`         | JPanel capable of rendering a background image. |
| `BaseFrameSetup.java`          | Common frame initialization routines. |
| `ButtonHoverEffect.java`       | Applies hover/press styles to buttons. |
| `CreateStepTracker.java`       | Visual component to show signup progress. |
| `CustomDialogUtil.java`        | Styled dialog windows (error, info). |
| `FontUtil.java`                | Loads custom font resources. |
| `FormComponent.java`           | Factory for rounded form inputs. |
| `LengthLimitFilter.java`       | Document filter that limits text length. |
| `RoundedComponents.java`       | Contains rounded Swing components. |
| `RoundedPanel.java`            | JPanel with rounded corners. |
| `SmartFieldFormatter.java`     | Formatting helpers for text fields. |
| `ToolTipUtil.java`             | Adds formatted tool tips. |
| `ValidationUtil.java`          | Common field validation logic. |

### Resources

- **fonts/** – Custom font files loaded by `FontUtil`.
- **icons/** – Icons referenced across pages.
- **images/** – Backgrounds, team photos and graphics.

### Building & Running

The project uses Maven. To compile and run tests:

```bash
mvn clean test
```

To package the application as a WAR/JAR:

```bash
mvn package
```

The output will be placed under `application-system/target`.

