package weathereffects.weathereffects.screens.settings;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import weathereffects.weathereffects.settings.SettingsManager;

public abstract class AbstractWeatherSettingsScreen extends Screen
{
	private static boolean paused;
	private static boolean showBG = true;
	
	protected Screen parent;
	protected CheckboxWidget bgCheckBox;
	protected CheckboxWidget pauseCheckBox;
	
	protected AbstractWeatherSettingsScreen(Text title, Screen parent)
	{
		super(title);
		this.parent = parent;
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client != null)
			paused = client.isPaused();
		bgCheckBox = this.addDrawableChild(new CheckboxWidget(this.width / 2 + 35, this.height - 28, 200, 20,
				new TranslatableText("screen.weatherEffects.options.showbg"), showBG));
		pauseCheckBox = this.addDrawableChild(new CheckboxWidget(this.width / 2 - 125, this.height - 28, 200, 20,
				new TranslatableText("screen.weatherEffects.options.gamepaused"), paused));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		if(showBG)
			renderBackground(matrices);
		OptionsScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 0xFFFFFF);
		if(client != null && client.world != null)
		{
			if(!client.world.isClient)
				pauseCheckBox.active = false;
			if(pauseCheckBox.isChecked() != paused)
				paused = pauseCheckBox.isChecked();
			if(bgCheckBox.isChecked() != showBG)
				showBG = bgCheckBox.isChecked();
		}
		super.render(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public boolean shouldPause()
	{
		return paused;
	}
	
	@Override
	public void removed()
	{
		super.removed();
		SettingsManager.save();
	}
}
