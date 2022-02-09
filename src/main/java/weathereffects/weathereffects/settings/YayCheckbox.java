package weathereffects.weathereffects.settings;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class YayCheckbox extends CheckboxWidget
{
	boolean showBackground;
	
	public YayCheckbox(int x, int y, int width, int height, Text message, boolean checked, boolean showBackground)
	{
		super(x, y, width, height, message, checked);
		this.showBackground = showBackground;
	}
	
	public void setShowBackground(boolean showBackground)
	{
		this.showBackground = showBackground;
	}
	
	@Override
	public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		int x = this.x + 21;
		int y = this.y + (this.height - 8) / 2;
		int length = MinecraftClient.getInstance().textRenderer.getWidth(getMessage());
		if(showBackground)
			fill(matrices, x, y - 3, x + length + 6, y + 10, -1072689136);
		super.renderButton(matrices, mouseX, mouseY, delta);
	}
	
	@Override
	public Text getMessage()
	{
		return super.getMessage().copy().setStyle(Style.EMPTY.withStrikethrough(!active));
	}
}
