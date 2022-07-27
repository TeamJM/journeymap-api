How to use the [JourneyMap API](https://github.com/TeamJM/journeymap-api)
=============================

To hook into JourneyMap from your mod, you'll write a plugin class that handles all interactions with JourneyMap
via the API interfaces in this repository.

The JourneyMap API is designed so that your mod will only have a **soft dependency** on it:  

 * You should **only** have a compile-time dependency via your plugin implementation.
 * You should **not** need a runtime dependency. As long as you don't declare a dependency on "journeymap" in your mods.toml file, your mod should load even if JourneyMap doesn't.
 * You should **never** include any JourneyMap API classes in your mod's jar. (No shading is needed.)

This page describes the recommended approach to writing a plugin for the JourneyMap API:
 
I. Add the API Dependency
=============================

To find out which API version to use, run Minecraft with JourneyMap. Open the Options manager or any other dialog in JourneyMap,
and you'll see the API version in the corner of the screen.  The mod info dialog also displays this information.

1. Add [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cjourneymap-api) to your list of repositories
2. Add a compile dependency on 'info.journeymap:journeymap-api:#version'

For example:

```
#!gradle

// Version of JourneyMap API to use
journeymap_api_version = 1.18.1-1.8 // or 1.8-SNAPSHOT

// Note: None of the blocks below belong in your buildscript block. Put them below it instead.
repositories {
    // JourneyMap API releases are here
    maven {
        name = "JourneyMap (Public)"
        url = "https://jm.gserv.me/repository/maven-public/"
    }
    maven {
        url "https://www.cursemaven.com"
    }
}

configurations.all {
    // Check for snapshots more frequently than Gradle's default of 1 day. 0 = every build.
    resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
}

dependencies {
    compileOnly fg.deobf(group: 'info.journeymap', name: 'journeymap-api', version: project.journeymap_api_version, changing: true)
    runtimeOnly fg.deobf("curse.maven:journeymap-${project.jm_project_id}:${project.jm_file_id}")
}

```
Example mods.toml entry for a soft dependency. Set `mandatory=true` for a hard dependency if needed.
```
[[dependencies.mymodId]]
modId = "journeymap"
mandatory = false
versionRange = "[5.8.0,)"
ordering = "NONE"
side = "CLIENT"
```
*Note that the journeymap-api.jar is built with deobfuscated code so that it can be used at compile time and when
stepping through a debugger in your development environment.*

II. Look at the Example Code
=============================

* Look in the [example package](../src/main/java/example) for a complete 
example of a mod that has implemented a plugin for the JourneyMap API.

* You can put the `journeymap-api-*-examplemod.jar` in your runtime mods directory (usually `/run/mods`)
to see the Example Mod code in action.  To get the examplemod.jar, download it from 
[Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cjourneymap-api) or build this project.

*Note that the examplemod.jar is built with re-obfuscated code so that it can be used like a normal mod.*

III. Write your Plugin
=============================

1. Write a class that implements the JourneyMap *[journeymap.client.api.IClientPlugin](../src/main/java/journeymap/client/api/IClientPlugin.java)* interface (like '[ExampleJourneymapPlugin](src/main/java/example/mod/client/plugin/ExampleJourneymapPlugin.java)')
    - Annotate the class with *[@journeymap.client.api.ClientPlugin](../src/main/java/journeymap/client/api/ClientPlugin.java)* so that JourneyMap can find and instantiate it
    - Don't make references to this class elsewhere in your mod. You don't want it classloaded if JourneyMap isn't loaded.
1. Write other classes as needed that use JourneyMap API classes, but only refer to them from your Plugin class.
    - Don't make references to these classes elsewhere in your mod. You don't want them classloaded if JourneyMap isn't loaded.
    
IV. Test your Plugin
=============================

1. Using the following gradle configuration above, your mod will load journeymap and the api in your development environment.
2. Run Minecraft in your development environment.  Forge will load JourneyMap and your mod, and the JourneyMap API will activate your plugin.
