package weathereffects.weathereffects.utilities;

import weathereffects.weathereffects.WeatherEffects;

public class TranslationUtil
{
	public static String getTranslationKey(String key, String id)
	{
		return key + "." + WeatherEffects.MODID + "." + id;
	}
}
