# Hawk #

This is an Android app for Hawk. You can find all the information about the project on [Hawk.so](https://github.com/codex-team/hawk) repository.

## Structure ##
The application development based on the MVP architectural pattern.
 
The application is divided into several modules, each of them is responsible for its layer in the application

Modules:
* `uicomponent` is the layer with a custom view and styles for an application.
* `codexbl` is the layer with business logic for an application and presenters for communication with UI logic.
* `hawkapi` is the layer with methods for communication with the server.
* `SourceInterface` is the model storing interfaces describing access to any source — API or other

## Libraries ##

Next libraries are used in the application:
* [Koin](https://github.com/InsertKoinIO/koin)
* [RxJava2](https://github.com/ReactiveX/RxJava)
* [Retrofit2](https://github.com/square/retrofit)
* [Apollo](https://github.com/apollographql/apollo-android)

## References

You can find useful information following the next links:

* [Design of an application](https://app.zeplin.io/project/5b4f2f07729e51c208c27414?seid=5d277835f400e26b00899977)
* [New design of an application](https://www.figma.com/file/DoU67iXlU5FjBKXBfwPd3l/HawkMobile?node-id=173%3A5752)
* [Site of Hawk project](https://stage.hawk.so/)
* [GraphQL playground](https://api.stage.hawk.so/graphql) (You can find scheme for GraphQL and send requests)
* [New web client](https://github.com/codex-team/hawk.garage)
