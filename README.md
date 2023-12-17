# The Bears
The Bears is a Kotlin multi-module Android application that allows users to log in with their email and password using Firebase Authentication.
Once logged in, users can observe real-time cryptocurrency prices with socket connection.

# Project Structures
![Logo](https://i.hizliresim.com/dzhznhz.jpg)

## Project Tech stack & Open-source libraries

- This app made %100 with  [Kotlin](https://developer.android.com/kotlin)

- Made with [Android Architecture Components ](https://developer.android.com/topic/architecture)for the Collection of libraries that help you design robust, testable, and maintainable apps.

- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel):The ViewModel class is a business logic or screen level state holder. It exposes state to the UI and encapsulates related business logic. Its principal advantage is that it caches state and persists it through configuration changes. This means that your UI doesn’t have to fetch data again when navigating between activities, or following configuration changes, such as when rotating the screen.

- [Kotlin Flow](https://developer.android.com/kotlin/flow):In coroutines, a flow is a type that can emit multiple values sequentially, as opposed to suspend functions that return only a single value. For example, you can use a flow to receive live updates from a database.

- [Kotlin Coroutine](https://developer.android.com/kotlin/coroutines):On Android, coroutines help to manage long-running tasks that might otherwise block the main thread and cause your app to become unresponsive. Over 50% of professional developers who use coroutines have reported seeing increased productivity. This topic describes how you can use Kotlin coroutines to address these problems, enabling you to write cleaner and more concise app code.

- [Dependency Injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android):Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Doing manual dependency injection requires you to construct every class and its dependencies by hand, and to use containers to reuse and manage dependencies.

- [Navigation Componenet](https://developer.android.com/guide/navigation):Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app. Android Jetpack's Navigation component helps you implement navigation, from simple button clicks to more complex patterns, such as app bars and the navigation drawer.

- [Retrofit](https://square.github.io/retrofit/):Retrofit is the class through which your API interfaces are turned into callable objects. By default, Retrofit will give you sane defaults for your platform but it allows for customization.

- [UseCase](https://developer.android.com/topic/architecture/domain-layer):Located domain layer that sits between the UI layer and the data layer.

- [Repository](https://developer.android.com/topic/architecture/data-layer):Located in data layer that contains application data and business logic.

- [Firebase Auth ](https://firebase.google.com/docs/auth?hl=tr):Firebase Authentication provides backend services, easy-to-use SDKs, and ready-made UI libraries to authenticate users to your app.

- [Mockito ](https://site.mockito.org/):A mocking framework that tastes really good. It lets you write beautiful tests with a clean & simple API.

- [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver):A scriptable web server for testing HTTP clients.

- [Truth ](https://truth.dev/):A library for performing assertions in tests.

- [Turbine ](https://github.com/cashapp/turbine):A small testing library for kotlinx.coroutines Flow.

# Modularization 

In this project has different modularazitaion 

## App Module

![Logo](https://i.hizliresim.com/r7zmjz8.jpg)

- In the app module using shared navigation. App module only have "Main Activity" and "Application Class"


## Feature Module

![Logo](https://i.hizliresim.com/i6wtn66.jpg)

- Feature module have 4 more :feature module;
  
  1- feature:signin
  
  - Responsible for user authentication using Firebase Authentication.

  2- feature:signup
  
  - Responsible for user authentication using Firebase Authentication.

  3- feature:profile
  
  - Responsible for user info and displaying Crypto Price from Rest Api service, And showing the socket connection status
  
  4- feature:home

  - Responsible for showing the socket connection status , real time "BTC" price getting the from websocket and adding the price value to chart view
 
## Localization Module

- Supports multiple languages for a global user base.

  
## Common Module

  This module have;
-  Extension ,
-  Base Adapter,
-  Base Fragment,
-  Base ViewHolder 


    


