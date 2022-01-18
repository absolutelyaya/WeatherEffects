package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;

@Environment(EnvType.CLIENT)
public interface SettingsOption
{
	Option asOption();
}
