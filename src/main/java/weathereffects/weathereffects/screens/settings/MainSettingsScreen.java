package weathereffects.weathereffects.screens.settings;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import weathereffects.weathereffects.settings.Settings;
import weathereffects.weathereffects.settings.SliderSetting;

public class MainSettingsScreen extends AbstractWeatherSettingsScreen
{
	public MainSettingsScreen(Screen parent)
	{
		super(new TranslatableText("screen.weatherEffects.options.main.title"), parent);
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client == null)
			return;
		SliderSetting particleAmount = Settings.PARTICLE_AMOUNT;
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 - 6, 150, 20, Text.of("Preset: TBA"), button -> {})).active = false;
		this.addDrawableChild(new DoubleOptionSliderWidget(client.options, this.width / 2 + 5, this.height / 6 - 6, 150, 20,
				(DoubleOption)particleAmount.asOption(), null));
		
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 42, 150, 20,
				new TranslatableText("screen.weatherEffects.options.rain.title").append("..."), button -> this.client.setScreen(new RainSettingsScreen(this))));
		this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 42, 150, 20,
				new TranslatableText("screen.weatherEffects.options.snow.title").append("..."), button -> this.client.setScreen(null)));
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 66, 150, 20,
				new TranslatableText("screen.weatherEffects.options.sandstorm.title").append("..."), button -> this.client.setScreen(null)));
		this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 66, 150, 20,
				new TranslatableText("screen.weatherEffects.options.clouds.title").append("..."), button -> this.client.setScreen(null)));
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 155, this.height / 6 + 90, 150, 20,
				new TranslatableText("screen.weatherEffects.options.fog.title").append("..."), button -> this.client.setScreen(null)));
		this.addDrawableChild(new ButtonWidget(this.width / 2 + 5, this.height / 6 + 90, 150, 20,
				new TranslatableText("screen.weatherEffects.options.stars.title").append("... (TBA)"), button -> this.client.setScreen(null))).active = false;
		
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 100,
				this.height - (client != null && client.world != null ? 56 : 26), 200, 20,
				ScreenTexts.DONE, button -> this.client.setScreen(this.parent)));
	}
}
