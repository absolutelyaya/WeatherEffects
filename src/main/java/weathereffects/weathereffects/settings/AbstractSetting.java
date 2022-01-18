package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import weathereffects.weathereffects.utilities.TranslationUtil;

@Environment(EnvType.CLIENT)
public abstract class AbstractSetting implements SettingsOption
{
	public String id, translationKey;
	
	public AbstractSetting(String id)
	{
		this.id = id;
		this.translationKey = TranslationUtil.getTranslationKey("setting", id);
	}
	
	public abstract Text getButtonText();
	
	public abstract String serialize();
}
