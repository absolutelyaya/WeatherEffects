package weathereffects.weathereffects.settings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import weathereffects.weathereffects.screens.settings.MainSettingsScreen;

public class ModMenu implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return MainSettingsScreen::new;
    }
}
