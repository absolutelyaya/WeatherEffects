package weathereffects.weathereffects.settings;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import weathereffects.weathereffects.accessors.ClickableWidgetAccessor;
import weathereffects.weathereffects.screens.widgets.YaySliderWidget;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class YaySlider extends DoubleOption
{
	private final Function<MinecraftClient, List<OrderedText>> tooltipsGetter;
	private final List<BooleanSetting> requirements;
	private final SliderSetting softMin, softMax;
	private final Supplier<Double> defaultSupplier;
	
	public YaySlider(String key, double min, double max, float step, Function<GameOptions, Double> getter,
					 BiConsumer<GameOptions, Double> setter, List<BooleanSetting> requirements,
					 BiFunction<GameOptions, DoubleOption, Text> displayStringGetter, SliderSetting softMin,
					 SliderSetting softMax, Supplier<Double> defaultSupplier)
	{
		super(key, min, max, step, getter, setter, displayStringGetter);
		this.requirements = requirements;
		this.tooltipsGetter = client -> ImmutableList.of();
		this.softMin = softMin;
		this.softMax = softMax;
		this.defaultSupplier = defaultSupplier;
	}
	
	@Override
	public ClickableWidget createButton(GameOptions options, int x, int y, int width) {
		List<OrderedText> list = tooltipsGetter.apply(MinecraftClient.getInstance());
		ClickableWidget slider = new YaySliderWidget(options, x, y, width, 20, this, list, softMin, softMax, defaultSupplier);
		((ClickableWidgetAccessor)slider).setRequirements(requirements);
		return slider;
	}
	
	public double getStep()
	{
		return step;
	}
}
