package com.jih10157.syncadvancement

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Listener {
    override fun onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this)
    }

    @EventHandler
    fun PlayerAdvancementCriterionGrantEvent.sync() {
        for (p in Bukkit.getOnlinePlayers())
            p.getAdvancementProgress(this.advancement).awardCriteria(this.criterion)
    }

    @EventHandler
    fun PlayerJoinEvent.sync() {
        for (p1 in Bukkit.getOnlinePlayers()) {
            p1.sync(this.player)
            this.player.sync(p1)
            break
        }
    }

    private fun Player.sync(player: Player) {
        for(advancement in Bukkit.advancementIterator()) {
            val progress = player.getAdvancementProgress(advancement)
            val progress2 = this.getAdvancementProgress(advancement)
            for (criteria in progress.awardedCriteria) {
                progress2.awardCriteria(criteria)
            }
        }
    }
}