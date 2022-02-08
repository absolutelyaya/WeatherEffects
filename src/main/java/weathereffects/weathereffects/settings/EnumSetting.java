package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class EnumSetting<E extends Enum<E>> extends AbstractSetting
{
	private final E defaultValue;
	private final Class<E> enumClass;
	
	public EnumSetting(String id, E defaultValue)
	{
		super(id);
		this.enumClass = defaultValue.getDeclaringClass();
		this.defaultValue = defaultValue;
		SettingsStorage.setEnum(id, defaultValue);
	}
	
	public EnumSetting(String id, E defaultValue, String name)
	{
		super(id, name);
		this.enumClass = defaultValue.getDeclaringClass();
		this.defaultValue = defaultValue;
	}
	
	@Override
	public Text getButtonText()
	{
		return new TranslatableText(translationKey, SettingsStorage.getEnum(id, enumClass).name());
	}
	
	public String getDefaultValue()
	{
		return defaultValue.name();
	}
	
	@Override
	public String serialize() {
		return "E#" + id + ":" + enumClass.getName() + "@" + SettingsStorage.getEnum(id, enumClass).toString();
	}
	
	@Override
	public String serialize(String prefix) {
		return "E#" + prefix + "." + id + ":" + enumClass.getName() + "@" + SettingsStorage.getEnum(prefix + "." + id, enumClass).toString();
	}
	
	public static Enum<?> deserialize(String data, String id)
	{
		String[] parts = data.split("@");
		try
		{
			return Enum.valueOf(Class.forName(parts[0]).asSubclass(Enum.class), parts[1]);
		}
		catch(ClassNotFoundException e)
		{
			System.err.println("Failed to load enum setting '" + id + "' because a class wasn't found. Setting reverted to default");
			e.printStackTrace();
		}
		return null;
	}
	
	private static <E extends Enum<E>> Text getValueText(EnumSetting<?> option, E value)
	{
		return new TranslatableText(option.translationKey + "." + value.name().toLowerCase());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Option asOption()
	{
		return CyclingOption.create(translationKey, defaultValue.getClass().getEnumConstants(),
				value -> getValueText(this, value), ignored -> SettingsStorage.getEnum(id, enumClass),
				(ignored, option, value) -> SettingsStorage.cycleEnum(id, enumClass));
	}
	
	public Class<? extends Enum> getEnumClass()
	{
		return enumClass;
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new EnumSetting<>(prefix + "." + id, defaultValue, id);
	}
}
