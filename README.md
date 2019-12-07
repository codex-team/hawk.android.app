# Hawk #

This is mobile adaptation for web site. You can find all the information about the site on [Hawk.so](https://github.com/codex-team/hawk) repository.

## Structure ##

The application is divided on several modules, each of where is responsible for his layer in the application

Modules:
* uicomponent is layer there declared custom view for application with styles for it.
* codexbl is layer there declared business logic for application and presenters for communication with ui logic.
* hawkapi is layer with methods for communication with server.
* SourceInterface is layer there declared all interfaced for source. That source provide methods for getting information from server, databases or other source.

## Libraries ##

In the application used next libraries:
* [Koin](https://github.com/InsertKoinIO/koin)
* [RxJava2](https://github.com/ReactiveX/RxJava)
* [Retrofit2](https://github.com/square/retrofit)
* [Apollo](https://github.com/apollographql/apollo-android)

## References

You can find useful information on next links:

* [Design of application](https://app.zeplin.io/project/5b4f2f07729e51c208c27414?seid=5d277835f400e26b00899977)
* [New design of application](https://www.figma.com/file/DoU67iXlU5FjBKXBfwPd3l/HawkMobile?node-id=173%3A5752)
* [Site of hawk project](https://stage.hawk.so/)
* [GraphQL playground](https://api.stage.hawk.so/graphql) (You can find scheme for GraphQL and send requests)
* [New web client](https://github.com/codex-team/hawk.garage)
