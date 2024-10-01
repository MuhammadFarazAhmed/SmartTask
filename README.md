


# Clean Architecture

The core principles of the clean approach can be summarized as followed:

#### 1. The application code is separated into layers.

These layers define the separation of concerns inside the code base.

#### 2. The layers follow a strict dependency rule.

Each layer can only interact with the layers below it.

#### 3. As we move toward the bottom layer — the code becomes generic.

The bottom layers dictate policies and rules, and the upper layers dictate implementation details such as the database, networking manager, and UI.


# Architecture Layers

The application consists of three layers:

The domain layer, the data layer, and the presentation layer.

Looking at project’s high-level structure, you’ll see that each layer is represented by a module in the project.

I like it because it helps me avoid accidentals “leaks” between the layers.

Data layer is based on abstraction rather than concrete implementations.

We can easily switch between Different data sources in the repository. according to the build variant .

Use MVI and MVVM patterns in the presentation layer for separation of concerns.


## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous and more..
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/) - A cold asynchronous data stream that sequentially emits values and completes normally or with an exception.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - A live data replacement.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for building native UIs.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
  - [Navigation Components](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigate fragments easier.
  
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
  - [Koin](https://insert-koin.io/) - Easier way to incorporate Dagger DI into Android application.


