[![Build](https://github.com/eirikb/FleetUI/actions/workflows/build.yml/badge.svg)](https://github.com/eirikb/FleetUI/actions/workflows/build.yml)

# FleetUI Demo
![image](https://user-images.githubusercontent.com/241706/201476692-be4520c9-9cb9-45fa-b556-d2e3fc6de2bb.png)  
![image](https://user-images.githubusercontent.com/241706/201476698-9276d762-0fad-49d5-91ac-c9be41792446.png)

This is a stand-alone demo of the UI used in [Fleet](https://www.jetbrains.com/fleet).  
Fleet has its own UI, called Noria. Contrary to what some might think it does not use Compose.  
JetBrains made Noria because Compose was not available at the time when Fleet was created.  
Noria is similar to Compose, and uses [Skiko](https://github.com/JetBrains/skiko) as a binding to Skia.  
In theory it can build to Windows, Linux, MacOS, Android, iOS and web.

This project tries to use Noria outside of Fleet.  
Making it possible for devs to make a full GUI, like Compose or flutter.

Build:

```bash
./gradlew build
```

Run:

```bash
./gradlew run
```

This will run the gallery app, with example components.

## Thanks to JetBrains

This would not have been possible without a ton of help from Manuel Unterhofer and Sergey Ignatov at JetBrains.  
They have also allowed me to share this project online.

## FAQ

**Q**: Wait what, Fleet doesn't use Compose?  
**A**: No it does not.  
Not JetPack Compose, not JetBrains Compose

**Q**: Is this a whole new UI?  
**A**: Yes

**Q**: Are you allowed to share this?  
**A**: Yes

**Q**: Can this be used in production?  
**A**: At the moment, Discouraged

**Q**: Does this work on all platforms?  
**A**: Haven't tried, but don't think so, Kotlin multiplatform would require source code which is not yet available for
Noria

**Q**: Where are the gallery example code?  
**A**: I have them, but haven't asked if I can share them yet
