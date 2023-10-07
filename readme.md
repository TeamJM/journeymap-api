[JourneyMap API](https://github.com/TeamJM/journeymap-api)
============================================================

[![JavaDocs](https://javadoc.io/badge2/info.journeymap/journeymap-api/javadoc.svg)](https://javadoc.io/doc/info.journeymap/journeymap-api) 

A plugin-style (soft dependency) API allowing other mods to create waypoints and show overlays and markers 
within [JourneyMap for Minecraft](http://journeymap.info) 1.8.9+.

If you have suggestions or improvements to the API structure, feel free to make Pull Requests. Chatting with the TeamJM
developers in [Discord](https://discord.gg/eP8gE69).  Before you change anything or submit code, however, be sure
to read the [License Information](docs/license.md).

News
============================================================
Versions API v2.0.0
- Updated Waypoints (Breaking Change)
- Removed ClientPlugin annotation in favor of JourneyMapPlugin annotation which takes the API version the addon was built against.
- API project now uses MultiLoader Template.
- Forge version is now a mod that JouneyMap now JarJars in the main mod.


[How to use the JourneyMap API](docs/howto.md)
============================================================

*Write a plugin for JourneyMap you can add to your mod.*


[Change Log](docs/changelog.md)
============================================================

*See what's changed across API versions.*


[License Information](docs/license.md)
============================================================

*Who owns this code and what can you do with it.*


Help Wanted
============================================================

**Areas of help needed:**

* Unit tests!
* Utility classes utilizing [java.awt.geom.Area.add()](https://docs.oracle.com/javase/7/docs/api/java/awt/geom/Area.html) to 
create optimized polygons comprised of multiple chunks.
