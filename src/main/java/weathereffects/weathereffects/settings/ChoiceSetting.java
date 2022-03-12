package weathereffects.weathereffects.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.Option;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ChoiceSetting extends AbstractSetting
{
	private List<String> options;
	
	public ChoiceSetting(String id, List<String> options, boolean setDefault)
	{
		super(id, setDefault);
		this.options = options;
		if(setDefault)
			setDefault();
	}
	
	public ChoiceSetting(String id, List<String> options, String name, boolean setDefault)
	{
		super(id, name, setDefault);
		this.options = options;
		if(setDefault)
			setDefault();
	}
	
	public void UpdateOptions(List<String> options)
	{
		this.options = options;
		SettingsStorage.setChoice(id, Math.min(SettingsStorage.getChoice(id)[0], options.size()), options.size());
	}
	
	@Override
	public Text getButtonText()
	{
		return new TranslatableText(translationKey, options.get(SettingsStorage.getChoice(id)[0]));
	}
	
	public String getOption() {
		return 0 + "/" + options.size();
	}
	
	@Override
	public void setDefault()
	{
		SettingsStorage.setChoice(id, 0, options.size());
	}
	
	@Override
	public void setDefault(String prefix)
	{
		SettingsStorage.setChoice(prefix + "." + id, 0, options.size());
	}
	
	@Override
	public String serialize() {
		return "E#" + id + ":" + SettingsStorage.getChoice(id)[0] + "/" + options.size();
	}
	
	@Override
	public String serialize(String prefix) {
		return "E#" + prefix + "." + id + ":" + SettingsStorage.getChoice(id)[0] + "/" + options.size();
	}
	
	public static int[] deserialize(String data)
	{
		String[] parts = data.split("/");
		return new int[] { Integer.parseInt(parts[0]), Integer.parseInt(parts[1]) };
	}
	
	@Override
	public Option asOption()
	{
		return new YayCycler<>(translationKey,
				ignored -> SettingsStorage.getChoice(id)[0],
				(ignored, option, value) -> SettingsStorage.setChoice(id, value, options.size()),
				this::choiceBuilder, requirements);
	}
	
	public CyclingButtonWidget.Builder<Integer> choiceBuilder() {
		return (new CyclingButtonWidget.Builder<Integer>(value -> Text.of(options.get(value % options.size())))).values(getValues());
	}
	
	private List<Integer> getValues()
	{
		List<Integer> result = new ArrayList<>();
		for (int i = 0; i < options.size(); i++)
			result.add(i);
		return result;
	}
	
	@Override
	public SettingsOption addIDPrefix(String prefix)
	{
		return new ChoiceSetting(prefix + "." + id, options, id, setDefault);
	}
}
