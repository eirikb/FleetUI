# FleetUI Demo

This is a stand-alone demo of the UI used in [Fleet](https://www.jetbrains.com/fleet).  
Fleet has its own UI, called Noria. Contrary to what some might think it does not use Compose.  
JetBrains made Noria because Compose was not available at the time when Fleet was created.

This project tries to use Noria outside of Fleet.  
Making it possible for devs to make a full GUI, like Compose or flutter.  
Noria uses [Skiko](https://github.com/JetBrains/skiko), and can in theory build to Windows, Linux, MacOS, Android, iOS
and web.

Build:

```bash
./gradlew clean build
```

Run:

```bash
./gradlew run
```
