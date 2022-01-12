package weathereffects.weathereffects;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import weathereffects.weathereffects.config.Configuration;

public class WeatherEffects implements ModInitializer
{
	public static final String MODID = "weathereffects";
	public static final DefaultParticleType RAIN_DROP = FabricParticleTypes.simple();
	public static final DefaultParticleType RAIN_RIPPLE = FabricParticleTypes.simple();
	public static final DefaultParticleType SNOW_FLAKE = FabricParticleTypes.simple();

	public static Configuration CONFIG;
	
	@Override
	public void onInitialize()
	{
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "raindrop"), RAIN_DROP);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "rainripple"), RAIN_RIPPLE);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier(WeatherEffects.MODID, "snowflake"), SNOW_FLAKE);

		AutoConfig.register(Configuration.class, GsonConfigSerializer::new);
		WeatherEffects.CONFIG = AutoConfig.getConfigHolder(Configuration.class).getConfig();
	}
}
