package weathereffects.weathereffects.settings;

import net.minecraft.client.option.Option;
import net.minecraft.text.Text;

import java.util.List;

public class PerEntrySetting<E extends Enum<E>> extends AbstractSetting
{
	public final Class<E> enumClass;
	
	private final List<SettingsOption> settings;
	private final String id;
	private final List<E> excludedEntries;
	
	//TODO: Save the settings values
	
	public PerEntrySetting(String id, Class<E> enumClass, List<SettingsOption> settings, List<E> excludedEntries)
	{
		super(id);
		this.enumClass = enumClass;
		this.settings = settings;
		this.id = id;
		this.excludedEntries = excludedEntries;
	}
	
	@Override
	public Text getButtonText()
	{
		return null;
	}
	
	@Override
	public String serialize()
	{
		return null;
	}
	
	@Override
	public Option asOption()
	{
		return new PerEntryOption<>(id, enumClass, settings, excludedEntries);
	}
}
