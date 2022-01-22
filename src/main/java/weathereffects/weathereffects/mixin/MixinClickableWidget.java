package weathereffects.weathereffects.mixin;

import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import weathereffects.weathereffects.accessors.ClickableWidgetAccessor;
import weathereffects.weathereffects.settings.BooleanSetting;
import weathereffects.weathereffects.settings.SettingsStorage;

import java.util.List;

@Mixin(ClickableWidget.class)
public class MixinClickableWidget implements ClickableWidgetAccessor
{
	@Shadow public boolean active;
	@Unique List<BooleanSetting> requirements;
	
	@Inject(method = "renderButton", at = @At("TAIL"))
	public void onRenderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci)
	{
		if(requirements != null)
		{
			if(requirements.size() > 0)
			{
				for(BooleanSetting requirement : requirements)
				{
					if(!SettingsStorage.getBoolean(requirement.id))
					{
						active = false;
						return;
					}
				}
			}
			active = true;
		}
	}
	
	public void setRequirements(List<BooleanSetting> requirements)
	{
		this.requirements = requirements;
	}
}
