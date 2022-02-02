package weathereffects.weathereffects.screens.widgets;

import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.OrderedText;
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
			val = Double.max(SettingsStorage.getDouble(softMin.id) + ((YaySlider)option).getStep(), val);
		if(softMax != null)
			val = Double.min(SettingsStorage.getDouble(softMax.id) - ((YaySlider)option).getStep(), val);
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
		this.value = defaultValue;
		option.set(options, defaultValue); ///TODO: fix slider reset rendering issue (wrong slider percentage)
		updateMessage();
		this.options.write();
	}
}
