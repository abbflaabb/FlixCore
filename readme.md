# FlixCore Plugin

**Plugin by:** Abbas  
**Support:** Discord `alis283sf`  
**Version:** Beta 1.0b

---

## 📌 Features

### 🔹 Commands
- `/gmc` – Switch to Creative Mode
- `/gms` – Switch to Survival Mode
- `/heal` – Heal yourself
- `/backbed` – Teleport back to your bed
- `/help` – Show help menu
- `/food` – Restore hunger
- `/farte` – Fun command 🎉

---

### 🔹 Events
1. **PlayerJoinEvent** → Custom join message & show IP
2. **KickEvent** → Custom kick message
3. **LeaveBedEvent** → Custom message + deal 1.5 hearts damage
4. **PlayerQuitEvent** → Custom leave message
5. **BlockBreakEvent** → Custom message & sound (only OP can break)
6. **BottleExpEvent** → Custom sound & effect
7. **ShearSheepEvent** → Custom message (only sheep entities can be sheared)
8. **PlayerAchievementEvent** → Disable achievements
9. **EntityDamageEvent** → Enabled by default (true)
10. **PlayerDropItemEvent** → Cancel drop & custom message (via `EventListener`)
11. **BedEnterEvent** → Custom message + cancelled (no one can sleep)

---

### 🔹 Chat Format
Custom player chat format:  
&7[&a{Player}&7]: &f{Message}

---

### 🔹 CommandPreprocessEvent
Block usage of:
- `/op`
- `/?`
- `/pl`
- `/plugins`
- `/bukkit:plugins`
- etc.

---

### 🔹 Permissions
- `myplugin.bypass` → Bypass restrictions
- `Plugin.Admin` → Admin commands

---

### ⏳ Cooldown
- Countdown support for **all commands**

---

## ⚡ Notes
- This plugin is still in **Beta (1.0b)**
- Future updates will include:
    - More customization
    - Better performance optimizations
    - Extra admin & fun features  
