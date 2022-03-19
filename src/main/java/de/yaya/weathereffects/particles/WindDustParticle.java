package de.yaya.weathereffects.particles;

import de.yaya.weathereffects.settings.SettingsStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WindDustParticle extends SpriteBillboardParticle
{
	SpriteProvider spriteProvider;
	float rotationSpeed;
	
	protected WindDustParticle(ClientWorld clientWorld, double d, double e, double f, SpriteProvider spriteProvider)
	{
		super(clientWorld, d, e, f);
		this.spriteProvider = spriteProvider;
		this.rotationSpeed = ((float)Math.random() - 0.5f) * 0.1f * (float) SettingsStorage.getDouble("sandstorm.wind-dust.rot-speed");
		this.gravityStrength = 0.1f * (float)SettingsStorage.getDouble("sandstorm.wind-dust.gravity");
	}
	
	@Override
	public void tick()
	{
		super.tick();
		this.prevAngle = this.angle;
		this.angle += (float)Math.PI * this.rotationSpeed * 2.0f;
		this.setSpriteForAge(this.spriteProvider);
	}
	
	@Override
	public ParticleTextureSheet getType()
	{
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	public static class WindDustParticleFactory implements ParticleFactory<DefaultParticleType>
	{
		protected final SpriteProvider spriteProvider;
		
		public WindDustParticleFactory(SpriteProvider spriteProvider)
		{
			this.spriteProvider = spriteProvider;
		}
		
		@Nullable
		@Override
		public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ)
		{
			Random r = new Random();
			WindDustParticle dust = new WindDustParticle(world, x, y, z, spriteProvider);
			dust.setMaxAge((int)((20 + r.nextInt(10)) * SettingsStorage.getDouble("sandstorm.wind-dust.lifetime")));
			dust.setVelocity(0, -r.nextDouble() / 6.935, 0);
			BlockPos pos = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, new BlockPos(x, y, z)).down();
			BlockState block = world.getBlockState(pos);
			if(block.isOf(Blocks.CACTUS))
				return null;
			int color = MinecraftClient.getInstance().getBlockColors().getParticleColor(block, world, pos);
			float red = (float)(color >> 16 & 0xFF) / 255.0f;
			float green = (float)(color >> 8 & 0xFF) / 255.0f;
			float blue = (float)(color & 0xFF) / 255.0f;
			dust.setColor(red, green, blue);
			dust.setVelocity(velocityX, velocityY, velocityZ);
			dust.setSpriteForAge(this.spriteProvider);
			return dust;
		}
	}
}
