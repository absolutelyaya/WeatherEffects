package weathereffects.weathereffects.screens.settings;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import weathereffects.weathereffects.settings.PerEntryOption;
import weathereffects.weathereffects.settings.Settings;
import weathereffects.weathereffects.settings.YayButtonList;
import weathereffects.weathereffects.utilities.TranslationUtil;

import java.util.ArrayList;
import java.util.List;

public class SettingsScreen extends AbstractWeatherSettingsScreen
{
	private final Settings.Category category;
	private final PerEntryOption<?> settings;
	
	private ButtonListWidget list;
	private boolean empty;
	private Enum<?> entry;
	
	protected SettingsScreen(Screen parent, Settings.Category category)
	{
		super(category.getTitle(), parent);
		this.category = category;
		this.settings = null;
	}
	
	public SettingsScreen(Screen parent, PerEntryOption<?> settings, Enum<?> entry)
	{
		super(new TranslatableText(TranslationUtil.getTranslationKey("screen", "fog.biome." + entry.name()).toLowerCase()), parent);
		this.category = null;
		this.settings = settings;
		this.entry = entry;
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client == null)
			return;
		this.list = new YayButtonList(this.client, this.width, this.height, 40, this.height - 80, 25);
		List<Option> options = new ArrayList<>();
		if(category != null)
		{
			var list = new ArrayList<>(List.of(Settings.getAsOptions(category)));
			list.removeIf(e -> e.getClass().equals(PerEntryOption.class));
			options.addAll(list);
			for (Option option : Settings.getAsOptions(category))
			{
				if(option.getClass().equals(PerEntryOption.class))
				{
					options.addAll(List.of(((PerEntryOption<?>)option).getEntries()));
				}
			}
		}
		else
			options.addAll(List.of(settings.getOptions(entry)));
		this.list.addAll(options.toArray(Option[]::new));
		this.addSelectableChild(this.list);
		this.list.setRenderBackground(false);
		this.list.setRenderHorizontalShadows(false);
		
		empty = options.size() == 0;
		
		this.addDrawableChild(new ButtonWidget(this.width / 2 - 100,
				this.height - (client != null && client.world != null ? 56 : 26), 200, 20,
				ScreenTexts.DONE, button -> this.client.setScreen(this.parent)));
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		list.render(matrices, mouseX, mouseY, delta);
		
		if(empty && client != null)
		{
			int x = width / 2;
			int y = height / 2;
			int length = client.textRenderer.getWidth(new TranslatableText("screen.weatherEffects.options.empty"));
			if(!showBG && client.world != null)
				fill(matrices, x - (length / 2 + 3), y - 7, x + length / 2 + 3, y + 7, -1072689136);
			drawCenteredText(matrices, client.textRenderer, new TranslatableText("screen.weatherEffects.options.empty"), x, y - 4, 0xFFFFFFFF);
		}
	}
}
