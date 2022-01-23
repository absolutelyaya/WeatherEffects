package weathereffects.weathereffects.screens.settings;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import weathereffects.weathereffects.settings.Settings;

public class SettingsScreen extends AbstractWeatherSettingsScreen
{
	private final Settings.Category category;
	
	private ButtonListWidget list;
	private boolean empty;
	
	protected SettingsScreen(Screen parent, Settings.Category category)
	{
		super(category.getTitle(), parent);
		this.category = category;
	}
	
	@Override
	protected void init()
	{
		super.init();
		if(client == null)
			return;
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 60, 25);
		Option[] options = Settings.getAsOptions(category);
		this.list.addAll(options);
		this.addSelectableChild(this.list);
		this.list.setRenderBackground(false);
		this.list.setRenderHorizontalShadows(false);
		
		empty = options.length == 0;
		
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
			int length = client.textRenderer.getWidth("This is currently empty...");
			if(!showBG && client.world != null)
				fill(matrices, x - (length / 2 + 3), y - 7, x + length / 2 + 3, y + 7, -1072689136);
			drawCenteredText(matrices, client.textRenderer, "This is currently empty...", x, y - 4, 0xFFFFFFFF);
		}
	}
}
