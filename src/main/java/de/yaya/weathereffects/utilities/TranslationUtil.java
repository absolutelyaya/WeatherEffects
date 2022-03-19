package de.yaya.weathereffects.utilities;

import de.yaya.weathereffects.WeatherEffects;

public class TranslationUtil
{
	public static String getTranslationKey(String key, String id)
	{
		return key + "." + WeatherEffects.MODID + "." + id;
	}
}
