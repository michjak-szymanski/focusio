# FocusIO

Open-source multiplatform focus/pomodoro timer. Provides GUI as wel as CLI.
Supports Linux, Windows, Android and macOS and web (provided or deployable).

### Technical details (to be deleted)
- CLI is written in Java and compiled by GraalVM to native image
- GUI is provided by Jetpack Compose (Kotin) and it's using Project Panama to use CLI native binary
- CLI is distributed with or without GUI
- web version needs to be lightweight (by size and memory usage), it can be deployed anywhere but one instance is provided by myself. GUI write in React or Jetpack Compose.