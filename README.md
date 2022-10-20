# FlowUI

PoC for noria stand-alone from Fleet.  
Gradle setup based on _gradle init_ + app.  
Requires Fleet to be installed.

First:  
Check if `fleetInstallDir` in _app/build.gradle.kts_ is correct.

Note:  
Will only work on Linux.  
Tested on Arch Linux with liberica-jdk-17-full.


Build:

```bash
./gradlew clean build
```

Run:

```bash
./gradlew run
```
