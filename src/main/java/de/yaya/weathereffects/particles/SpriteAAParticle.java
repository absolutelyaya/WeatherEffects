package de.yaya.weathereffects.particles;

import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

public abstract class SpriteAAParticle extends SpriteBillboardParticle
{
	protected final SpriteProvider spriteProvider;
	
	protected Vec3f scale;
	
	protected SpriteAAParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider)
	{
		super(world, x, y, z);
		float s = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
		this.scale = new Vec3f(s, s, s);
		this.spriteProvider = spriteProvider;
	}
	
	@Override
	public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta)
	{
		Vec3d camPos = camera.getPos();
		Vec3d dir = new Vec3d(x, y, z).subtract(camPos).normalize();
		float f = (float)(MathHelper.lerp(tickDelta, this.prevPosX, this.x) - camPos.getX());
		float g = (float)(MathHelper.lerp(tickDelta, this.prevPosY, this.y) - camPos.getY());
		float h = (float)(MathHelper.lerp(tickDelta, this.prevPosZ, this.z) - camPos.getZ());
		
		Vec3f[] vec3fs = new Vec3f[]{
				new Vec3f(-1.0F, -1.0F, 0.0F),
				new Vec3f(-1.0F, 1.0F, 0.0F),
				new Vec3f(1.0F, 1.0F, 0.0F),
				new Vec3f(1.0F, -1.0F, 0.0F)};
		
		for(int k = 0; k < 4; ++k)
		{
			Vec3f vec3f = vec3fs[k];
			vec3f.rotate(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)Math.toDegrees(Math.atan2(dir.x, dir.z))));
			vec3f.multiplyComponentwise(scale.getX(), scale.getY(), scale.getZ());
			vec3f.add(f, g, h);
		}
		
		int n = this.getBrightness(tickDelta);
		vertexConsumer.vertex(vec3fs[0].getX(), vec3fs[0].getY(), vec3fs[0].getZ()).texture(getMaxU(), getMaxV()).color(this.red, this.green, this.blue, this.alpha).light(n).next();
		vertexConsumer.vertex(vec3fs[1].getX(), vec3fs[1].getY(), vec3fs[1].getZ()).texture(getMaxU(), getMinV()).color(this.red, this.green, this.blue, this.alpha).light(n).next();
		vertexConsumer.vertex(vec3fs[2].getX(), vec3fs[2].getY(), vec3fs[2].getZ()).texture(getMinU(), getMinV()).color(this.red, this.green, this.blue, this.alpha).light(n).next();
		vertexConsumer.vertex(vec3fs[3].getX(), vec3fs[3].getY(), vec3fs[3].getZ()).texture(getMinU(), getMaxV()).color(this.red, this.green, this.blue, this.alpha).light(n).next();
	}
}
