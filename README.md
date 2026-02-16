# Class Reminder – Android App

A simple Android app that helps students never miss scheduled classes again.

This project is being built in stages to simulate real-world incremental development. Each commit represents a meaningful feature addition.

---

## Project Goal

The goal of this app is to solve a real problem: forgetting online classes.

Instead of building everything at once, this project evolves step-by-step:

- schedule a class
- trigger reminders
- track attendance
- show basic stats

This repository demonstrates Android fundamentals like background services, notifications, and local data storage.

---

## Tech Stack

- Kotlin
- Android SDK
- AlarmManager
- Notifications
- Room Database (planned)
- MVVM architecture (planned)

---

## Development Roadmap

### Stage 1 – Basic UI

Status: ✅ Completed / ⬜ In Progress

- Simple main screen
- Add class title
- Select time
- Save schedule locally

Goal: Create a working UI and input flow.

---

### Stage 2 – Reminder System

Status: ⬜ Planned

- AlarmManager integration
- Notification before class
- Notification at class start
- BroadcastReceiver handling

Goal: Automated reminders even when the app is closed.

---

### Stage 3 – Attendance Tracking

Status: ⬜ Planned

- Mark class as attended
- Store daily attendance
- Simple attendance history list

Goal: Track consistency and build data storage logic.

---

### Stage 4 – Dashboard

Status: ⬜ Planned

- Today's class summary
- Countdown to next class
- Attendance percentage

Goal: Turn the app into a useful daily dashboard.

---

### Stage 5 – Improvements (Optional)

- Multiple classes
- Dark mode
- Widgets
- Firebase sync
- Streak system

---

## Why This Project Exists

This is a learning project focused on:

- real problem solving
- clean commit history
- incremental feature development
- Android architecture practice

It’s intentionally simple, but structured like a real product.

---

## How to Run

1. Clone the repository
2. Open in Android Studio
3. Build and run on emulator or device

---

## Future Ideas

- Weekly timetable view
- Smart snooze reminders
- Statistics screen
- Export attendance data

---

## Author

Built as a student Android learning project.
