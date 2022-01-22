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
	//TODO: Add presets
	//General
	public static final SliderSetting PARTICLE_AMOUNT = new SliderSetting("general.particle-amount", 10.0, 0.0, 20.0, 0.05f, 1);
	//Rain
	public static final BooleanSetting CUSTOM_RAIN = new BooleanSetting("rain.enabled", true);
	public static final SliderSetting RAINDROP_OPACITY = new SliderSetting("rain.opacity", 0.75, 0.0, 1.0, 0.01f)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final BooleanSetting RANDOM_RAINDROP_LENGTH = new BooleanSetting("rain.random-length", true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAINDROP_MIN_LENGTH = new SliderSetting("rain.min-length", 0.1, 0.1, 0.99, 0.01f, 2)
			.setRequirements(List.of(CUSTOM_RAIN, RANDOM_RAINDROP_LENGTH));
	public static final SliderSetting RAINDROP_MAX_LENGTH = new SliderSetting("rain.max-length", 0.4, 0.11, 1.0, 0.01f, 2)
			.setRequirements(List.of(CUSTOM_RAIN, RANDOM_RAINDROP_LENGTH));
	public static final BooleanSetting RAIN_DISTANCE_TRANSLUCENCY = new BooleanSetting("rain.distance-translucency", true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAIN_ACCELERATION = new SliderSetting("rain.acceleration", 1.025, 1.001, 1.1, 0.001f, 3)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final BooleanSetting RAIN_SPLASHING = new BooleanSetting("rain.splashes", true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final BooleanSetting RAIN_RIPPLES = new BooleanSetting("rain.ripples", true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAIN_RIPPLE_SPEED = new SliderSetting("rain.ripple-speed", 1.0, 0.01, 2.0, 0.01f, 2)
			.setRequirements(List.of(CUSTOM_RAIN, RAIN_RIPPLES));
	
	static final HashMap<Category, List<AbstractSetting>> SETTINGS = new HashMap<>();
	
	static
	{
		SETTINGS.put(Category.GENERAL, List.of(PARTICLE_AMOUNT));
		SETTINGS.put(Category.RAIN, List.of(CUSTOM_RAIN, RAINDROP_OPACITY, RANDOM_RAINDROP_LENGTH, RAINDROP_MIN_LENGTH,
				RAINDROP_MAX_LENGTH, RAIN_DISTANCE_TRANSLUCENCY, RAIN_ACCELERATION, RAIN_SPLASHING, RAIN_RIPPLES, RAIN_RIPPLE_SPEED));
		
		RAINDROP_MIN_LENGTH.setSoftMax(RAINDROP_MAX_LENGTH);
		RAINDROP_MAX_LENGTH.setSoftMin(RAINDROP_MIN_LENGTH);
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
