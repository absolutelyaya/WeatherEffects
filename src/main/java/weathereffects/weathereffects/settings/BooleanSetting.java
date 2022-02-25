package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class BooleanSetting extends AbstractSetting
{
	public final boolean defaultValue;
	
	public BooleanSetting(String id, boolean defaultValue, boolean setDefault)
	{
		super(id, setDefault);
		this.defaultValue = defaultValue;
		if(setDefault)
			setDefault();
	}
	
	public BooleanSetting(String id, boolean defaultValue, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.defaultValue = defaultValue;
		if(setDefault)
			setDefault();
	}
	
	public String getDefaultValue()
	{
		return Boolean.toString(defaultValue);
	}
	
	@Override
	public void setDefault()
	{
		SettingsStorage.setBoolean(id, defaultValue);
	}
	
	@Override
	public void setDefault(String prefix)
	{
		SettingsStorage.setBoolean(prefix + "." + id, defaultValue);
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
	public String serialize(String prefix) {
		return "B#" + prefix + "." + id + ":" + SettingsStorage.getBoolean(prefix + "." + id);
	}
	
	@Override
	public Option asOption()
	{
		return new YayCycler(translationKey,
				ignored -> SettingsStorage.getBoolean(id), (ignored, option, value) -> SettingsStorage.setBoolean(id, value),
				CyclingButtonWidget::onOffBuilder, requirements);
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new BooleanSetting(prefix + "." + id, defaultValue, id, setDefault);
	}
}
