package de.yaya.weathereffects.screens;

import de.yaya.weathereffects.WeatherEffects;
import de.yaya.weathereffects.settings.Settings;
import de.yaya.yayconfig.screens.settings.AbstractWeatherSettingsScreen;
import de.yaya.yayconfig.screens.settings.SettingsScreen;
import de.yaya.yayconfig.screens.widgets.YaySliderWidget;
import de.yaya.yayconfig.settings.SliderSetting;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.text.TranslatableText;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainSettingsScreen extends AbstractWeatherSettingsScreen
{
	YaySliderWidget particlesWidget;
	
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
		Settings.PRESET.UpdateOptions(getPresets());
		this.addDrawableChild(Settings.PRESET.asOption().createButton(client.options, this.width / 2 - 155, this.height / 6 - 6, 150));
		particlesWidget = new YaySliderWidget(client.options, this.width / 2 + 5, this.height / 6 - 6, 150, 20,
				(DoubleOption)particleAmount.asOption(), null, null, null, () -> particleAmount.defaultValue);
		this.addDrawableChild(particlesWidget);
		
		for (int i = 1; i < Settings.Category.values().length; i++)
		{
			int x = (i - 1) % 2;
			int y = (i - 1) / 2;
			Settings.Category cat = Arrays.stream(Settings.Category.values()).toList().get(i);
			
			this.addDrawableChild(new ButtonWidget(this.width / 2 - 155 + 160 * x, this.height / 6 + 42 + 24 * y, 150, 20,
					cat.getTitle().append("..."), button -> this.client.setScreen(new SettingsScreen(this, cat))));
		}
		
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 100,
				this.height - (client != null && client.world != null ? 56 : 26), 200, 20,
				ScreenTexts.DONE, button -> this.client.setScreen(this.parent)));
	}
	
	private List<String> getPresets()
	{
		List<String> result = new ArrayList<>();
		File[] files = new File(FabricLoader.getInstance().getConfigDir().toFile(), WeatherEffects.MODID + "/presets").listFiles();
		if(files != null)
		{
			for (File f : files)
			{
				result.add(f.getName().split("\\.")[0]);
			}
		}
		if(result.size() == 0)
			result = List.of("§4ERROR");
		return result;
	}
	
	public void UpdateSlider()
	{
		if(particlesWidget != null)
			particlesWidget.refresh(Settings.PARTICLE_AMOUNT.id);
	}
}
