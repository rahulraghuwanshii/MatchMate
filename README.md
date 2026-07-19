# MatchMate 🚀

MatchMate is a modern Android dating/networking application built with a focus on smooth user experience and clean architecture. It features a custom "Swipe Stack" UI for exploring profiles, powered by the latest Android Jetpack libraries.

## 📱 App Preview

| Explore/Discover Profiles | Matched Profiles |
| :---: | :---: |
| <img src="https://github.com/user-attachments/assets/89b3a91c-3f73-445a-82dd-9ee559b9f833" width="300" /> | <img src="https://github.com/user-attachments/assets/7b821ac2-5770-40e9-a1dd-abe6f1b41cc1" width="300" /> |

![image](https://github.com/user-attachments/assets/89b3a91c-3f73-445a-82dd-9ee559b9f833)
![image](https://github.com/user-attachments/assets/7b821ac2-5770-40e9-a1dd-abe6f1b41cc1)

### 🎬 Demo Video
![App Demo](./screenshots/app_demo.gif)
*(Replace with a link to your video or a GIF)*

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
