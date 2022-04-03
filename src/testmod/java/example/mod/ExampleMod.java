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

package example.mod;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Example mod showing how to use the JourneyMap API.
 */

public class ExampleMod implements ModInitializer
{
    public static final String MODID = "examplemodjm";
    public static final String VERSION = "1.8";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MODID);

    @Override
    public void onInitialize()
    {

    }
}
