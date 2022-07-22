# business-card

"Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option"
https://stackoverflow.com/questions/70992947/how-do-i-resolve-error-message-inheritance-from-an-interface-with-jvmdefault
https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/

include in build.gradle (module
```gradle
    kotlinOptions {
        freeCompilerArgs += [
            "-Xjvm-default=all",
        ]
    }
```

