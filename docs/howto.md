How to use the [JourneyMap API](src/master/readme.md)
=============================

To hook into JourneyMap from your mod, you'll write a plugin class that handles all interactions with JourneyMap
via the API interfaces in this repository.

The JourneyMap API is designed so that your mod will only have a **soft dependency** on it:  

 * You should **only** have a compile-time dependency via your plugin implementation.
 * You should **not** need a runtime dependency. As long as you don't declare a dependency on "journeymap" in your mcmod.info file, your mod should load even if JourneyMap doesn't.
 * You should **never** include any JourneyMap API classes in your mod's jar. (No shading is needed.)

This page describes the recommended approach to writing a plugin for the JourneyMap API:
 
I. Add the API Dependency
=============================

1. Add [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cjourneymap-api) to your list of repositories
2. Add a compile dependency on 'info.journeymap:journeymap-api:#version'

For example:

```
#!gradle
buildscript {
    repositories {
        // JourneyMap API artifacts are hosted in Maven Central
        mavenCentral()
    }
}

dependencies {
    // Add JourneyMAP API to classpath
    compile 'info.journeymap:journeymap-api:1.8.9-1.0'
}

```

*Note that the journeymap-api.jar is built with deobfuscated code so that it can be used at compile time and when
stepping through a debugger in your development environment.*

II. Look at the Example Code
=============================

* Look in the [example package](src/master/src/main/java/example) for a complete 
example of a mod that has implemented a plugin for the JourneyMap API.

* You can put the `journeymap-api-*-examplemod.jar` in your runtime mods directory (usually `/run/mods`)
to see the Example Mod code in action.  To get the examplemod.jar, download it from 
[Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cjourneymap-api) or build this project.

*Note that the examplemod.jar is built with re-obfuscated code so that it can be used like a normal mod.*


III. Write your Plugin
=============================

1. Write a class that implements the JourneyMap *[journeymap.client.api.IClientPlugin](src/master/src/main/java/journeymap/client/api/IClientPlugin.java)* interface (like '[ExampleJourneymapPlugin](src/master/src/main/java/example/mod/client/plugin/ExampleJourneymapPlugin.java)')
    - Annotate the class with *[@journeymap.client.api.ClientPlugin](src/master/src/main/java/journeymap/client/api/ClientPlugin.java)* so that JourneyMap can find and instantiate it
    - Don't make references to this class elsewhere in your example.mod. You don't want it classloaded if JourneyMap isn't loaded.
1. Define a facade interface (like '[IExampleMapFacade](src/master/src/main/java/example/mod/client/facade/IExampleMapFacade.java)') for your example.mod's map-related functions (like 'showWaypoint(x,y,z)')
    - The facade interface will always be classloaded, so it should not use JourneyMap API classes. Use primitives or your own objects.
    - Add a facade interface field to your example.mod's ClientProxy class (like '[ClientProxy.MapFacade](src/master/src/main/java/example/mod/client/ClientProxy.java)') so it can be used by your example.mod.
1. Write an implementation of your facade interface (like '[ExampleMapFacade](src/master/src/main/java/example/mod/client/plugin/ExampleMapFacade.java)') that uses the JourneyMap [IClientAPI](src/master/src/main/java/journeymap/client/api/IClientAPI.java) interface to perform the actual facade work needed.
    - Have your IClientPlugin create an instance of the facade implementation using the [IClientAPI](src/master/src/main/java/journeymap/client/api/IClientAPI.java) provided by JourneyMap.
    - Have your IClientPlugin set the instance on your example.mod's ClientProxy class (like '[ClientProxy.MapFacade](src/master/src/main/java/example/mod/client/ClientProxy.java)')
    - Don't make references to this implementation class elsewhere in your mod. You don't want it classloaded if JourneyMap isn't loaded.
    
IV. Test your Plugin
=============================

**Note: There is not a release of JourneyMap that implements the API yet. It's coming soon.**

1. [Download JourneyMap](http://journeymap.info/Download) and place it in your runtime mods directory (usually `/run/mods`). 
You don't need a "dev" or "deobf" version of JourneyMap; Forge 1.8-11.14.3.1503 or later now handles automatic deobfuscation for you.
2. Run Minecraft in your development environment.  Forge will load JourneyMap and your mod, and the JourneyMap API will activate your plugin.