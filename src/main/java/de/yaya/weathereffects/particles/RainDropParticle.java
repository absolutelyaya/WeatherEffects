package de.yaya.weathereffects.particles;

import de.yaya.weathereffects.WeatherEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import de.yaya.weathereffects.settings.SettingsStorage;

public class RainDropParticle extends SpriteAAParticle
{
	protected RainDropParticle(ClientWorld clientWorld, double x, double y, double z, SpriteProvider spriteProvider)
	{
		super(clientWorld, x, y, z, spriteProvider);
		this.gravityStrength = 0.06f;
		this.maxAge = 200;
		this.velocityY = -0.75;
		if(SettingsStorage.getBoolean("rain.random-length"))
			scale = new Vec3f(scale.getX(), random.nextFloat((float)SettingsStorage.getDouble("rain.min-length"),
					(float)SettingsStorage.getDouble("rain.max-length")), scale.getZ());
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
											MathHelper.clamp((float) dist - 3f, 0f, 25f) / 25f : 0f,
					(float)SettingsStorage.getDouble("rain.opacity"), 0f);
			velocityY *= SettingsStorage.getDouble("rain.acceleration");
			FluidState state = world.getFluidState(new BlockPos(new Vec3d(x, y, z)));
			if(state.isOf(Fluids.WATER))
			{
				onGround = true;
			}
			if(onGround)
			{
				nextParticle(new BlockPos(new Vec3d(x, y, z)));
				markDead();
			}
		}
	}
	
	void nextParticle(BlockPos pos)
	{
		FluidState state = world.getFluidState(pos);
		if(state.isIn(FluidTags.WATER) && state.isStill() && SettingsStorage.getBoolean("rain.ripples"))
			world.addParticle(WeatherEffects.RAIN_RIPPLE, x, pos.getY() + 0.9, z, 0, 0, 0);
		else if(state.isIn(FluidTags.LAVA))
			world.addParticle(ParticleTypes.SMOKE, x, pos.getY() + 0.9, z, 0, 0, 0);
		else if(SettingsStorage.getBoolean("rain.splashes"))
			world.addParticle(ParticleTypes.SPLASH, x, y, z, 0, 0, 0);
	}
	
	@Environment(EnvType.CLIENT)
	public static class RainDropFactory implements ParticleFactory<DefaultParticleType>
	{
		protected final SpriteProvider spriteProvider;
		
		public RainDropFactory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}
		
		@Nullable
		@Override
		public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			RainDropParticle raindrop = new RainDropParticle(world, x, y, z, spriteProvider);
			raindrop.setSprite(spriteProvider);
			raindrop.setAlpha(0f);
			return raindrop;
		}
	}
}
