package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class Settings
{
	
	static final List<AbstractSetting> SETTINGS = new ArrayList<>();
	
	//TODO add setting categories
	
	static
	{
	
	}
	
	public static Option[] getAsOptions()
	{
		List<Option> options = new ArrayList<>();
		for (SettingsOption so : SETTINGS)
		{
			options.add(so.asOption());
		}
		return options.toArray(Option[]::new);
	}
	
	public static void applyDefaults()
	{
		for(AbstractSetting as : SETTINGS)
		{
			if(as instanceof EnumSetting<?>)
				SettingsStorage.setEnum(as.id, ((EnumSetting<?>)as).getDefaultValue());
			else if(as instanceof BooleanSetting)
				SettingsStorage.setBoolean(as.id, ((BooleanSetting)as).getDefaultValue());
			else if(as instanceof SliderSetting)
				SettingsStorage.setDouble(as.id, ((SliderSetting)as).getDefaultValue());
		}
	}
}
