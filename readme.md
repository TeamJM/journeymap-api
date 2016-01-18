[JourneyMap API](https://bitbucket.org/TeamJM/journeymap-api)
====

A plugin-style (soft dependency) API allowing other mods to create waypoints
and show overlays and markers within [JourneyMap for Minecraft](http://journeymap.info) 1.8.9+.

Before you Start Coding
===

This API is currently being developed and is subject to change. **It hasn't been implemented in JourneyMap yet.**

You're seeing it so you can provide feedback to Techbrew on Espernet IRC #journeymap before he finalizes 
it and starts implementing it in JourneyMap.

This page will be updated as development progresses.

Dependencies
===
The only dependencies for this API are for libraries already included with Minecraft and Forge:

* Guava
* Gson
* Forge
* JUnit

Changelog
===

**API v1.8.9-0.5**

* Overhauled design to use the annotated plugin approach recommended by the Forge team.
* Updated dependency to Forge 1.8.9

**0.4 and earlier**

* Iterative designs, now obsolete

Building Code in this Repository
===

You can simply get the API jar file (containing both source and classes) from the
[Downloads](https://bitbucket.org/TeamJM/journeymap-api/downloads) page, but if you want 
to build the source code, read on...

**Assumptions:**

1. You already know how to use Git and Gradle.
2. You already have Forge built in your local Gradle repo.

Simply run the Gradle 'build' task to generate the following:

* */build/libs/journeymap-api-#.jar*
* */build/libs/journeymap-api-#-javadoc.jar*
* */build/libs/journeymap-api-#-example.zip*

How to use the API classes in your Mod
===

The goal for using the JourneyMap API is that you have a **soft dependency** only.  

 * You should **only** have a compile-time dependency
 * You should **not** have a runtime dependency if JourneyMap isn't loaded
 * You should **never** include any JourneyMap API classes in your mod's jar. 

Here is the recommended approach:  (See [src/test/java/example](https://bitbucket.org/TeamJM/journeymap-api/src/master/src/test/java/example/mod/) examples)

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

All code in this journeymap-api repository is Copyright (c) Techbrew.  **All Rights Reserved.**

*However, the following limited rights are granted to you:*

**You MAY:**

* Write your own code that uses the API source code herein as a dependency.
* Distribute compiled classes of unmodified API source code which your code depends upon.
* Fork and modify API source code for the purpose of submitting Pull Requests to the this repository. Submitting new or modified code to the repository eans that you are granting Techbrew all rights over the code.

**You MAY NOT:**
 
* Submit any code to this repository with a different license than this one.
* Distribute modified versions of the API source code or compiled artifacts of modified API source code. In this context, "modified" means changes which have not been both approved and merged into this repository.
* Use or distribute the API code in any way not explicitly granted by this license statement.