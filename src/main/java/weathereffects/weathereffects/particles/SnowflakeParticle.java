package weathereffects.weathereffects.particles;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import org.jetbrains.annotations.Nullable;
import weathereffects.weathereffects.WeatherEffects;

import java.util.Random;

public class SnowflakeParticle extends SpriteBillboardParticle
{
	float groundTime, maxGroundTime, rotSpeed, startScale;
	double windStrength = 1.0;
	PerlinNoiseSampler perlinNoise;
	float meltSpeed = 0.5f;
	
	protected SnowflakeParticle(ClientWorld world, double x, double y, double z)
	{
		super(world, x, y, z);
		perlinNoise = new PerlinNoiseSampler(new AtomicSimpleRandom(System.currentTimeMillis()));
		gravityStrength = 0.1f;
		startScale = scale;
	}
	
	@Override
	public void tick()
	{
		super.tick();
		Entity camera = MinecraftClient.getInstance().getCameraEntity();
		if(camera != null)
		{
			prevAngle = angle;
			alpha = Math.min(((float) this.age) / (float) this.maxAge * 20, 1f);
			scale = startScale * alpha;
			BlockPos pos = new BlockPos(new Vec3d(x, y, z));
			FluidState state = world.getFluidState(pos);
			if (onGround || state.isIn(FluidTags.WATER) || state.isIn(FluidTags.LAVA))
			{
				if(groundTime == 0)
				{
					if(state.isIn(FluidTags.WATER))
					{
						world.addParticle(WeatherEffects.RAIN_RIPPLE, x, pos.getY() + 0.9, z, 0, 0, 0);
						meltSpeed = 3f;
					}
					else if(state.isIn(FluidTags.LAVA))
					{
						world.addParticle(ParticleTypes.SMOKE, x, pos.getY() + 0.9, z, 0, 0, 0);
						markDead();
					}
				}
				setVelocity(0, 0, 0);
				setAlpha(alpha - groundTime / maxGroundTime);
				scale = startScale * Math.max(alpha - groundTime / maxGroundTime, 0);
				if ((groundTime += meltSpeed) > maxGroundTime)
				{
					markDead();
				}
			}
			else
			{
				setAlpha(alpha);
				wind();
				angle += rotSpeed;
			}
		}
	}
	
	void wind()
	{
		velocityX += (perlinNoise.sample(x / 10, y, z / 10) * windStrength) / 42.069;
		velocityZ += (perlinNoise.sample((x + 30) / 10, y, (z + 30) / 10) * windStrength) / 42.069;
	}
	
	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	public void setMaxGroundTime(int maxGroundTime)
	{
		this.maxGroundTime = maxGroundTime;
	}
	
	public void setRotSpeed(float rotSpeed)
	{
		this.rotSpeed = rotSpeed;
	}
	
	public static class SnowflakeParticleFactory implements ParticleFactory<DefaultParticleType>
	{
		protected final SpriteProvider spriteProvider;
		
		public SnowflakeParticleFactory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}
		
		@Nullable
		@Override
		public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			Random r = new Random();
			SnowflakeParticle snowflake = new SnowflakeParticle(world, x, y, z);
			snowflake.setSprite(spriteProvider);
			snowflake.setMaxGroundTime(10 + r.nextInt(15));
			snowflake.setVelocity(0, -r.nextDouble() / 6.935, 0);
			snowflake.setMaxAge(400);
			snowflake.setRotSpeed((2 * r.nextFloat() - 1) / 4);
			snowflake.setAlpha(0f);
			return snowflake;
		}
	}
}
