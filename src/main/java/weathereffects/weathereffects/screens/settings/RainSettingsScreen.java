package weathereffects.weathereffects.screens.settings;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import weathereffects.weathereffects.settings.Settings;

public class RainSettingsScreen extends AbstractWeatherSettingsScreen
{
	private ButtonListWidget list;
	
	protected RainSettingsScreen(Screen parent)
	{
		super(new TranslatableText("screen.weatherEffects.options.rain.title"), parent);
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client == null)
			return;
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 60, 25);
		this.list.addAll(Settings.getAsOptions(Settings.Category.RAIN));
		this.addSelectableChild(this.list);
		this.list.setRenderBackground(false);
		this.list.setRenderHorizontalShadows(false);
		
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 100,
				this.height - (client != null && client.world != null ? 56 : 26), 200, 20,
				ScreenTexts.DONE, button -> this.client.setScreen(this.parent)));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		list.render(matrices, mouseX, mouseY, delta);
	}
}
