package de.yaya.weathereffects.particles;

import de.yaya.weathereffects.settings.SettingsStorage;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RainRippleParticle extends AnimatedFloorEffectParticle
{
	protected RainRippleParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider)
	{
		super(world, x, y, z, spriteProvider);
	}
	
	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		Entity camera = MinecraftClient.getInstance().getCameraEntity();
		if(camera != null)
		{
			double dist = camera.getPos().distanceTo(new Vec3d(x, y, z));
			alpha = MathHelper.lerp(SettingsStorage.getBoolean("rain.distance-translucency") ?
											MathHelper.clamp((float) dist - 3f, 0f, 25f) / 25f : 0f, 1f, 0f);
			setSpriteForAge(spriteProvider);
			if (this.age > this.maxAge / 4)
				this.setAlpha(Math.max(alpha - ((float) this.age - (float) (this.maxAge / 4)) / (float) this.maxAge, 0f));
		}
	}
	
	public static class RainRippleFactory implements ParticleFactory<DefaultParticleType>
	{
		protected final SpriteProvider spriteProvider;
		
		public RainRippleFactory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}
		
		@Nullable
		@Override
		public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			RainRippleParticle rainRipple = new RainRippleParticle(world, x, y, z, spriteProvider);
			rainRipple.setSpriteForAge(spriteProvider);
			rainRipple.setMaxAge((int)((new Random().nextFloat() * 10f + 5f) / SettingsStorage.getDouble("rain.ripple-speed")));
			rainRipple.setAlpha(0);
			return rainRipple;
		}
	}
}
