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
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, boolean setDefault)
	{
		super(id, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = true;
		this.decimals = 0;
		if(setDefault)
			setDefault();
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = true;
		this.decimals = 0;
		if(setDefault)
			setDefault();
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, int decimals, boolean setDefault)
	{
		super(id, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = decimals == 0;
		this.decimals = decimals;
		if(setDefault)
			setDefault();
	}
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step, int decimals, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		this.displayPercent = decimals == 0;
		this.decimals = decimals;
		if(setDefault)
			setDefault();
	}
	
	public String getDefaultValue()
	{
		return Double.toString(defaultValue);
	}
	
	@Override
	public void setDefault()
	{
		SettingsStorage.setDouble(id, defaultValue);
	}
	
	@Override
	public void setDefault(String prefix)
	{
		SettingsStorage.setDouble(prefix + "." + id, defaultValue);
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
		return new SliderSetting(prefix + "." + id, defaultValue, min, max, step, decimals, id, setDefault);
	}
}
