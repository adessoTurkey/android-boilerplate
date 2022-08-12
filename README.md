![GitHub Actions](https://github.com/adessoTurkey/android-boilerplate/workflows/PR%20Checks/badge.svg) 
[![CircleCI](https://circleci.com/gh/adessoTurkey/android-boilerplate.svg?style=shield)](https://circleci.com/gh/adessoTurkey/android-boilerplate) 
[![CodeStyle](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)
[![Kotlin Version](https://img.shields.io/badge/kotlin-1.6.10-blue.svg)](http://kotlinlang.org/)
[![Gradle](https://img.shields.io/badge/gradle-7.3.3-blue.svg)](https://lv.binarybabel.org/catalog/gradle/latest)
[![API](https://img.shields.io/badge/API-21%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Android Boilerplate

Android boilerplate app that showcases architecture and libraries used at adesso Turkey.

There are two simple list-detail pages to show basic implementations followed.

For more detailed implementation, please have a look [Android sample app][sample-app] developed with this Android boilerplate.

Movie list (Light)                                  |  Movie detail (Dark)
:-------------------------------------------------:|:-------------------------:
<img src="/images/ss_movie-list_light.png" width="300">  |  <img src="/images/ss_movie-detail_dark.png" width="300">


## Prerequisites

- #### API Key

To run the application, an API key from [TMBD](https://developers.themoviedb.org/3/getting-started/introduction) should be supplied. WIt should be added following variable to local environment:

`` API_KEY_TMDB = Your API Key ``

How to set an environment variable in [Mac](https://medium.com/@himanshuagarwal1395/setting-up-environment-variables-in-macos-sierra-f5978369b255) / [Windows](https://www.architectryan.com/2018/08/31/how-to-change-environment-variables-on-windows-10/)

- #### Keystore details (Release builds only)

To be able to build the application with release configs, keystore details should be set. It would be enough to rename `keystore.properties.sample` file as `keystore.properties`, and update the content.

Keystore file will be resolved at root level `build.gradle` file if the build variant is `release`.

Here is a sample `keystore.properties` file:

```
RELEASE.STORE_FILE=./movee.keystore
RELEASE.STORE_PASSWORD=password-for-store
RELEASE.KEY_ALIAS=alias-for-key
RELEASE.KEY_PASSWORD=password-for-key
```

## Development

### Code style [*](https://github.com/VMadalin/kotlin-sample-app)

To maintain the style and quality of the code, are used the bellow static analysis tools. All of them use properly configuration and you find them in the project root directory `config/.{toolName}`.

| Tools                     | Config file                            | Check command               | Fix command               |
|---------------------------|---------------------------------------:|-----------------------------|---------------------------|
| [detekt][detekt]          | [.detekt.yml](/config/.detekt.yml)     | `./gradlew detekt`          | -                         |
| [ktlint][ktlint]          | -                                      | `./gradlew ktlint`          | `./gradlew ktlintFormat`  |
| [spotless][spotless]      | -                                      | `./gradlew spotlessCheck`   | `./gradlew spotlessApply` |
| [lint][lint]              | [.lint.xml](/config/.lint.xml)         | `./gradlew lint`            | -                         |
|                           |                                        | __`./gradlew checkFormat`__ | __`./gradlew reformat`__  |

All these tools are integrated in [pre-commit git hook](https://git-scm.com/book/en/v2/Customizing-Git-Git-Hooks), in order
ensure that all static analysis and tests passes before you can commit your changes. To skip them for specific commit add this option at your git command:

```properties
git commit --no-verify
```

It's highly recommended to fix broken code styles. There is a gradle task [`reformat`](/build.gradle#64) which executes `ktlintFormat` and `spotlessApply` for you:

```properties
./gradlew reformat
```

There is another gradle task [`checkFormat`](/build.gradle#63) which executes `detekt`, `ktlint`, `spotlessCheck` and `lint` to check the code style:

```properties
./gradlew checkFormat
```

The pre-commit git hooks have exactly the same checks as [CircleCI](https://circleci.com/) and are defined in this [script](/config/scripts/git-hooks/pre-commit.sh). This step ensures that all commits comply with the established rules. However the continuous integration will ultimately be validated that the changes are correct.


If you want to know more about naming convention, code style and more please look at our [Android guideline](https://github.com/adessoTurkey/android-guideline) repository.

## Architecture

- Single Activity
- MVVM Pattern

**View:** Renders UI and delegates user actions to ViewModel

**ViewModel:** Can have simple UI logic but most of the time just gets the data from UseCase

**UseCase:** Contains all business rules and they written in the manner of single responsibility principle

**Repository:** Single source of data. Responsible to get data from one or more data sources

<img src="/images/architecture-diagram.png" width="500">

## Tech Stack

#### Dependencies

- **[Navigation Component](https://developer.android.com/jetpack/androidx/releases/navigation):** Consistent navigation between views
- **[LiveData](https://developer.android.com/topic/libraries/architecture/livedata):** Lifecycle aware observable and data holder
- **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel):** Holds UI data across configuration changes
- **[Databinding](https://developer.android.com/topic/libraries/data-binding/):** Binds UI components in layouts to data sources
- **[Hilt](https://dagger.dev/hilt/):** Dependency injector
- **[Coroutines](https://github.com/Kotlin/kotlinx.coroutines):** Asynchronous programming
- **[Glide](https://github.com/bumptech/glide):** Image loading and caching
- **[Retrofit](https://github.com/square/retrofit):** Type safe HTTP client
- **[Moshi](https://github.com/square/moshi):** JSON serializer/deserializer

#### Plugins

- **[Detekt][detekt]:** Static code analysis for Kotlin
- **[Spotless][spotless]:** Keep your code spotless
- **[Ktlint][ktlint]:** Kotlin linter
- **[Lint][lint]:** Static program analysis tools


## Join the crew!

[Act now to join][linkedin/jobs] our team and become an adessi — enjoy a Great Place to Work!

## License

```
Copyright 2022 adesso Turkey

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[sample-app]: https://github.com/adessoTurkey/android-sample-app
[detekt]: https://github.com/arturbosch/detekt
[ktlint]: https://github.com/pinterest/ktlint
[spotless]: https://github.com/diffplug/spotless       
[lint]: https://developer.android.com/studio/write/lint
[linkedin/jobs]: https://www.linkedin.com/jobs/view/1989162077
