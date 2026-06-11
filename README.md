# Fundoo Notes Backend 📝

Hey there! Welcome to the backend for **Fundoo Notes**, a full-stack note-taking app inspired by Google Keep. 

This is the engine that drives the whole application. I built it using Spring Boot with a focus on speed, clean security, and handling heavy lifting in the background—like automating tasks and managing user data smoothly.

## 🛠️ Tech Stack & Services

I put this backend together using a modern Java stack and a few external services:
- **Framework:** Java 17 + Spring Boot 3
- **Database:** MySQL (with Spring Data JPA & Hibernate)
- **Caching:** Redis (for fast session management)
- **Message Queue:** RabbitMQ / CloudAMQP (for background processing)
- **Security:** Spring Security & JWT 
- **Emails:** Spring Mail + Gmail SMTP

## ✨ What This Backend Handles

Here is a look at what goes on under the hood:

### 🔐 Auth & Security
- Safe, straightforward user signup and login.
- Passwords are never stored in plain text—they are encrypted using BCrypt before hitting the database.
- Uses JWT tokens to keep user sessions secure and independent.
- **Forgot Password?** The app generates a temporary, secure token and emails a reset link directly to the user.

### 📝 Note Organizing
- Full CRUD features so users can create, read, update, and delete notes.
- Support for **Labels** to keep things organized.
- Core workspace management: users can **Pin** important notes, **Archive** old ones, or send items to the **Trash**.
- Input validation keeps things stable by stopping extra-long titles or text from breaking the database.

### ⏱️ Background Automation (The Fun Stuff!)
I integrated RabbitMQ to handle tasks asynchronously so the user interface never feels sluggish:
- **Reminders:** When a user sets a reminder, the app schedules a message via RabbitMQ and fires off an email alert at the exact timestamp.
- **Auto-Cleanup:** A simple background scheduler runs periodically to permanently wipe notes that have been sitting in the Trash for more than 7 days.

## 🚀 Running It Locally

To get this running on your machine, make sure you have MySQL, Redis, and RabbitMQ installed and active.

### Quick Setup
1. **Clone the project:**
   ```bash
   git clone https://github.com
   cd fundoo-notes-backend
   ```
2. **Set up your environment variables:** 
   To keep credentials safe, secrets are not hardcoded. Set these up in your IDE config or system environment before launching:
   ```properties
   JWT_SECRET=your_secret_key_here
   MAIL_USER=your_gmail@gmail.com
   MAIL_PASSWORD=your_google_app_password
   ```
3. **Prepare the database:** 
   The default `application-dev.properties` looks for a local MySQL database named `fundoonotes`. Go ahead and create that empty database; Hibernate will handle creating all the tables for you when the app boots up.
4. **Fire it up:** 
   Run `FundooNotesApplication.java` from your IDE, or spin it up from the terminal:
   ```bash
   ./mvnw spring-boot:run
   ```
   The backend boots up on `http://localhost:8082` by default.

## ☁️ Production Deployment

I have this fully configured to deploy on **Railway**. 

By passing the environment variable `SPRING_PROFILES_ACTIVE=prod`, the application seamlessly switches over to `application-prod.properties`. Instead of looking for localhost, it automatically hooks into my live cloud databases and CloudAMQP instance using secure environment variables injected through the Railway dashboard.

---
