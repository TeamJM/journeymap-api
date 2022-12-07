[JourneyMap API](https://github.com/TeamJM/journeymap-api) Changelog
======================================================
**API v1.19.3-1.9-fabric-SNAPSHOT**
* Updated to mc 1.19.3
* Changed BufferedImage to NativeImage

**v1.18.2-1.8-fabric**
* Updated for fabric

**API v1.16.5-1.8, v1.17.1-1.8, v1.18.1-1.8** 
* 1.17.1-1.8 Java 16 Required
* 1.18.1-1.8 Java 17 Required
* 1.18.1-1.8 is compatible with 1.18.2
* Added Waypoint Crud Events
* Added EntityRadarUpdateEvent
* Added PopupMenuEvent
* Added FullscreenMapEvent
* Added FullscreenDisplayEvent
* Added RegistryEvent
* Added ClientAPI hook for getting Waypoints
* Added ClientAPI hook for getting the current data path.

**API v1.9.4-1.3, v1.10.2-1.3**

* Java 8 required
* Support for Topo maps
* Experimental support for IClientAPI.requestMapTile()

**API v1.8.9-1.2, API v1.9-1.2**
* Minor documentation updates to stay in sync

**API v1.8.9-1.1**

* Basic support implemented in [JourneyMap 5.1.5 for Minecraft 1.8.9](http://minecraft.curseforge.com/projects/journeymap-32274/files/2285371)
* Too many changes to be worth listing, since 1.0 wasn't ever implemented. Look at commits if you care to.

**API v1.8.9-1.0**

* Added constructor to Displayable that doesn't require displayId, added getGuid() and getDisplayType() methods, made several methods final.
* `IClientAPI.show()` now explicitly throws Exception. Added `IClientAPI.exists(Displayable)` overload, removed `IClientAPI.isVisible()`.
* Jars now available in Maven Central!
* Build of `journeymap-api-[version].jar` is still deobf, but no longer has 'deobf' in the name

**API v1.8.9-0.9**

* ModWaypoint now has an isEditable property
* Example Mod's `ExampleMapFacade.showBedWaypoint()` now takes a BlockPos
* Bugfix in Example Mod's SleepEventListener: event.result is always null
* Build updated to Forge 11.15.0.1712

**API v1.8.9-0.8**

* MapPoint has been replaced with Minecraft's BlockPos

**API v1.8.9-0.7**

* ClientEvent is now potentially cancellable.
* Added new ClientEvent.Type: DEATH_WAYPOINT

**API v1.8.9-0.6**

* Build now produces a `journeymap-api-*-deobf.jar` for use in a dev environment and a `journeymap-api-*-examplemod.jar` for use in the `run/mods` directory
* Method signature changes and bugfixes in PluginHelper

**API v1.8.9-0.5**

* Overhauled design to use the annotated plugin approach recommended by the Forge team.
* Updated dependency to Forge 1.8.9

**0.4 and earlier**

* Iterative designs, now obsolete