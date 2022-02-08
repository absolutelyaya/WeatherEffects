package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import weathereffects.weathereffects.WeatherEffects;

import java.io.*;
import java.util.List;

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
				List<String> lines = br.lines().toList();
				for (int i = 0; i < lines.size(); i++)
				{
					String line = lines.get(i);
					if(line.contains("#") && line.contains(":") && !line.startsWith("\t"))
					{
						String[] segments = line.split("[#:]");
						if(segments.length == 3)
						{
							switch (segments[0])
							{
								case "E" -> SettingsStorage.setEnum(segments[1], EnumSetting.deserialize(segments[2], segments[1]));
								case "B" -> SettingsStorage.setBoolean(segments[1], Boolean.parseBoolean(segments[2]));
								case "D" -> SettingsStorage.setDouble(segments[1], Double.parseDouble(segments[2]));
								case "PES" -> {
									StringBuilder data = new StringBuilder();
									for (int ii = i + 1; ii < i + 1 + Integer.parseInt(segments[2]); ii++)
									{
										data.append(lines.get(ii)
												.replace("\t\t", data.toString().endsWith("=") ? "" : "|")
												.replace("\t", i + 1 == ii ? "" : "+"));
									}
									SettingsStorage.setPerEntrySetting(data.toString());
								}
							}
						}
					}
				}
				br.close();
			}
		}
		catch (Exception e)
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
