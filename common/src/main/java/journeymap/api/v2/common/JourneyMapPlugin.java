/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * The following limited rights are granted to you:
 *
 * You MAY:
 *  + Write your own code that uses the API source code in journeymap.* packages as a dependency.
 *  + Write and distribute your own code that uses, modifies, or extends the example source code in example.* packages
 *  + Fork and modify any source code for the purpose of submitting Pull Requests to the TeamJM/journeymap-api repository.
 *    Submitting new or modified code to the repository means that you are granting Techbrew all rights to the submitted code.
 *
 * You MAY NOT:
 *  - Distribute source code or classes (whether modified or not) from journeymap.* packages.
 *  - Submit any code to the TeamJM/journeymap-api repository with a different license than this one.
 *  - Use code or artifacts from the repository in any way not explicitly granted by this license.
 *
 */

package journeymap.api.v2.common;

import journeymap.api.v2.client.IClientPlugin;
import journeymap.api.v2.server.IServerPlugin;

import javax.annotation.Nullable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used by JourneyMap to discover and classload plugin classes.
 * <p>
 * Classes with this annotation must have a no-arg constructor
 * and must also implement the {@link IClientPlugin} interface for client plugins
 * or {@link IServerPlugin} interface for server plugins.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface JourneyMapPlugin
{
    /**
     * Mod Devs can supply the version of journeymap-api the mod was built against,
     * so that it will not load the plugin if there is a breaking change.
     *
     * @return - The Api Version
     */
    @Nullable
    String apiVersion();

    /**
     * Optional list of mod ids this plugin depends on. When non-empty, JourneyMap
     * evaluates these against the loaded mods according to {@link #require()}:
     * with {@code require() == true} (the default) every listed mod must be loaded;
     * with {@code require() == false} at least one must be loaded. An empty array
     * (the default) imposes no dependency requirement - the plugin always loads,
     * which is backwards compatible with plugins built against older API versions.
     *
     * @return - the required mod ids.
     */
    String[] dependencies() default {};

    /**
     * Controls how {@link #dependencies()} is evaluated. When {@code true} (the
     * default), every listed mod id must be loaded for the plugin to initialize.
     * When {@code false}, the plugin initializes if at least one listed mod id
     * is loaded. Has no effect when {@code dependencies()} is empty.
     *
     * @return - true to require all dependencies, false to require any one.
     */
    boolean require() default true;
}
