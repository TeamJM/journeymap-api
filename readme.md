[JourneyMap API](https://bitbucket.org/TeamJM/journeymap-api)
====

An API for integrating mods with [JourneyMap for Minecraft](http://journeymap.info).  Allows mods to create waypoints
and place overlays and markers in one or more map displays within JourneyMap.

Integrating this Code into your Mod
===

This API is currently being developed and is likely to change. 

**It doesn't work with JourneyMap yet.**

You're seeing it so you can provide feedback to Techbrew on Espernet IRC #journeymap before he finalizes 
it and starts implementing it in JourneyMap.

This page will be updated as development progresses.

Building Code in this Repository
===

Assumptions:  You already know how to use Git and Gradle.

Simply run the Gradle 'build' task to generate the following:

* */build/libs/journeymap-api-#.jar*
* */build/libs/journeymap-api-#-javadoc.jar*
* */build/libs/journeymap-api-#-sources.jar*

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