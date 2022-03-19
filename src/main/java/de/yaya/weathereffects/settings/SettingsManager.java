package de.yaya.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import de.yaya.weathereffects.WeatherEffects;

import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SettingsManager
{
	private static File file;
	
	private static void prepareConfigFile()
	{
		if (file != null)
			return;
		file = new File(FabricLoader.getInstance().getConfigDir().toFile(), WeatherEffects.MODID + "/settings.txt");
		
		try
		{
			ClassLoader cl = SettingsManager.class.getClassLoader();
			URL resource = cl.getResource("assets/" + WeatherEffects.MODID + "/presets");
			if(resource != null)
			{
				for(Path p : Files.walk(Paths.get(resource.toURI())).toList())
				{
					try
					{
						if(p.toFile().isFile())
						{
							Files.copy(p, FabricLoader.getInstance().getConfigDir().resolve(WeatherEffects.MODID + "/presets/" + p.getFileName()));
						}
					}
					catch(FileAlreadyExistsException ignored){}
				}
			}
			
			new File(FabricLoader.getInstance().getConfigDir().toFile(), WeatherEffects.MODID + "/presets").mkdirs();
			Files.copy(FabricLoader.getInstance().getConfigDir().resolve(WeatherEffects.MODID + "/presets/default.txt"), file.toPath());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void load()
	{
		prepareConfigFile();
		load(file);
	}
	
	public static void load(File file)
	{
		try
		{
			if(!SettingsManager.file.exists())
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
								case "E" -> {
									int[] ii = ChoiceSetting.deserialize(segments[2]);
									SettingsStorage.setChoice(segments[1], ii[0], ii[1]);
								}
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
			System.err.println("Failed to load Weather Effects settings.");
			e.printStackTrace();
			Settings.applyDefaults();
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
