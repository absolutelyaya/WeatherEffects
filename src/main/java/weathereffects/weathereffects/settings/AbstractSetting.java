package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.Text;
import weathereffects.weathereffects.utilities.TranslationUtil;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public abstract class AbstractSetting implements SettingsOption
{
	public String id, translationKey;
	
	protected List<BooleanSetting> requirements = new ArrayList<>();
	
	public AbstractSetting(String id)
	{
		this.id = id;
		this.translationKey = TranslationUtil.getTranslationKey("setting", id);
	}
	
	public abstract Text getButtonText();
	
	public abstract String serialize();
	
	public <T extends AbstractSetting>T setRequirements(List<BooleanSetting> bools)
	{
		for(BooleanSetting bool : bools)
		{
			if(bool != this)
				requirements.add(bool);
		}
		return (T)this;
	}
	
	@Override
	public SliderSetting setSoftMax(SliderSetting max)
	{
		return null;
	}
	
	@Override
	public SliderSetting setSoftMin(SliderSetting min)
	{
		return null;
	}
}
