package weathereffects.weathereffects.settings;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.Option;
import weathereffects.weathereffects.accessors.ClickableWidgetAccessor;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class YayCycler extends Option
{
	private final CyclingOption.Setter<Boolean> setter;
	private final Function<GameOptions, Boolean> getter;
	private final Supplier<CyclingButtonWidget.Builder<Boolean>> buttonBuilderFactory;
	private final List<BooleanSetting> requirements;
	
	private Function<MinecraftClient, CyclingButtonWidget.TooltipFactory<Boolean>> tooltips = client -> value -> ImmutableList.of();
	
	public YayCycler(String key, Function<GameOptions, Boolean> getter, CyclingOption.Setter<Boolean> setter, Supplier<CyclingButtonWidget.Builder<Boolean>> buttonBuilderFactory, List<BooleanSetting> requirements)
	{
		super(key);
		this.setter = setter;
		this.getter = getter;
		this.buttonBuilderFactory = buttonBuilderFactory;
		this.requirements = requirements;
	}
	
	@Override
	public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
		CyclingButtonWidget.TooltipFactory<Boolean> tooltipFactory = this.tooltips.apply(MinecraftClient.getInstance());
		ClickableWidget button = this.buttonBuilderFactory.get().tooltip(tooltipFactory).initially(this.getter.apply(options)).build(x, y, width, 20, this.getDisplayPrefix(), (b, value) -> {
			this.setter.accept(options, this, value);
			options.write();
		});
		((ClickableWidgetAccessor)button).setRequirements(requirements);
		return button;
	}
}