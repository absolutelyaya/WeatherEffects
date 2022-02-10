package weathereffects.weathereffects.settings;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Objects;

public class YayButtonList extends ButtonListWidget
{
	private ButtonEntry hoveredEntry;
	
	public YayButtonList(MinecraftClient minecraftClient, int i, int j, int k, int l, int m)
	{
		super(minecraftClient, i, j, k, l, m);
	}
	
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
	{
		super.render(matrices, mouseX, mouseY, delta);
		this.hoveredEntry = this.isMouseOver(mouseX, mouseY) ? this.getEntryAtPosition(mouseX, mouseY) : null;
	}
	
	@Override
	protected void renderList(MatrixStack matrices, int x, int y, int mouseX, int mouseY, float delta)
	{
		int i = this.getEntryCount();
		for (int index = 0; index < i; ++index)
		{
			int left = this.getRowLeft();
			int entryTop = this.getRowTop(index);
			int entryBottom = entryTop + this.itemHeight;
			if (entryBottom < this.top || entryTop > this.bottom) continue;
			float fade = 10f;
			float alpha;
			float f;
			if((f = (float)Math.abs(this.top - entryBottom) / fade) < 1f)
				alpha = Math.max(f, 0.025f);
			else if((f = (float)Math.abs(this.bottom - entryTop) / fade) < 1f)
				alpha = Math.max(f, 0.025f);
			else
				alpha = 1f;
			int entryHeight = this.itemHeight - 4;
			ButtonEntry entry = this.getEntry(index);
			int entryWidth = this.getRowWidth();
			for (Element e : entry.children())
			{
				if(e instanceof ClickableWidget)
				{
					((ClickableWidget)e).setAlpha(alpha);
				}
			}
			mouseX = (mouseX > this.left && mouseX < this.right) ? mouseX : -1;
			mouseY = (mouseY > this.top && mouseY < this.bottom) ? mouseY : -1;
			entry.render(matrices, index, entryTop, left, entryWidth, entryHeight, mouseX, mouseY,
					Objects.equals(this.hoveredEntry, entry), delta);
		}
	}
}
