package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class SliderSetting extends AbstractSetting
{
	public final double defaultValue, min, max;
	public final float step;
	public final boolean displayPercent;
	public final int decimals;
	
	private SliderSetting softMin, softMax;
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step)
	{
		super(id);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = true;
		this.decimals = 0;
		SettingsStorage.setDouble(id, defaultValue);
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, String name)
	{
		super(id, name);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = true;
		this.decimals = 0;
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, int decimals)
	{
		super(id);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = decimals == 0;
		this.decimals = decimals;
		SettingsStorage.setDouble(id, defaultValue);
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, int decimals, String name)
	{
		super(id, name);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = decimals == 0;
		this.decimals = decimals;
	}
	
	public String getDefaultValue()
	{
		return Double.toString(defaultValue);
	}
	
	@Override
	public Text getButtonText()
	{
		if(displayPercent)
			return new TranslatableText(translationKey).append(": " + (int)(SettingsStorage.getDouble(id) / max * 100) + "%");
		else
			return new TranslatableText(translationKey).append(": " + String.format("%." + decimals + "f", (SettingsStorage.getDouble(id))));
	}
	
	@Override
	public String serialize()
	{
		return "D#" + id + ":" + SettingsStorage.getDouble(id);
	}
	
	@Override
	public String serialize(String prefix) {
		return "D#" + prefix + "." + id + ":" + SettingsStorage.getDouble(prefix + "." + id);
	}
	
	@Override
	public Option asOption()
	{
		return new YaySlider(translationKey, min, max, step, options -> SettingsStorage.getDouble(id),
				(options, value) -> SettingsStorage.setDouble(id, value), requirements, (a, b) -> getButtonText(), softMin, softMax, defaultValue);
	}
	
	@Override
	public SliderSetting setSoftMin(SliderSetting min)
	{
		this.softMin = min;
		return this;
	}
	
	@Override
	public SliderSetting setSoftMax(SliderSetting max)
	{
		this.softMax = max;
		return this;
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new SliderSetting(prefix + "." + id, defaultValue, min, max, step, decimals, id);
	}
}
