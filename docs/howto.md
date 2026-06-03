How to use the [JourneyMap API](https://github.com/TeamJM/journeymap-api)
=============================

To hook into JourneyMap from your mod, you'll write a plugin class that handles all interactions with JourneyMap
via the API interfaces in this repository.

The JourneyMap API is designed so that your mod will only have a **soft dependency** on it:

* You should **only** have a compile-time dependency via your plugin implementation.
* You should **not** need a runtime dependency. As long as you don't declare a dependency on "journeymap" in your
  mods.toml file, your mod should load even if JourneyMap doesn't.
* You should **never** include any JourneyMap API classes in your mod's jar. (No shading is needed.)

This page describes the recommended approach to writing a plugin for the JourneyMap API:

I. Add the API Dependency
=============================

To find out which API version to use, run Minecraft with JourneyMap. Open the Options manager or any other dialog in
JourneyMap,
and you'll see the API version in the corner of the screen. The mod info dialog also displays this information.

1. Add [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cjourneymap-api) to your list of repositories
2. Add a compile dependency on 'info.journeymap:journeymap-api:#version'
3. Add journeymap mod in your run/mods folder or as a runtimeOnly/modRuntimeOnly dependency from cursemaven or
   modrinth's maven.
   If set as a runtime dependency, you will also need to
   add [common-networking](https://github.com/mysticdrew/common-networking) as a dependency.

For example:

```
#!gradle

// Version of JourneyMap API to use
`journeymap-api-forge:26.2-2.0.0-SNAPSHOT`
`journeymap-api-neoforge:26.2-2.0.0-SNAPSHOT`
`journeymap-api-fabric:26.2-2.0.0-SNAPSHOT`
`journeymap-api-paper:26.2-2.0.0-SNAPSHOT`

// for multiloader setups, common jar
`journeymap-api-common:26.2-2.0.0-SNAPSHOT`

journeymap_api_version = 26.2-2.0.0-SNAPSHOT

// Note: None of the blocks below belong in your buildscript block. Put them below it instead.
repositories {
    // JourneyMap API releases are here
    maven {
        name = "JourneyMap (Public)"
        url = "https://jm.gserv.me/repository/maven-public/"
    }
    // New maven repo our API. Please switch to this maven.
    // We will continue to upload to jm.gserve.me, but that maven goes down more than we'd like.
    maven {
        name = "New JourneyMap Maven"
        url = 'https://maven.blamejared.com'
    }
    // Optional cursemaven
    maven {
        name = "Curse Maven"
        url "https://www.cursemaven.com"
    }
    // Optional Modrinth Maven
    maven {
        name = "Modrinth Maven"
        url = "https://api.modrinth.com/maven"
    }
}

configurations.all {
    // Check for snapshots more frequently than Gradle's default of 1 day. 0 = every build.
    resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
}

// FORGE
dependencies {
    compileOnly group: 'info.journeymap', name: 'journeymap-api-forge', version: project.journeymap_api_version, changing: true
}

// NEOFORGE
dependencies {
    compileOnly group: 'info.journeymap', name: 'journeymap-api-neoforge', version: project.journeymap_api_version, changing: true
}

// FABRIC/QUILT
dependencies {
    modCompileOnlyApi group: 'info.journeymap', name: 'journeymap-api-fabric', version: project.journeymap_api_version, changing: true
}

// PAPER (server-side addons only)
dependencies {
    compileOnly group: 'info.journeymap', name: 'journeymap-api-paper', version: project.journeymap_api_version, changing: true
}

```

The Paper API artifact is the server-side counterpart for Paper/Spigot plugins. It is published
with the Minecraft version baked into the version string (e.g. `journeymap-api-paper:26.1-2.0.0-SNAPSHOT`)
rather than the loader-agnostic suffix used by the mod-loader artifacts. The jar bundles the full
API surface (common + server + client) so shared code resolves at compile time; only the
`journeymap.api.v2.common.*` and `journeymap.api.v2.server.*` packages are usable on a Paper server
at runtime.

Example forge: mods.toml entry for a soft dependency. Set `mandatory=true` for a hard dependency if needed.

```
[[dependencies.mymodId]]
modId = "journeymap"
mandatory = false
versionRange = "[1.21-6.0.0-beta.1,)"
ordering = "NONE"
side = "CLIENT"
```

Example neoforge: neoforge.mods.toml entry for a soft dependency. Set `type = "required"` for a hard dependency if
needed.

```
[[dependencies.mymodId]]
modId = "journeymap"
type = "required"
versionRange = "[1.21-6.0.0-beta.1,)"
ordering = "NONE"
side = "CLIENT"
```

Example paper: a Paper addon is itself a Paper plugin, so it declares the dependency in
`paper-plugin.yml`. `join-classpath: true` makes the JourneyMap API classes (bundled inside the
JourneyMap Paper plugin jar) visible to your plugin's classloader at runtime.

```
dependencies:
  server:
    journeymap:
      load: BEFORE
      required: true
      join-classpath: true
```

*Note that the journeymap-api.jar is built with deobfuscated code so that it can be used at compile time and when
stepping through a debugger in your development environment.*

II. Look at the Example Code
=============================

The example mod is consolidated into one shared package under `common/src/testmod/`.
Each loader (fabric, forge, neoforge) keeps a tiny entry shim plus its mod manifest;
all plugin logic, sample factories, and event subscriptions live in common.

* Loader-agnostic plugin code:
  [common/src/testmod/java/example/mod/](../common/src/testmod/java/example/mod/)
  - [ExampleMod](../common/src/testmod/java/example/mod/ExampleMod.java) - shared MODID, LOGGER, init() called by every loader entry.
  - [client/plugin/ExampleJourneymapPlugin](../common/src/testmod/java/example/mod/client/plugin/ExampleJourneymapPlugin.java) - IClientPlugin discovered by JourneyMap.
  - [client/plugin/ClientEventListener](../common/src/testmod/java/example/mod/client/plugin/ClientEventListener.java) - subscribes every ClientEventRegistry, FullscreenEventRegistry, and MinimapEventRegistry hook.
  - [client/plugin/handler/](../common/src/testmod/java/example/mod/client/plugin/handler/) - loader-agnostic handlers for chunk-load and player-sleep events; each loader forwards its native event into these.
  - [common/plugin/CommonEventListener](../common/src/testmod/java/example/mod/common/plugin/CommonEventListener.java) - subscribes every CommonEventRegistry hook.
  - [server/plugin/ExampleServerPlugin](../common/src/testmod/java/example/mod/server/plugin/ExampleServerPlugin.java) - IServerPlugin demonstrating the server API.
  - [server/plugin/ServerEventListener](../common/src/testmod/java/example/mod/server/plugin/ServerEventListener.java) - subscribes every ServerEventRegistry hook.

* Loader entry shims (each one calls ExampleMod.init() and forwards loader-native events):
  - Fabric: [fabric/src/testmod/java/example/mod/fabric/FabricExampleMod.java](../fabric/src/testmod/java/example/mod/fabric/FabricExampleMod.java)
  - Forge: [forge/src/testmod/java/example/mod/forge/ForgeExampleMod.java](../forge/src/testmod/java/example/mod/forge/ForgeExampleMod.java)
  - NeoForge: [neoforge/src/testmod/java/example/mod/neoforge/NeoForgeExampleMod.java](../neoforge/src/testmod/java/example/mod/neoforge/NeoForgeExampleMod.java)

* Paper addon example (server-side only, since Paper has no client, so it does not use the shared
  `common/src/testmod/` example mod):
  - [paper/src/testmod/java/example/paper/ExampleJourneyMapPaperAddon.java](../paper/src/testmod/java/example/paper/ExampleJourneyMapPaperAddon.java) - a single class that is both a Paper plugin (`extends JavaPlugin`) and a JourneyMap addon (`implements IServerPlugin`, annotated `@JourneyMapPlugin`).
  - [paper/src/testmod/resources/paper-plugin.yml](../paper/src/testmod/resources/paper-plugin.yml) - the plugin manifest, including the `journeymap` server dependency.

* Running the test mod in dev:
  - The testmod source set is registered as a runnable mod in each loader's run config.
  - Use the standard run tasks: `./gradlew :fabric:runClient`, `./gradlew :forge:runClient`, `./gradlew :neoforge:runClient`.
  - In IntelliJ, the per-loader run configs include the testmod source set automatically.
  - Drop the JourneyMap jar into `{loader}/run/client/mods/` before launching.
  - Paper: `./gradlew :paper:runDevBundleServer` boots a Paper server with the example addon. Without
    the JourneyMap Paper plugin present, only `onEnable` fires (this verifies the API classes load on
    a real Paper classpath); full addon discovery requires a Paper server that has JourneyMap installed.

III. Write your Plugin
=============================

1. Write a class that implements the JourneyMap
   *[journeymap.api.v2.client.IClientPlugin](../common/src/main/java/journeymap/api/v2/client/IClientPlugin.java)* interface
   (see [ExampleJourneymapPlugin](../common/src/testmod/java/example/mod/client/plugin/ExampleJourneymapPlugin.java) for a complete example)
    - Annotate the class with
      *[@journeymap.api.v2.common.JourneyMapPlugin](../common/src/main/java/journeymap/api/v2/common/JourneyMapPlugin.java)* so
      that JourneyMap can find and instantiate it.
    - Don't make references to this class elsewhere in your mod. You don't want it classloaded if JourneyMap isn't
      loaded.
2. Write other classes as needed that use JourneyMap API classes, but only refer to them from your Plugin class.
    - Don't make references to these classes elsewhere in your mod. You don't want them classloaded if JourneyMap isn't
      loaded.
3. For Forge/NeoForge: automatically detects the plugin via the annotation.
4. For Fabric: In your `fabric.mod.json` file add the path to your class that implements `IClientPlugin` to the
   `journeymap` entrypoint. Example:

```
    "journeymap": [
      "mymod.modhooks.MyJourneymapClientPlugin"
    ]
```

```
    "journeymap_server": [
      "mymod.modhooks.MyJourneymapServerPlugin"
    ]
```

5. For server-side plugins, implement
   *[journeymap.api.v2.server.IServerPlugin](../common/src/main/java/journeymap/api/v2/server/IServerPlugin.java)*
   instead of `IClientPlugin`, annotate the same way, and (on Fabric) add the class to the `journeymap_server` entrypoint
   alongside any client plugin. See [ExampleServerPlugin](../common/src/testmod/java/example/mod/server/plugin/ExampleServerPlugin.java) for a working example.
6. For Paper: a Paper addon is a server-side plugin only. Implement `IServerPlugin` (Paper has no
   client, so `IClientPlugin` is not usable at runtime). Annotate the class with `@JourneyMapPlugin`.
   The JourneyMap Paper plugin discovers addons by scanning every loaded plugin jar for the
   annotation, so there is no entrypoint list to maintain. Your addon must be a normal Paper plugin
   (`extends JavaPlugin` with a `paper-plugin.yml`) that declares the `journeymap` server dependency
   shown in Section I. See [ExampleJourneyMapPaperAddon](../paper/src/testmod/java/example/paper/ExampleJourneyMapPaperAddon.java)
   for a working example.

IV. Test your Plugin
=============================

1. Using the gradle configuration above, your mod will load JourneyMap and the API in your development
   environment.
2. Run Minecraft in your development environment. The loader will load JourneyMap and your mod, and the JourneyMap API will
   activate your plugin.
