package weathereffects.weathereffects.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import weathereffects.weathereffects.WeatherEffects;

@Config(name = WeatherEffects.MODID)
public class Configuration implements ConfigData
{
    static boolean testToggle = true;

    public static Screen makeWindow(Screen previous)
    {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(previous)
                .setDefaultBackgroundTexture(new Identifier("textures/block/white_wool.png"));
        builder.getOrCreateCategory(Text.of("ayaya"))
                .addEntry(builder.entryBuilder().startBooleanToggle(Text.of("testToggle"), testToggle).setDefaultValue(true).build());
        return builder.build();
    }
}
