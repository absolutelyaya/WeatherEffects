package weathereffects.weathereffects.settings;

import net.minecraft.client.option.Option;
import net.minecraft.text.Text;

import java.util.List;

public class PerEntrySetting<E extends Enum<E>> extends AbstractSetting
{
	public final Class<E> enumClass;
	
	private final List<AbstractSetting> settings;
	private final String id;
	private final List<E> excludedEntries;
	
	public PerEntrySetting(String id, Class<E> enumClass, List<AbstractSetting> settings, List<E> excludedEntries)
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
		StringBuilder sb = new StringBuilder("PES#" + id + ":");
		for (E entry : enumClass.getEnumConstants())
		{
			if(!excludedEntries.contains(entry))
			{
				sb.append("\n\t").append(entry.name()).append(":");
				for (AbstractSetting setting : settings)
				{
					sb.append("\n\t\t").append(setting.serialize());
				}
			}
		}
		return sb.toString();
	}
	
	@Override
	public Option asOption()
	{
		return new PerEntryOption<>(id, enumClass, settings, excludedEntries);
	}
}
