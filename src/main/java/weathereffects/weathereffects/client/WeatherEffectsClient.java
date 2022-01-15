package weathereffects.weathereffects.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import weathereffects.weathereffects.WeatherEffects;
import weathereffects.weathereffects.particles.RainDropParticle;
import weathereffects.weathereffects.particles.RainRippleParticle;
import weathereffects.weathereffects.particles.SnowflakeParticle;
import weathereffects.weathereffects.particles.WindDustParticle;

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
