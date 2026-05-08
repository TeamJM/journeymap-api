/*
 * JourneyMap API (http://journeymap.info)
 * http://github.com/TeamJM/journeymap-api
 *
 * Copyright (c) 2011-2016 Techbrew.  All Rights Reserved.
 * See the LICENSE file in this repo for license terms.
 */

package example.mod.client.plugin;

import journeymap.api.v2.client.option.BooleanOption;
import journeymap.api.v2.client.option.CustomIntegerOption;
import journeymap.api.v2.client.option.CustomTextOption;
import journeymap.api.v2.client.option.EnumOption;
import journeymap.api.v2.client.option.FloatOption;
import journeymap.api.v2.client.option.IntegerOption;
import journeymap.api.v2.client.option.KeyedEnum;
import journeymap.api.v2.client.option.OptionCategory;

/**
 * Sample option set, registered from {@code RegistryEvent.OptionsRegistryEvent}.
 * Demonstrates each public option type exposed by the API.
 */
public class ClientProperties
{
    private final OptionCategory category = new OptionCategory("testmod", "Test Label", "Displays test options");

    public final BooleanOption newValue;
    public final EnumOption<TestEnum> enumOption;
    public final CustomIntegerOption integerOption;
    public final CustomTextOption textOption;
    public final FloatOption floatOption;
    public final IntegerOption intOption;

    public ClientProperties()
    {
        this.newValue = new BooleanOption(category, "test1", "test boolean", true);
        this.enumOption = new EnumOption<>(category, "testEnum", "Test enum", TestEnum.TEST_ENUM_A);
        this.integerOption = new CustomIntegerOption(category, "testInt", "Integer Test", 10, 0, 100, false);
        this.textOption = new CustomTextOption(category, "testText", "testText", "test");
        this.floatOption = new FloatOption(category, "floatTest", "float test", 2F, 0F, 10F);
        this.intOption = new IntegerOption(category, "intTest", "int test", 2, 0, 10);
    }

    public enum TestEnum implements KeyedEnum
    {
        TEST_ENUM_A("TestValueA"),
        TEST_ENUM_B("TestValueB"),
        TEST_ENUM_C("TestValueC"),
        TEST_ENUM_D("TestValueD");

        private final String key;

        TestEnum(String key)
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
