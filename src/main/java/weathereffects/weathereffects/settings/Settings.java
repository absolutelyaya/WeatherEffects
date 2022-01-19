package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class Settings
{
	static final HashMap<Category, List<AbstractSetting>> SETTINGS = new HashMap<>();
	
	//TODO add setting categories
	
	static
	{
	
	}
	
	public static Option[] getAsOptions(Category category)
	{
		List<Option> options = new ArrayList<>();
		for (SettingsOption so : SETTINGS.get(category))
		{
			options.add(so.asOption());
		}
		return options.toArray(Option[]::new);
	}
	
	public static void applyDefaults()
	{
		for(Category cat : Category.values())
		{
			if(SETTINGS.get(cat) != null)
			{
				for(AbstractSetting as : SETTINGS.get(cat))
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
	}
	
	public enum Category
	{
		GENERAL,
		RAIN,
		SNOW,
		SANDSTORM,
		CLOUDS,
		FOG,
		STARS
	}
}
