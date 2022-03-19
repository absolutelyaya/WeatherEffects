package de.yaya.weathereffects;

import de.yaya.weathereffects.settings.Settings;
import de.yaya.yayconfig.settings.SettingsManager;
import de.yaya.yayconfig.utilities.TranslationUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WeatherEffects implements ModInitializer
{
	public static final String MODID = "weathereffects";
	public static final DefaultParticleType RAIN_DROP = FabricParticleTypes.simple();
	public static final DefaultParticleType RAIN_RIPPLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SNOW_FLAKE = FabricParticleTypes.simple();
	public static final DefaultParticleType WIND_DUST = FabricParticleTypes.simple();
	
	@Override
	public void onInitialize()
	{
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "raindrop"), RAIN_DROP);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "rainripple"), RAIN_RIPPLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "snowflake"), SNOW_FLAKE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "wind_dust"), WIND_DUST);
		
		TranslationUtil.setActiveGroupID(WeatherEffects.MODID);
		new Settings(Settings.Category.class);
		SettingsManager.load();
	}
}
