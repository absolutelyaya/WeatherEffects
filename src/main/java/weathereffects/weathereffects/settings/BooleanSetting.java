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
	
	public BooleanSetting(String id, boolean defaultValue)
	{
		super(id);
		this.defaultValue = defaultValue;
		SettingsStorage.setBoolean(id, defaultValue);
	}
	
	public BooleanSetting(String id, boolean defaultValue, String name)
	{
		super(id, name);
		this.defaultValue = defaultValue;
	}
	
	public String getDefaultValue()
	{
		return Boolean.toString(defaultValue);
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
		return new BooleanSetting(prefix + "." + id, defaultValue, id);
	}
}
