package weathereffects.weathereffects.settings;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import weathereffects.weathereffects.screens.settings.SettingsScreen;

public class SubOptionsButtonOption extends Option
{
	private final PerEntryOption<?> settings;
	private final Enum<?> entry;
	private final Text label;
	
	public SubOptionsButtonOption(String key, PerEntryOption<?> settings, Enum<?> entry)
	{
		super(key);
		this.settings = settings;
		this.entry = entry;
		this.label = new TranslatableText(key).append("...");
	}
	
	@Override
	public ClickableWidget createButton(GameOptions options, int x, int y, int width)
	{
		return new ButtonWidget(x, y, width, 20,
				label, button -> MinecraftClient.getInstance().setScreen(new SettingsScreen(MinecraftClient.getInstance().currentScreen, settings, entry)));
	}
}
