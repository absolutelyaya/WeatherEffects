package de.yaya.weathereffects.settings;

import de.yaya.weathereffects.WeatherEffects;
import de.yaya.weathereffects.screens.MainSettingsScreen;
import de.yaya.yayconfig.settings.AbstractSetting;
import de.yaya.yayconfig.settings.BooleanSetting;
import de.yaya.yayconfig.settings.ChoiceSetting;
import de.yaya.yayconfig.settings.PerEntrySetting;
import de.yaya.yayconfig.settings.SettingsCategory;
import de.yaya.yayconfig.settings.SettingsManager;
import de.yaya.yayconfig.settings.SettingsStorage;
import de.yaya.yayconfig.settings.SliderSetting;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.biome.Biome;

import java.io.File;
import java.util.List;

@Environment(EnvType.CLIENT)
public class Settings extends de.yaya.yayconfig.settings.Settings
{
	//General
	public static final SliderSetting PARTICLE_AMOUNT = new SliderSetting("general.particle-amount", 10.0, 0.0, 20.0, 0.05f, 1, true);
	//Rain
	public static final BooleanSetting CUSTOM_RAIN = new BooleanSetting("rain.enabled", true, true);
	public static final SliderSetting RAINDROP_AMOUNT = new SliderSetting("rain.amount", 1.0, 0.0, 1.0, 0.01f, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAINDROP_OPACITY = new SliderSetting("rain.opacity", 0.75, 0.0, 1.0, 0.01f, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final BooleanSetting RANDOM_RAINDROP_LENGTH = new BooleanSetting("rain.random-length", true, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAINDROP_MIN_LENGTH = new SliderSetting("rain.min-length", 0.1, 0.1, 0.99, 0.01f, 2, true)
			.setRequirements(List.of(CUSTOM_RAIN, RANDOM_RAINDROP_LENGTH));
	public static final SliderSetting RAINDROP_MAX_LENGTH = new SliderSetting("rain.max-length", 0.4, 0.11, 1.0, 0.01f, 2, true)
			.setRequirements(List.of(CUSTOM_RAIN, RANDOM_RAINDROP_LENGTH));
	public static final BooleanSetting RAIN_DISTANCE_TRANSLUCENCY = new BooleanSetting("rain.distance-translucency", true, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAIN_ACCELERATION = new SliderSetting("rain.acceleration", 1.025, 1.001, 1.1, 0.001f, 3, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final BooleanSetting RAIN_SPLASHING = new BooleanSetting("rain.splashes", true, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final BooleanSetting RAIN_RIPPLES = new BooleanSetting("rain.ripples", true, true)
			.setRequirements(List.of(CUSTOM_RAIN));
	public static final SliderSetting RAIN_RIPPLE_SPEED = new SliderSetting("rain.ripple-speed", 1.0, 0.01, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(CUSTOM_RAIN, RAIN_RIPPLES));
	//Snow
	public static final BooleanSetting CUSTOM_SNOW = new BooleanSetting("snow.enabled", true, true);
	public static final SliderSetting SNOWFLAKE_AMOUNT = new SliderSetting("snow.amount", 1.0, 0.0, 1.0, 0.01f, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_SIZE = new SliderSetting("snow.size", 1.0, 0.0, 2.0, 0.05f, 2, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_ROTATION = new SliderSetting("snow.rotation", 1.0, 0.0, 2.0, 0.1f, 1, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_GRAVITY = new SliderSetting("snow.gravity", 1.0, 0.5, 1.5, 0.05f, 2, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_MELT_SPEED = new SliderSetting("snow.melt-speed", 1.0, 0.5, 10.0, 0.1f, 1, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_WIND_STRENGTH = new SliderSetting("snow.wind.strength", 1.0, 0.0, 2.0, 0.1f, 1, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_WIND_CHANGE_RATE = new SliderSetting("snow.wind.change-rate", 1.0, 0.0, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	public static final SliderSetting SNOWFLAKE_WIND_CHANGE_SPEED = new SliderSetting("snow.wind.change-speed", 1.0, 0.0, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(CUSTOM_SNOW));
	
	//Sandstorm
	public static final BooleanSetting SANDSTORM = new BooleanSetting("sandstorm.enabled", true, true);
	public static final SliderSetting SANDSTORM_DUST_AMOUNT = new SliderSetting("sandstorm.dust.amount", 1.0, 0.0, 1.0, 0.01f, true)
			.setRequirements(List.of(SANDSTORM));
	public static final SliderSetting SANDSTORM_WIND_STRENGTH = new SliderSetting("sandstorm.wind.strength", 1.0, 0.0, 2.0, 0.1f, 1, true)
			.setRequirements(List.of(SANDSTORM));
	public static final SliderSetting SANDSTORM_WIND_CHANGE_RATE = new SliderSetting("sandstorm.wind.change-rate", 1.0, 0.0, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(SANDSTORM));
	public static final SliderSetting SANDSTORM_WIND_CHANGE_SPEED = new SliderSetting("sandstorm.wind.change-speed", 1.0, 0.0, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(SANDSTORM));
	public static final BooleanSetting SANDSTORM_WIND_DUST = new BooleanSetting("sandstorm.wind-dust", true, true)
			.setRequirements(List.of(SANDSTORM));
	public static final SliderSetting SANDSTORM_WIND_DUST_LIFETIME = new SliderSetting("sandstorm.wind-dust.lifetime", 1.0, 0.5, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(SANDSTORM, SANDSTORM_WIND_DUST));
	public static final SliderSetting SANDSTORM_WIND_DUST_ROTSPEED = new SliderSetting("sandstorm.wind-dust.rot-speed", 1.0, 0.01, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(SANDSTORM, SANDSTORM_WIND_DUST));
	public static final SliderSetting SANDSTORM_WIND_DUST_GRAVITY = new SliderSetting("sandstorm.wind-dust.gravity", 1.0, 0.01, 2.0, 0.01f, 2, true)
			.setRequirements(List.of(SANDSTORM, SANDSTORM_WIND_DUST));
	public static final BooleanSetting SANDSTORM_GROUND_DUST = new BooleanSetting("sandstorm.ground-dust", true, true)
			.setRequirements(List.of(SANDSTORM));
	
	//Clouds
	public static final BooleanSetting CLOUDS = new BooleanSetting("clouds.enabled", true, true);
	
	//Fog
	public static final BooleanSetting FOG = new BooleanSetting("fog.enabled", true, true);
	public static final PerEntrySetting<Biome.Category> FOG_PER_BIOME = new PerEntrySetting<>("fog.biome", Biome.Category.class,
			List.of(
					new SliderSetting("fog.color.fade-speed", 1.0, 0.1, 2.0, 0.01f, 2, false),
					new SliderSetting("fog.speed", 1.0, 0.1, 2.0, 0.01f, 2, false)
			),
			List.of(Biome.Category.THEEND, Biome.Category.NONE, Biome.Category.NETHER, Biome.Category.UNDERGROUND))
			.setRequirements(List.of(FOG));
	
	//Peset
	public static final ChoiceSetting PRESET = new ChoiceSetting("general.preset", List.of("a", "b", "c", "d"), true);
	
	static
	{
		SETTINGS.put(Category.GENERAL, List.of(PRESET, PARTICLE_AMOUNT));
		SETTINGS.put(Category.RAIN, List.of(CUSTOM_RAIN, RAINDROP_AMOUNT, RAINDROP_OPACITY, RANDOM_RAINDROP_LENGTH, RAINDROP_MIN_LENGTH,
				RAINDROP_MAX_LENGTH, RAIN_DISTANCE_TRANSLUCENCY, RAIN_ACCELERATION, RAIN_SPLASHING, RAIN_RIPPLES, RAIN_RIPPLE_SPEED));
		SETTINGS.put(Category.SNOW, List.of(CUSTOM_SNOW, SNOWFLAKE_AMOUNT, SNOWFLAKE_SIZE, SNOWFLAKE_ROTATION, SNOWFLAKE_GRAVITY, SNOWFLAKE_MELT_SPEED,
				SNOWFLAKE_WIND_STRENGTH, SNOWFLAKE_WIND_CHANGE_RATE, SNOWFLAKE_WIND_CHANGE_SPEED));
		SETTINGS.put(Category.SANDSTORM, List.of(SANDSTORM, SANDSTORM_DUST_AMOUNT, SANDSTORM_WIND_STRENGTH, SANDSTORM_WIND_CHANGE_RATE,
				SANDSTORM_WIND_CHANGE_SPEED, SANDSTORM_WIND_DUST, SANDSTORM_WIND_DUST_LIFETIME, SANDSTORM_WIND_DUST_ROTSPEED,
				SANDSTORM_WIND_DUST_GRAVITY, SANDSTORM_GROUND_DUST));
		SETTINGS.put(Category.CLOUDS, List.of(CLOUDS));
		SETTINGS.put(Category.FOG, List.of(FOG, FOG_PER_BIOME));
		
		RAINDROP_MIN_LENGTH.setSoftMax(RAINDROP_MAX_LENGTH);
		RAINDROP_MAX_LENGTH.setSoftMin(RAINDROP_MIN_LENGTH);
	}
	
	public Settings(Class<? extends SettingsCategory> category)
	{
		super(category);
		Settings.category = category;
		PRESET.setChangeConsumer(Settings::applyPreset);
	}
	
	public static void applyPreset(String name)
	{
		if(name.equals("custom") || name.equals("§4ERROR"))
			return;
		String[] segments = name.split("\\|");
		SettingsManager.load(new File(FabricLoader.getInstance().getConfigDir().toFile(),
				WeatherEffects.MODID + "/presets/" + segments[0] + ".txt"));
		Screen screen = MinecraftClient.getInstance().currentScreen;
		if(screen instanceof MainSettingsScreen)
			((MainSettingsScreen)screen).UpdateSlider();
		for(Category cat : Category.values())
		{
			if(SETTINGS.get(cat) != null)
			{
				for(AbstractSetting as : SETTINGS.get(cat))
				{
					if(as instanceof BooleanSetting bool)
						bool.defaultValue = SettingsStorage.getBoolean(bool.id);
					else if(as instanceof SliderSetting slider)
						slider.updateDefault(SettingsStorage.getDouble(slider.id));
				}
			}
		}
		if(!Boolean.parseBoolean(segments[1]))
			SettingsManager.load();
	}
	
	public enum Category implements de.yaya.yayconfig.settings.SettingsCategory
	{
		GENERAL(new TranslatableText("screen.weatherEffects.options.main.title")),
		RAIN(new TranslatableText("screen.weatherEffects.options.rain.title")),
		SNOW(new TranslatableText("screen.weatherEffects.options.snow.title")),
		SANDSTORM(new TranslatableText("screen.weatherEffects.options.sandstorm.title")),
		CLOUDS(new TranslatableText("screen.weatherEffects.options.clouds.title")),
		FOG(new TranslatableText("screen.weatherEffects.options.fog.title"));
		
		private final TranslatableText title;
		
		Category(TranslatableText title)
		{
			this.title = title;
		}
		
		public TranslatableText getTitle()
		{
			return title.copy();
		}
		
		@Override
		public SettingsCategory[] getValues()
		{
			return values();
		}
	}
}
