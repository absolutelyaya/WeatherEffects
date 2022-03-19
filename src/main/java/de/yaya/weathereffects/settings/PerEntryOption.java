package de.yaya.weathereffects.settings;

import de.yaya.weathereffects.utilities.TranslationUtil;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;

import java.util.ArrayList;
import java.util.List;

public class PerEntryOption<E extends Enum<E>> extends Option
{
	public final Class<E> enumClass;
	
	private final List<AbstractSetting> settings;
	private final String id;
	private final List<E> excludedEntries;
	private final List<BooleanSetting> requirements;
	
	public PerEntryOption(String id, Class<E> enumClass, List<AbstractSetting> settings, List<E> excludedEntries, List<BooleanSetting> requirements)
	{
		super(id);
		this.enumClass = enumClass;
		this.settings = settings;
		this.id = id;
		this.excludedEntries = excludedEntries;
		this.requirements = requirements;
	}
	
	public Option[] getOptions(Enum<?> entry)
	{
		if(!entry.getClass().equals(enumClass))
			return null;
		List<Option> options = new ArrayList<>();
		for (SettingsOption setting : settings)
		{
			options.add(setting.addIDPrefix(entry.name().toLowerCase().replace("_", "-")).asOption());
		}
		return options.toArray(Option[]::new);
	}
	
	public Option[] getEntries()
	{
		List<Option> entries = new ArrayList<>();
		for (E entry : enumClass.getEnumConstants())
		{
			if(!excludedEntries.contains(entry))
				entries.add(new SubOptionsButtonOption(TranslationUtil.getTranslationKey("screen",
						id + "." + entry.name()).toLowerCase().replace("_", "-"), this, entry, requirements));
		}
		return entries.toArray(Option[]::new);
	}
	
	@Override
	public ClickableWidget createButton(GameOptions options, int x, int y, int width)
	{
		return null;
	}
}
