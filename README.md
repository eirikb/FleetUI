[![Build](https://github.com/eirikb/FleetUI/actions/workflows/build.yml/badge.svg)](https://github.com/eirikb/FleetUI/actions/workflows/build.yml)

# FleetUI

```kotlin
vbox {
    uiText("Hello, world!")
}
```

![image](https://user-images.githubusercontent.com/241706/201476692-be4520c9-9cb9-45fa-b556-d2e3fc6de2bb.png)  
![image](https://user-images.githubusercontent.com/241706/201476698-9276d762-0fad-49d5-91ac-c9be41792446.png)
![image](https://user-images.githubusercontent.com/241706/203863492-5eac77af-bba3-4593-8590-33a662c84c45.png)
![image](https://user-images.githubusercontent.com/241706/203863597-e8a4938a-ba94-4833-a4c6-29d41f33158a.png)
![image](https://user-images.githubusercontent.com/241706/203863798-03385dee-246c-4beb-8df2-7de1a80c85eb.png)
![image](https://user-images.githubusercontent.com/241706/203863819-5e09af82-d1fd-475f-9e23-205813e48e2e.png)

This is a stand-alone demo of the UI used in [Fleet](https://www.jetbrains.com/fleet).  
Fleet has its own UI, called Noria. Contrary to what some might think it does not use Compose.  
JetBrains made Noria because Compose was not available at the time when Fleet was created.  
Noria is similar to Compose, and uses [Skiko](https://github.com/JetBrains/skiko) as a binding to Skia.  
In theory it can build to Windows, Linux, MacOS, Android, iOS and web.

This project tries to use Noria outside of Fleet.  
Making it possible for devs to make a full GUI, like Compose or Flutter.

Build:

```bash
./gradlew build
```

Run:

```bash
./gradlew run
```

This will run the gallery app, with example components.

Requires JDK 17, preferable [JBR 17](https://github.com/JetBrains/JetBrainsRuntime/releases).

## Examples

All the gallery examples are located
at [app/src/main/resources/examples](https://github.com/eirikb/FleetUI/tree/main/app/src/main/resources/examples)

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
**A**: At the moment; discouraged

**Q**: Does this work on all platforms?  
**A**: Not yet. All JVM desktop platforms should work (Windows, Linux, MacOS)
