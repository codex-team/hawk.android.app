# HAWK #

This is mobile adaptation for web site. You can find all the information about the site on [Hawk.so](https://github.com/codex-team/hawk) repository.

## Structure ##
The application development based on the MVP architectural pattern.
 
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

