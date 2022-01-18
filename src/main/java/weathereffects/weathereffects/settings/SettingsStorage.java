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
	
	@SuppressWarnings("unchecked")
	public static <E extends Enum<E>> E getEnum(String id, Class<E> enumClass)
	{
		return (E)ENUM_SETTINGS.get(id);
	}
	
	public static boolean getBoolean(String id)
	{
		return BOOLEAN_SETTINGS.get(id);
	}
	
	public static double getDouble(String id)
	{
		return DOUBLE_SETTINGS.get(id);
	}
	
	public static <E extends Enum<E>> E cycleEnum(String id, Class<E> typeClass) {
		E[] values = typeClass.getEnumConstants();
		E currentValue = getEnum(id, typeClass);
		E newValue = values[(currentValue.ordinal() + 1) % values.length];
		setEnum(id, newValue);
		return newValue;
	}
}
