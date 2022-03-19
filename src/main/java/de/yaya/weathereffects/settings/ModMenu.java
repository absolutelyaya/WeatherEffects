package de.yaya.weathereffects.settings;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.yaya.weathereffects.screens.settings.MainSettingsScreen;

public class ModMenu implements ModMenuApi
{
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory()
    {
        return MainSettingsScreen::new;
    }
}
