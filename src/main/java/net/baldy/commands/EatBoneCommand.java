package net.baldy.commands;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

import net.baldy.BoneEaterPlugin;
import net.baldy.BoneEaterUtils;
import net.md_5.bungee.api.ChatColor;

public class EatBoneCommand implements CommandExecutor {
    public static ChatColor baldy_green = ChatColor.of("#00CB9B");

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(baldy_green + "§l[BoneEater] §r§cYou must be a puppy to use this command!");
            return true;
        }

        BoneEaterUtils.UtilsPlayer target = BoneEaterUtils.get_player(commandSender, args, 0);
        if (target.player == null) {
            commandSender.sendMessage(baldy_green + "§l[BoneEater] §r§cPlayer '" + baldy_green + target.target_player_name + "§c' not found!");
            return true;
        }
    

        PlayerInventory inventory = target.player.getInventory();
        ItemStack bone_item = null;
        if (BoneEaterPlugin.config.getBoolean("survival_mode")) {
            for (ItemStack item : inventory.getContents()) {
                if (item != null) {
                    if (item.getType() == Material.BONE) {
                        bone_item = item;
                        break;
                    }
                }
            }

            if (bone_item == null) {
                String message = baldy_green + "§l[BoneEater] §r§c";

                if (target.is_self) {
                    message = message + "Your inventory has no bone, get a bone and try again!";
                }
                else {
                    message = message + "Player '" + baldy_green + target.target_player_name + "§c' has no bone in their inventory!";
                }

                commandSender.sendMessage(message);
                return true;
            }
        }
        else {
            bone_item = new ItemStack(Material.BONE, 1);
        }

        if (inventory.getHelmet() != null) {
            ItemStack helmet = inventory.getHelmet();

            if (helmet.getType() == Material.BONE) {
                String message = baldy_green + "§l[BoneEater] §r§e";

                if (target.is_self) {
                    message = message + "You already have a bone!";
                }
                else {
                    message = message + "Player: '" + baldy_green + target.target_player_name + "§e' already has a bone!";
                }

                commandSender.sendMessage(message);
                return true;
            }

            HashMap<Integer, ItemStack> leftovers = inventory.addItem(helmet);
            if (!leftovers.isEmpty()) {
                target.player.sendMessage(baldy_green + "§l[BoneEater] §r§eYour inventory is full! Your helmet was dropped");
                target.player.getWorld().dropItem(target.player.getLocation().add(0, 3, 0), helmet);
            }

            helmet.subtract();
        }


        inventory.setHelmet(bone_item.asOne());
        bone_item.subtract();

        if (target.is_self) {
            commandSender.sendMessage(baldy_green + "§l[BoneEater] §rBone added! Bark!!");
        }
        else {
            commandSender.sendMessage(baldy_green + "§l[BoneEater] §rBone added to '" + baldy_green + target.target_player_name + "§r'!");
            target.player.sendMessage(baldy_green + "§l[BoneEater] §r" + baldy_green + commandSender.getName() + "§r Added a bone to you! Bark!!");
        }

        return true;
    }
}
