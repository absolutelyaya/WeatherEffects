package de.yaya.weathereffects.client;

import de.yaya.weathereffects.WeatherEffects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import de.yaya.weathereffects.particles.RainDropParticle;
import de.yaya.weathereffects.particles.RainRippleParticle;
import de.yaya.weathereffects.particles.SnowflakeParticle;
import de.yaya.weathereffects.particles.WindDustParticle;

@Environment(EnvType.CLIENT)
public class WeatherEffectsClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register((((atlasTexture, registry) ->
		{
			registry.register(new Identifier(WeatherEffects.MODID, "particle/raindrop"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/ripple1"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/ripple2"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/ripple3"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/ripple4"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/snowflake1"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/snowflake2"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/snowflake3"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/snowflake4"));
			registry.register(new Identifier(WeatherEffects.MODID, "particle/snowflake5"));
		})));
		
		ParticleFactoryRegistry registry = ParticleFactoryRegistry.getInstance();
		registry.register(WeatherEffects.RAIN_DROP, RainDropParticle.RainDropFactory::new);
		registry.register(WeatherEffects.RAIN_RIPPLE, RainRippleParticle.RainRippleFactory::new);
		registry.register(WeatherEffects.SNOW_FLAKE, SnowflakeParticle.SnowflakeParticleFactory::new);
		registry.register(WeatherEffects.WIND_DUST, WindDustParticle.WindDustParticleFactory::new);
	}
}
