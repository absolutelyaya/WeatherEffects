package de.yaya.weathereffects.settings;

import de.yaya.weathereffects.accessors.ClickableWidgetAccessor;
import de.yaya.weathereffects.screens.settings.SettingsScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class SubOptionsButtonOption extends Option
{
	private final PerEntryOption<?> settings;
	private final Enum<?> entry;
	private final Text label;
	private final List<BooleanSetting> requirements;
	
	public SubOptionsButtonOption(String key, PerEntryOption<?> settings, Enum<?> entry, List<BooleanSetting> requirements)
	{
		super(key);
		this.settings = settings;
		this.entry = entry;
		this.label = new TranslatableText(key).append("...");
		this.requirements = requirements;
	}
	
	@Override
	public ClickableWidget createButton(GameOptions options, int x, int y, int width)
	{
		ClickableWidget button = new ButtonWidget(x, y, width, 20,
				label, b -> MinecraftClient.getInstance().setScreen(new SettingsScreen(MinecraftClient.getInstance().currentScreen, settings, entry)));
				((ClickableWidgetAccessor)button).setRequirements(requirements);
		return button;
	}
}
