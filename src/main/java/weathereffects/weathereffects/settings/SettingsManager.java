package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import weathereffects.weathereffects.WeatherEffects;

import java.io.*;

@Environment(EnvType.CLIENT)
public class SettingsManager
{
	private static File file;
	
	private static void prepareConfigFile() {
		if (file != null) {
			return;
		}
		file = new File(FabricLoader.getInstance().getConfigDir().toFile(), WeatherEffects.MODID + ".txt");
	}
	
	public static void load()
	{
		prepareConfigFile();
		
		Settings.applyDefaults();
		
		try
		{
			if(!file.exists())
				save();
			if(file.exists())
			{
				BufferedReader br = new BufferedReader(new FileReader(file));
				for (String line : br.lines().toList())
				{
					if(line.contains("#") && line.contains(":"))
					{
						String[] type = line.split("#");
						String[] id = type[1].split(":");
						String value = id[1];
						switch(type[0])
						{
							case "E" -> SettingsStorage.setEnum(id[0], EnumSetting.deserialize(value, id[0]));
							case "B" -> SettingsStorage.setBoolean(id[0], Boolean.parseBoolean(value));
							case "D" -> SettingsStorage.setDouble(id[0], Double.parseDouble(value));
						}
					}
				}
				br.close();
			}
		}
		catch (IOException e)
		{
			System.err.println("Failed to load Weather Effects settings file. Reverting to defaults");
			e.printStackTrace();
		}
	}
	
	public static void save()
	{
		prepareConfigFile();
		
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for(Settings.Category cat : Settings.Category.values())
			{
				if(Settings.SETTINGS.get(cat) != null)
					for (AbstractSetting setting : Settings.SETTINGS.get(cat))
					{
						bw.write(setting.serialize());
						bw.newLine();
					}
			}
			bw.close();
		}
		catch (IOException e)
		{
			System.err.println("Failed to save Weather Effects settings file.");
			e.printStackTrace();
		}
	}
}
