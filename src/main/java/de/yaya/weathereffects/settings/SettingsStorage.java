package de.yaya.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class SettingsStorage
{
	public static final Map<String, int[]> CHOICE_SETTINGS = new HashMap<>();
	public static final Map<String, Boolean> BOOLEAN_SETTINGS = new HashMap<>();
	public static final Map<String, Double> DOUBLE_SETTINGS = new HashMap<>();
	
	public static void setChoice(String id, int choice, int options)
	{
		CHOICE_SETTINGS.put(id, new int[] {choice, options});
	}
	
	public static void setBoolean(String id, boolean value)
	{
		if(Settings.PRESET != null)
			Settings.PRESET.setValueTo("custom");
		BOOLEAN_SETTINGS.put(id, value);
	}
	
	public static void setDouble(String id, double value)
	{
		if(Settings.PRESET != null)
			Settings.PRESET.setValueTo("custom");
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
					case "E" ->{
						int[] i = ChoiceSetting.deserialize(segments[2]);
						setChoice(segments[1], i[0], i[1]);
					}
					case "B" -> setBoolean(segments[1], Boolean.parseBoolean(segments[2]));
					case "D" -> setDouble(segments[1], Double.parseDouble(segments[2]));
				}
			}
		}
	}
	
	public static int[] getChoice(String id)
	{
		return CHOICE_SETTINGS.getOrDefault(id, null);
	}
	
	public static boolean getBoolean(String id)
	{
		return BOOLEAN_SETTINGS.getOrDefault(id, false);
	}
	
	public static double getDouble(String id)
	{
		return DOUBLE_SETTINGS.getOrDefault(id, 0.0);
	}
}
