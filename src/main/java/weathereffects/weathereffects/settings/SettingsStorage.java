package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SettingsStorage
{
	public static final Map<String, Enum<?>> ENUM_SETTINGS = new HashMap<>();
	public static final Map<String, Boolean> BOOLEAN_SETTINGS = new HashMap<>();
	public static final Map<String, Double> DOUBLE_SETTINGS = new HashMap<>();
	
	public static void setEnum(String id, Enum<?> value)
	{
		if(value == null)
			return;
		ENUM_SETTINGS.put(id, value);
	}
	
	public static void setBoolean(String id, boolean value)
	{
		BOOLEAN_SETTINGS.put(id, value);
	}
	
	public static void setDouble(String id, double value)
	{
		DOUBLE_SETTINGS.put(id, value);
	}
	
	public static void setPerEntrySetting(String data)
	{
		String[] entries = data.split("\\+");
		for (String rawEntry : entries)
		{
			String[] entry = rawEntry.split("=");
			entry[0] = entry[0].toLowerCase().replace("_", "-");
			String[] entrySettings = entry[1].split("\\|");
			for(String setting : entrySettings)
			{
				String[] segments = setting.split("[#:]");
				switch(segments[0])
				{
					case "E" -> setEnum(segments[1], EnumSetting.deserialize(segments[2], segments[1]));
					case "B" -> setBoolean(segments[1], Boolean.parseBoolean(segments[2]));
					case "D" -> setDouble(segments[1], Double.parseDouble(segments[2]));
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E getEnum(String id, Class<E> enumClass)
	{
		return (E)ENUM_SETTINGS.getOrDefault(id, null);
	}
	
	public static boolean getBoolean(String id)
	{
		return BOOLEAN_SETTINGS.getOrDefault(id, false);
	}
	
	public static double getDouble(String id)
	{
		return DOUBLE_SETTINGS.getOrDefault(id, 0.0);
	}
	
	public static <E extends Enum<E>> E cycleEnum(String id, Class<E> typeClass) {
		E[] values = typeClass.getEnumConstants();
		E currentValue = getEnum(id, typeClass);
		E newValue = values[(currentValue.ordinal() + 1) % values.length];
		setEnum(id, newValue);
		return newValue;
	}
}
