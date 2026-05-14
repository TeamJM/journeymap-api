/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.server.plugin;

import example.mod.ExampleMod;
import journeymap.api.v2.common.option.BooleanOption;
import journeymap.api.v2.common.option.EnumOption;
import journeymap.api.v2.common.option.IntegerOption;
import journeymap.api.v2.common.option.KeyedEnum;
import journeymap.api.v2.common.option.OptionCategory;

/**
 * Sample server-side option set, registered from
 * {@code ServerEventRegistry.OPTIONS_REGISTRY_EVENT}. These options appear in
 * JourneyMap's Server Admin options screen for admins/ops to edit.
 */
public class ServerProperties
{
    private final OptionCategory category = new OptionCategory(ExampleMod.MODID, "Test Server Label", "Demonstrates server addon options");

    public final BooleanOption serverFeatureEnabled;
    public final IntegerOption serverThreshold;
    public final EnumOption<TestServerEnum> serverMode;

    public ServerProperties()
    {
        this.serverFeatureEnabled = new BooleanOption(category, "serverFeature", "Server feature enabled", true);
        this.serverThreshold = new IntegerOption(category, "serverThreshold", "Server threshold", 5, 0, 20);
        this.serverMode = new EnumOption<>(category, "serverMode", "Server mode", TestServerEnum.MODE_A);
    }

    public enum TestServerEnum implements KeyedEnum
    {
        MODE_A("ModeA"),
        MODE_B("ModeB"),
        MODE_C("ModeC");

        private final String key;

        TestServerEnum(String key)
        {
            this.key = key;
        }

        @Override
        public String getKey()
        {
            return key;
        }
    }
}
