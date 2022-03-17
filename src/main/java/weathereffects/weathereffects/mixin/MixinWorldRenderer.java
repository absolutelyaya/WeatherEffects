package weathereffects.weathereffects.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weathereffects.weathereffects.WeatherEffects;
import weathereffects.weathereffects.settings.SettingsStorage;

import java.util.Random;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer
{
	@Shadow private ClientWorld world;
	@Shadow private int field_20793;
	
	@Shadow @Final private MinecraftClient client;
	
	@Unique private float fogDistance;
	@Unique private float fogEndDistance = -1;
	@Unique private boolean isRaining;
	@Unique private Vec2f windDir = new Vec2f(0.2f, 0.2f), curWindDir = new Vec2f(0.2f, 0.2f);
	
	@Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
	public void onRenderWeather(LightmapTextureManager manager, float delta, double x, double y, double z, CallbackInfo ci)
	{
		if((world.getRainGradient(delta) > 0 || world.getThunderGradient(delta) > 0) && !(client.isPaused() && world.isClient))
		{
			if(client.player != null)
			{
				Vec3d playerVelocity = client.player.getVelocity().multiply(30f, 5f, 30f);
				x += playerVelocity.x;
				y += playerVelocity.y;
				z += playerVelocity.z;
			}
			double minHeight = y + 15;
			double maxHeight = this.world.getDimensionEffects().getCloudsHeight();
			Random r = new Random();
			if(r.nextFloat() < 0.01 * SettingsStorage.getDouble("sandstorm.wind.change-rate") * delta)
			{
				windDir = new Vec2f(r.nextFloat() - 0.5f, r.nextFloat() - 0.5f).multiply((float)SettingsStorage.getDouble("sandstorm.wind.strength"));
			}
			curWindDir = new Vec2f(MathHelper.lerp((float)(0.02 * SettingsStorage.getDouble("sandstorm.wind.change-speed") * delta), curWindDir.x, windDir.x),
					MathHelper.lerp((float)(0.02 * SettingsStorage.getDouble("sandstorm.wind.change-speed") * delta), curWindDir.y, windDir.y));
			for (int i = 0; i < world.getRainGradient(delta) * SettingsStorage.getDouble("general.particle-amount") * delta; i++)
			{
				Vec3d pos = new Vec3d((r.nextDouble() - 0.5) * 30,
						Math.min(minHeight, maxHeight) + (r.nextDouble() - 0.5) * 3,
						(r.nextDouble() - 0.5) * 30);
				if(world.getTopY(Heightmap.Type.MOTION_BLOCKING, (int)(x + pos.x), (int)(z + pos.z)) < pos.y)
				{
					switch (world.getBiome(new BlockPos(pos.add(new Vec3d(x, 0, z)))).getPrecipitation())
					{
						case RAIN -> {
							if (SettingsStorage.getBoolean("rain.enabled"))
								rain(r, x, z, pos);
						}
						case SNOW -> {
							if (SettingsStorage.getBoolean("snow.enabled"))
								snow(r, x, z, pos);
						}
						default -> {
							if (SettingsStorage.getBoolean("sandstorm.enabled"))
								sandstorm(r, x, z, pos);
						}
					}
				}
			}
		}
		ci.cancel();
	}
	
	@Unique
	public void rain(Random r, double x, double z, Vec3d pos)
	{
		double amount = SettingsStorage.getDouble("rain.amount");
		if(amount == 1.0 || r.nextFloat() < SettingsStorage.getDouble("rain.amount"))
		{
			world.addParticle(WeatherEffects.RAIN_DROP, x + pos.x, pos.y, z + pos.z, 0, 0, 0);
		}
	}
	
	@Unique
	public void snow(Random r, double x, double z, Vec3d pos)
	{
		double amount = SettingsStorage.getDouble("snow.amount");
		if(amount == 1.0 || r.nextFloat() < SettingsStorage.getDouble("snow.amount"))
		{
			world.addParticle(WeatherEffects.SNOW_FLAKE, x + pos.x * 3, pos.y - r.nextFloat() * 10f,
					z + pos.z * 3, 0, 0, 0);
		}
	}
	
	@Unique
	public void sandstorm(Random r, double x, double z, Vec3d pos)
	{
		double amount = SettingsStorage.getDouble("sandstorm.dust.amount");
		if(amount == 1.0 || r.nextFloat() < SettingsStorage.getDouble("sandstorm.dust.amount"))
		{
			Biome.Category biome = world.getBiome(new BlockPos(pos).add(new Vec3i(x, 0, z))).getCategory();
			if(biome.equals(Biome.Category.DESERT) || biome.equals(Biome.Category.MESA))
			{
				int top = world.getTopY(Heightmap.Type.MOTION_BLOCKING, (int)(x + pos.x), (int)(z + pos.z));
				float particle = r.nextFloat();
				BlockState b = world.getBlockState(new BlockPos(x + pos.x, top, z + pos.z).down());
				if(b.isOf(Blocks.SAND) || b.isOf(Blocks.RED_SAND))
				{
					if(particle < 0.75f)
					{
						if(SettingsStorage.getBoolean("sandstorm.ground-dust"))
							world.addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, b.getBlock().asItem().getDefaultStack()),
									x + pos.x, top + 0.1, z + pos.z, curWindDir.x, r.nextFloat() / 5, curWindDir.y);
					}
					else
						if(SettingsStorage.getBoolean("sandstorm.wind-dust"))
							world.addParticle(WeatherEffects.WIND_DUST,
									x + pos.x, top + r.nextInt(5), z + pos.z, curWindDir.x, 0.1, curWindDir.y);
				}
			}
		}
	}
	
	@Inject(method = "render", at = @At(value = "INVOKE",
			target = "Lnet/minecraft/client/render/WorldRenderer;updateChunks(Lnet/minecraft/client/render/Camera;)V",
			shift = At.Shift.AFTER))
	public void onRenderFog(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci)
	{
		if(!SettingsStorage.getBoolean("fog.enabled"))
			return;
		Profiler profiler = this.world.getProfiler();
		profiler.swap("fog");
		Vec3d pos = camera.getPos();
		applyFogSettings(world.getBiome(new BlockPos(pos.x, pos.y, pos.z)).getCategory(), camera, tickDelta, gameRenderer);
	}
	
	@Unique
	public void applyFogSettings(Biome.Category biomeCategory, Camera camera, float tickDelta, GameRenderer gameRenderer)
	{
		if(fogDistance == -1)
			fogDistance = RenderSystem.getShaderFogStart();
		if(fogEndDistance == -1)
			fogEndDistance = RenderSystem.getShaderFogEnd();
		float viewDistance = gameRenderer.getViewDistance();
		CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
		Entity entity = camera.getFocusedEntity();
		if(cameraSubmersionType.equals(CameraSubmersionType.NONE) &&
				   (entity instanceof LivingEntity && !((LivingEntity)entity).hasStatusEffect(StatusEffects.BLINDNESS)))
		{
			float fogSpeed = (float)SettingsStorage.getDouble(biomeCategory.getName().toLowerCase().replace("_", "-") + ".fog.speed");
			float g = world.getRainGradient(tickDelta);
			if (g > 0f && !(g < 1f && isRaining))
			{
				if(g == 1)
					isRaining = true;
				switch(biomeCategory)
				{
					case DESERT, MESA -> {
						fogDistance = MathHelper.lerp(tickDelta * 0.01f * fogSpeed, fogDistance, 16f);
						fogEndDistance = MathHelper.lerp(tickDelta * 0.01f * fogSpeed, fogEndDistance, 64f);
						RenderSystem.setShaderFogStart(fogDistance);
						RenderSystem.setShaderFogEnd(fogEndDistance);
						return;
					}
					case SWAMP -> {
						fogDistance = MathHelper.lerp(tickDelta * 0.01f * fogSpeed, fogDistance, 4f);
						fogEndDistance = MathHelper.lerp(tickDelta * 0.01f * fogSpeed, fogEndDistance, 96f);
						RenderSystem.setShaderFogStart(fogDistance);
						RenderSystem.setShaderFogEnd(fogEndDistance);
						return;
					}
				}
			}
			else if(g == 0 && isRaining)
				isRaining = false;
			float b = MathHelper.clamp(viewDistance / 10.0f, 4.0f, 64.0f);
			fogDistance = MathHelper.lerp(fogSpeed * 0.04f * tickDelta, fogDistance, Math.max(viewDistance, 32.0f) - b);
			fogEndDistance = MathHelper.lerp(fogSpeed * 0.04f * tickDelta, fogEndDistance, Math.max(viewDistance, 32.0f));
			RenderSystem.setShaderFogStart(fogDistance);
			RenderSystem.setShaderFogEnd(fogEndDistance);
		}
	}
	
	@Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
	public void onTickRainSplashing(Camera camera, CallbackInfo ci)
	{
		BlockPos blockPos = new BlockPos(camera.getPos());
		if(world.isRaining() && !(client.isPaused() && world.isClient) && SettingsStorage.getBoolean("rain.enabled"))
		{
			Random random = new Random();
			int k = random.nextInt(21) - 10;
			int l = random.nextInt(21) - 10;
			BlockPos blockPos2 = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, blockPos.add(k, 0, l).down());
			if (blockPos2 != null && random.nextInt(3) < this.field_20793++ &&
						world.getBiome(blockPos2).getPrecipitation().equals(Biome.Precipitation.RAIN))
			{
				this.field_20793 = 0;
				if (blockPos2.getY() > blockPos.getY() + 1 && world.getTopPosition(Heightmap.Type.WORLD_SURFACE,
						blockPos).getY() > MathHelper.floor((float) blockPos.getY()))
				{
					world.playSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
				} else
				{
					world.playSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
				}
			}
		}
		ci.cancel();
	}
	
	@Inject(method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V",
			at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/util/Identifier;)V",
					shift = At.Shift.AFTER))
	public void onRenderClouds(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, double d, double e, double f, CallbackInfo ci)
	{
		if(!SettingsStorage.getBoolean("clouds.enabled"))
			return;
		float gradient = world.getRainGradient(tickDelta);
		float thunderGradient = world.getThunderGradient(tickDelta);
		
		RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		if(thunderGradient > 0f && thunderGradient < 1f)
		{
			RenderSystem.setShaderTexture(0, new Identifier(WeatherEffects.MODID,"textures/environment/thunder_clouds" + (int)(thunderGradient * 3) + ".png"));
		}
		else if(gradient > 0f && gradient < 1f)
		{
			RenderSystem.setShaderTexture(0, new Identifier(WeatherEffects.MODID,"textures/environment/rain_clouds" + (int)(gradient * 3) + ".png"));
		}
		else if(gradient > 0f)
		{
			RenderSystem.setShaderTexture(0, new Identifier(WeatherEffects.MODID,
					thunderGradient > 0 ? "textures/environment/thunder_clouds2.png" : "textures/environment/rain_clouds2.png"));
		}
		else
		{
			RenderSystem.setShaderTexture(0, new Identifier(WeatherEffects.MODID,"textures/environment/clouds.png"));
		}
	}
}
