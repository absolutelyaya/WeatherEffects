package de.yaya.weathereffects.settings;

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
		super(id, false);
		this.enumClass = enumClass;
		this.settings = settings;
		this.id = id;
		this.excludedEntries = excludedEntries;
		setDefault();
	}
	
	@Override
	public Text getButtonText()
	{
		return null;
	}
	
	@Override
	public String serialize()
	{
		StringBuilder sb = new StringBuilder("PES#" + id + ":" + ((enumClass.getEnumConstants().length - excludedEntries.size()) * (1 + settings.size())));
		for (E entry : enumClass.getEnumConstants())
		{
			if(!excludedEntries.contains(entry))
			{
				sb.append("\n\t").append(entry.name()).append("=");
				for (AbstractSetting setting : settings)
				{
					sb.append("\n\t\t").append(setting.serialize(entry.name().toLowerCase().replace("_", "-")));
				}
			}
		}
		return sb.toString();
	}
	
	@Override
	public String serialize(String prefix)
	{
		StringBuilder sb = new StringBuilder("PES#" + prefix + "." + id + ":" + ((enumClass.getEnumConstants().length - excludedEntries.size()) * (1 + settings.size())));
		for (E entry : enumClass.getEnumConstants())
		{
			if(!excludedEntries.contains(entry))
			{
				sb.append("\n\t").append(entry.name()).append("=");
				for (AbstractSetting setting : settings)
				{
					sb.append("\n\t\t").append(setting.serialize(entry.name().toLowerCase().replace("_", "-")));
				}
			}
		}
		return sb.toString();
	}
	
	@Override
	public Option asOption()
	{
		return new PerEntryOption<>(id, enumClass, settings, excludedEntries, requirements);
	}
	
	@Override
	public String getOption()
	{
		StringBuilder data = new StringBuilder();
		for(E entry : enumClass.getEnumConstants())
		{
			if(!excludedEntries.contains(entry))
			{
				for (AbstractSetting setting : settings)
				{
					data.append(entry.name()).append("=").append(entry.name().toLowerCase().replace("_", "-"))
							.append(setting.id).append(":").append(setting.getOption()).append("|");
				}
				data.setCharAt(data.length() - 1, '+');
			}
		}
		data.setLength(data.length() - 1);
		return data.toString();
	}
	
	@Override
	public void setDefault()
	{
		for (E entry : enumClass.getEnumConstants())
		{
			if(!excludedEntries.contains(entry))
			{
				for(AbstractSetting setting : settings)
				{
					setting.setDefault(entry.name().toLowerCase().replace("_", "-"));
				}
			}
		}
	}
	
	@Override
	public void setDefault(String prefix)
	{
		setDefault();
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new PerEntrySetting<>(prefix + "." + id, enumClass, settings, excludedEntries);
	}
}
