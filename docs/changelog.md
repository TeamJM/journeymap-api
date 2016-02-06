[JourneyMap API](https://bitbucket.org/TeamJM/journeymap-api) Changelog
======================================================

**API v1.8.9-1.1-SNAPSHOT**

* Added `IClientAPI.subscribe()`, refactored `ClientEvent` to be a base class.  `IClientPlugin.onClient()` is now passed a subclass corresponding to the event type.  
* Replaced `ClientEvent.Type.DISPLAY_STARTED` with `ClientEvent.Type.DISPLAY_UPDATE`.
* Added `IClientAPI.subscribe()` to explicitly request for ClientEvent notifications.
* `IClientAPI.isActive()` now takes Context enums as arguments
* Added `Overlay.activeUIs`, `Overlay.activeMapTypes`, and `Overlay.activeMapLayers` fields
* Removed `Overlay.inFullscreen`, `Overlay.inMinimap`, `Overlay.inWebmap` fields.
* Added `TextProperties.activeUIs` and `TextProperties.fontShadow` fields
* `MapPolygon` now only needs 3 points and doesn't require a redundant last point. 
* `PolygonHelper` now starts correctly with south-west (lower-left) corner.
* `Overlay.minZoom` and `Overlay.maxZoom` now default to 0 and 8 respectively
* Added `Displayable.displayOrder`.
* `Overlay.zIndex` renamed to `Overlay.displayOrder`.
* Added `IClientAPI.getModId()`, removed `IClientAPI.getShownIds()`, removed `IClientAPI.exists(String, DisplayType, String)`, removed `IClientAPI.remove(String, DisplayType, String)`
* `ClientEvent` refactored to use explicit value object type, `ClientEvent.Type.cancellable` and `ClientEvent.Type.valueClass` added
* `PluginHelper` refactored to uses a Map instead of a List, keyed by modId
* Improvements to Example mod behavior

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