![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-6C3FD1.svg?style=flat&logo=kotlin&labelColor=white)
![Android](https://img.shields.io/badge/Android-34A853?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-gray?logo=apple&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-0078C0)
![WebAssembly](https://img.shields.io/badge/WebAssembly-654FF0?logo=webassembly&logoColor=white)
[![Maven Central](https://img.shields.io/badge/MavenCentral-1.0.1-064F8C?labelColor=white)](https://central.sonatype.com/artifact/com.akexorcist.kotlin.multiplatform/dayandnight)
![Apache 2.0](https://img.shields.io/badge/License-Apache%202-D22128?logo=apache&labelColor=white&logoColor=D22128)

# Day & Night Switch
Realistic and fancy switch that is inspired by a popular meme about Designer vs. Developer

![Preview](./public/preview.gif)

See an example on [GitHub Pages](https://akexorcist.github.io/day-and-night-switch)

## Highlight
* No image. All of UI components are written by Compose Multiplatform!
* Realistic light and shadow

## Powered by
* [Kotlin Multiplatform](https://www.jetbrains.com/kotlin-multiplatform/)
* [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)

## Usage
```kotlin
val version = "1.0.1"

// Common
implementation("com.akexorcist.kotlin.multiplatform:dayandnight:$version")

// Android
implementation("com.akexorcist.kotlin.multiplatform:dayandnight-android:$version")

// iOS
implementation("com.akexorcist.kotlin.multiplatform:dayandnight-iosx64:$version")
implementation("com.akexorcist.kotlin.multiplatform:dayandnight-iosarm64:$version")
implementation("com.akexorcist.kotlin.multiplatform:dayandnight-iossimulatorarm64:$version")

// Desktop
implementation("com.akexorcist.kotlin.multiplatform:dayandnight-desktop:$version")

// WebAssembly
implementation("com.akexorcist.kotlin.multiplatform:dayandnight-wasm-js:$version")
```

```kotlin
var selected by remember { mutableStateOf(true) }

// Switch Only
DayAndNightSwitch(
  modifier = Modifier,
  selected = selected,
  onSwitchToggle = { selected = !selected },
)

// With Container
DayAndNightContainer(
    modifier = Modifier.fillMaxSize(),
    selected = selected,
) {
    DayAndNightSwitch(
        modifier = Modifier.scale(2f),
        selected = selected,
        onSwitchToggle = { selected = !selected },
    )
}
```

## Run example
* **Android**: Run at example module with Android App configuration
* **Desktop**: Run `gradlew desktopRun -DmainClass=MainKt --quiet`
* **WebAssembly**: Run `gradlew wasmJsBrowserDevelopmentRun`
* **iOS**:
  * Run `gradlew build commonizeNativeDistribution`
  * Open `./iosApp/iosApp.xcodeproj` with Xcode
  * Run the app on Xcode

## Design reference
* https://designer-vs-developer-meme-with-sound.webflow.io/

## License
[Apache License Version 2.0](./LICENSE)
