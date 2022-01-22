package weathereffects.weathereffects.screens.widgets;

import net.minecraft.client.gui.widget.DoubleOptionSliderWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.OrderedText;
import weathereffects.weathereffects.settings.SettingsStorage;
import weathereffects.weathereffects.settings.SliderSetting;
import weathereffects.weathereffects.settings.YaySlider;

import java.util.List;

public class YaySliderWidget extends DoubleOptionSliderWidget
{
	private final DoubleOption option;
	private final SliderSetting softMin, softMax;
	
	public YaySliderWidget(GameOptions gameOptions, int x, int y, int width, int height, DoubleOption option, List<OrderedText> orderedTooltip, SliderSetting softMin, SliderSetting softMax)
	{
		super(gameOptions, x, y, width, height, option, orderedTooltip);
		this.option = option;
		this.softMin = softMin;
		this.softMax = softMax;
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
}
