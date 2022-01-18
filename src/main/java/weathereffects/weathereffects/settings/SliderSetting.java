package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class SliderSetting extends AbstractSetting
{
	public final double defaultValue, min, max;
	public final float step;
	
	public SliderSetting(String id, double defaultValue, double min, double max, float step)
	{
		super(id);
		this.defaultValue = defaultValue;
		this.min = min;
		this.max = max;
		this.step = step;
		SettingsStorage.setDouble(id, defaultValue);
	}
	
	public double getDefaultValue()
	{
		return defaultValue;
	}
	
	@Override
	public Text getButtonText()
	{
		return new TranslatableText(translationKey).append(" " + (int)(SettingsStorage.getDouble(id) / max * 100) + "%");
	}
	
	@Override
	public String serialize()
	{
		return "D#" + id + ":" + SettingsStorage.getDouble(id);
	}
	
	@Override
	public Option asOption()
	{
		return new DoubleOption(translationKey, min, max, step, options -> SettingsStorage.getDouble(id),
				(options, value) -> SettingsStorage.setDouble(id, value), (a, b) -> getButtonText());
	}
}
