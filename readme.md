[JourneyMap API](https://bitbucket.org/TeamJM/journeymap-api)
====

- - - -

**Note:** This API is currently being developed and is subject to change. It hasn't been implemented in JourneyMap yet. You're seeing it so you can provide feedback to Techbrew on Espernet IRC #journeymap before he finalizes 
it and starts implementing it in JourneyMap. This page will be updated as development progresses.

- - - -

A plugin-style (soft dependency) API allowing other mods to create waypoints and show overlays and markers within [JourneyMap for Minecraft](http://journeymap.info) 1.8.9+.

The JourneyMap API is designed so that your mod will only have a **soft dependency** on it:  

 * You should **only** have a compile-time dependency via your plugin implementation.
 * You should **not** need a runtime dependency. As long as you don't declare a dependency on "journeymap" in your mcmod.info file, your mod should load even if JourneyMap doesn't.
 * You should **never** include any JourneyMap API classes in your mod's jar. No shading is needed.

Changelog
===

**API v1.8.9-0.6**

* Build now produces a `journeymap-api-*-deobf.jar` for use in a dev environment and a `journeymap-api-*-examplemod.jar` for use in the `run/mods` directory
* Method signature changes and bugfixes in PluginHelper

**API v1.8.9-0.5**

* Overhauled design to use the annotated plugin approach recommended by the Forge team.
* Updated dependency to Forge 1.8.9

**0.4 and earlier**

* Iterative designs, now obsolete

How to use the JourneyMap API in your development environment
===

1. Either get the files you'll need from the [Downloads](https://bitbucket.org/TeamJM/journeymap-api/downloads) page, or clone
this repository and run `gradlew setupDecompWorkspace build`.

2. Put the `journeymap-api-*-deobf.jar` and the `journeymap-api-*-sources.jar` in your project's `/libs` directory.  
(Create the directory if you don't have one.)

3. Update your Gradle build's dependencies (if needed) to find jars in the `/libs` directory.  For example:

~~~~groovy
dependencies {
    compile fileTree(dir: 'libs', include: '*.*');
}
~~~~

Optional: You can put the `journeymap-api-*-examplemod.jar` in your runtime mods directory (usually `/run/mods`)
to see the Example Mod code in action.

How to write a plugin for the JourneyMap API in your mod.
===

Here is the recommended approach:

1. Write a class that implements the JourneyMap *[journeymap.client.api.IClientPlugin](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/journeymap/client/api/IClientPlugin.java)* interface (like '[ExampleJourneymapPlugin](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/example/mod/client/plugin/ExampleJourneymapPlugin.java)')
    - Annotate the class with *[@journeymap.client.api.ClientPlugin](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/journeymap/client/api/ClientPlugin.java)* so that JourneyMap can find and instantiate it
    - Don't make references to this class elsewhere in your example.mod. You don't want it classloaded if JourneyMap isn't loaded.
1. Define a facade interface (like '[IExampleMapFacade](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/example/mod/client/facade/IExampleMapFacade.java)') for your example.mod's map-related functions (like 'showWaypoint(x,y,z)')
    - The facade interface will always be classloaded, so it should not use JourneyMap API classes. Use primitives or your own objects.
    - Add a facade interface field to your example.mod's ClientProxy class (like '[ClientProxy.MapFacade](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/example/mod/client/ClientProxy.java)') so it can be used by your example.mod.
1. Write an implementation of your facade interface (like '[ExampleMapFacade](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/example/mod/client/plugin/ExampleMapFacade.java)') that uses the JourneyMap [IClientAPI](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/journeymap/client/api/IClientAPI.java) interface to perform the actual facade work needed.
    - Have your IClientPlugin create an instance of the facade implementation using the [IClientAPI](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/journeymap/client/api/IClientAPI.java) provided by JourneyMap.
    - Have your IClientPlugin set the instance on your example.mod's ClientProxy class (like '[ClientProxy.MapFacade](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/main/java/example/mod/client/ClientProxy.java)')
    - Don't make references to this implementation class elsewhere in your example.mod. You don't want it classloaded if JourneyMap isn't loaded.

Help Wanted
===
If you have suggestions or improvements to the API structure, feel free to make Pull Requests. Chatting with the TeamJM
developers in Espernet IRC #journeymap is highly suggested.  Before you change anything or submit code, however, be sure
to read the **Licence Information** below.

**Areas of help needed:**

* Unit tests!
* Utility classes utilizing [java.awt.geom.Area.add()](https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Area.html) to 
create optimized polygons comprised of multiple chunks.

License Information
===

**All code in this journeymap-api repository is Copyright (&copy;) Techbrew. All Rights Reserved.**

*However, the following limited rights are granted to you:*

**You MAY:**

* Write your own code that uses the API source code herein as a dependency.
* Distribute compiled classes of unmodified API source code which your code depends upon.
* Fork and modify API source code for the purpose of submitting Pull Requests to the this repository. Submitting new or modified code to the repository eans that you are granting Techbrew all rights over the code.

**You MAY NOT:**
 
* Submit any code to this repository with a different license than this one.
* Distribute modified versions of the API source code or compiled artifacts of modified API source code. In this context, "modified" means changes which have not been both approved and merged into this repository.
* Use or distribute the API code in any way not explicitly granted by this license statement.