package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class BooleanSetting extends AbstractSetting
{
	public final boolean defaultValue;
	
	public BooleanSetting(String id, boolean defaultValue)
	{
		super(id);
		this.defaultValue = defaultValue;
		SettingsStorage.setBoolean(id, defaultValue);
	}
	
	public boolean getDefaultValue()
	{
		return defaultValue;
	}
	
	@Override
	public Text getButtonText()
	{
		return new TranslatableText(translationKey, defaultValue);
	}
	
	@Override
	public String serialize()
	{
		return "B#" + id + ":" + SettingsStorage.getBoolean(id);
	}
	
	@Override
	public Option asOption()
	{
		return CyclingOption.create(translationKey,
				ignored -> SettingsStorage.getBoolean(id),
				(ignored, option, value) -> SettingsStorage.setBoolean(id, value));
	}
}
