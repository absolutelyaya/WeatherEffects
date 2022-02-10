package weathereffects.weathereffects.screens.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import weathereffects.weathereffects.settings.SettingsStorage;
import weathereffects.weathereffects.settings.SliderSetting;
import weathereffects.weathereffects.settings.YaySlider;

import java.util.List;

public class YaySliderWidget extends DoubleOptionSliderWidget
{
	private final DoubleOption option;
	private final SliderSetting softMin, softMax;
	private final double defaultValue;
	
	private float popupOpacity;
	private Text popupMessage;
	
	public YaySliderWidget(GameOptions gameOptions, int x, int y, int width, int height, DoubleOption option, List<OrderedText> orderedTooltip, SliderSetting softMin, SliderSetting softMax, double defaultValue)
	{
		super(gameOptions, x, y, width, height, option, orderedTooltip);
		this.option = option;
		this.softMin = softMin;
		this.softMax = softMax;
		this.defaultValue = defaultValue;
	}
	
	@Override
	protected void applyValue()
	{
		double val = this.option.getValue(this.value);
		if(softMin != null)
		{
			double min = SettingsStorage.getDouble(softMin.id) + ((YaySlider) option).getStep();
			if(val < min - ((YaySlider) option).getStep())
				updatePopup(new TranslatableText("popup.weathereffects.restricted-slider", new TranslatableText(softMin.translationKey)), true);
			val = Double.max(min, val);
		}
		if(softMax != null)
		{
			double max = SettingsStorage.getDouble(softMax.id) - ((YaySlider) option).getStep();
			if(val > max + ((YaySlider) option).getStep())
				updatePopup(new TranslatableText("popup.weathereffects.restricted-slider", new TranslatableText(softMax.translationKey)), true);
			val = Double.min(max, val);
		}
		this.value = (val - option.getMin()) / (option.getMax() - option.getMin());
		this.option.set(this.options, val);
		this.options.write();
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		boolean b = super.mouseClicked(mouseX, mouseY, button);
		if(clicked(mouseX, mouseY) && button == 1)
			reset();
		return b;
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if(keyCode == GLFW.GLFW_KEY_R)
			reset();
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
	void reset()
	{
		this.value = (defaultValue - option.getMin()) / (option.getMax() - option.getMin());
		applyValue();
		updateMessage();
		onRelease(0, 0);
		updatePopup(new TranslatableText("popup.weathereffects.reset-slider"), false);
	}
	
	void updatePopup(Text popup, boolean force)
	{
		if(popupOpacity <= 0f || force || popup.equals(popupMessage))
		{
			popupOpacity = 2f;
			popupMessage = popup;
		}
	}
	
	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.renderButton(matrices, mouseX, mouseY, delta);
		if(popupOpacity > 0f)
		{
			TextRenderer tr = MinecraftClient.getInstance().textRenderer;
			int length = tr.getWidth(popupMessage);
			int x = this.x + this.width / 2;
			int y = this.y - this.height / 2;
			fill(matrices, x - length / 2 - 3, y - 3, x + length / 2 + 3, y + 10,
					MathHelper.ceil(Math.min(popupOpacity + 0.025f, 1f) / 2f * 255f) << 24);
			ClickableWidget.drawCenteredText(matrices, tr,
					popupMessage, x, y, 0xffffff | MathHelper.ceil(Math.min(popupOpacity + 0.025f, 1f) * 255f) << 24);
			popupOpacity -= delta / 10f;
		}
	}
	
	@Override
	protected void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, WIDGETS_TEXTURE);
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
		int i = (active && visible ? (this.isHovered() ? 2 : 1) * 20 : 0);
		this.drawTexture(matrices, this.x + (int)(this.value * (double)(this.width - 8)), this.y, 0, 46 + i, 4, 20);
		this.drawTexture(matrices, this.x + (int)(this.value * (double)(this.width - 8)) + 4, this.y, 196, 46 + i, 4, 20);
	}
}
