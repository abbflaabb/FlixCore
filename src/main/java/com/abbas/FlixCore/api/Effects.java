package com.abbas.FlixCore.api;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@Getter
public enum Effects {
    LevelUp(Sound.LEVEL_UP),
    Explode(Sound.EXPLODE);

    private final Sound sound;

    Effects(Sound sound) {
        this.sound = sound;
    }

    //How Programmer Run this code option one
    public void play(Player player, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
    //Play a sound in any location's
    public void play(Location loc, float volume, float pitch) {
        loc.getWorld().playSound(loc,sound,volume,pitch);
    }
}

