# FlixCore Plugin

**Plugin by:** Abbas
**Support:** Discord `alis283sf`
**Version:** [![](https://jitpack.io/v/abbflaabb/FlixCore.svg)](https://jitpack.io/#abbflaabb/FlixCore)

---

## 📌 Features

### 🔹 Commands

* `/gmc` – Switch to Creative Mode
* `/gms` – Switch to Survival Mode
* `/heal` – Heal yourself
* `/backbed` – Teleport back to your bed
* `/help` – Show help menu
* `/food` – Restore hunger
* `/farte` – Fun command 🎉
* `/fly` – Fly command
* `/menu` – Show game selector

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

```
&7[&a{Player}&7]: &f{Message}
```

---

### 🔹 CommandPreprocessEvent

Blocks usage of:

* `/op`
* `/?`
* `/pl`
* `/plugins`
* `/bukkit:plugins`
* etc.

---

### 🔹 Permissions

* `myplugin.bypass` → Bypass restrictions
* `Plugin.Admin` → Admin commands
* `  Fly.Commands` → for Fly
* ` Fly.command.others` for fly others

---

### ⏳ Cooldown

* Countdown support for **all commands**

---

## 📦 Maven Dependency

```xml
<dependency>
    <groupId>com.github.abbflaabb</groupId>
    <artifactId>FlixCore</artifactId>
    <version>Use Any version available</version>
</dependency>

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

---

## ⚡ Notes

* This plugin is still in **Release (1.2)**
* Future updates will include:

    * More customization
    * Better performance optimizations
    * Extra admin & fun features
