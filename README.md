# Chinese Checkers

A multiplayer Chinese Checkers game implemented in Java with both console and JavaFX GUI support. The project features a custom client-server architecture, move validation logic, and persistence via Spring Boot and MySQL.

---

## 🧩 Features

- ✅ Playable via **console or JavaFX GUI**
- ✅ Supports **multiplayer over sockets**
- ✅ Game rule enforcement (legal moves, hops, turns)
- ✅ Modular and well-structured codebase
- ✅ Database integration via **Spring Boot + JPA**
- ✅ Unit tests with **JUnit** and **Mockito**

---

## 🛠️ Tech Stack

| Layer        | Technologies Used                       |
|-------------|-------------------------------------------|
| Language     | Java (100%)                              |
| UI           | JavaFX                                   |
| Backend      | Spring Boot, Socket Programming          |
| Persistence  | Spring Data JPA, MySQL                   |
| Build Tool   | Maven                                    |
| Testing      | JUnit 4 & 5, Mockito                     |

---

## 📁 Project Structure

pwr.tp  
├── client       # Console client & JavaFX GUI  
├── server       # Game host server  
├── domain       # Message classes & domain models  
├── game         # Core game engine  
├── gameplay     # Turn/order management  
├── movement     # Move validation rules  
└── utilityClases # Helpers (e.g., error pop-ups)

