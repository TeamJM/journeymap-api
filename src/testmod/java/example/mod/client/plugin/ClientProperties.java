package example.mod.client.plugin;

import journeymap.client.api.option.BooleanOption;
import journeymap.client.api.option.CustomIntegerOption;
import journeymap.client.api.option.CustomTextOption;
import journeymap.client.api.option.EnumOption;
import journeymap.client.api.option.FloatOption;
import journeymap.client.api.option.IntegerOption;
import journeymap.client.api.option.KeyedEnum;
import journeymap.client.api.option.OptionCategory;

public class ClientProperties
{
    private OptionCategory category = new OptionCategory("testmod", "Test Label", "Displays test options");

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
