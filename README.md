# MatchMate 🚀

MatchMate is a modern Android dating/networking application built with a focus on smooth user experience and clean architecture. It features a custom "Swipe Stack" UI for exploring profiles, powered by the latest Android Jetpack libraries.

## 📱 App Preview

| Explore/Discover Profiles | Matched Profiles |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/85484878-3bfc-439e-9ecf-1ce38e782c60" width="300" /> | <img src="https://github.com/user-attachments/assets/3555bad8-be3a-4aa5-930d-1418fc4507fc" width="300" /> |


### 🎬 Demo Video
https://github.com/user-attachments/assets/77509c71-038b-4470-b884-384c41a2794d

---

## ✨ Features

- **Tinder-style Swipe Interface**: A custom `SwipeStackLayoutManager` that renders profiles in a deck.
- **Infinite Discovery**: Integrated with **Paging 3** for seamless, endless scrolling/swiping through profiles.
- **Smart Prefetching**: Custom logic to trigger data loading before the user reaches the end of the stack.
- **Clean Architecture**: Strictly follows Domain, Data, and Presentation layer separation.
- **Offline Support**: Local caching and persistence using **Room Database**.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI Framework**: Native Android (XML with ViewBinding)
- **Asynchronous Flow**: Coroutines & Flow
- **Dependency Injection**: Hilt (Dagger)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture
- **Networking**: Retrofit & OkHttp
- **Database**: Room
- **Pagination**: Paging 3
- **Image Loading**: Glide

---
